package com.thesong.authority.shiro;

import com.thesong.authority.entity.Permissions;
import com.thesong.authority.entity.Role;
import com.thesong.authority.entity.User;
import com.thesong.authority.service.LoginService;
import com.thesong.authority.service.SpringContextBeanService;
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
import org.springframework.util.StringUtils;

/**
 * @Author thesong
 * @Date 2020/11/17 17:25
 * @Version 1.0
 * @Describe
 */
public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private LoginService loginService;



    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 权限配置
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        if (userToRoleService == null) {
            this.userToRoleService = SpringContextBeanService.getBean(IUserToRoleService.class);
        }
        if (menuService == null) {
            this.menuService = SpringContextBeanService.getBean(IMenuService.class);
        }
        if (roleService == null) {
            this.roleService = SpringContextBeanService.getBean(IRoleService.class);
        }

        String userNo = JWTUtil.getUserNo(principals.toString());
        User user = userService.selectById(userNo);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        if(null != user){
            UserToRole userToRole = userToRoleService.selectByUserNo(user.getUserNo());

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
