package com.thesong.authority.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

/**
 * @Author thesong
 * @Date 2020/11/17 17:13
 * @Version 1.0
 * @Describe
 */

@Data
@AllArgsConstructor
public class Role {
    private int id;
    private String roleName;
    /**
     * 角色对应权限集合
     */
    private Set<Permissions> permissions;

}
