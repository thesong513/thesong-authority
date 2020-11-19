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
    private ITUserRoleService itUserRoleService;

    @Autowired
    private ITRoleService itRoleService;

    @Autowired
    private ITUserService itUserService;

    @Autowired
    private ITPowerService itPowerService;

    @Autowired
    private ITRolePowerService itRolePowerService;

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        Integer userId = JWTUtil.getUserId(principals.toString());
        TUser tUser = itUserService.getById(userId);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        if (null != tUser) {
            QueryWrapper<TUserRole> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("userId", tUser.getUserId());
            List<TUserRole> tUserRoles = itUserRoleService.list(queryWrapper);
            Set<String> roles = new HashSet<>();
            Set<String> powers = new HashSet<>();
            if (!ComUtil.isEmpty(tUserRoles)) {
                for (TUserRole userRole : tUserRoles) {
                    int roleId = userRole.getRoleId();
                    TRole role = itRoleService.getById(roleId);
                    roles.add(role.getRoleName());
                    QueryWrapper<TRolePower> queryWrapper1 = new QueryWrapper<>();
                    queryWrapper.eq("roleId", roleId);
                    List<TRolePower> tRolePowers = itRolePowerService.list(queryWrapper1);
                    if (!ComUtil.isEmpty(tRolePowers)) {
                        for (TRolePower tRolePower : tRolePowers) {
                            int powerId = tRolePower.getPowerId();
                            TPower power = itPowerService.getById(powerId);
                            powers.add(power.getOperation());
                        }
                    }
                }
            }
            // 类中用@RequiresPermissions("user:list") 对应数据库中code字段来控制controller
            simpleAuthorizationInfo.addRoles(roles);
            simpleAuthorizationInfo.addStringPermissions(powers);
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
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        if (Constant.METHOD_URL_SET.contains(request.getRequestURI())) {
            request.setAttribute("currentUser", new TUser());
            return new SimpleAuthenticationInfo(token, token, this.getName());
        }
        // 解密获得username，用于和数据库进行对比
        Integer userId = JWTUtil.getUserId(token);
        if (userId == null) {
            throw new AuthenticationException("token invalid");
        }
        TUser userBean = itUserService.getById(userId);
        if (userBean == null) {
            throw new AuthenticationException("User didn't existed!");
        }
        if (!JWTUtil.verify(token, userId, userBean.getPassword())) {
            throw new AuthenticationException("Username or password error!");
        }
        return new SimpleAuthenticationInfo(token, token, this.getName());
    }
}



