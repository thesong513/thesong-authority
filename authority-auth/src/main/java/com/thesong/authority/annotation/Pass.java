package com.thesong.authority.annotation;

import java.lang.annotation.*;

/**
 * @Author thesong
 * @Date 2020/11/18 14:30
 * @Version 1.0
 * @Describe
 */
@Target( { ElementType.METHOD } )
@Retention( RetentionPolicy.RUNTIME )
@Documented
public @interface Pass {

}