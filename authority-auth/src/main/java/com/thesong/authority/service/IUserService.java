package com.thesong.authority.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.thesong.authority.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author thesong
 * @since 2020-11-22
 */
@Service
public interface IUserService extends IService<User> {
    User getUserByUserName(String username);
    Map<String,Object> checkUsernameAndPassword(JSONObject requestJson) throws Exception;
    void resetPassword(User currentUser, JSONObject requestJson) throws Exception;
    User insertUser(JSONObject requestJson) throws Exception;
    Page<User> selectPageByConditionUser(Page<User> userPage,String roleName,  Integer[] bans);

}
