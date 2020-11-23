package com.thesong.authority.constant;

/**
 * @Author thesong
 * @Date 2020/11/18 16:04
 * @Version 1.0
 * @Describe
 */
public enum  CodeEnum {
    SUCCESS("10000","操作成功"),
    ERROR("20000","操作失败"),
    IDENTIFICATION_ERROR("20001","权限不足"),
    DATA_ERROR("20002","业务错误"),
    PARAM_ERROR("20003","参数错误"),
    SIGNIN_ERROR("20004","未登录错误"),
    ADMIN_ERROR("20005","不能修改管理员信息"),
    INVALID_USERNAME_PASSWORD("20006","用户名或密码错误"),
    ACCOUNT_ERROR("20007","用户账号不正常"),
    INVALID_USER("20008","用户不存在"),
    INVALID_USER_EXIST("20009","用户已存在"),
    INVALID_ROLE("20010","角色不存在"),
    USER_NO_PERMITION("20011","当前用户无该接口权限"),
    INVALID_RE_PASSWORD("20012","两次输入密码不一致"),
    UPDATE_ROLEINFO_ERROR("20015","更新角色信息失败");

    private String respCode;
    private String respMsg;


    CodeEnum(String respCode, String respMsg){
        this.respCode = respCode;
        this.respMsg=respMsg;
    }

    public String getCode() {
        return respCode;
    }
    public String getMsg() {
        return respMsg;
    }
}
