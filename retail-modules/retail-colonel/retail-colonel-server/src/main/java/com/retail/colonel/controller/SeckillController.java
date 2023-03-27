package com.retail.colonel.controller;

import com.retail.colonel.domain.SeckillEntity;
import com.retail.colonel.service.SeckillService;
import com.retail.common.domain.vo.SeckillSpuVo;
import com.retail.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ProjectName:    retail-cloud
 * @Package:        com.retail.colonel.controller
 * @ClassName:      SeckillController
 * @Author:     2766395184
 * @Description:  秒杀控制层
 * @Date:    2023/3/25 9:51
 * @Version:    1.0
 */
@RestController
@RequestMapping("colonel/seckill")
public class SeckillController {


    @Autowired
    private SeckillService seckillService;


    /**
     * 拼团商品明细
     * @param id
     * @return
     */
    @RequestMapping("itemDetail/{id}")
    public Result<SeckillSpuVo> itemDetail(@PathVariable("id")Long id){

        if (id==null){
           Result.error(502,"请选择正确的秒杀商品");
        }

        Result< SeckillSpuVo> seckillEntity=seckillService.itemDetail(id);

        return seckillEntity;


    }







}
