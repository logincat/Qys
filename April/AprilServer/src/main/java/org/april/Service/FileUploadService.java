package org.april.Service;

import org.april.Bean.FileMataInfo;
import org.april.Dao.impl.FileMataDaoImpl;

import javax.servlet.http.Part;
import java.io.File;

/**
 * 文件上传接口
 */
public interface FileUploadService {

    /**
     * 对上传的文件 进行加密和保存
     * @param fileMataInfo 文件元数据对象
     * @return 存储结果
     */
    public int saveAndencrypt(FileMataInfo fileMataInfo, File file);


    /**
     * 生成文件元数据对象
     * @param part
     * @param fileName 因为中文乱码
     * @return
     */
    public FileMataInfo getFileMataInfo(Part part,String fileName);
}
