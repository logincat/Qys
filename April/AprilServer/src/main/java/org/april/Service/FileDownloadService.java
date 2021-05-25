package org.april.Service;

public interface FileDownloadService {
    /**
     *
     * @param fileUUID 文件的UUID
     * @return 该文件
     */
    public byte[] getFileById(String fileUUID);
}
