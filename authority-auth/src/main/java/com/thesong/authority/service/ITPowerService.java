package com.thesong.authority.service;

import com.thesong.authority.entity.TPower;
import com.baomidou.mybatisplus.extension.service.IService;
import com.thesong.authority.entity.TRole;
import com.thesong.authority.entity.TUser;

import java.util.Map;
import java.util.Set;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author thesong
 * @since 2020-11-19
 */
public interface ITPowerService extends IService<TPower> {

    Set<TPower> getPowersByRole(TRole role);
    Set<TPower> getPowersByUser(TUser user);

}
