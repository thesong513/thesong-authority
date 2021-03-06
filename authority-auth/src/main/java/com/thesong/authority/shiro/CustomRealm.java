package com.thesong.authority.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.thesong.authority.constant.Constant;
import com.thesong.authority.entity.*;
import com.thesong.authority.service.*;
import com.thesong.common.utils.ComUtil;
import com.thesong.common.utils.JWTUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author thesong
 * @Date 2020/11/17 17:25
 * @Version 1.0
 * @Describe
 */
public class CustomRealm extends AuthorizingRealm {

    /**
     * 大坑！，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    @Autowired
    private IUserRoleService iUserRoleService;

    @Autowired
    private IRoleService iRoleService;

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IPowerService iPowerService;

    @Autowired
    private IRolePowerService iRolePowerService;

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        String userId = JWTUtil.getUserId(principals.toString());
        User user = iUserService.getById(userId);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        if (null != user) {
            Set<Role> roles = iRoleService.getRolesByUser(user);
            Set<String> rolenames = roles.stream().map(Role::getRoleName).collect(Collectors.toSet());
            Set<Power> powers = iPowerService.getPowersByUser(user);
            Set<String> powernames = powers.stream().map(Power::getOperation).collect(Collectors.toSet());
            // 类中用@RequiresPermissions("user:list") 对应数据库中code字段来控制controller
            simpleAuthorizationInfo.addRoles(rolenames);
            simpleAuthorizationInfo.addStringPermissions(powernames);
        }
        return simpleAuthorizationInfo;
    }

    /**
     * 认证配置
     *
     * @param auth
     * @return SimpleAuthenticationInfo
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        if (Constant.METHOD_URL_SET.contains(request.getRequestURI())) {
            request.setAttribute("currentUser", new User());
            return new SimpleAuthenticationInfo(token, token, this.getName());
        }
        // 解密获得username，用于和数据库进行对比
        String userId = JWTUtil.getUserId(token);
        if (userId == null) {
            throw new AuthenticationException("token invalid");
        }
        User user = iUserService.getById(userId);
        if (user == null) {
            throw new AuthenticationException("User didn't existed!");
        }
        if (!JWTUtil.verify(token, userId, user.getPassword())) {
            throw new AuthenticationException("Username or password error!");
        }
        return new SimpleAuthenticationInfo(token, token, this.getName());
    }
}



