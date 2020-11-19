package com.thesong.authority.controller;

import com.thesong.common.response.Result;
import com.thesong.common.response.ResultGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

/**
 * @Author thesong
 * @Date 2020/11/16 18:01
 * @Version 1.0
 * @Describe
 */

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @GetMapping("/test")
    public Result testController() {
        return ResultGenerator.genSuccessResult("server is success running！");
    }

    @RequiresRoles("admin")
    @GetMapping("/admin")
    public String admin() {
        return "admin success!";
    }

    @RequiresPermissions("query")
    @GetMapping("/index")
    public String index() {
        return "index success!";
    }

    @RequiresPermissions("add")
    @GetMapping("/add")
    public String add() {
        return "add success!";
    }


}
