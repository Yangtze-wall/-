package com.retail.shop.service.impl;

import com.retail.shop.domain.ClassifiedEntity;
import com.retail.shop.domain.vo.ClassifiedVo;
import com.retail.shop.mapper.ClassifiedMapper;
import com.retail.shop.service.ClassifiedService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;
import java.util.stream.Collectors;


@Service("classifiedService")
public class ClassifiedServiceImpl extends ServiceImpl<ClassifiedMapper, ClassifiedEntity> implements ClassifiedService {
    /**
     *  递归 分类展示
     * @return
     */
    @Override
    public List<ClassifiedVo> selectClassifiedTreeList(){
        List<ClassifiedEntity> classifiedEntityList = baseMapper.selectList(null);
        //ream流链式处理(复制到vo) map：转换
        List<ClassifiedVo> classifiedVoList = classifiedEntityList.stream().map(item -> {
            ClassifiedVo classifiedVo = new ClassifiedVo();
            BeanUtils.copyProperties(item, classifiedVo);//复制
            return classifiedVo;
        }).collect(Collectors.toList());
        //第一级菜单 filter 过滤
        List<ClassifiedVo> collect = classifiedVoList.stream().filter(c -> c.getParentId().equals(0L)).map(item -> {
            item.setChildList(childList(item.getId(), classifiedVoList));
            return item;
        }).collect(Collectors.toList());

        return collect;
    }
    public List<ClassifiedVo> childList(Long id,List<ClassifiedVo> list){
        List<ClassifiedVo> collect = list.stream().filter(c -> c.getParentId().equals(id)).map(item -> {
            item.setChildList(childList(item.getId(),list));
            return item;
        }).collect(Collectors.toList());
        return collect;
    }

}
