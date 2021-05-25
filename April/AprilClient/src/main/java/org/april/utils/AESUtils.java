package org.april.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.SecureRandom;

public class AESUtils {
    /**
     * AES加密
     * @param file 需要加密的
     * @param password 密钥
     * @return 加密后的文件
     */
    public static byte[] encrypt(File file, String password) {
        try {
            //将文件存到byte数组中
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            byte[] bytIn = new byte[(int) file.length()];
            bis.read(bytIn);
            bis.close();

            KeyGenerator kgen = KeyGenerator.getInstance("AES");// 创建AES的Key生产者
            kgen.init(128, new SecureRandom(password.getBytes()));// 利用用户密码作为随机数初始化出
            //加密没关系，SecureRandom是生成安全随机数序列，password.getBytes()是种子，只要种子相同，序列就一样，所以解密只要有password就行
            SecretKey secretKey = kgen.generateKey();// 根据用户密码，生成一个密钥
            byte[] enCodeFormat = secretKey.getEncoded();// 返回基本编码格式的密钥，如果此密钥不支持编码，则返回
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");// 转换为AES专用密钥
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器

            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化为加密模式的密码器
            byte[] result = cipher.doFinal(bytIn);// 加密
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * AES解密
     * @param content 需要解密的文件
     * @param password 密钥
     * @return  解密后的文件
     */
    public static byte[] decrypt(byte[] content, String password) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");// 创建AES的Key生产者
            kgen.init(128, new SecureRandom(password.getBytes()));
            SecretKey secretKey = kgen.generateKey();// 根据用户密码，生成一个密钥
            byte[] enCodeFormat = secretKey.getEncoded();// 返回基本编码格式的密钥
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");// 转换为AES专用密钥
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化为解密模式的密码器
            byte[] result = cipher.doFinal(content);
            return result; // 明文
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
