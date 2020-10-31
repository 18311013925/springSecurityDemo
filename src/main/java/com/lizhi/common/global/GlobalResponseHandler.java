package com.lizhi.common.global;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author lizhi
 * @date: 2020/10/31 16:28
 */

@Slf4j
@RestControllerAdvice
public class GlobalResponseHandler implements ResponseBodyAdvice<Object> {
    /**
     * Whether this component supports the given controller method return type
     * and the selected {@code HttpMessageConverter} type.
     *
     * @param returnType    the return type
     * @param converterType the selected converter type
     * @return {@code true} if {@link #beforeBodyWrite} should be invoked;
     * {@code false} otherwise
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        //判断支持的类型，因为我们定义的BaseResponseVo 里面的data可能是任何类型，这里就不判断统一放过
        //如果你想对执行的返回体进行操作，可将上方的Object换成你自己的类型
        return true;
    }

    /**
     * Invoked after an {@code HttpMessageConverter} is selected and just before
     * its write method is invoked.
     *
     * @param body                  the body to be written
     * @param returnType            the return type of the controller method
     * @param selectedContentType   the content type selected through content negotiation
     * @param selectedConverterType the converter type selected to write to the response
     * @param request               the current request
     * @param response              the current response
     * @return the body that was passed in or a modified (possibly new) instance
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        log.info("请求返回数据类型class={}", body.getClass().getName());
        BaseResponseVo result = null;
        //兼容原来的接口返回
        if (body instanceof ResponseDataVo) {
            result = (ResponseDataVo) body;
        } else if (body instanceof PageResponseDataVo) {
            result = (PageResponseDataVo) body;
        } else if (body instanceof BaseResponseVo) {
            result = (BaseResponseVo) body;
        } else {
            result = ResponseDataUtils.success(body);
        }
        //debug时打印响应结果
        if (log.isDebugEnabled()) {
            log.debug("响应参数：{} ", JSON.toJSONString(result));
        }
        //处理返回值是String的情况
        if (body instanceof String) {
            return JSON.toJSONString(result);
        }
        return result;
    }

}
