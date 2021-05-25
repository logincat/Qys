package org.april.Service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import org.april.Bean.FileMataInfo;
import org.april.Dao.FileMataDao;
import org.april.Dao.impl.FileMataDaoImpl;
import org.april.Service.FileUploadService;
import org.april.Utils.AESUtils;
import org.april.Utils.FIleUtils;
import org.april.Utils.RSAUtils;

import javax.servlet.http.Part;

/**
 * 文件上传接口
 */
public class FileUploadServiceImpl implements FileUploadService {
    /**
     * 加密和保存文件
     * @param fileMataInfo 文件元数据对象
     * @param file 需要被AES加密的文件
     * @return 返回 1 表示成功
     */
    @Override
    public int saveAndencrypt(FileMataInfo fileMataInfo,File file) {
        int result = 0;
        //对文件加密
        byte[] encryptFile = AESUtils.encrypt(file, fileMataInfo.getFileEncrypt());

        //将加密的文件存储到磁盘
        FIleUtils.saveFileByPath(fileMataInfo.getSaveFileAddr(),fileMataInfo.getUuid(),encryptFile);

        //对AES密钥进行RSA加密
        try {
            String encrypt = RSAUtils.encrypt(fileMataInfo.getFileEncrypt());
            fileMataInfo.setFileEncrypt(encrypt);//RSA加密后的 密钥 即 信封
            FileMataDao fileMataDao = new FileMataDaoImpl();

            //将文件元数据存储到数据库中
            result = fileMataDao.insert(fileMataInfo);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }

    /**
     * 将客户端提交过来的数据存储到 文件元数据对象
     * @param part
     * @param fileName
     * @return 文件元数据对象
     */
    @Override
    public FileMataInfo getFileMataInfo(Part part,String fileName) {
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
//        String fileName = part.getSubmittedFileName();//上传时文件的全称
        long size = part.getSize();//文件的大小 字节
        String type = fileName.substring(fileName.lastIndexOf('.')+1);//文件扩招名即文件类型
        Date createTime = new Date();//创建时间
        String folder = new SimpleDateFormat("yyyy-MM-dd").format(createTime);
        String saveFileAddr = "D://logincat//"+folder;//文件加密后需要存放的地址

        //随机生成5个字符长度的字串
        String string = "abcdefghijklmnopqrstuvwxyz";
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 5; i++) {
            int index = (int) Math.floor(Math.random() * string.length());//向下取整0-25
            sb.append(string.charAt(index));
        }

        String fileEncrypt = sb.toString();//AES密钥
        FileMataInfo fileMataInfo = new FileMataInfo(uuid,fileName,size,type,createTime,
                saveFileAddr,fileEncrypt);
        return fileMataInfo;
    }

}
