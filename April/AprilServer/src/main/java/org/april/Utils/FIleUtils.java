package org.april.Utils;

import java.io.*;

/**
 * 存储文件
 * 下载文件
 */
public class FIleUtils {

    /**
     * 获取指定路径文件数据
     * @param filePath
     * @return 字符串类型的文件
     */
    public static String getFileByPath(String filePath) {
        File file = new File(filePath);
        String result = "";
        try {
            InputStream is = new FileInputStream(file);
            int len = 0;
            byte[] buf = new byte[1024];
            while((len = is.read(buf))!= -1) {
                result = result + new String(buf,0,len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 通过文件保存路径保存文件
     * @param filePath
     * @return 0 表示存储失败 1表示存储成功
     */
    public static int saveFileByPath(String filePath,String filename,byte[] file){
        int result = 0;

        File outfiledir = new File(filePath);
        if (!outfiledir.exists()) {
            outfiledir.mkdir();
        }
        File outfile = new File(filePath+"//"+filename);
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(
                    new FileOutputStream(outfile));
            bos.write(file);
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
