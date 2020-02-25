package com.lizhi.oauth.third;

import com.lizhi.common.util.JsonUtil;
import com.lizhi.common.util.RSACoder;
import com.lizhi.mapper.SecretKeyMapper;
import com.lizhi.model.EncryptDataModel;
import com.lizhi.model.SecretKey;
import com.lizhi.model.Ticket;
import com.lizhi.security.exception.SecurityError;
import com.lizhi.security.exception.ServiceException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author: lizhi
 * @Date: 2020/1/15 11:17
 * @Description:
 * 第三方免密登录实现
 */
@Data
@RestController
@RequestMapping("security/certification")
@Slf4j
public class ThirdCertificationResource {
    @Resource
    private SecretKeyMapper secretKeyMapper;

    /**
     * 生成秘钥
     * @return
     */
    @PostMapping("/generatorSecretKey")
    public SecretKey generatorSecretkey(){
        // 删除当前用户已经生成的密钥对
//        SecurityUser principal = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        secretKeyMapper.deleteByPrimaryKey(principal.getId());

        // 开始生成密钥对
        SecretKey secretKey = new SecretKey();
        try {
            Map<String, Object> keyMap = RSACoder.initKey();
            secretKey.setTenantId(123456L);
            secretKey.setCreateById(123456L);
            secretKey.setUpdateById(123456L);
            secretKey.setPublicKey(RSACoder.getPublicKey(keyMap));
            secretKey.setPrivateKey(RSACoder.getPrivateKey(keyMap));
            // TODO: 17/5/17  更新秘钥，并存入数据库
            secretKeyMapper.insert(secretKey);
        } catch (Exception e){
            log.error("生成密钥失败{]", e);
            throw new ServiceException(SecurityError.GENERATOR_SECRET_KEY_ERROR,e);
        }
        return secretKey;
    }


    /**
     * 获取秘钥信息
     * @return
     */
    @GetMapping("/secretKey")
    public SecretKey getSecretkey(){
//        Long tenantId = ClientUtils.getTenantId();
        Long tenantId = 123456L;
        SecretKey secretKey = secretKeyMapper.selectByTenantId(tenantId);
        if(secretKey==null){
            secretKey = new SecretKey();
        }
        secretKey.setWbgPublicKey(RSACoder.TENANT_PUBLIC_KEY);
        // TODO: 17/5/17 从数据库里取出秘钥 管理后台展示
        return secretKey;
    }

    /**
     * 获取第三方免密认证信息
     * @return
     */
    @GetMapping("/ticket")
    public EncryptDataModel getEncryptDataForLogin(){
        EncryptDataModel encryptData = new EncryptDataModel();
        Ticket ticket = new Ticket();
        // 从数据库查询到的用户信息，以及第三方信息
       /* Long userId = ClientUtils.getUserId();
        Long tenantId = ClientUtils.getTenantId();
        Employee account = employeeMapper.getByIdFromOld(userId);*/

        ticket.setUId(123456L);
        ticket.setTId(123456L);
        ticket.setUn("公司名称");
        ticket.setCt(System.currentTimeMillis());
        ticket.setSn("公司签名");
        ticket.setAc("登录账号");
        String ticketStr  = JsonUtil.serialize(ticket);
        // TODO: 17/5/17 从数据库里获取该公司的秘钥信息
        SecretKey secretKey =  getSecretkey();
        if (secretKey == null || StringUtils.isEmpty(secretKey.getPublicKey())) {
            throw new ServiceException(SecurityError.WITHOUT_SECRET_KEY_ERROR);
        }

        String publicKey= secretKey.getPublicKey();
        try {
            //转成字节数组，用公钥进行加密
            byte[] data = ticketStr.getBytes();
            byte[] encodedData = RSACoder.encryptByPublicKey(data, publicKey);
            //用base64将密文字节数组转成密文字符串
            String ciphertext = Base64.encodeBase64URLSafeString(encodedData);

            // 用WBG私钥对加密数据进行签名，对方获取加密信息后，可用WBG开放的 公钥信息 和 签名，加密数据 三者进行验证，
            // 确保这份数据确实是微办公发出，且没有经过第三方篡改。验证通过，即可进行解密
            String signature = RSACoder.sign(encodedData, RSACoder.TENATN_PRIVATE_KEY);
            encryptData.setTicket(ciphertext);
            encryptData.setSignature(signature);
        } catch (Exception e) {
            throw new ServiceException(SecurityError.ENCRYPT_LOGIN_INFO_RSA_ERROR,e);
        }
        return encryptData;
    }

    public static void main(String[] args) {
        ThirdCertificationResource thirdCertificationResource = new ThirdCertificationResource();
        EncryptDataModel encryptDataForLogin = thirdCertificationResource.getEncryptDataForLogin();
        System.out.printf("ex"+ encryptDataForLogin.toString());
    }
}

