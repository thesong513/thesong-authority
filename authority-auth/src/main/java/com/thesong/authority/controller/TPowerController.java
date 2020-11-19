package com.thesong.authority.controller;


import com.thesong.authority.annotation.Pass;
import com.thesong.authority.config.ResponseHelper;
import com.thesong.authority.config.ResponseModel;
import com.thesong.authority.entity.TPower;
import com.thesong.authority.service.ITPowerService;
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
 * @since 2020-11-19
 */
@RestController
@RequestMapping("/user")
public class TPowerController {

    @Autowired
    private ITPowerService powerService;

    @GetMapping("/test")
    @Pass
    public ResponseModel<String> testController() {
        return ResponseHelper.succeed("server is runing！");
    }

    @GetMapping("/test1")
    @RequiresPermissions(value = {"user:list"})
    public ResponseModel<TPower> userController() {
        TPower power = powerService.getById(1);
        return ResponseHelper.succeed(power);
    }



}

