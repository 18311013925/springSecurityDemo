package com.lizhi.model;

import lombok.Data;

/**
 * @author: lizhi
 * @Date: 2020/1/15 15:08
 * @Description:
 */
@Data
public class EncryptDataModel {
    //密文
    private String ticket;
    //签名
    private String signature;
}
