package com.thesong.authority.controller;


import com.alibaba.fastjson.JSONObject;
import com.thesong.authority.annotation.Log;
import com.thesong.authority.annotation.Pass;
import com.thesong.authority.annotation.ValidationParam;
import com.thesong.authority.config.ResponseHelper;
import com.thesong.authority.config.ResponseModel;
import com.thesong.authority.constant.Constant;
import com.thesong.authority.service.ITUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.spring.web.json.Json;

import java.util.Map;

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
@Api(description="身份认证模块")
public class TUserController {

    @Autowired
    private ITUserService itUserService;


    @ApiOperation(value="用户名密码登录", notes="不需要Authorization",produces = "application/json")
    @PostMapping("/signin")
    @Pass
    public ResponseModel<Map<String, Object>> signInController(@ValidationParam("username , password") @RequestBody JSONObject jsonObject) throws Throwable {
        Map<String, Object> stringObjectMap = itUserService.checkUsernameAndPassword(jsonObject);
        return ResponseHelper.succeed(stringObjectMap);
    }

    @ApiOperation(value="用戶注冊", notes="不需要Authorization",produces = "application/json")
    @PostMapping("/register")
    @RequiresRoles(value = {"sysadmin","admin"},logical =  Logical.OR)
    public ResponseModel registerController(@ValidationParam("username , password") @RequestBody JSONObject jsonObject) throws Throwable {
        return ResponseHelper.succeed(itUserService.insertUser(jsonObject));
    }

}

