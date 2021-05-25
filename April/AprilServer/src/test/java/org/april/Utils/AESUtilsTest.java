package org.april.Utils;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;

import static org.junit.Assert.*;

public class AESUtilsTest {

    @Test
    public void encrypt() throws Exception {
        File file = new File("test.txt");
        boolean exists = file.exists();
        System.out.println("file exists is:"+exists);

        FileInputStream fis = new FileInputStream(file);
        String context = "";
        int len = 0;
        byte[] buf = new byte[1024];
        while((len = fis.read(buf))!= -1) {
            context = context + new String(buf,0,len);
        }
        System.out.println("file context is:"+context);

        String key = "123";
        byte[] encrypt = AESUtils.encrypt(file, key);

        System.out.println("encrypt file byte is "+ Arrays.toString(encrypt));
        System.out.println("encrypt file string is" + new String(encrypt));
    }

    @Test
    public void decrypt()  throws Exception{
        File file = new File("test.txt");
        String key = "123";
        FileInputStream fis = new FileInputStream(file);
        String context = "";
        int len = 0;
        byte[] buf = new byte[1024];
        while((len = fis.read(buf))!= -1) {
            context = context + new String(buf,0,len);
        }
        System.out.println("file context is:"+context);
        byte[] encrypt = AESUtils.encrypt(file, key);
        System.out.println("encrypt file byte is "+ Arrays.toString(encrypt));

        byte[] decrypt = AESUtils.decrypt(encrypt, key);
        String s = new String(decrypt, 0, decrypt.length);
        System.out.println("decrypt is:"+s);
    }
}