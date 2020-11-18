package com.thesong.authority.config;

import com.thesong.authority.annotation.ValidationParam;
import com.thesong.authority.aspect.AspectApiImpl;
import com.thesong.authority.aspect.ValidationParamAspect;
import com.thesong.common.utils.ComUtil;
import com.thesong.common.utils.StringUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

/**
 * @Author thesong
 * @Date 2020/11/18 15:32
 * @Version 1.0
 * @Describe
 */

@Aspect
@Configuration
public class ControllerAspect {

    @Pointcut("execution(* com.thesong.authority.controller..*.*(..))  ")
    public void aspect() {
    }

    @Around(value = "aspect()")
    public Object validationPoint(ProceedingJoinPoint pjp) throws Throwable{
        Method method = currentMethod(pjp,pjp.getSignature().getName());
        //创建被装饰者
        AspectApiImpl aspectApi = new AspectApiImpl();
        //是否需要验证参数
        if (!ComUtil.isEmpty(StringUtil.getMethodAnnotationOne(method, ValidationParam.class.getSimpleName()))) {
            new ValidationParamAspect(aspectApi).doHandlerAspect(pjp,method);
        }
//        //是否需要限流
//        if (method.isAnnotationPresent(AccessLimit.class)) {
//            new AccessLimitAspect(aspectApi).doHandlerAspect(pjp,method);
//        }
//        //是否需要拦截xss攻击
//        if(method.isAnnotationPresent( ParamXssPass.class )){
//            new ParamXssPassAspect(aspectApi).doHandlerAspect(pjp,method);
//        }
//        //是否需要记录日志
//        if(method.isAnnotationPresent(Log.class)){
//            return new RecordLogAspect(aspectApi).doHandlerAspect(pjp,method);
//        }
        return  pjp.proceed(pjp.getArgs());
    }

    /**
     * 获取目标类的所有方法，找到当前要执行的方法
     */
    private Method currentMethod (ProceedingJoinPoint joinPoint , String methodName ) {
        Method[] methods      = joinPoint.getTarget().getClass().getMethods();
        Method   resultMethod = null;
        for ( Method method : methods ) {
            if ( method.getName().equals( methodName ) ) {
                resultMethod = method;
                break;
            }
        }
        return resultMethod;
    }

}
