package com.retail.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.retail.common.result.Result;
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

    @Override
    public List<ClassifiedVo> selectClassifiedTreeList(){
        List<ClassifiedEntity> classifiedEntityList = baseMapper.selectList(null);
        //stream流链式处理(复制到vo) map：转换
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


    @Override
    public Result insertClassified(ClassifiedEntity classifiedEntity) {
        baseMapper.insert(classifiedEntity);
        return Result.success();
    }

    @Override
    public List<ClassifiedEntity> selectClassifiedParent() {
        List<ClassifiedEntity> entityList = baseMapper.selectList(new QueryWrapper<ClassifiedEntity>().lambda().eq(ClassifiedEntity::getParentId, 0L));

        return entityList;
    }

    @Override
    public List<ClassifiedEntity> selectClassifiedChildById(Long parentId) {
        List<ClassifiedEntity> entityList = baseMapper.selectList(new QueryWrapper<ClassifiedEntity>().lambda().eq(ClassifiedEntity::getParentId, parentId));

        return entityList;
    }

    @Override
    public Result deleteClassifiedById(Long id) {
        ClassifiedEntity classifiedEntity = new ClassifiedEntity();
        classifiedEntity.setId(id);
        classifiedEntity.setStatus(0);
        baseMapper.updateById(classifiedEntity);
        return Result.success();
    }

    @Override
    public Result updateClassified(ClassifiedEntity classifiedEntity) {
        baseMapper.update(classifiedEntity,new UpdateWrapper<ClassifiedEntity>().lambda().eq(ClassifiedEntity::getId,classifiedEntity.getId()));

        return Result.success();
    }


}
