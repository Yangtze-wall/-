package com.retail.bargain.domain.request;

import lombok.Data;

/**
 * @author BargainEntityEntryRequest
 * @BelongsProject: retail-cloud
 * @BelongsPackage: com.retail.bargain.domain.vo
 * @date: 2023-03-26  18:43
 * @Created by:  12871
 * @Description:
 * @Version:
 */
@Data
public class BargainEntityRequest {
    private Integer pageNum=1;
    private Integer pageSize=2;
}
