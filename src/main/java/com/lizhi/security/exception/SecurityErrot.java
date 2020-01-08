package com.lizhi.security.exception;

/**
 * @author: lizhi
 * @Date: 2020/1/8 14:37
 * @Description:
 */
public enum SecurityErrot implements ErrorCode{

    IMAGE_CODE(10000, "图形验证码校验失败"),

    ;
    private final Integer code;

    private final String message;

    SecurityErrot(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

}
