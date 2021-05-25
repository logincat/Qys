package org.april.Service.impl;

import org.april.Bean.FileMataInfo;
import org.april.Dao.FileMataDao;
import org.april.Dao.impl.FileMataDaoImpl;
import org.april.Service.FileDownloadService;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileDownloadServiceImpl implements FileDownloadService {
    /**
     * 通过文件的uuid下载文件
     * @param fileUUID 文件的UUID
     * @return 文件byte数组
     */
    public byte[] getFileById(String fileUUID) {
        //因为文件是被加密存储的 所以需要 文件的元数据信息 中的数字信封进行解密
        FileMataDao fileMataDao = new FileMataDaoImpl();
        FileMataInfo fileMataInfo = fileMataDao.getMataInfoById(fileUUID);
        String fileAddr = fileMataInfo.getSaveFileAddr()+"//"+fileUUID;//获取文件存储的地址

        Path path = Paths.get(fileAddr);
        File file = path.toFile();
        //将加密过的文件存储到 字节数组中
        BufferedInputStream bis = null;
        byte[] byteFile = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(file));
            byteFile  = new byte[(int) file.length()];
            bis.read(byteFile);
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return byteFile;
    }
}
