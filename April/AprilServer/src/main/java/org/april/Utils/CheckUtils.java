package org.april.Utils;

import javax.servlet.http.HttpServletRequest;

/**
 * 校验客户端
 */
public class CheckUtils {
    /**
     * 客户端将 X_SID 用私钥生成 签名 X_Signature
     * 传到服务器
     * 服务器用 公钥 对 签名 X_Signature 验签
     * @param req
     * @return
     */
    public static boolean check(HttpServletRequest req){
        boolean flag = false;
        String x_sid = req.getParameter("X_SID");
        String x_signature = req.getParameter("X_Signature");

        String str = x_signature;
        str = str.replace("[","");
        str = str.replace("]","");
        str = str.replace(" ","");

        //去除 , 并生成byte数组
        int wn = 0;
        for(int i = 0; i < str.length(); i++){
            if(str.charAt(i) == ','){wn++;}
        }
        wn++;
        byte[] signature = new byte[wn];
        for(int i = 0; i < wn; i++){
            if( i != (wn - 1)){
                signature[i] = (byte) Integer.valueOf(str.substring(0, str.indexOf(","))).intValue();
                str = str.substring(str.indexOf(",") + 1, str.length());
            }else{
                signature[i] = (byte) Integer.valueOf(str).intValue();
            }
        }


        //RSA验签
        try {
            flag = RSAUtils.check(x_sid, signature);
             return flag;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
}
