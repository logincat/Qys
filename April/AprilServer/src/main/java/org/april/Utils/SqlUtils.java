package org.april.Utils;

/**
 * 数据库操作的sql语句
 */
public class SqlUtils {
    /**
     * 建表
     * @return
     */
    public static String createTableSql(){
        String sql = null;
        sql = "CREATE TABLE filematainfo (uuid VARCHAR(1024) PRIMARY KEY,name VARCHAR(1024),size INTEGER," +
                "type VARCHAR(32),createtime DATE,savefileaddr VARCHAR(1024),fileencrypt VARCHAR(1024))";
        return sql;
    }

    /**
     * uuid name size type createtime
     * savefileaddr fileencrypt
     * @return 插入sql语句
     */
    public static String getInsertSql(){
       String sql = null;
       sql = "insert into filematainfo (uuid,name,size,type,createtime,savefileaddr,fileencrypt) " +
               "values(?,?,?,?,?,?,?)";
       return sql;
    }

    public static String getInfoSql(String uuid){
        String sql = null;
        sql = "select * from filematainfo where uuid='" + uuid + "'";
        return sql;
    }

    public static String getSelectALLSql(){
        String sql = null;
        sql = "SELECT * FROM filematainfo";
        return sql;
    }
}
