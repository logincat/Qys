package org.april.Dao;

import org.april.Bean.FileMataInfo;

import java.util.List;

/**
 * 文件元数据的持久化
 */
public interface FileMataDao {
    /**
     * 元数据写入到数据库
     * @param fileMataInfo 元数据对象
     * @return 执行结果
     */
    public int insert(FileMataInfo fileMataInfo);

    /**
     * 通过元数据uuid 获取元数据信息
     * @param fileUUID
     * @return 元数据对象
     */
    public FileMataInfo getMataInfoById(String fileUUID);

    /**
     * 查找数据库中所有数据
     * @return 数据库中所有元数据对象
     */
//    public List<FileMataInfo> queryALL();
}
