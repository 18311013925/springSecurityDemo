package com.lizhi.common.global;

import org.apache.commons.lang3.StringUtils;

import static com.lizhi.common.global.ResponseEnum.SUCCESS;

/**
 * @author lizhi
 * @date: 2020/10/31 16:27
 */
public class ResponseDataUtils {
    /**
     * 请求成功时封装数据
     *
     * @param data 数据
     * @return 返回BaseResponseVo封装后的数据
     */
    public static BaseResponseVo success(Object data) {
        ResponseDataVo result = new ResponseDataVo();
        result.setRtn(SUCCESS.getRtn());
        result.setMessage(SUCCESS.getMessage());
        result.setData(data);
        return result;
    }

    /**
     * 请求成功时封装数据
     *
     * @return 返回BaseResponseVo封装后的数据
     */
    public BaseResponseVo success() {
        return success(null);
    }

    /**
     * 请求失败结果封装
     *
     * @param responseEnum 响应状态码
     * @param message      响应信息
     * @return 返回BaseResponseVo封装后的数据
     */
    public static BaseResponseVo error(ResponseEnum responseEnum, String message) {
        BaseResponseVo result = new BaseResponseVo();
        result.setRtn(responseEnum.getRtn());
        String msg = StringUtils.isEmpty(message) ? responseEnum.getMessage() : message;
        result.setMessage(msg);
        return result;
    }

    /**
     * 请求失败结果封装
     *
     * @param responseEnum 响应状态码
     * @return 返回BaseResponseVo封装后的数据
     */
    public static BaseResponseVo error(ResponseEnum responseEnum) {
        return error(responseEnum, null);
    }

}
