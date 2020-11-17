package com.thesong.authority.service;

import com.thesong.authority.entity.Permissions;
import com.thesong.authority.entity.Role;
import com.thesong.authority.entity.User;
import com.thesong.authority.service.LoginService;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Author thesong
 * @Date 2020/11/17 17:18
 * @Version 1.0
 * @Describe
 */
public class LoginServiceImpl implements LoginService {
    @Override
    public User getUserByName(String username) {
        return getMapByName(username);
    }

    private User getMapByName(String username) {
        Permissions permissions1 = new Permissions(1, "query");
        Permissions permissions2 = new Permissions(2, "add");
        Set<Permissions> permissionsSet = new HashSet<>();
        permissionsSet.add(permissions1);
        permissionsSet.add(permissions2);
        Role role = new Role(1, "admin", permissionsSet);
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);
        User user = new User(1, "wsl", "123456", roleSet);
        Map<String, User> map = new HashMap<>();
        map.put(user.getUsername(), user);

        Set<Permissions> permissionsSet1 = new HashSet<>();
        permissionsSet1.add(permissions1);
        Role role1 = new Role(2, "user", permissionsSet1);
        Set<Role> roleSet1 = new HashSet<>();
        roleSet1.add(role1);
        User user1 = new User(2, "zhangsan", "123456", roleSet1);
        map.put(user1.getUsername(), user1);
        return map.get(username);
    }
}
