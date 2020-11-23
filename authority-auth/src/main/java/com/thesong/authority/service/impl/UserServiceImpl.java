package com.thesong.authority.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
    public void resetPassword(User currentUser, JSONObject requestJson) throws Exception {
        if (!requestJson.getString("password").equals(requestJson.getString("rePassword"))) {
            throw  new BusinessException(CodeEnum.INVALID_RE_PASSWORD.getMsg(),CodeEnum.INVALID_RE_PASSWORD.getCode());
        }
        if(!BCrypt.checkpw(requestJson.getString("oldPassword"), currentUser.getPassword())){
            throw  new BusinessException(CodeEnum.INVALID_USERNAME_PASSWORD.getMsg(),CodeEnum.INVALID_USERNAME_PASSWORD.getCode());
        }
        currentUser.setPassword(BCrypt.hashpw(requestJson.getString("password"),BCrypt.gensalt()));
        this.updateById(currentUser);
    }


    @Override
    public User insertUser(JSONObject requestJson) throws Exception {
        User user = requestJson.toJavaObject(User.class);
        if(!ComUtil.isEmpty(this.getUserByUserName(user.getUserName()))){
            throw new BusinessException(CodeEnum.INVALID_USER_EXIST.getMsg(),CodeEnum.INVALID_USER_EXIST.getCode());
        }
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        user.setUserId(GenerationSequenceUtil.generateUUID("user"));
        boolean save = this.save(user);
        if (save) {
            Role role = iRoleService.getRoleByRoleName(Constant.RoleType.DEFAULT_USER);
            if (null != requestJson.get("rolename")) {
                role = iRoleService.getRoleByRoleName(requestJson.getString("rolename"));
            }
            if (null != role) {
                UserRole userRole = UserRole.builder().userId(user.getUserId()).roleId(role.getRoleId()).build();
                iUserRoleService.save(userRole);
            }
        }
        return user;
    }

    @Override
    public Page<User> selectPageByConditionUser(Page<User> userPage, String roleName, Integer[] bans) {
        Page<User> records = userPage.setRecords(userMapper.selectPageByConditionUser(userPage, roleName , bans));
        return records;
    }

}


