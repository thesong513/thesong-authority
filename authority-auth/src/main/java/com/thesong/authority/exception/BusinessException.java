package com.thesong.authority.exception;

/**
 * @Author thesong
 * @Date 2020/11/18 17:31
 * @Version 1.0
 * @Describe
 */
public class BusinessException extends Exception{

    private static final long serialVersionUID = 3455708526465670030L;

    public BusinessException(String msg){
        super(msg);
    }

    public BusinessException(String msg,String code){
        super(msg+":--:"+code);
    }

}
