package com.lizhi.common.global;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * @author lizhi
 * @date: 2020/10/31 16:19
 */

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * Create by lrt<br/>
     * Date：2018/10/24
     * Description： 拦截异常进行处理返回前台友好信息
     *
     * @param e 异常对象
     * @return java.lang.String 返回前台信息
     */
    @ExceptionHandler(Exception.class)
    public BaseResponseVo exceptionHandler(Exception e) {
        e.printStackTrace();
        //参数校验错误
        if (e instanceof BindException) {
            BindException bindException = (BindException) e;
            List<ObjectError> objectErrors = bindException.getBindingResult().getAllErrors();
            return getValidExceptionResult(objectErrors);
        }
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException bindException = (MethodArgumentNotValidException) e;
            List<ObjectError> objectErrors = bindException.getBindingResult().getAllErrors();
            return getValidExceptionResult(objectErrors);
        }
        //post请求未传参数
        if (e instanceof HttpMessageNotReadableException) {
            return ResponseDataUtils.error(ResponseEnum.BAD_REQUEST);
        }

        //请求Content type不支持
        if (e instanceof HttpMediaTypeNotSupportedException) {
            return ResponseDataUtils.error(ResponseEnum.MEDIA_TYPE_NOT_SUPPORTED);
        }

        return ResponseDataUtils.error(ResponseEnum.SERVER_ERROR);
    }

    //参数校验异常处理
    private BaseResponseVo getValidExceptionResult(List<ObjectError> objectErrors) {
        StringBuilder sb = new StringBuilder();
        for (ObjectError error : objectErrors) {
            sb.append(error.getDefaultMessage()).append(";");
        }
        String message = sb.length() > 0 ? sb.toString().substring(0, sb.length() - 1) : sb.toString();
        return ResponseDataUtils.error(ResponseEnum.BAD_REQUEST_PARAMETER, message);
    }
}
