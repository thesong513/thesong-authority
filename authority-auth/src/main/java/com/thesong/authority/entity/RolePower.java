package com.thesong.authority.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
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
@Builder
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_role_power")
@ApiModel(value="RolePower对象", description="")
public class RolePower implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String roleId;

    private String powerId;


}
