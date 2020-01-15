package com.lizhi.security.exception;

/**
 * @author: lizhi
 * @Date: 2020/1/8 14:37
 * @Description:
 */
public enum SecurityError implements ErrorCode{

    IMAGE_CODE(10000, "图形验证码校验失败"),
    WITHOUT_SECRET_KEY_ERROR(10001,"暂无秘钥，请先在管理后台生成秘钥！"),
    GENERATOR_SECRET_KEY_ERROR(10002, "生成秘钥失败！"),
    ENCRYPT_LOGIN_INFO_RSA_ERROR(10003, "RSA加密失败！"),
    ;
    private final Integer code;

    private final String message;

    SecurityError(Integer code, String message) {
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
