package com.thesong.authority.service;

import com.thesong.authority.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.thesong.authority.entity.User;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author thesong
 * @since 2020-11-22
 */
@Service
public interface IRoleService extends IService<Role> {
    Set<Role> getRolesByUser(User user);
    Role getRoleByRoleName(String roleName);

}
