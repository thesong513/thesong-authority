package com.thesong.authority.filter;

import com.thesong.common.response.Result;
import com.thesong.common.response.ResultGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class MyExceptionHandler {

    @ExceptionHandler
    @ResponseBody
    public Result ErrorHandler(AuthorizationException e) {
        log.error("没有通过权限验证！", e);
        return ResultGenerator.genFailResult("没有通过权限验证");
    }
}