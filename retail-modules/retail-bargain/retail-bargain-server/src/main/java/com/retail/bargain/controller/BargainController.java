package com.retail.bargain.controller;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.retail.bargain.domain.BargainEntity;
import com.retail.bargain.domain.UrlEntry;
import com.retail.bargain.domain.request.BargainEntityRequest;
import com.retail.bargain.service.UrlService;
import com.retail.bargain.utils.DecUtil;
import com.retail.common.domain.vo.BargainEntityVo;
import com.retail.common.result.PageResult;
import com.retail.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;


import com.retail.bargain.service.BargainService;

import javax.servlet.http.HttpServletRequest;


/**
 * 砍价表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-23 11:22:38
 */
@CrossOrigin
@RestController
@RequestMapping("bargain/bargain")
@Component
public class BargainController {

    @Autowired
    private BargainService bargainService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private UrlService urlService;


    /**
     * 砍价表 分页
     * @param bargainEntityRequest
     * @return
     */
    @PostMapping("/getBargainList")
    public Result<PageResult<BargainEntity>> list(@RequestBody BargainEntityRequest bargainEntityRequest){
        System.out.println(bargainEntityRequest);
        PageHelper.startPage(bargainEntityRequest.getPageNum(),bargainEntityRequest.getPageSize());
        List<BargainEntity> list=bargainService.select();
        PageInfo<BargainEntity> colonelEntityPageInfo = new PageInfo<>(list);
        Result<PageResult<BargainEntity>> pageResultResult = PageResult.toResult(colonelEntityPageInfo.getTotal(), list);
        System.out.println(pageResultResult);
        return pageResultResult;
    }


    /**
     * 砍价表 添加
     * @param bargainEntity
     * @return
     */
    @PostMapping("/bargainInsert")
    public Result bargainInsert(@RequestBody BargainEntity bargainEntity){
        return bargainService.bargainInsert(bargainEntity);
    }
    /**
     * 砍价表 修改
     * @param bargainEntity
     * @return
     */
    @PutMapping("/bargainUpdate")
    public Result bargainUpdate(@RequestBody BargainEntity bargainEntity){
        return bargainService.bargainUpdate(bargainEntity);
    }

    /**
     * 砍价表回显
     * @param id
     * @return
     */
    @GetMapping("/findByIdBargainEntity/{id}")
    public Result<BargainEntityVo> findByIdBargainEntity(@PathVariable("id") Long id){
        Result<BargainEntityVo> byIdBargainEntity = bargainService.findByIdBargainEntity(id);
        return byIdBargainEntity;
    }



    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result deleteBargainlEntity(@PathVariable("id") Long id){
        return bargainService.deleteBargainlEntity(id);
    }


    /**
     * 获取id
     * @param id
     * @return
     */
    @GetMapping("/findBargainEntity/{id}")
    public Result<String> findUrl(@PathVariable("id") Long id){
        return bargainService.findUrl(id);
    }

    @GetMapping("/updateInsertBargain/{url}")
    public Result<String> updateInsertBargain(@PathVariable("url") String url){
        UrlEntry urlEntry = urlService.getOne(new QueryWrapper<UrlEntry>().lambda().eq(UrlEntry::getUrl, url));
        if (urlEntry==null){
            return Result.error("地址错误");
        }
        // 解密操作
        String key = urlEntry.getUrlKey();
        byte[] decodedBytes = new byte[0];
        try {
            decodedBytes = DecUtil.decode(Base64.getDecoder().decode(url), key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String decodedString = new String(decodedBytes, StandardCharsets.UTF_8);
        System.out.println("Decoded string: " + decodedString);
        Long id = Long.parseLong(decodedString);
        return bargainService.updateInsertBargain(id,url);
    }


}
