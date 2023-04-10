package com.retail.colonel.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.retail.colonel.bean.Face;
import org.apache.ibatis.annotations.Mapper;

/**
* @author typsusan
* @description 针对表【face】的数据库操作Mapper
* @createDate 2022-07-17 03:33:50
* @Entity .bean.Face
*/
@Mapper
public interface FaceMapper extends BaseMapper<Face> {

}




