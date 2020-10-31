package com.lizhi.common.global;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lizhi
 * @date: 2020/10/31 16:23
 */
@Data
@NoArgsConstructor
public class ResponseDataVo<T> extends BaseResponseVo  {
    private T data;
}
