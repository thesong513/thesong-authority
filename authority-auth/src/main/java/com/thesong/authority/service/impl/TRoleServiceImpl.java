package com.thesong.authority.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.thesong.authority.entity.TRole;
import com.thesong.authority.entity.TUser;
import com.thesong.authority.entity.TUserRole;
import com.thesong.authority.mapper.TRoleMapper;
import com.thesong.authority.service.ITRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thesong.authority.service.ITUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author thesong
 * @since 2020-11-19
 */
@Service
public class TRoleServiceImpl extends ServiceImpl<TRoleMapper, TRole> implements ITRoleService {

    @Autowired
    private ITUserRoleService itUserRoleService;
    @Autowired
    private ITRoleService itRoleService;

    @Override
    public Set<TRole> getRolesByUser(TUser user) {
        Set<TRole> roles = new HashSet<>();
        if (null != user) {
            Integer userId = user.getUserId();
            QueryWrapper<TUserRole> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id", userId);
            List<TUserRole> tUserRoles = itUserRoleService.list(queryWrapper);
            for (TUserRole tUserRole : tUserRoles) {
                Integer roleId = tUserRole.getRoleId();
                TRole role = itRoleService.getById(roleId);
                roles.add(role);
            }
        }
        return roles;

    }
}
