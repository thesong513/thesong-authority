package com.thesong.authority.service;

import com.alibaba.fastjson.JSONObject;
import com.thesong.authority.entity.TUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author thesong
 * @since 2020-11-19
 */
public interface ITUserService extends IService<TUser> {

    TUser getUserByUserName(String username);
    Map<String,Object> checkUsernameAndPassword(JSONObject requestJson) throws Exception;
    TUser updateForgetPasswd(JSONObject requestJson) throws Exception;
    TUser insertUser(JSONObject requestJson) throws Exception;





}
