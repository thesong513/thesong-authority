package com.thesong.authority.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.thesong.authority.entity.Role;
import com.thesong.authority.entity.User;
import com.thesong.authority.entity.UserRole;
import com.thesong.authority.mapper.RoleMapper;
import com.thesong.authority.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thesong.authority.service.IUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author thesong
 * @since 2020-11-22
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    private IUserRoleService iUserRoleService;
    @Autowired
    private IRoleService iRoleService;

    @Override
    public Set<Role> getRolesByUser(User user) {
        Set<Role> roles = new HashSet<>();
        if (null != user) {
            String userId = user.getUserId();
            QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id", userId);
            List<UserRole> userRoles = iUserRoleService.list(queryWrapper);
            for (UserRole userRole : userRoles) {
                String roleId = userRole.getRoleId();
                Role role = iRoleService.getById(roleId);
                roles.add(role);
            }
        }
        return roles;

    }

    @Override
    public Role getRoleByRoleName(String roleName) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_name",roleName).last("limit 1");
        return iRoleService.getOne(queryWrapper);
    }

}
