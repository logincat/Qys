package org.april.Utils;

import java.sql.*;

public class DerbyUtils {
    private static final String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    private static final String dbName = "EmbeddedDB";
    private static final String dbURL = "jdbc:derby:"+dbName+";create=true";

    static{
        try {
            Class.forName(driver).newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    //获取数据库连接
    public static Connection getConnection(){
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(dbURL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    //关流方法
    public static void close(ResultSet res, PreparedStatement pst, Connection conn) {
        if(null !=res) {
            try {
                res.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(null !=pst) {
            try {
                pst.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(null !=conn) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
