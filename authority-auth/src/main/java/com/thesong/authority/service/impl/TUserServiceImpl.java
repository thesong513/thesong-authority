package com.thesong.authority.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.thesong.authority.constant.CodeEnum;
import com.thesong.authority.constant.Constant;
import com.thesong.authority.entity.TUser;
import com.thesong.authority.entity.TUserRole;
import com.thesong.authority.exception.BusinessException;
import com.thesong.authority.mapper.TUserMapper;
import com.thesong.authority.service.ITRoleService;
import com.thesong.authority.service.ITUserRoleService;
import com.thesong.authority.service.ITUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.thesong.common.utils.ComUtil;
import com.thesong.common.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.mindrot.jbcrypt.BCrypt;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author thesong
 * @since 2020-11-19
 */
@Service
public class TUserServiceImpl extends ServiceImpl<TUserMapper, TUser> implements ITUserService {

    @Autowired
    private ITUserService itUserService;

    @Autowired
    private ITRoleService itRoleService;
    @Autowired
    private ITUserRoleService itUserRoleService;


    @Override
    public TUser getUserByUserName(String username) {
        QueryWrapper<TUser> queryWrapper = new QueryWrapper<TUser>();
        queryWrapper.eq("user_name",username).last("limit 1");
        return itUserService.getOne(queryWrapper);
    }

    @Override
    public Map<String, Object> checkUsernameAndPassword(JSONObject requestJson) throws Exception {
        String username = requestJson.getString("username");
        Map<String, Object> result = new HashMap<>();
        TUser user = this.getUserByUserName(username);
        if(!ComUtil.isEmpty(user) && user.getBan()!=1){
            throw new BusinessException(CodeEnum.ACCOUNT_ERROR.getMsg(), CodeEnum.ACCOUNT_ERROR.getCode());
        }
        if (ComUtil.isEmpty(user) || !BCrypt.checkpw(requestJson.getString("password"), user.getPassword())) {
            throw new BusinessException(CodeEnum.INVALID_USERNAME_PASSWORD.getMsg(),CodeEnum.INVALID_USERNAME_PASSWORD.getCode());
        }
        user.setToken(JWTUtil.sign(user.getUserId(),user.getPassword()));
        result.put("user",user);
        return result;
    }

    @Override
    public TUser updateForgetPasswd(JSONObject requestJson) throws Exception {
        return null;
    }

    @Override
    public TUser insertUser(JSONObject requestJson) throws Exception {
        TUser user = requestJson.toJavaObject(TUser.class);
        if(!ComUtil.isEmpty(this.getUserByUserName(user.getUserName()))){
            throw new BusinessException(CodeEnum.INVALID_USER_EXIST.getMsg(),CodeEnum.INVALID_USER_EXIST.getCode());
        }
        user.setPassword(BCrypt.hashpw("123456", BCrypt.gensalt()));
        boolean save = this.save(user);
        if(save) {
            TUserRole userRole = new TUserRole();
            userRole.setUserId(user.getUserId());
            userRole.setRoleId(Constant.DEFAULT_ROLE_CODE);
            itUserRoleService.save(userRole);
        }
        return user;
    }


}
