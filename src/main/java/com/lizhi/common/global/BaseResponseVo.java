package com.lizhi.common.global;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lizhi
 * @date: 2020/10/31 16:22
 */

@Data
@NoArgsConstructor
public class BaseResponseVo {
    protected Integer rtn;
    protected String message;
}
