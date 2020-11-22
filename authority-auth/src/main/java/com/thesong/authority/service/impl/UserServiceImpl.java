package com.thesong.authority.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.thesong.authority.constant.CodeEnum;
import com.thesong.authority.constant.Constant;
import com.thesong.authority.entity.Role;
import com.thesong.authority.entity.User;
import com.thesong.authority.entity.UserRole;
import com.thesong.authority.exception.BusinessException;
import com.thesong.authority.mapper.UserMapper;
import com.thesong.authority.service.IRoleService;
import com.thesong.authority.service.IUserRoleService;
import com.thesong.authority.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thesong.common.utils.ComUtil;
import com.thesong.common.utils.GenerationSequenceUtil;
import com.thesong.common.utils.JWTUtil;
import com.thesong.common.utils.StringUtil;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author thesong
 * @since 2020-11-22
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IRoleService iRoleService;
    @Autowired
    private IUserRoleService iUserRoleService;

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserByUserName(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", username);
        return iUserService.getOne(queryWrapper);

    }

    @Override
    public Map<String, Object> checkUsernameAndPassword(JSONObject requestJson) throws Exception {
        String username = requestJson.getString("username");
        Map<String, Object> result = new HashMap<>();
        User user = this.getUserByUserName(username);
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
    public User updateForgetPasswd(JSONObject requestJson) throws Exception {
        return null;
    }

    @Override
    public User insertUser(JSONObject requestJson) throws Exception {
        User user = requestJson.toJavaObject(User.class);
        if(!ComUtil.isEmpty(this.getUserByUserName(user.getUserName()))){
            throw new BusinessException(CodeEnum.INVALID_USER_EXIST.getMsg(),CodeEnum.INVALID_USER_EXIST.getCode());
        }
        user.setPassword(BCrypt.hashpw("123456", BCrypt.gensalt()));
        user.setUserId(GenerationSequenceUtil.generateUUID("user"));
        boolean save = this.save(user);
        if(save) {
            UserRole userRole = new UserRole();
            userRole.setUserId(user.getUserId());
            Role role = iRoleService.getRoleByRoleName(Constant.RoleType.DEFAULT_USER);
            if(null!=role) {
                userRole.setRoleId(role.getRoleId());
                iUserRoleService.save(userRole);
            }
        }
        return user;
    }

}
