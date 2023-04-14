package com.retail.bargain.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.retail.bargain.config.RedisSonConfig;
import com.retail.bargain.constant.BargainConstant;
import com.retail.bargain.domain.BargainRecordEntity;
import com.retail.bargain.domain.UrlEntry;
import com.retail.bargain.feign.ShopFeignService;
import com.retail.bargain.mapper.BargainRecordMapper;
import com.retail.bargain.mapper.UrlMapper;
import com.retail.bargain.utils.DecUtil;
import com.retail.common.constant.TokenConstants;
import com.retail.common.domain.vo.*;
import com.retail.common.exception.BizException;
import com.retail.common.result.Result;
import com.retail.common.utils.JwtUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.retail.bargain.mapper.BargainMapper;
import com.retail.bargain.domain.BargainEntity;
import com.retail.bargain.service.BargainService;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;


@Service("bargainService")
public class  BargainServiceImpl extends ServiceImpl<BargainMapper, BargainEntity> implements BargainService {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private BargainRecordMapper bargainRecordMapper;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private RedisSonConfig redisSonConfig;

    @Autowired
    private ShopFeignService shopFeignService;

    @Autowired
    private UrlMapper urlMapper;

    @Override
    public List<BargainEntity> select() {
        List<BargainEntity> bargainEntityList = baseMapper.selectList(new QueryWrapper<BargainEntity>().lambda().eq(BargainEntity::getUserId,userInfo().getId()));
        return bargainEntityList;
    }

    @Override
    public Result bargainInsert(BargainEntity bargainEntity) {
        bargainEntity.setUserId(userInfo().getId());

        // 根据spuid 查询 库存是否足够
        Result<InventoryEntityVo>  inventoryEntityVoResult=shopFeignService.findByInventoryEntity(bargainEntity.getSpuId());
        InventoryEntityVo inventoryEntityVo = inventoryEntityVoResult.getData();
        if (inventoryEntityVo.getInventoryCount().equals(0)){
            return Result.error("库存不足");
        }
        bargainEntity.setStatus(0);
        long l = System.currentTimeMillis()+ 259200000;
        Date date = new Date(l);
        bargainEntity.setTotalExpirationTime(date);
        //  自动生成 百分比 需要人数
        baseMapper.insert(bargainEntity);

        return Result.success();
    }

    @Override
    public Result bargainUpdate(BargainEntity bargainEntity) {
        baseMapper.updateById(bargainEntity);
        return Result.success();

    }

    @Override
    public Result<BargainEntityVo> findByIdBargainEntity(Long id) {
        BargainEntity bargainEntity = baseMapper.selectOne(new QueryWrapper<BargainEntity>().lambda().eq(BargainEntity::getId, id));
        BargainEntityVo bargainEntityVo = new BargainEntityVo();
        BeanUtil.copyProperties(bargainEntity,bargainEntityVo);
        return Result.success(bargainEntityVo);
    }

    @Override
    public Result deleteBargainlEntity(Long id) {
        baseMapper.deleteById(id);
        return Result.success();
    }

    @Override
    public Result<String> findUrl(Long id) {
        // 加密 id

        String key = RandomUtil.randomString(8);
        System.out.println("使用的key:"+key);
        byte[] encodedBytes = new byte[0];
        try {
            encodedBytes = DecUtil.encrypt(id.toString().getBytes(StandardCharsets.UTF_8), key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String encodedString = Base64.getEncoder().encodeToString(encodedBytes);
        System.out.println("Encoded string: " + encodedString);
        // 通过路径 查询该活动
        UrlEntry selectOne = urlMapper.selectOne(new QueryWrapper<UrlEntry>().lambda().eq(UrlEntry::getUrl, encodedString));
        if (selectOne!=null){
            return Result.error();
        }
        UrlEntry urlEntry = new UrlEntry();
        urlEntry.setUrl(encodedString);
        urlEntry.setUrlKey(key);
        urlEntry.setUrlStatus(0);
        urlMapper.insert(urlEntry);
        String url= "http://192.168.231.1:9205/bargain/bargain/updateInsertBargain/"+encodedString;
        return Result.success(url);
    }

    /**
     *  砍价
     * @param id
     * @param url
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result updateInsertBargain(Long id, String url) {
        //加锁
        RedissonClient redissonClient = redisSonConfig.redissonClient();
        RLock lock = redissonClient.getLock(BargainConstant.REDISSION_LOCK + userInfo().getId());
        boolean flag = lock.tryLock();
        if (!flag){
            throw new BizException(401,"正在砍价请稍后");
        }
        lock.lock(3, TimeUnit.SECONDS);
        try {
        //通过id查询  活动
        BargainEntity bargainEntity = baseMapper.selectOne(new QueryWrapper<BargainEntity>().lambda().eq(BargainEntity::getId, id));
        // 判断是否存在
        if (bargainEntity==null){
            return Result.error("该活动不存在");
        }
        // 判断两个状态
        if (!bargainEntity.getBargainStatus().equals(0)){
            return Result.error("砍价完成了");
        }
        if (!bargainEntity.getStatus().equals(0)){
            return Result.error("该活动结束了");
        }
        // 判断活动时间 >当前时间  修改 活动状态 砍价状态
        long time = bargainEntity.getTotalExpirationTime().getTime();
        long l = System.currentTimeMillis();
        if (time<l){
            bargainEntity.setBargainStatus(1);
            bargainEntity.setStatus(1);
            baseMapper.update(bargainEntity,new QueryWrapper<BargainEntity>().lambda().eq(BargainEntity::getId,id));
            return Result.error("该活动过期了");
        }
            // 根据spuId 查询sku表 获取价格
            Result<SkuEntityVo> skuEntryResult = shopFeignService.findBySkuEntry(bargainEntity.getSpuId());
            SkuEntityVo skuEntityVo = skuEntryResult.getData();
            // 判断  剩余百分比==0 成功砍价人数==需要人数   时  sku 添加销量  锁定库存库存
            if (bargainEntity.getNeedNumberPeople().equals(bargainEntity.getBargainNumberPeople()) && bargainEntity.getNeedNumberPeople().equals(bargainEntity.getBargainNumberPeople())){
                // sku 添加销量
                skuEntityVo.setSkuSell(skuEntityVo.getSkuSell()+1);
                shopFeignService.updateSkuSell(skuEntityVo);
                //  锁定库存库存
                Result<InventoryEntityVo>  inventoryEntityVoResult=shopFeignService.findByInventoryEntity(bargainEntity.getSpuId());
                InventoryEntityVo inventoryEntityVo = inventoryEntityVoResult.getData();
                inventoryEntityVo.setInventoryLock(inventoryEntityVo.getInventoryLock()+1);
                shopFeignService.updateInventoryLock(inventoryEntityVo);
                // 删除砍价地址表  逻辑删除
                UrlEntry urlEntry = urlMapper.selectOne(new QueryWrapper<UrlEntry>().lambda().eq(UrlEntry::getUrl, url));
                urlEntry.setUrlStatus(1);
                urlMapper.update(urlEntry,new QueryWrapper<UrlEntry>().lambda().eq(UrlEntry::getUrl,url));
                //
                bargainEntity.setBargainStatus(2);
                baseMapper.update(bargainEntity,new QueryWrapper<BargainEntity>().lambda().eq(BargainEntity::getId,id));
            }
        //需要人数 == 成功砍价人数 是否相同  <
        if (bargainEntity.getNeedNumberPeople().equals(bargainEntity.getBargainNumberPeople())){
            return Result.error("该砍价活动被人砍完了");
        }
        // 判断剩余百分比 是否等于0
        if (bargainEntity.getBargainUnfinished().equals(0)){
            bargainEntity.setBargainStatus(2);
            bargainEntity.setStatus(1);
            baseMapper.update(bargainEntity,new QueryWrapper<BargainEntity>().lambda().eq(BargainEntity::getId,id));
            return Result.error("该砍价活动被人砍完了   剩余百分比0");
        }
        // 判断本人是否拼团过
            Long userId = bargainEntity.getUserId();
            Long userIdLogin = userInfo().getId();
            if (userId.equals(userIdLogin)){
                return Result.error("你不能砍价");
            }

            BargainRecordEntity entity = bargainRecordMapper.selectOne(new QueryWrapper<BargainRecordEntity>().lambda().eq(BargainRecordEntity::getBargainId,id).eq(BargainRecordEntity::getUserId, userIdLogin));
            if (entity!=null){
                return Result.error("您已经助力过了,该商品了");
            }

            /**
             *  添加砍价记录表
             */
            BargainRecordEntity bargainRecordEntity = new BargainRecordEntity();
            //bargainId
            bargainRecordEntity.setBargainId(id);
            //userId
            bargainRecordEntity.setUserId(userIdLogin);
            //bargainOrderAccomplish
            bargainRecordEntity.setBargainOrderAccomplish(BigDecimal.valueOf(0.01));
            //bargainOrderPrice
            BigDecimal skuPrice = skuEntityVo.getSkuPrice();
            bargainRecordEntity.setBargainOrderPrice(skuPrice.multiply(BigDecimal.valueOf(0.01)));
            //bargainOrderTime
            bargainRecordEntity.setBargainOrderTime(new Date());
            //status
            bargainRecordEntity.setStatus(0);
            bargainRecordMapper.insert(bargainRecordEntity);


            /**
             *  砍价成功做的修改
             */
            //bargainAccomplish   已砍百分比
            bargainEntity.setBargainAccomplish(bargainEntity.getBargainAccomplish().add(BigDecimal.valueOf(0.01)));
            //bargainUnfinished  剩余百分比
            bargainEntity.setBargainUnfinished(bargainEntity.getBargainUnfinished().subtract(BigDecimal.valueOf(0.01)));
            //bargainNumberPeople  成功砍价人数
            bargainEntity.setBargainNumberPeople(bargainEntity.getBargainNumberPeople()+1);

            baseMapper.update(bargainEntity,new QueryWrapper<BargainEntity>().lambda().eq(BargainEntity::getId, id));


            return Result.success("拼团成功");
        }catch (Exception e){
            lock.forceUnlock();
            e.printStackTrace();
        }
        return Result.error("等会别急");
    }

    public UserEntityVo userInfo(){
        String token = request.getHeader("token");
        String userKey = JwtUtils.getUserKey(token);
        String s = redisTemplate.opsForValue().get(TokenConstants.LOGIN_TOKEN_KEY + userKey);
        UserEntityVo user = JSON.parseObject(s, UserEntityVo.class);
        return user;
    }


}
