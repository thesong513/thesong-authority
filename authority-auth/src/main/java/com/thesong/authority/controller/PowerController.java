package com.thesong.authority.controller;


import com.thesong.authority.annotation.Pass;
import com.thesong.authority.config.ResponseHelper;
import com.thesong.authority.config.ResponseModel;
import com.thesong.authority.entity.Power;
import com.thesong.authority.service.IPowerService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author thesong
 * @since 2020-11-22
 */
@RestController
@RequestMapping("/power")
public class PowerController {

    @Autowired
    private IPowerService powerService;

    @GetMapping("/test")
    @Pass
    public ResponseModel<String> testController() {
        return ResponseHelper.succeed("server is runing！");
    }

    @GetMapping("/test1")
    @RequiresPermissions(value = {"admin:1"})
    public ResponseModel<Power> userController() {
        Power power = powerService.getById("1");
        return ResponseHelper.succeed(power);
    }

}

