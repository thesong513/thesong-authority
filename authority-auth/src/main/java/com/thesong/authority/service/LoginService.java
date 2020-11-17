package com.thesong.authority.service;

import com.thesong.authority.entity.User;
import org.springframework.stereotype.Service;

/**
 * @Author thesong
 * @Date 2020/11/17 17:16
 * @Version 1.0
 * @Describe
 */
@Service
public interface LoginService {
    User getUserByName(String getMapByName);

}
