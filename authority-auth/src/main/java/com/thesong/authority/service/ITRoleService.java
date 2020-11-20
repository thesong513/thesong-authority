package com.thesong.authority.service;

import com.thesong.authority.entity.TRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.thesong.authority.entity.TUser;

import java.util.Set;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author thesong
 * @since 2020-11-19
 */
public interface ITRoleService extends IService<TRole> {

    Set<TRole> getRolesByUser(TUser user);

}
