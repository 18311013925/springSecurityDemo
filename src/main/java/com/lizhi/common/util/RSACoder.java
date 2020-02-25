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
    public static final String TENATN_PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKY27bU2MWsP9nTTwvzi34pbk7ZCs965ZEdjyeVtnVFzTcMVbuyKzHw-ZRmmMXLPYf45TWRKcRNaMgB7U4JXm1TuW2xwnRcyaxTnXx8hP2Qi9BNlPHPb90PxXdsW3zTw7G875DizG3JlsP1CGS7QsK3Q8OBWe-5DJbQAPeKW7jHtAgMBAAECgYBRcoUU0RNyNQY9xS69IKcNc0Z0f0cIkqvy15EtXYNDUE8Ak1YhjvoYv7Jm2StN6RVJr-aeiGPUadQqrEQFkMxKCYrPVJvXN45K0GzcE5NLZY_nFRBtu5HNSydvYlFmcAx-ApMqecIkaKuIS7NS90y6L8OkS_QqzcHmn08j8HbfgQJBAPzozJv740vnv3juHPV2fQBkxSWxcmactWJhU2B1iRcWwL7Bl0dwbBOF8kzUn3bPXHYaytRyMcc53cKaBhiE8x0CQQCoPungHYuPnEw-DnGQDny5Jh-K-WPcIJiw2BBQ3UDkcgk-tVDJ1vJmxGihaNt5k-LAvyAU3wjJ8RAvRvNHn7ERAkEAoynp_2RVcv4TiIhORIpqANwCWNwPS2Ykm1DhAqUxiaTTOv4YhC1ifIH5HVo7euIy67tDSiZWOQr7J_ZIon77fQJAEoJpZHibo_8D_EfGgpy1aAEjszxgmhUT91Ct6teMjhVlovp7mtkE7f1prWhFzQhSB_Z-5wzqgJz-tNTPau_MoQJBAMKkDuzDSeCOu0tIdEfR0dAqhgAC3jsaPywM_UAbf3UiQnJxqEygPD8CQ3fyQJwdEdeRBeMbGDNvbwsiPxD_9qI";
    public static final String TENANT_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCmNu21NjFrD_Z008L84t-KW5O2QrPeuWRHY8nlbZ1Rc03DFW7sisx8PmUZpjFyz2H-OU1kSnETWjIAe1OCV5tU7ltscJ0XMmsU518fIT9kIvQTZTxz2_dD8V3bFt808OxvO-Q4sxtyZbD9Qhku0LCt0PDgVnvuQyW0AD3ilu4x7QIDAQAB";


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
        String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAIe41swTDVczMXNyXycRIOoU3W3Qw43lgD4XUMH5_nvsR-OA2VQkTF0bmgucW1Lh7cix4k-eYJqMYY6VVbdHL5kLV3FiidZcnnZIYhBxdlHGEozEK8UTq_3lXntj7OchZJQOvUlFgbCv2GNg9DXEDRyzmVsLTq82MqSEINPGdJEzAgMBAAECgYA6UkFrHFzFNehwFZux6_ha3nBweh8uYIqFI_u9_zFOCzCxIHNDZv2xnOTLTHsp2JAXh-kaEZKtltltLdrRkTNwTlsWl_bflW-B3ihserRQ4h2vBcAFYnA-xWWNlIkdeOy-lt2huXef3QDyW34QMAD_Qmr73i1et2jMV75900YJIQJBAMA51isz6yYN7ll6mU6BOYBQ_JhTYF73LhBPUyIQEvogIgi5PuGUt2ZEMPa240jehZVTq3ZYv0QlKjMrkZte0bsCQQC0wAEbwrcWNiTMP9pyTuRmRqJgCDdOD_nnvu-Wd-09_bWao7uj9qI7vHRVZpzysKRuhNMBUtTna-Q7JunUzSrpAkAxL1tWiJ1VimD_cjhB9xzAcXiAfWz-P58Au1tZLuW8LHao5sVpMQVQXIhuyFMVtSJXGCaaTJD8HcpTd4V1TOi7AkBwbeICn4p84W7gMXOymIoPaC5GJ7Ym818SHL0wziYe4jpdFy13Xsko-R8vo-VKPD_7ktkhQKG31W-gr6LRQFFJAkA7NcUGTDMHK_iCo5yBb199qgHeMvMQ0hvaEUEiJkkcl7VMAD6w4E_hFSDvGpHXYs4KREtuqj-JkuvRFVT8hb3N";
        //本公司的key
        String tenantPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCmNu21NjFrD_Z008L84t-KW5O2QrPeuWRHY8nlbZ1Rc03DFW7sisx8PmUZpjFyz2H-OU1kSnETWjIAe1OCV5tU7ltscJ0XMmsU518fIT9kIvQTZTxz2_dD8V3bFt808OxvO-Q4sxtyZbD9Qhku0LCt0PDgVnvuQyW0AD3ilu4x7QIDAQAB";

        String signature = "fCQhv36Y9sz4Afq0wcRHwtEX2lcVUmDMjFXfa-1UU-m_POzSj1SXk2wxNCbM1HMJkKljR9QvZBc3N1V3pG7C9itIyxSiQVvC7-j98G5ESA3Ss70M7xdb83C2k6Sq5MznhDxz9zwTdt-4NUfBVm_sH_g7wEsfHhoDb0KrojmgpPM";
        String ticket = "UgHiG-Hdw3-716oN2xgJK8ZWcQdGZgcHm1Xrgyc5N93HeIRYP4hxlSBhlyYxmsaC__1Pl9KCYiwm9TbfJ_sb8mji1Fdr7F2-cMC17d651FQBSh8do3WTTNV6HR-Qw7h20rR-OaE6sTFsFIw8a6dz49KK4LUDVCHkKAQ-qGWGn9U";
        System.out.println("签名验证：\n" + testVerifyData(ticket, signature, tenantPublicKey));

        System.out.println("解密信息：\n" + testDecryptByPrivateKey(ticket, privateKey));
    }


}
