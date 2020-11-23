package com.thesong.authority.constant;

import java.util.Set;

import com.google.common.collect.Sets;

/**
 * @Author thesong
 * @Date 2020/11/18 14:08
 * @Version 1.0
 * @Describe
 */
public final class Constant {
    public static final int BYTE_BUFFER = 1024;

    public static Set<String> METHOD_URL_SET = Sets.newConcurrentHashSet();
    public class RoleType{
        //超级管理员
        public static final String SYS_ASMIN_ROLE= "sysadmin";
        //管理员
        public static final String ADMIN= "admin";
        //普通用户
        public static final String GUEST= "guest";
        //默認用戶
        public static final  String DEFAULT_USER = GUEST;
    }



}
