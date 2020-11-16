package com.thesong.authority.Filter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 * @Author thesong
 * @Date 2020/11/16 17:50
 * @Version 1.0
 * @Describe 用户过滤器
 */

public class UserFilter implements Filter {

    private static Log log = LogFactory.getLog(com.thesong.authority.Filter.UserFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("user filter is inited!");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("user filter is begin!");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        log.info("user filter is destroyed!");
    }
}
