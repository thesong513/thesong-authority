package com.thesong.authority.controller;


import com.thesong.authority.entity.TPower;
import com.thesong.authority.entity.TUser;
import com.thesong.authority.service.ITPowerService;
import com.thesong.common.response.Result;
import com.thesong.common.response.ResultGenerator;
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
@RequestMapping("/t-power")
public class TPowerController {

    @Autowired
    private ITPowerService powerService;

    @GetMapping("/test")
    public Result testController() {
        return ResultGenerator.genSuccessResult("server is success running！");
    }


    @GetMapping("/test1")
    public TPower userController() {
        TPower power = powerService.getById(1);
        return power;
    }





}

