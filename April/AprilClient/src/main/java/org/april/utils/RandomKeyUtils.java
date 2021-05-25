package org.april.utils;

/**
 * 随机 字符串 生成工具
 */
public class RandomKeyUtils {
    public static String getSID(){
        //随机生成5个字符长度的字串
        String string = "abcdefghijklmnopqrstuvwxyz";
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 5; i++) {
            int index = (int) Math.floor(Math.random() * string.length());//向下取整0-25
            sb.append(string.charAt(index));
        }
        return sb.toString();
    }
}
