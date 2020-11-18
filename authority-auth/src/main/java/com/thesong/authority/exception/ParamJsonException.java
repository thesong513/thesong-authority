package com.thesong.authority.exception;

/**
 * @Author thesong
 * @Date 2020/11/18 15:19
 * @Version 1.0
 * @Describe
 */
public class ParamJsonException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    public ParamJsonException() {}

    public ParamJsonException(String message) {
        super(message);
        this.message = message;
    }
}
