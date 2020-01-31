第三方服务免密登录 使用指南
    
    为了满足客户对本公司系统和原有系统的免密对接，本公司系统推出了 免密登录 功能，以简化本公司系统与原有系统之间的登录操作。

免密登录流程
    
    1.登录本公司系统管理后台；
    2.一键生成加密公钥对；
    3.九宫格配置外部系统入口，配置免登录；
    4.本公司系统工作台左侧菜单点击外部系统连接，本公司系统生成外部认证ticket；
    5.外部系统获取本公司系统ticket，按照解密规则进行解密认证，认证通过直接登录外部系统；

免密登录的适用场景
    
    为了便于客户在本公司系统中能够一键登录到第三方系统，本公司系统系统支持一键免密登录第三方服务。可以按照用户的配置，进行各种第三方服务的登录对接。

配置环节

    登录本公司系统管理后台，添加自定义应用。详情请参考应用快速接入在本公司系统管理后台【应用管理】->【应用中心】->【应用开发】->【RSA公钥/私钥管理】进行配置。如果没有设置过密钥，请点击一键生成密钥，生成企业密钥对，包含publicKey公钥，和privateKey私钥，私钥切记妥善保存，公钥上传到本公司系统后台。

接收本公司系统免密登录个人凭据

    1.免密登录是本公司系统服务器和你服务器间的交互，本公司系统当前用户的个人凭据，通过url参数的形式传递给你服务的登录页面。

    2.你需要监听并获取你们服务登录页面url中的ticket和signature数据。代表当前登录是本公司系统系统里跳转过来的。ticket是加密后的本公司系统用户的个人凭据，signature是本公司系统对加密数据做的签名。

验证signature签名（可选）
    
    为了进一步确保数据传输安全性， 本公司系统免密登录提供了签名验证。你需要验证该ticket和signature是来自于本公司系统服务后再进行解密，与你们服务账号绑定，跳过登录，实现免密。

签名简介

    本公司系统的免密登录跳转链接url参数包含了签名字段——signature，可以使用该签名验证链接url参数中的个人凭据——ticket的合法性。签名用RSA私钥对个人凭据——ticket进行签名，以base64格式输出。

验证签名
    
    本公司系统在管理后台中提供了RSA公钥 -> wbg_public_key，供验证签名，该公钥具体获取路径：管理后台【横向导航栏】->【应用管理】->【应用中心】(左侧导航栏) ->【应用开发】->【RSA公钥/私钥管理】。验证签名需要以下几步：
    (1)从url参数里取出signature签名数据，并对其进行base64解码。
    (2)获取url参数里的ticket数据，并对其进行base64解码。
    (3)将获取到的ticket数据，本公司系统管理后台提供的RSA公钥、和base64解码后的签名 三者一同放入RSA的签名函数中进行非对称的签名运算，来判断签名是否验证通过。本公司系统提供了验证签名的Demo

解密ticket

    (1)获取url参数里的ticket数据，并对其进行base64解码。
    (2)将之前保存的企业私钥——privateKey进行base64解码。
    (3)将解码后的ticket和解码后的privateKey放入RSA的解密函数中进行解密，得到ticket的明文json格式数据。
    {
    "uid":123456,     //本公司系统用户唯一ID
    "tid":123456,     //本公司系统公司唯一ID
    "un":"张三",       //本公司系统用户名字 
    "ac":12345678901, //本公司系统用户登录账号
    "sn":"A001",      //外部系统员工唯一编号
    "ct":1479439482070 //凭证ticket生成服务器时间毫秒
    }
    (4)获取到ticket后，需要自己实现ticket中的userId，和第三方系统服务中的账号的关系绑定，直接跳过登录，实现免密登录。

代码示例:
    
    下列代码运行环境java环境1.7及1.7以上。

    代码中的Base64类需要引入commens-codec.jar包

    maven项目引入jar包，pom.xml中加入:

    <dependency>
      <groupid>commons-codec</groupid>
      <artifactid>commons-codec</artifactid>
      <version>1.10</version>
    </dependency>
                                
    非maven管理 java-web项目:
    
    前往http://mvnrepository.com/artifact/commons-codec/commons-codec/1.10页面下载该jar包。添加到项目依赖中。

验证签名代码：

    /**
     *
     * @param ciphertext 密文
     * @param sign 数字签名
     * @param publicKeyStr 本公司系统公钥
     * @return
     */
    public static boolean verifyData(String ciphertext,String sign,String publicKeyStr) throws Exception{
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
                            
解密示例代码：

    /**
     *
     * @param ciphertext 密文
     * @param privateKeyStr 私钥
     * @return
     * @throws Exception
     */
    public static String decryptByPrivateKey(String ciphertext, String privateKeyStr) throws Exception {
       // 对密文进行base64解码
        byte[]  encryptData = Base64.decodeBase64(ciphertext);
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
                            
测试代码：

    public static void main(String[] args) throws Exception{
        String privateKey =
                "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIDbDpshtBkiPxx4W1emqlAyx+lu\n"+
                "/3yuDkwGtiW53fmZ6JnZKUQ+ixlxFINenTKUilJQB7xqlJxot7y3jFJ6quzDMxjRaLRKg/Te1lhE\n"+
                "3ak4E87Rzj70QMqw1o32/1aiqcRD4gf1vm3p5e/+L8T4fUr4E89vPrW8suwL1iAvrpVfAgMBAAEC\n"+
                "gYA1yLmpAQFmeEf1fztJM7TlMr0E76LOjOxEyYxf5NwFYjuXc/Bont56qRuR1f0xbpTp1KPV/nq3\n"+
                "anI+4DI+/xq2jBKBnWMUNcm8n8Q2Lk8VNpSoAG26jEKU0hibW2QIuxhzef8RbhP7UyubF5aoUxxm\n"+
                "y6xplczxQUdDcfOX4AaBAQJBAN0+kKzgE6Qj6Qthh4av8VhtsUoedIogaF/PViB41bMEkWBniht0\n"+
            "1qLoSumf2fBMG6Xn+Pra487kPVVMvmNok98CQQCVGQrAAhz39meBgYsaFdcKVXyqxiOLXc/jWkno\n"+
            "vhw/W9eOMKF/YpfH/1rgHq6M9BWhobuc12ycyVnK5nZv1i6BAkBfyy8pKvxWHZZR7zCXCKce+p/A\n"+
            "z971z4JhuYw5Exi0Ik4vx6TsrnIgpyOIdEYQ3WM7Om17cDsi4YAM5uWodSdRAkEAkIeS5SfnPvMr\n"+
            "yN1zoxiyuMC/taeWgx2wTNqUNLo9FaoI3TwFAT/olCXTyNfVKqvdYC4weMxW6/E/SrF289hygQJA\n"+
            "WwoeIdB+WjOTJPI8nYg+ZeG91bzC4n8fL+j+Hr20jEH0bdN53hjbCZJNCPiq/iKyAYlnpCStqnrD\n"+
            "ny3G06dfYw==";
    String wbgPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCNi8REkef/8jSLtzYVpFlBcfjVQWK90vRWmcI0\n"+
            "5mNYgPv2hdimCHJcdPP3YWK7/BhmT5hsWzvdQ/jLJ2Tw9nGlz77cZTtf18sBM0pK4tM7RAt6m6ce\n"+
            "8rcpQea8lCYHc5bFalRnE4mmTIydlfBgfdWN1XYrpJZ+nPjC1HX2tV9O7wIDAQAB";
    String signature = "O-PTkJhPU7CKT3LxmrxbUFcNOkgkWe2kEoAOit6yza4Ys8JRSROPSBCls5LQhDfPXfwLVSi9DL41XC7umsuRslCs4cTc3c-N6_mJivMN_g1VADrObKnlpDJlk6neulG5BNCkzd-4Y6BDoyHaJrKtBCZhRoM_X2lM-CTMiNC_ZPU";
    String ticket = "TUUX7-1bk_yR5bBRyj-M-cBB2zBzsBOcfgr6WpX1A2u2UV3dnh-1CYUCkyudInFy8if4jFNAmMyxuaxQnhMU7pftthw51eGoD-cn3agTd2971g7PezQMixgTjoucOn8u4Eug9Ztif8bAEvsEAQBp9q9yn91jufgx3_eJqmATCXc";
 
    System.out.println("签名验证：\n"+verifyData(ticket,signature,wbgPublicKey));
 
    System.out.println("解密信息：\n"+decryptByPrivateKey(ticket,privateKey));
}
                            