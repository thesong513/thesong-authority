package com.thesong.authority.filter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author thesong
 * @Date 2020/11/16 17:50
 * @Version 1.0
 * @Describe 用户过滤器
 */

public class UserFilter implements Filter {

    private static Log log = LogFactory.getLog(UserFilter.class);

    String[] includeUrls = new String[]{"/user/login","/user/register","/user/test"};


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        try {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            String uri = request.getRequestURI();
            if (!this.isNeedFilter(uri)) {
                //不过滤走
                filterChain.doFilter(servletRequest, servletResponse);
            }else {
                //这里是过滤方法
                String accessKey = request.getHeader("access-key");
                if(accessKey !=null){
                    filterChain.doFilter(servletRequest, servletResponse);
                }else {
                    response.sendRedirect(request.getContextPath()+"/user/login");
                }
            }
        }catch (Exception e){
            log.error("error",e);
        }
    }


    public boolean isNeedFilter(String uri) {
        for (String includeUrl : includeUrls) {
            if(includeUrl.equals(uri)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void destroy() {
        log.info("user filter is destroyed!");
    }
}
