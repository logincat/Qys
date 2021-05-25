package org.april.Dao.impl;

import org.april.Bean.FileMataInfo;
import org.april.Dao.FileMataDao;
import org.april.Utils.DerbyUtils;
import org.april.Utils.SqlUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class FileMataDaoImpl implements FileMataDao {
    /**
     * 将文件的元数据持久化到数据库中
     *
     * @param fileMataInfo 元数据对象
     * @return
     */
    public int insert(FileMataInfo fileMataInfo) {
        String sql = SqlUtils.getInsertSql();
        int result = 0;
        Connection conn = DerbyUtils.getConnection();
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, fileMataInfo.getUuid());
            pst.setString(2, fileMataInfo.getName());
            pst.setLong(3, fileMataInfo.getSize());
            pst.setString(4, fileMataInfo.getType());
            pst.setDate(5, new java.sql.Date(fileMataInfo.getCreateTime().getTime()));
            pst.setString(6, fileMataInfo.getSaveFileAddr());
            pst.setString(7, fileMataInfo.getFileEncrypt());
            result = pst.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();//出错的话事务回滚
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            DerbyUtils.close(null, pst, conn);//最后关闭所有连接
        }
        return result;
    }

    /**
     * 通过uuid查找文件元数据信息
     *
     * @param fileUUID
     * @return 元数据对象
     */
    @Override
    public FileMataInfo getMataInfoById(String fileUUID) {
        String sql = SqlUtils.getInfoSql(fileUUID);
        FileMataInfo mata = null;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            conn = DerbyUtils.getConnection();
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            String name = "";
            long size = 0;
            String type = "";
            Date createTime = null;
            String saveFileAddr = "";
            String fileEncrypt = "";


            while (rs.next()) {
                name = rs.getString("name");
                size = rs.getLong("size");
                type = rs.getString("type");
                createTime = rs.getDate("createtime");
                saveFileAddr = rs.getString("saveFileAddr");
                fileEncrypt = rs.getString("fileEncrypt");
            }
            mata = new FileMataInfo(fileUUID, name, size,type,createTime,saveFileAddr,fileEncrypt);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DerbyUtils.close(rs, pst, conn);
        }
        return mata;
    }


}
