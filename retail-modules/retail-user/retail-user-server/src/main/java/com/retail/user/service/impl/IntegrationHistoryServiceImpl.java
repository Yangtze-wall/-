package com.retail.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.retail.common.constant.TokenConstants;
import com.retail.common.domain.vo.IntegrationHistoryEntityVo;
import com.retail.common.domain.vo.UserEntityVo;
import com.retail.common.result.Result;
import com.retail.common.utils.JwtUtils;
import com.retail.user.domain.UserEntity;
import com.retail.user.mapper.UserMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.retail.user.mapper.IntegrationHistoryMapper;
import com.retail.user.domain.IntegrationHistoryEntity;
import com.retail.user.service.IntegrationHistoryService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


@Service("integrationHistoryService")
@Log4j2
public class IntegrationHistoryServiceImpl extends ServiceImpl<IntegrationHistoryMapper, IntegrationHistoryEntity> implements IntegrationHistoryService {

    @Autowired
    private IntegrationHistoryMapper integrationHistoryMapper;

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Autowired
    private UserMapper userMapper;

    @Override
    public Result insertIntegrationBySign(IntegrationHistoryEntity integrationHistoryEntity) {
        //获取用户userId
        Long userId = userInfo().getId();
        if (userId==null){
            return Result.error("获取用户信息失败");
        }
        //根据用户id 查询用户表数据
        UserEntity userEntity = userMapper.selectById(userId);
        //积分记录表 userId赋值
        integrationHistoryEntity.setUserId(userId);

        //当前天的年月日时分秒（例：2023-04-03 12:00:00）
        Date date = DateUtil.date();
        log.info("当前天的年月日时分秒"+date);
        //当前天的开始时间（例：2023-04-03 00:00:00）
        Date beginOfDay = DateUtil.beginOfDay(date);
        log.info("当前天的开始时间"+beginOfDay);
        //日期时间偏移(往前推7天)（例：2023-03-27 00:00:00）
        Date startTime = DateUtil.offset(beginOfDay, DateField.DAY_OF_MONTH, -7);
        log.info("日期时间偏移(往前推7天)"+startTime);
        //当前天的结束时间（例：2023-04-03 23:59:59）
        Date endTime = DateUtil.endOfDay(date);
        log.info("当前天的结束时间"+endTime);

        //一天只能签到一次
        List<IntegrationHistoryEntity> integrationHistoryEntityList1 =
                selectSign(beginOfDay, endTime, userId);
        //今天已经签到过了 ，不需要再次签到了
        if(integrationHistoryEntityList1!=null && integrationHistoryEntityList1.size()>=1){
            return Result.error("用户今天已经签到过了，不需要再次签到了");
        }
        //查询用户id在区间范围内 用户签到记录
        List<IntegrationHistoryEntity> integrationHistoryEntityList =
                selectSign(startTime, endTime, userId);
        // 判断 连续签到7天后 再次签到送积分70分连签奖励
        if (integrationHistoryEntityList!=null && integrationHistoryEntityList.size()==7){
            userEntity.setIntegration(userEntity.getIntegration()+70);
            //修改用户表中积分数
            userMapper.update(userEntity,new UpdateWrapper<UserEntity>().lambda().eq(UserEntity::getId,userId));
            //积分记录表 赋值
            //来源类型（1签到，2购买，3兑换优惠券）
            integrationHistoryEntity.setSourceType(1);
            integrationHistoryEntity.setCreateTime(new Date());
            integrationHistoryEntity.setRemark("连续7天签到送积分70");
            integrationHistoryEntity.setCount(70);
        }else {
            //没有连签7天，用户签到 在用户积分的基础上加签到积分10
            userEntity.setIntegration(userEntity.getIntegration()+10);
            userMapper.update(userEntity,new UpdateWrapper<UserEntity>().lambda().eq(UserEntity::getId,userEntity.getId()));
            //积分记录表 赋值
            //来源类型（1签到，2购买，3兑换优惠券）
            integrationHistoryEntity.setSourceType(1);
            integrationHistoryEntity.setCreateTime(new Date());
            integrationHistoryEntity.setRemark("不满7天连续签到，签到积分10");
            integrationHistoryEntity.setCount(10);
        }
        this.baseMapper.insert(integrationHistoryEntity);
        return Result.success();
    }

    private List<IntegrationHistoryEntity> selectSign(Date startTime, Date endTime, Long userId) {
        List<IntegrationHistoryEntity> integrationHistoryEntityList =integrationHistoryMapper.selectSign(startTime,endTime,userId);

        return integrationHistoryEntityList;
    }

    public UserEntityVo userInfo(){
        String token = request.getHeader("token");
        String userKey = JwtUtils.getUserKey(token);
        String s = redisTemplate.opsForValue().get(TokenConstants.LOGIN_TOKEN_KEY + userKey);
        UserEntityVo user = JSON.parseObject(s, UserEntityVo.class);
        return user;
    }


    @Resource
    private RedisTemplate myRedisTemplate;

    /**
     * 用户签到，可以补签
     *
     * @param userId  用户ID
     * @param dateStr 查询的日期，默认当天 yyyy-MM-dd
     * @return 连续签到次数和总签到次数
     */
    @Override
    public Map<String, Object> doSign(Long userId, String dateStr) {
        Map<String, Object> result = new HashMap<>();
        // 获取日期
        Date date = getDate(dateStr);
        // 获取日期对应的天数，多少号
        int day = DateUtil.dayOfMonth(date) - 1; // 从 0 开始
        // 构建 Redis Key
        String signKey = buildSignKey(userId, date);
        // 查看指定日期是否已签到
        boolean isSigned = myRedisTemplate.opsForValue().getBit(signKey, day);
        if (isSigned) {
            result.put("message", "当前日期已完成签到，无需再签");
            result.put("code", 400);
            return result;
        }
        // 签到
        myRedisTemplate.opsForValue().setBit(signKey, day, true);
        // 根据当前日期统计签到次数
        Date today = new Date();
        // 统计连续签到次数
        int continuous = getContinuousSignCount(userId, today);
        // 统计总签到次数
        long count = getSumSignCount(userId, today);
        result.put("message", "签到成功");
        result.put("code", 200);
        result.put("continuous", continuous);
        result.put("count", count);
        return result;
    }

    @Override
    public Result insertIntegrationByOrder(IntegrationHistoryEntityVo integrationHistoryEntityVo) {
        IntegrationHistoryEntity integrationHistoryEntity = new IntegrationHistoryEntity();
        BeanUtil.copyProperties(integrationHistoryEntityVo,integrationHistoryEntity);
        this.baseMapper.insert(integrationHistoryEntity);
        return Result.success();
    }

    /**
     * 统计连续签到次数
     *
     * @param userId 用户ID
     * @param date   查询的日期
     * @return
     */
    private int getContinuousSignCount(Long userId, Date date) {
        // 获取日期对应的天数，多少号，假设是 31
        int dayOfMonth = DateUtil.dayOfMonth(date);
        // 构建 Redis Key
        String signKey = buildSignKey(userId, date);
        // e.g. bitfield user:sign:5:202103 u31 0
        BitFieldSubCommands bitFieldSubCommands = BitFieldSubCommands.create()
                .get(BitFieldSubCommands.BitFieldType.unsigned(dayOfMonth))
                .valueAt(0);
        // 获取用户从当前日期开始到 1 号的所有签到状态
        List<Long> list = myRedisTemplate.opsForValue().bitField(signKey, bitFieldSubCommands);
        if (list == null || list.isEmpty()) {
            return 0;
        }
        // 连续签到计数器
        int signCount = 0;
        long v = list.get(0) == null ? 0 : list.get(0);
        // 位移计算连续签到次数
        for (int i = dayOfMonth; i > 0; i--) {// i 表示位移操作次数
            // 右移再左移，如果等于自己说明最低位是 0，表示未签到
            if (v >> 1 << 1 == v) {
                // 用户可能当前还未签到，所以要排除是否是当天的可能性
                // 低位 0 且非当天说明连续签到中断了
                if (i != dayOfMonth) {
                    break;
                }
            } else {
                // 右移再左移，如果不等于自己说明最低位是 1，表示签到
                signCount++;
            }
            // 右移一位并重新赋值，相当于把最低位丢弃一位然后重新计算
            v >>= 1;
        }
        return signCount;
    }

    /**
     * 统计总签到次数
     *
     * @param userId 用户ID
     * @param date   查询的日期
     * @return
     */
    private Long getSumSignCount(Long userId, Date date) {
        // 构建 Redis Key
        String signKey = buildSignKey(userId, date);
        // e.g. BITCOUNT user:sign:5:202103
        return (Long) myRedisTemplate.execute(
                (RedisCallback<Long>) con -> con.bitCount(signKey.getBytes())
        );
    }

    /**
     * 获取日期
     *
     * @param dateStr yyyy-MM-dd
     * @return
     */
    private Date getDate(String dateStr) {
        return StrUtil.isBlank(dateStr) ?
                new Date() : DateUtil.parseDate(dateStr);
    }

    /**
     * 构建 Redis Key - user:sign:userId:yyyyMM
     *
     * @param userId 用户ID
     * @param date   日期
     * @return
     */
    private String buildSignKey(Long userId, Date date) {
        return String.format("user:sign:%d:%s", userId,
                DateUtil.format(date, "yyyyMM"));
    }
}
