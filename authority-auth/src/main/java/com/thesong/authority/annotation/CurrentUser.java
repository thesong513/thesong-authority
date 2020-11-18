package com.thesong.authority.annotation;

import java.lang.annotation.*;

/**
 * @Author thesong
 * @Date 2020/11/18 17:27
 * @Version 1.0
 * @Describe
 */

@Target(ElementType.PARAMETER)          // 可用在方法的参数上
@Retention(RetentionPolicy.RUNTIME)
public @interface CurrentUser {
}
