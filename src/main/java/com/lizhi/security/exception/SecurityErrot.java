package com.lizhi.security.exception;

/**
 * @author: lizhi
 * @Date: 2020/1/8 14:37
 * @Description:
 */
public enum SecurityErrot implements ErrorCode{

    imageCode(10000, "图形验证码错误"),

    ;
    private final Integer code;

    private final String message;

    SecurityErrot(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return null;
    }

    @Override
    public String getMessage() {
        return null;
    }
}
