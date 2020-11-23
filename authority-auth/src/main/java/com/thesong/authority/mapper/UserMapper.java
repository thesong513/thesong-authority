package com.thesong.authority.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.thesong.authority.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author thesong
 * @since 2020-11-22
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    List<User> selectPageByConditionUser(Page<User> page, @Param("roleName") String roleName, @Param("bans") Integer[] bans);

}
