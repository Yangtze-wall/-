package com.retail.common.domain.response;

import lombok.Data;

/**
 * @author JwtResponse
 * @BelongsProject: retail-cloud
 * @BelongsPackage: com.retail.common.domain.response
 * @date: 2023-03-24  09:34
 * @Created by:  12871
 * @Description:
 * @Version:
 */
@Data
public class JwtResponse {
    private String token;
    private String expireTime;
}
