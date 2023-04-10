package com.retail.bargain.controller;

import com.retail.bargain.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 拼团表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-23 11:22:38
 */
@RestController
@RequestMapping("bargain/team")
public class TeamController {

    @Autowired
    private TeamService teamService;
}
