package com.thesong.authority.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author thesong
 * @Date 2020/11/16 18:01
 * @Version 1.0
 * @Describe
 */

@RestController
public class UserController {

    @GetMapping("/filter")
    public String testFilter(){
        return "filter is ok";
    }
}
