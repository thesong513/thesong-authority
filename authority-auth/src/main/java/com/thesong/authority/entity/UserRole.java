package com.thesong.authority.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author thesong
 * @since 2020-11-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_user_role")
@ApiModel(value="UserRole对象", description="")
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userId;

    private String roleId;


}
