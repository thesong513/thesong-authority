package com.thesong.authority.shiro;

import com.alibaba.fastjson.JSONObject;
import com.thesong.authority.config.ResponseHelper;
import com.thesong.authority.constant.CodeEnum;
import com.thesong.authority.constant.Constant;
import com.thesong.authority.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * @Author thesong
 * @Date 2020/11/18 17:07
 * @Version 1.0
 * @Describe
 */
@Slf4j
public class JWTFilter extends BasicHttpAuthenticationFilter {


    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String authorization = req.getHeader("Authorization");
        return authorization != null;
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws AuthenticationException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String authorization = httpServletRequest.getHeader("Authorization");
        JWTToken token = new JWTToken(authorization);
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        getSubject(request, response).login(token);
        // 如果没有抛出异常则代表登入成功，返回true
        setUserBean(request, response, token);
        return true;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        String requestURI = ((HttpServletRequest) request).getRequestURI();
        if(Constant.METHOD_URL_SET.contains(requestURI)){
            return true;
        }
        if (isLoginAttempt(request, response)) {
            try {
                executeLogin(request, response);
                return true;
            } catch (AuthenticationException e) {
                log.info(e.getMessage());
                responseError(request, response);
            }
        }
        return false;
    }

    private void setUserBean(ServletRequest request, ServletResponse response, JWTToken token) {
        Object principal = SecurityUtils.getSubject().getPrincipal();
        if(principal instanceof User){
            User userBean =(User)principal;
            request.setAttribute("currentUser", userBean);
        }
    }




    /**
     * 对跨域提供支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }

    /**
     * 非法url返回身份错误信息
     */
    private void responseError(ServletRequest request, ServletResponse response) {
        Writer out= null;
        OutputStreamWriter outputStreamWriter =null;
        try {
            outputStreamWriter = new OutputStreamWriter(response.getOutputStream(), "utf-8");
            response.setContentType("application/json; charset=utf-8");
            out = new BufferedWriter(outputStreamWriter);
            out.write(JSONObject.toJSONString(ResponseHelper.failed2Message(CodeEnum.IDENTIFICATION_ERROR.getMsg())));
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(null !=  outputStreamWriter){
                try {
                    outputStreamWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



}
