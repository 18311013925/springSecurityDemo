package com.lizhi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

@Data
public class SecretKey {
    @JsonIgnore
    private Long id;

    private Long tenantId;

    private String publicKey;

    private String privateKey;
    @JsonIgnore
    private Long createById;
    @JsonIgnore
    private Long updateById;

    private Date createAt;

    private Date updateAt;

    private String wbgPublicKey;

}