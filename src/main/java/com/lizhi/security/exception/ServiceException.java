package com.lizhi.security.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.MessageFormat;

/**
 * @author: lizhi
 * @Date: 2020/1/8 14:41
 * @Description:
 */
public class ServiceException extends RuntimeException{
    private Integer code = 0;

    private String pattern;

    private Object[] arguments;

    private Object data;

    public ServiceException() {
    }

    public ServiceException(ErrorCode errorCode, Object... arguments) {
        this(errorCode.getCode(), errorCode.getMessage(), arguments);
    }

    public ServiceException(Integer code, String pattern, Object... arguments) {
        this.code = code;
        this.pattern = pattern;
        this.arguments = arguments;
    }

    @JsonProperty("status")
    public Integer getCode() {
        return code;
    }

    @JsonIgnore
    public String getPattern() {
        return pattern;
    }

    @JsonIgnore
    public Object[] getArguments() {
        return arguments;
    }

    @JsonProperty("message")
    public String getMessage() {
        if (arguments == null || arguments.length == 0 || pattern == null) {
            return pattern;
        }
        return MessageFormat.format(pattern, arguments);
    }

    @JsonProperty("data")
    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    @JsonIgnore
    public StackTraceElement[] getStackTrace() {
        return super.getStackTrace();
    }

    @Override
    @JsonIgnore
    public String getLocalizedMessage() {
        return super.getLocalizedMessage();
    }

    @Override
    @JsonIgnore
    public synchronized Throwable getCause() {
        return super.getCause();
    }
}
