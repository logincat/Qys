package org.april.Bean;

import java.sql.ResultSet;
import java.util.Date;

/**
 * 原始文件名
 * 文件大小
 * 文件类型
 * 创建时间
 * 文件保存的地址
 * 文件加密数字信封
 *
 * 文件重命名(UUID)
 */
public class FileMataInfo {
    private String uuid;//重命名后的文件名
    private String name;//原始文件名
    private Long size;//文件大小
    private String type;//文件类型
    private Date createTime;//创建时间
    private String saveFileAddr;//文件保存地址
    private String fileEncrypt;//文件加密数字信封



    public FileMataInfo() {
    }

    public FileMataInfo(String uuid, String name, Long size, String type, Date createTime,
                          String saveFileAddr, String fileEncrypt) {
        this.uuid = uuid;
        this.name = name;
        this.size = size;
        this.type = type;
        this.createTime = createTime;
        this.saveFileAddr = saveFileAddr;
        this.fileEncrypt = fileEncrypt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getSaveFileAddr() {
        return saveFileAddr;
    }

    public void setSaveFileAddr(String saveFileAddr) {
        this.saveFileAddr = saveFileAddr;
    }

    public String getFileEncrypt() {
        return fileEncrypt;
    }

    public void setFileEncrypt(String fileEncrypt) {
        this.fileEncrypt = fileEncrypt;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "FileMataInfo{" +
                "name='" + name + '\'' +
                ", size=" + size +
                ", type='" + type + '\'' +
                ", createTime=" + createTime +
                ", saveFileAddr='" + saveFileAddr + '\'' +
                ", fileEncrypt='" + fileEncrypt + '\'' +
                ", uuid='" + uuid + '\'' +
                '}';
    }

    public String toJson() {
        String json =
                "[{\"uuid\":\""+this.uuid+"\",\"name\":\"" +this.name+"\",\"size\":\""+this.size+
                        "\",\"type\":\""+this.type+"\",\"createtime\":\""+this.createTime+"\"," +
                        "\"savefileaddr" +
                        "\":\""+this.saveFileAddr+
                        "\",\"fileencrypt\":\""+this.fileEncrypt+"\"}]";
        return json;
    }
}
