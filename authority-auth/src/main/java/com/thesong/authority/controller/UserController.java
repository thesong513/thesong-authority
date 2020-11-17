package com.thesong.authority.controller;

import com.thesong.authority.entity.User;
import com.thesong.common.response.Result;
import com.thesong.common.response.ResultGenerator;
import com.thesong.common.utils.JWTUtil;
import com.thesong.common.utils.RedisUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UserController {

    RedisUtil redisUtil = new RedisUtil();
    User user1 = new User(1, "admin", "admin", "A1");
    User user2 = new User(2, "admin2", "admin1", "A1");
    List<User> users = new ArrayList<User>() {{
        add(user1);
        add(user2);
    }};

    @Autowired
    private Validator validator;

    @PostMapping("/register")
    public Result registerController(@RequestBody Map<String, String> person) {
        // todo 去重
        String username = person.get("username");
        String password = person.get("password");
        User user = new User(10, username, password, "B1");
        Set<ConstraintViolation<User>> violationSet = validator.validate(user);
        if (violationSet.size() != 0) {
            return ResultGenerator.genFailResult("参数错误");
        }
        users.add(user);
        Map<String, String> data = new HashMap<>();
        String accessKey = JWTUtil.createAccessKey(username, user.getRoles(), 10);
        data.put("access-key", accessKey);
        redisUtil.setex(accessKey, 3600, user.getId().toString());
        return ResultGenerator.genSuccessResult(data);
    }

    @GetMapping("/login")
    public Result loginController(String username, String password) {

        Map<String, String> data = new HashMap<>();
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                String accessKey = JWTUtil.createAccessKey(username, "A1", 10);
                data.put("access-key", accessKey);
                redisUtil.setex(accessKey, 3600, user.getId().toString());
                return ResultGenerator.genSuccessResult(data);
            }
        }
        return ResultGenerator.genFailResult("用户名或密码错误");
    }

    @GetMapping("/test")
    public Result testController() {
        return ResultGenerator.genFailResult("success");
    }


}
