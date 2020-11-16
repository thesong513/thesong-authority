package com.thesong.common.response;

import com.alibaba.fastjson.JSON;

/**
 * @Author thesong
 * @Date 2020/11/16 17:19
 * @Version 1.0
 * @Describe
 */
public class Result<T> {

    private int code;
    private String message;
    private T data;

    public Result setCode(ResultCode resultCode) {
        this.code = resultCode.code();
        return this;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Result setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public Result setData(T data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
