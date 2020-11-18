package com.thesong.authority.annotation;

import java.lang.annotation.*;

/**
 * @Author thesong
 * @Date 2020/11/18 14:28
 * @Version 1.0
 * @Describe
 */


@Target(ElementType.PARAMETER)          // 可用在方法的参数上
@Retention(RetentionPolicy.RUNTIME)     // 运行时有效
@Documented
public @interface ValidationParam {

    String value() default "";
}
