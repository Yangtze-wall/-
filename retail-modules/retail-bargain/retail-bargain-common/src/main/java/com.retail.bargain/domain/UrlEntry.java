package com.retail.bargain.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author UrlEntry
 * @BelongsProject: retail-cloud
 * @BelongsPackage: com.retail.bargain.domain
 * @date: 2023-03-27  20:05
 * @Created by:  12871
 * @Description:
 * @Version:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("retail_url")
public class UrlEntry {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String url;

    private String urlKey;

    private Integer urlStatus;
}
