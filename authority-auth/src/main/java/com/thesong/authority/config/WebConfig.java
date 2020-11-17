package com.thesong.authority.config;

import com.thesong.authority.filter.UserFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author thesong
 * @Date 2020/11/17 15:03
 * @Version 1.0
 * @Describe
 */
@Configuration
public class WebConfig {
//    @Bean
//    public FilterRegistrationBean testFilterRegistration() {
//        FilterRegistrationBean registration = new FilterRegistrationBean(new UserFilter());
//        registration.addUrlPatterns("/*");
//        registration.setName("userFilter");
//        return registration;
//    }
}
