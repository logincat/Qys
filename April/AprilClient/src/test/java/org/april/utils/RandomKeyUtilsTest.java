package org.april.utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * 工具类测试
 *
 */
public class RandomKeyUtilsTest {
    /**
     * 获取随机密钥
     */
    @Test
    public void testGetSID() {
        String sid = RandomKeyUtils.getSID();
        assertNull(sid);
    }
}