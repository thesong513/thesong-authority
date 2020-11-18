package com.thesong.common.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @Author thesong
 * @Date 2020/11/18 15:12
 * @Version 1.0
 * @Describe
 */
public class StringUtil {

    public static String getMethodAnnotationOne(Method method, String validationParamValue) {
        String retParam =null;
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (int i = 0; i < parameterAnnotations.length; i++) {
            for (int j = 0; j < parameterAnnotations[i].length; j++) {
                String str = parameterAnnotations[i][j].toString();
                if(str.indexOf(validationParamValue) >0){
                    retParam = str.substring(str.indexOf("=")+1,str.indexOf(")"));
                }
            }
        }
        return retParam;
    }


}
