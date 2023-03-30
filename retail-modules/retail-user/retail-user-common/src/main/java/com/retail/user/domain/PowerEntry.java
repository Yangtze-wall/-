package com.retail.user.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author PowerEntry
 * @BelongsProject: retail-cloud
 * @BelongsPackage: com.retail.user.domain
 * @date: 2023-03-26  19:39
 * @Created by:  12871
 * @Description:
 * @Version:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("retail_power")
public class PowerEntry {
    private Long id;
    private String name;

}
