package com.thesong.authority.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.thesong.authority.entity.*;
import com.thesong.authority.mapper.TPowerMapper;
import com.thesong.authority.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thesong.common.utils.ComUtil;
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
 * @since 2020-11-19
 */
@Service
public class TPowerServiceImpl extends ServiceImpl<TPowerMapper, TPower> implements ITPowerService {

    @Autowired
    private ITRoleService itRoleService;

    @Autowired
    private ITPowerService itPowerService;

    @Autowired
    private ITRolePowerService itRolePowerService;

    @Override
    public Set<TPower> getPowersByRole(TRole role) {
        Set<TPower> powers = new HashSet<>();
        if(null!=role){
            Integer roleId = role.getRoleId();
            QueryWrapper<TRolePower> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("role_id", roleId);
            List<TRolePower> tRolePowers = itRolePowerService.list(queryWrapper);
            for (TRolePower tRolePower : tRolePowers) {
                Integer powerId = tRolePower.getPowerId();
                TPower power = itPowerService.getById(powerId);
                powers.add(power);
            }
        }
        return powers;
    }

    @Override
    public Set<TPower> getPowersByUser(TUser user) {
        Set<TPower> powers = new HashSet<>();
        if(null!=user){
            Set<TRole> roles = itRoleService.getRolesByUser(user);
            for (TRole role : roles) {
                Set<TPower> power = this.getPowersByRole(role);
                powers.addAll(power);
            }
        }
        return powers;
    }
}
