package com.thesong.authority.entity;

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
 * @since 2020-11-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="TRolePower对象", description="")
public class TRolePower implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer roleId;

    private Integer powerId;


}