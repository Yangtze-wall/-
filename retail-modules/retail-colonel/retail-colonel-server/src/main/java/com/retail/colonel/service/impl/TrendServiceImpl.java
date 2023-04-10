package com.retail.colonel.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.retail.colonel.domain.ColonelEntity;
import com.retail.colonel.domain.Trend;
import com.retail.colonel.domain.vo.CommentVo;
import com.retail.colonel.domain.vo.SelectVo;
import com.retail.colonel.domain.vo.TrendCommentVo;
import com.retail.colonel.mapper.ColonelMapper;
import com.retail.colonel.mapper.CommentMapper;
import com.retail.colonel.mapper.TrendMapper;
import com.retail.colonel.service.TrendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 团长动态表 服务实现类
 * </p>
 *
 * @author
 * @since 2023-03-26
 */
@Service
public class TrendServiceImpl extends ServiceImpl<TrendMapper, Trend> implements TrendService {
    @Autowired
    private TrendMapper trendMapper;
    @Autowired
    private ColonelMapper colonelMapper;
    @Override
    public List<Trend> selectTrend(Long id) {
        List<ColonelEntity> colonelEntities = this.colonelMapper.selectList(new QueryWrapper<ColonelEntity>().lambda()
                .eq(ColonelEntity::getUserId, id));
        if (colonelEntities.size()==0){
            return null;
        }
        List<Trend> trends = this.trendMapper.selectList(new QueryWrapper<Trend>().lambda()
                .eq(Trend::getColonelId, colonelEntities.get(0).getId()));
        return trends;
    }
    @Autowired
    private CommentMapper commentMapper;
    @Override
    public TrendCommentVo selectList(String id) {
        SelectVo selectVo = new SelectVo();
        selectVo.setId(Long.valueOf(id));
        selectVo.setParentId(0L);
        Trend trend = trendMapper.selectById(id);
        List<CommentVo> list=  findById(selectVo);
        if (list!=null){
            List<CommentVo> collect = list.stream().map(c -> {
                selectVo.setParentId(c.getId());
                List<CommentVo> byId = findById(selectVo);
                c.setChildren(byId);
                return c;
            }).collect(Collectors.toList());
            TrendCommentVo build = new TrendCommentVo();
            build.setTrend(trend);
            build.setList(collect);
            return  build;
        }
        TrendCommentVo trendCommentVo = new TrendCommentVo();
        trendCommentVo.setTrend(trend);
        return trendCommentVo;
    }
    public List<CommentVo> findById(SelectVo selectVo){
        List<CommentVo> list=  commentMapper.findByTrendId(selectVo);
        List<CommentVo> collect = list.stream().map(c -> {
            selectVo.setParentId(c.getId());
            List<CommentVo> byId = findById(selectVo);
            c.setChildren(byId);
            return c;
        }).collect(Collectors.toList());
        return collect;
    }
}
