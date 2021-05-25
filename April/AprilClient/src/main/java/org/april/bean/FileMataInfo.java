package org.april.bean;

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
    //为了方便Gson 转对象 哈哈哈
    private String uuid;//重命名后的文件名
    private String name;//原始文件名
    private String size;//文件大小
    private String type;//文件类型
    private String createtime;//创建时间
    private String savefileaddr;//文件保存地址
    private String fileencrypt;//文件加密数字信封

    public FileMataInfo() {
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getSavefileaddr() {
        return savefileaddr;
    }

    public void setSavefileaddr(String savefileaddr) {
        this.savefileaddr = savefileaddr;
    }

    public String getFileencrypt() {
        return fileencrypt;
    }

    public void setFileencrypt(String fileencrypt) {
        this.fileencrypt = fileencrypt;
    }

    public String toJson() {
        String json =
                "[{\"uuid\":\""+this.uuid+"\",\"name\":\"" +this.name+"\",\"size\":\""+this.size+
                        "\",\"type\":\""+this.type+"\",\"createtime\":\""+this.createtime+"\"," +
                        "\"savefileaddr" +
                        "\":\""+this.savefileaddr+
                        "\",\"fileencrypt\":\""+this.fileencrypt+"\"}]";
        return json;
    }
}
