package com.thesong.authority.annotation;

import java.lang.annotation.*;

/**
 * @Author thesong
 * @Date 2020/11/18 14:31
 * @Version 1.0
 * @Describe
 */
@Target( { ElementType.METHOD } )
@Retention( RetentionPolicy.RUNTIME )
@Documented
public @interface Log {

    /**
     * 模块名称
     */
    String modelName() default "";

    /**
     * 操作
     */
    String action()default "";
    /**
     * 描述.
     */
    String description() default "";

}
