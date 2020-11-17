package com.thesong.authority.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Set;

/**
 * @Author thesong
 * @Date 2020/11/17 13:05
 * @Version 1.0
 * @Describe
 */
@Data
@AllArgsConstructor
public class User {
    private int id;
    @NotBlank(message = "username can't not be Null！")
    private String username;
    @NotBlank(message = "password can't not be Null！")
    private String password;
    private Set<Role> roles;

}
