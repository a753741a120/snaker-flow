package com.snaker.framework.enums;

public enum ResultEnums {
    SUCCESS(200, "请求成功"),
    ERROR(1111, "请求失败"),
    SYSTEM_ERROR(1000, "系统异常"),
    BUSSINESS_ERROR(2001, "业务逻辑错误"),
    VERIFY_CODE_ERROR(2002, "业务参数错误"),
    PARAM_ERROR(2002, "业务参数错误"),
    NO_AUTH_CODE(1001, "非法请求"),
    LIMIT_ERROR_CODE(5001, "当前负载太高，请稍后重试"),
    TOKEN_TIMEOUT_CODE(3001, "TOKEN过期"),
    UNKNOWN_ERROR(5009, "未知错误"),
    SERVICE_UNAVAILABLE_ERROR(2003, "服务不可用"),
    API_UNAVAILABLE_ERROR(2004, "接口不可用"),
    API_REQUEST_LIMIT_ERROR(2005, "接口调用达到上限"),
    UNAUTHORIZED_IP_ERROR(2006, "未授权IP"),
    UNAUTHORIZED_REFERER_ERROR(2007, "未授权Referer"),
    NO_ACCESS_PERMISSION_ERROR(2008, "无权限访问"),
    PRECONDITION_CHECK_ERROR(2010, "先决条件校验失败"),
    ACCOUNT_OR_PASSWORD_ERROR(2011, "帐号或密码错误"),
    ACCOUNT_FROZEN_ERROR(3012, "帐号禁用"),
    SESSION_EXPIRE_ERROR(3013, "会话过期"),
    TOKEN_INVALID_ERROR(3015, "token无效"),
    SIGNATURE_INVALID_ERROR(3016, "签名无效"),
    UN_LOGIN_ERROR(3017, "用户未登录"),
    FEIGN_REQUEST_ERROR(3018, "feign请求失败");



    private Integer status;
    private String msg;

    private ResultEnums(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
