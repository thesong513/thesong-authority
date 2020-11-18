package com.thesong.authority.aspect;

import java.lang.reflect.Method;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @Author thesong 装饰器模式
 * @Date 2020/11/18 15:00
 * @Version 1.0
 * @Describe
 */
public interface AspectApi {
    Object doHandlerAspect(ProceedingJoinPoint pjp, Method method)throws Throwable;
}
