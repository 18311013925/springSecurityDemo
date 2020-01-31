package com.lizhi.model;

import lombok.Data;

/**
 * @author: lizhi
 * @Date: 2020/1/15 15:09
 * @Description:第三方认证票据信息
 */

/**
 * {
 * "uid":123456,     //微办公用户唯一ID
 * "tid":123456,     //微办公公司唯一ID
 * "un":"张三",       //微办公用户名字
 * "ac":12345678901, //微办公用户登录账号
 * "sn":"A001",      //外部系统员工唯一编号
 * "ct":1479439482070 //凭证ticket生成服务器时间毫秒
 * }
 */


@Data
public class Ticket {
    /**
     * 用户唯一ID
     */
    private Long uId;
    /**
     * 公司唯一ID
     */
    private Long tId;
    /**
     *用户名字
     */
    private String un;
    /**
     *用户登录账号
     */
    private String ac;
    /**
     *外部系统员工唯一编号
     */
    private String sn;
    /**
     *凭证ticket生成服务器时间毫秒
     */
    private Long ct;


}

