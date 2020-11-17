package com.thesong.authority.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author thesong
 * @Date 2020/11/17 17:14
 * @Version 1.0
 * @Describe
 */

@Data
@AllArgsConstructor
public class Permissions {
    private int id;
    private String permissionsName;
}
