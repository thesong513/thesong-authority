package com.thesong.authority.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.thesong.authority.annotation.CurrentUser;
import com.thesong.authority.annotation.Pass;
import com.thesong.authority.annotation.ValidationParam;
import com.thesong.authority.config.ResponseHelper;
import com.thesong.authority.config.ResponseModel;
import com.thesong.authority.entity.User;
import com.thesong.authority.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author thesong
 * @since 2020-11-22
 */
@RestController
@RequestMapping("/user")
@Api(description = "用戶认证模块")
public class UserController {

    @Autowired
    private IUserService iUserService;

    @Pass
    @ApiOperation(value = "测试", notes = "不需要Authorization", produces = "application/json")
    @RequestMapping(value = "/test", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseModel<String> testController() {
        return ResponseHelper.succeed("server is runing！");
    }

    @Pass
    @ApiOperation(value = "用户名密码登录", notes = "不需要Authorization", produces = "application/json")
    @PostMapping("/signin")
    public ResponseModel<Map<String, Object>> signInController(@ValidationParam("username , password") @RequestBody JSONObject jsonObject) throws Throwable {
        Map<String, Object> stringObjectMap = iUserService.checkUsernameAndPassword(jsonObject);
        return ResponseHelper.succeed(stringObjectMap);
    }

    @Pass
    @ApiOperation(value = "用戶注冊", notes = "不需要Authorization", produces = "application/json")
    @PostMapping("/register")
    public ResponseModel registerController(@ValidationParam("username , password") @RequestBody JSONObject jsonObject) throws Throwable {
        return ResponseHelper.succeed(iUserService.insertUser(jsonObject));
    }

    @ApiOperation(value = "用戶列表", notes = "需要Authorization", produces = "application/json")
    @GetMapping("/getUsers")
    @RequiresRoles(value = {"sysadmin", "admin"}, logical = Logical.OR)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageIndex", value = "第几页"
                    , dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页多少条"
                    , dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "roleName", value = "角色名字"
                    , dataType = "String", paramType = "query")
    })
    public ResponseModel<Page<User>> getUsersController(@RequestParam(name = "pageIndex", defaultValue = "1", required = false) Integer pageIndex,
                                                        @RequestParam(name = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                                        @RequestParam(name = "roleName", defaultValue = "guest", required = false) String roleName) throws Throwable {
        Integer[] bans = {0, 1};
        Page<User> userPage = iUserService.selectPageByConditionUser(new Page<User>(pageIndex, pageSize), roleName, bans);
        return ResponseHelper.succeed(userPage);
    }

    @ApiOperation(value = "获取用户详细信息", notes = "根据url的id来获取用户详细信息")
    @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "String", paramType = "path")
    @PostMapping(value = "/getUserInfo")
    @RequiresRoles(value = {"sysadmin", "admin"}, logical = Logical.OR)
    public ResponseModel<User> findOneUser(@ValidationParam("userId") @RequestBody JSONObject jsonObject) throws Throwable {
        User user = iUserService.getById(jsonObject.getString("userId"));
        return ResponseHelper.succeed(user);
    }

    @ApiOperation(value = "删除用户", notes = "需要Authorization", produces = "application/json")
    @PostMapping("/deleteUser")
    @RequiresRoles(value = {"sysadmin", "admin"}, logical = Logical.OR)
    public ResponseModel deleteUserController(@ValidationParam("userId") @RequestBody JSONObject jsonObject) throws Throwable {
        iUserService.removeById(jsonObject.getString("userId"));
        return ResponseHelper.succeed(null);
    }

    @ApiOperation(value = "重置密码", notes = "需要Authorization", produces = "application/json")
    @PostMapping("/resetPassword")
    public ResponseModel resetPassWord(@CurrentUser User currentUser,
                                       @ValidationParam("oldPassword,password,rePassword") @RequestBody JSONObject requestJson) throws Exception {
        iUserService.resetPassword(currentUser, requestJson);
        return ResponseHelper.succeed(null);
    }


}

