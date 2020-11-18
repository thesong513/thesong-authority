package com.thesong.authority.controller;

import com.alibaba.fastjson.JSONObject;
import com.thesong.authority.annotation.ValidationParam;
import com.thesong.authority.entity.User;
import com.thesong.common.response.Result;
import com.thesong.common.response.ResultGenerator;
import com.thesong.common.utils.JWTUtil;
import com.thesong.common.utils.RedisUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.*;

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


//    RedisUtil redisUtil = new RedisUtil();
//    User user1 = new User(1, "admin", "admin", "A1");
//    User user2 = new User(2, "admin2", "admin1", "A1");
//    List<User> users = new ArrayList<User>() {{
//        add(user1);
//        add(user2);
//    }};
//

//
//    @PostMapping("/register")
//    public Result registerController(@RequestBody Map<String, String> person) {
//        // todo 去重
//        String username = person.get("username");
//        String password = person.get("password");
//        User user = new User(username, password);
//        Set<ConstraintViolation<User>> violationSet = validator.validate(user);
//        if (violationSet.size() != 0) {
//            return ResultGenerator.genFailResult("参数错误");
//        }
//        users.add(user);
//        Map<String, String> data = new HashMap<>();
//        String accessKey = JWTUtil.createAccessKey(username, user.getRoles(), 10);
//        data.put("access-key", accessKey);
//        redisUtil.setex(accessKey, 3600, user.getId().toString());
//        return ResultGenerator.genSuccessResult(data);
//    }
//
//    @GetMapping("/login")
//    public Result loginController(String username, String password) {
//
//        Map<String, String> data = new HashMap<>();
//        for (User user : users) {
//            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
//                String accessKey = JWTUtil.createAccessKey(username, "A1", 10);
//                data.put("access-key", accessKey);
//                redisUtil.setex(accessKey, 3600, user.getId().toString());
//                return ResultGenerator.genSuccessResult(data);
//            }
//        }
//        return ResultGenerator.genFailResult("用户名或密码错误");
//    }

    @GetMapping("/test")
    public Result testController() {
        return ResultGenerator.genSuccessResult("server is success running！");
    }


    @PostMapping("/loginV2")
    public Result login(@ValidationParam("username ,password") @RequestBody JSONObject requestJson) throws Exception{
        User user = new User(requestJson.get("username").toString(), requestJson.get("password").toString());
        return ResultGenerator.genSuccessResult();
        //用户认证信息
//        Subject subject = SecurityUtils.getSubject();
//        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
//                user.getUsername(),
//                user.getPassword()
//        );
//        try {
//            //进行验证，这里可以捕获异常，然后返回对应信息
//            subject.login(usernamePasswordToken);
//            subject.checkRole("admin");
//            subject.checkPermissions("query", "add");
//        } catch (UnknownAccountException e) {
//            log.error("用户名不存在！", e);
//            return ResultGenerator.genFailResult("请输入正确的用户名");
//        } catch (AuthenticationException e) {
//            log.error("账号或密码错误！", e);
//            return ResultGenerator.genFailResult("请输入正确的用户名和密码");
//        } catch (AuthorizationException e) {
//            log.error("没有权限！", e);
//            return ResultGenerator.genFailResult("没有权限");
//        }
//        return ResultGenerator.genSuccessResult("登陆成功");
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
