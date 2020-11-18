package com.thesong.authority.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @Author thesong
 * @Date 2020/11/18 17:18
 * @Version 1.0
 * @Describe
 */
public class JWTToken implements AuthenticationToken {

    // 密钥
    private String token;

    public JWTToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
