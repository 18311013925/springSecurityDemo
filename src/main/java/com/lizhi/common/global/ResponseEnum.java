package com.lizhi.common.global;

/**
 * @author lizhi
 * @date: 2020/10/31 16:22
 */
public enum ResponseEnum {
    /**
     * 请求成功
     */
    SUCCESS(0, "ok"),
    /**
     * 前台请求参数错误
     */
    BAD_REQUEST_PARAMETER(1, "请求参数不符合规定"),
    /**
     * 请求错误
     */
    BAD_REQUEST(2, "请求错误"),

    /**
     * 请求的Content-Type错误
     */
    MEDIA_TYPE_NOT_SUPPORTED(3, "请求的Content-Type错误"),
    SERVER_ERROR(4, "服务错误"),
    ;

    private Integer rtn;
    private String message;

    ResponseEnum(Integer rtn, String message) {
        this.rtn = rtn;
        this.message = message;
    }

    public Integer getRtn() {
        return rtn;
    }

    public String getMessage() {
        return message;
    }

    }
