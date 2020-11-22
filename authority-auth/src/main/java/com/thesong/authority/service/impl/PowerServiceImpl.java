package com.thesong.authority.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.thesong.authority.entity.Power;
import com.thesong.authority.entity.Role;
import com.thesong.authority.entity.RolePower;
import com.thesong.authority.entity.User;
import com.thesong.authority.mapper.PowerMapper;
import com.thesong.authority.service.IPowerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thesong.authority.service.IRolePowerService;
import com.thesong.authority.service.IRoleService;
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
public class PowerServiceImpl extends ServiceImpl<PowerMapper, Power> implements IPowerService {

    @Autowired
    private IRoleService iRoleService;

    @Autowired
    private IPowerService iPowerService;

    @Autowired
    private IRolePowerService iRolePowerService;

    @Override
    public Set<Power> getPowersByRole(Role role) {
        Set<Power> powers = new HashSet<>();
        if(null!=role){
            String roleId = role.getRoleId();
            QueryWrapper<RolePower> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("role_id", roleId);
            List<RolePower> rolePowers = iRolePowerService.list(queryWrapper);
            for (RolePower rolePower : rolePowers) {
                String powerId = rolePower.getPowerId();
                Power power = iPowerService.getById(powerId);
                powers.add(power);
            }
        }
        return powers;
    }

    @Override
    public Set<Power> getPowersByUser(User user) {
        Set<Power> powers = new HashSet<>();
        if(null!=user){
            Set<Role> roles = iRoleService.getRolesByUser(user);
            for (Role role : roles) {
                Set<Power> power = this.getPowersByRole(role);
                powers.addAll(power);
            }
        }
        return powers;
    }

}
