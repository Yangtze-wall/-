package com.retail.user.controller;

import java.util.List;


import com.retail.common.domain.vo.RoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.retail.user.service.RoleService;


/**
 * 角色表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-22 18:26:56
 */

@RestController
@RequestMapping("user/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("selectOrderAddress2")
    public List<RoleVo> selectOrderAddress2(){
        List<RoleVo> roleVoList=   roleService.selectOrderAddress2();

        return  roleVoList;
    }
}
