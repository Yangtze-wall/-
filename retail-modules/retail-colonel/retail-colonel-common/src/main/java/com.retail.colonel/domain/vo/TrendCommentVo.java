package com.retail.colonel.domain.vo;

import com.retail.colonel.domain.Trend;
import lombok.Data;

import java.util.List;

/**
 * ClassName TrendCommentVo
 * Date 2023/3/27 15:12
 **/
@Data
public class TrendCommentVo {
    private Trend trend;
    private List<CommentVo> list;
}
