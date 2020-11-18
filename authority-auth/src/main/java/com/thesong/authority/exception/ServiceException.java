package com.thesong.authority.exception;

/**
 * @Author thesong
 * @Date 2020/11/18 14:00
 * @Version 1.0
 * @Describe
 */
public class ServiceException extends RuntimeException{

    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
