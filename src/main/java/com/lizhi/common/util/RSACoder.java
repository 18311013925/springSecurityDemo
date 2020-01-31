package com.lizhi.common.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: lizhi
 * @Date: 2020/1/15 11:32
 * @Description:
 */
public class RSACoder {
    // algorithm [ˈælɡərɪðəm]  算法;计算程序
    public static final String KEY_ALGORITHM = "RSA";

    // 签名算法
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    private static final String PUBLIC_KEY = "RSAPublicKey";

    private static final String PRIVATE_KEY = "RSAPrivateKey";

    private static final Integer SECRET_KEY_SIZE = 1024;

    //当前本公司的私钥和公钥
    public static final String TENATN_PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKItcprNtIwtW7B6Zlca6PR_3NUd4jxCg4JitAHaqpuVpw3o5qpslWlpCV6hw_z8C4Fa_hekI81NT2_tF002CreXBdUhf_CABf-9oXBNS4bgSfqWBaOzQKJ1LWAxLCGpZF0rIrNMociYrnpd5anBsjAxvJQCoG0txZVR6tNwOmdjAgMBAAECgYARBTAH6xrNs1zyEoZ3Ec3tCxZED5TUui5PGsjFgQtytKwJm63JqQetPmbLVbLZ9CHI-6G7HXDcdQanOny9j7RkEJN_HouotxWK-Q5FdjdxUEKVZdoZycrw-IM3cySiG1das8l38TAC91XAVRXqRFHQQxRN21zKn6DoD7X7kLAdkQJBAPdwUjl-MPye8rEkUV_S7i9CsEFjMcNymPo0jEVaCA3gAZ8qPTpFVnuNcxHZkctPycGHjGXHx0ey5_3M-QmyQJcCQQCnye25-UoX5rbAERa4lbhW9akmuTy3RwxnYyFATqqeN4iddSihI6RioScNSO91TU_ZhN6IuSkL-wRAdnbGRh0VAkEAtX23ceAzgjf7_x6KaWTHu_aIXY2qhJdbybodYQkGe1bhGaPbwQ_2vS0lR-Rz4dKmQ8NuoYEFlHuPWkOsLwaGBQJAcSQYyW7wJ23LjXf4ej97Lig5B4OhDXU7R_vwUAy7wDRySrrVytLd5iQE0VB9J_wZ-_M8m7U_mHSLSWyYatqcgQJATDtHHAujaFggVk7_2rZ5vy6iloXNQz6lQ9G1d9OQOoBHJ7J1nAd842pv7Dwm22t2NX9dMu-BKDz5hRNthatxig";
    public static final String TENANT_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCiLXKazbSMLVuwemZXGuj0f9zVHeI8QoOCYrQB2qqblacN6OaqbJVpaQleocP8_AuBWv4XpCPNTU9v7RdNNgq3lwXVIX_wgAX_vaFwTUuG4En6lgWjs0CidS1gMSwhqWRdKyKzTKHImK56XeWpwbIwMbyUAqBtLcWVUerTcDpnYwIDAQAB";


    /**
     * 用私钥对信息生成数字签名
     *
     * @param data       加密数据
     * @param privateKey 私钥
     * @return
     * @throws Exception
     */
    public static String sign(byte[] data, String privateKey) throws Exception {
        // 解密由base64编码的私钥
        byte[] keyBytes = Base64.decodeBase64(privateKey);
        // 构造PKCS8EncodedKeySpec对象
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        // KEY_ALGORITHM 指定的加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        // 取私钥匙对象
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 用私钥对信息生成数字签名
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(priKey);
        signature.update(data);
        return Base64.encodeBase64URLSafeString(signature.sign());
    }

    /**
     * 校验数字签名
     *
     * @param data      加密数据
     * @param publicKey 公钥
     * @param sign      数字签名
     * @return 校验成功返回true 失败返回false
     * @throws Exception
     */
    public static boolean verify(byte[] data, String publicKey, String sign)
            throws Exception {
        // 解密由base64编码的公钥
        byte[] keyBytes = Base64.decodeBase64(publicKey);
        // 构造X509EncodedKeySpec对象
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        // KEY_ALGORITHM 指定的加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        // 取公钥匙对象
        PublicKey pubKey = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(pubKey);
        signature.update(data);
        // 验证签名是否正常
        return signature.verify(Base64.decodeBase64(sign));
    }


    /**
     * 解密<br>
     * 用私钥解密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] data, String key)
            throws Exception {
        // 对密钥解密
        byte[] keyBytes = Base64.decodeBase64(key);
        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }


    /**
     * 解密<br>
     * 用公钥解密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] data, String key)
            throws Exception {
        // 对密钥解密
        byte[] keyBytes = Base64.decodeBase64(key);
        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);
        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }

    /**
     * 加密<br>
     * 用公钥加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, String key)
            throws Exception {
        // 对公钥解密
        byte[] keyBytes = Base64.decodeBase64(key);
        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);

        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }

    /**
     * 加密<br>
     * 用私钥加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, String key)
            throws Exception {
        // 对密钥解密
        byte[] keyBytes = Base64.decodeBase64(key);
        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    /**
     * 取得私钥
     *
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);

        return Base64.encodeBase64URLSafeString(key.getEncoded());
    }

    /**
     * 取得公钥
     *
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPublicKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);

        return Base64.encodeBase64URLSafeString(key.getEncoded());
    }

    /**
     * 初始化密钥
     *
     * @return
     * @throws Exception
     */
    public static Map<String, Object> initKey() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator
                .getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(SECRET_KEY_SIZE);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        // 公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        // 私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    /**
     * @param ciphertext   密文
     * @param sign         数字签名
     * @param publicKeyStr 微办公公钥
     * @return
     */
    private static boolean testVerifyData(String ciphertext, String sign, String publicKeyStr) throws Exception {
        // 对密文base64解码
        byte[] encryptData = Base64.decodeBase64(ciphertext);
        // 对密钥base64解码
        byte[] keyBytes = Base64.decodeBase64(publicKeyStr);
        // 构造X509EncodedKeySpec对象
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        // KEY_ALGORITHM 指定的加密算法
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        // 取公钥匙对象
        PublicKey pubKey = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initVerify(pubKey);
        signature.update(encryptData);
        // 验证签名是否正常
        return signature.verify(Base64.decodeBase64(sign));
    }

    /**
     * @param ciphertext    密文
     * @param privateKeyStr 私钥
     * @return
     * @throws Exception
     */
    private static String testDecryptByPrivateKey(String ciphertext, String privateKeyStr) throws Exception {
        // 对密文进行base64解码
        byte[] encryptData = Base64.decodeBase64(ciphertext);
        // 对私钥进行base64解码
        byte[] keyBytes = Base64.decodeBase64(privateKeyStr);
        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cipher.doFinal(encryptData));
    }


    public static void main(String[] args) throws Exception {
 // 商户请求获取到的私钥
        String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJ8juH3Tff8Zn_r6ZyEoazCbv_T2PRCegG_JsU7LKOCBHN0OrnbsP0Dly7gFxalsQ6NcMhQwSxqQ4ahORfPby6tzhOQrKYn0PvvJudTQS9Ibbc5hk1nW0J8yAjbEGgHHNvrybHCI4vIvzby4ngdk_rWftxL6g97M-r__LADJFlFLAgMBAAECgYBIvOf_wyJM7mZ8LeXjdvj_f8EOc1pX9UqZTeQYMq3dZ8ZMmABQZQ1IWYBy_VhqZwzHZ0WQir5ZRy2NYzGAE8-UMy2jKyC8e0qDiNOc6ev0cA7i6aTC4jWpekIbr-8_sXJ5MFPvoZJVzgGkGpRI6uaozuJtNrqM6f6t00jpEIum-QJBAPVmuJF0oFJ3aG4bm23sfVeVlR-LGIpBgPjjGsMoSqTZ1S4sW5K74TvaC9kRPSvLNmZj8NhliEf0HFlObctPYJ0CQQCmAz85cZKA8gANsWdoxkrGQqqO7L2RpDifdOnJP91UX-B54XEv8SU0U4bKREe2q7w68k35RRkK4RbIMGrn7VEHAkEAg51FDhYbyj7qjmtWED2O-ANsyE8kqTn6fwHmn5ajegiukiYOMwN7CEuooRHi2fMSSRX-3UCPN_APqEItFqaK7QJAdXFYme_Bn90TspwtNs8I-QcSl_fdCBpwQEY6WbgHgPgu2qplI3BqTIFGmvMA9La7e8wM42pBlBO2xTqTdq3JowJBAJWFTeYm4CSBOmZPY_MxPnvwitaiWechCO4NjVyvAX9RfhUe08Em8WlEbCHuqCffJtImW2yy1j_QOQaFz77gXhg";
        //本公司的key
        String tenantPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCiLXKazbSMLVuwemZXGuj0f9zVHeI8QoOCYrQB2qqblacN6OaqbJVpaQleocP8_AuBWv4XpCPNTU9v7RdNNgq3lwXVIX_wgAX_vaFwTUuG4En6lgWjs0CidS1gMSwhqWRdKyKzTKHImK56XeWpwbIwMbyUAqBtLcWVUerTcDpnYwIDAQAB";


        String signature = "JEq5tg8-lhUvCFWcFcEQdt_dVbL8E-5aoBxNq8t-SwQHIrOpvWnueyjl7PY1B5cYgtr65MciHC-adb7OV9HqlrBfLBe7QvAq0FTlmCSUR1SWTrqdIs4uqr1IUtlFe6CI5yrpuXUohnqvO8IylcfzYGVGyQwqANYKhb7Rd3RErMA";

        String ticket = "CxGOUAUjusdSn7m2RMKHpFO-h3Kcado4d9u8qzN8C6z9p1IaZBiX81ETPhTCQyLAdEXRZJ1ZaGxt1hh9spcyZ5x1n3cCB1Td4fFlSWzeBOw23Vfmy3Hn5A0bLS6r3ajpmuts5myH3J49ZEhsxr-wFVSGz3ueCO1n6VxupC6h14Y";

        System.out.println("签名验证：\n" + testVerifyData(ticket, signature, tenantPublicKey));

        System.out.println("解密信息：\n" + testDecryptByPrivateKey(ticket, privateKey));
    }


}
