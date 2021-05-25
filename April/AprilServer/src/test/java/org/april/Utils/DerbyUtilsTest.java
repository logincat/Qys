package org.april.Utils;

import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.Assert.*;

/**
 * 工具类测试
 */
public class DerbyUtilsTest {
    /**
     * 测试数据库连接
     */
    @Test
    public void testGetConnection() {
        Connection conn = DerbyUtils.getConnection();
        assertNotNull(conn);
    }

    @Test
    public void testCreateTable() throws Exception{
        Connection conn = DerbyUtils.getConnection();
        PreparedStatement pst =conn.prepareStatement(SqlUtils.createTableSql());
        ResultSet res = pst.executeQuery();
        DerbyUtils.close(res, pst, conn);
    }
}