package com.thesong.authority.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.thesong.authority.entity.TUser;
import com.thesong.authority.entity.TUserRole;
import com.thesong.authority.service.ITRoleService;
import com.thesong.authority.service.ITUserRoleService;
import com.thesong.authority.service.ITUserService;
import com.thesong.authority.service.SpringContextBeanService;
import com.thesong.common.utils.JWTUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author thesong
 * @Date 2020/11/17 17:25
 * @Version 1.0
 * @Describe
 */
public class CustomRealm extends AuthorizingRealm {







//    @Override
//    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
//        return null;
//    }
//
//    @Override
//    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
//        return null;
//    }

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

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        if (itUserRoleService == null) {
            this.itUserRoleService = SpringContextBeanService.getBean(ITUserRoleService.class);
        }

        if (itRoleService == null) {
            this.itRoleService = SpringContextBeanService.getBean(ITRoleService.class);
        }

        Integer userId = JWTUtil.getUserId(principals.toString());
        TUser tUser = itUserService.getById(userId);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        if(null != tUser){
            QueryWrapper<TUserRole> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("userId", tUser.getUserId());
            List<TUserRole> tUserRole = itUserRoleService.list(queryWrapper);

        /*
        Role role = roleService.selectOne(new EntityWrapper<Role>().eq("role_code", userToRole.getRoleCode()));
        //添加控制角色级别的权限
        Set<String> roleNameSet = new HashSet<>();
        roleNameSet.add(role.getRoleName());
        simpleAuthorizationInfo.addRoles(roleNameSet);
        */
            //控制菜单级别按钮  类中用@RequiresPermissions("user:list") 对应数据库中code字段来控制controller
            ArrayList<String> pers = new ArrayList<>();
            List<Menu> menuList = menuService.findMenuByRoleCode(userToRole.getRoleCode());
            for (Menu per : menuList) {
                if (!ComUtil.isEmpty(per.getCode())) {
                    pers.add(String.valueOf(per.getCode()));
                }
            }
            Set<String> permission = new HashSet<>(pers);
            simpleAuthorizationInfo.addStringPermissions(permission);
        }
        return simpleAuthorizationInfo;
    }

    /**
     * 认证配置
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        if (userService == null) {
            this.userService = SpringContextBeanService.getBean(IUserService.class);
        }
        String token = (String) auth.getCredentials();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        if(Constant.METHOD_URL_SET.contains(request.getRequestURI())){
            request.setAttribute("currentUser",new User());
            return new SimpleAuthenticationInfo(token, token, this.getName());
        }
        // 解密获得username，用于和数据库进行对比
        String userNo = JWTUtil.getUserNo(token);
        if (userNo == null) {
            throw new AuthenticationException("token invalid");
        }
        User userBean = userService.selectById(userNo);
        if (userBean == null) {
            throw new AuthenticationException("User didn't existed!");
        }
        if (! JWTUtil.verify(token, userNo, userBean.getPassword())) {
            throw new AuthenticationException("Username or password error");
        }
        return new SimpleAuthenticationInfo(token, token, this.getName());
    }
}
