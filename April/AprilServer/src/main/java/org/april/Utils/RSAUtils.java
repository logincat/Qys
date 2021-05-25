package org.april.Utils;

import java.io.*;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;


public class RSAUtils {
    //公钥地址
    private static final String rsa_pub_addr = System.getProperty("user.dir")+File.separator+
            "AprilServer"+File.separator+"src" +File.separator+
            "main"+File.separator+"resources"+File.separator+"rsa_pub";
    //私钥地址
    private static final String rsa_addr = System.getProperty("user.dir")+File.separator+
            "AprilClient" +File.separator+ "src" +File.separator+ "main"+File.separator+
            "resources"+File.separator+"rsa";

    //签名算法
    public static final String SIGN_ALGORITHMS = "SHA1WithRSA";


    /**
     * RSA公钥加密
     * @param msg
     * @return
     * @throws Exception
     */
    public static String encrypt(String msg) throws Exception {

        RSAPublicKey pbk = getPublicKey();

        BigInteger e = pbk.getPublicExponent();
        BigInteger n = pbk.getModulus();
        // 获取明文的大整数
        byte ptext[] = msg.getBytes("utf-8");
        BigInteger m = new BigInteger(ptext);
        // 加密明文
        BigInteger fileEncrypt = m.modPow(e, n);
        //生成数字信封
        return fileEncrypt.toString();
    }

    /**
     * 私钥解密
     * @param fileEncrypt
     * @return
     */
    public static String decrypt(String fileEncrypt) throws Exception {
        //从数据库中获取数字信封fileEncrypt
        BigInteger c = new BigInteger(fileEncrypt);
        // 获取私钥
        RSAPrivateKey prk = getPrivateKey();

        // 获取私钥的参数d,n
        BigInteger d = prk.getPrivateExponent();
        BigInteger n = prk.getModulus();

        // 解密明文
        BigInteger m = c.modPow(d, n);

        // 计算明文对应的字符串并返回。
        byte[] mt = m.toByteArray();
        return new String(mt, "utf-8");

    }

    /**
     * 私钥签名
     * @param SID 需要签名的 数据
     * @return 签名signature
     * @throws Exception
     */
    public static byte[] signature(String SID) throws Exception{
        // 获取私钥
        RSAPrivateKey privateKey = getPrivateKey();
        Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
        signature.initSign(privateKey);
        signature.update( SID.getBytes());
        byte[] signed = signature.sign();
        return signed;
    }

    /**
     * 公钥验签
     * @param SID 被签名的数据
     * @param sign 签名
     * @return 验签结果
     * @throws Exception
     */
    public static boolean check(String SID,byte[] sign) throws Exception{
        //获取公钥
        RSAPublicKey publicKey = getPublicKey();
        Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
        signature.initVerify(publicKey);
        signature.update(SID.getBytes());
        return signature.verify(sign);
    }

    /**
     * 生成公钥文件和私钥文件
     * 分别保存在 项目的 resource下面
     */
    private static void generateKey() {
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
            final int KEY_SIZE = 1024;
            keyPairGen.initialize(KEY_SIZE, new SecureRandom());
            KeyPair key = keyPairGen.genKeyPair();

            File privateKeyFile = new File(rsa_addr);
            File publicKeyFile = new File(rsa_pub_addr);

            if (privateKeyFile.getParentFile() != null) {
                privateKeyFile.getParentFile().mkdirs();
            }
            privateKeyFile.createNewFile();
            if (publicKeyFile.getParentFile() != null) {
                publicKeyFile.getParentFile().mkdirs();
            }
            publicKeyFile.createNewFile();

            ObjectOutputStream publicKeyOS = new ObjectOutputStream(new FileOutputStream(publicKeyFile));
            publicKeyOS.writeObject(key.getPublic());
            publicKeyOS.close();
            ObjectOutputStream privateKeyOS = new ObjectOutputStream(new FileOutputStream(privateKeyFile));
            privateKeyOS.writeObject(key.getPrivate());
            privateKeyOS.close();
        } catch (Exception e) {
        }
    }

    /**
     * 获取私钥
     * @return
     * @throws Exception
     */
    private static RSAPrivateKey getPrivateKey() throws Exception {
        FileInputStream fis = new FileInputStream(rsa_addr);
        BufferedInputStream bis = new BufferedInputStream(fis);
        ObjectInputStream ois = new ObjectInputStream(bis);
        RSAPrivateKey privateKey = (RSAPrivateKey) ois.readObject();
        ois.close();
        bis.close();
        fis.close();
        return privateKey;
    }

    /**
     * 获取公钥
     * @return
     * @throws Exception
     */
    private static RSAPublicKey getPublicKey() throws Exception {
        FileInputStream fis = new FileInputStream(rsa_pub_addr);
        BufferedInputStream bis = new BufferedInputStream(fis);
        ObjectInputStream ois = new ObjectInputStream(bis);
        RSAPublicKey publicKey = (RSAPublicKey) ois.readObject();
        ois.close();
        bis.close();
        fis.close();
        return publicKey;
    }
}
