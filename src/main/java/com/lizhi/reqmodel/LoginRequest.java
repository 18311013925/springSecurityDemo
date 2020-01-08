package com.lizhi.reqmodel;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author: lizhi
 * @Date: 2020/1/6 11:32
 * @Description:
 */
@Data
public class LoginRequest {
//    @NotNull(message = "账号必须填")
//    @Pattern(regexp = "^[1][3,4,5,7,8][0-9]{9}$", message = "账号请输入11位手机号") // 手机号
    private String username;

//    @NotNull(message = "密码必须填")
//    @Size(min = 6, max = 16, message = "密码6~16位")
    private String password;
}
