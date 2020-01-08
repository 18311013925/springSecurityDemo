package com.lizhi.security.exception;

/**
 * @author: lizhi
 * @Date: 2020/1/8 14:35
 * @Description:
 */
public class VerificationCodeException extends ServiceException {

    public VerificationCodeException() {
    }

    public VerificationCodeException(ErrorCode errorCode, Object... arguments) {
        super(errorCode, arguments);
    }

    public VerificationCodeException(Integer code, String pattern, Object... arguments) {
        super(code, pattern, arguments);
    }
}
