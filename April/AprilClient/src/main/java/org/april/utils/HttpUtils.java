package org.april.utils;

import com.google.gson.Gson;
import org.april.bean.FileMataInfo;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 服务器间通信工具
 */
public class HttpUtils {

    /**
     * 发送文件 获取uuid
     * @return uuid
     */
    public static String sendFileAndgetUUID(byte[] file){
        String uuid = "";
        try {
            URL url = new URL("http://127.0.0.1:8088/fileupload");
            HttpURLConnection huc = (HttpURLConnection)url.openConnection();
            huc.setDoOutput(true);
            huc.setDoInput(true);
            huc.setRequestMethod("POST");
            huc.setConnectTimeout(30000);
            huc.setRequestProperty("Content-Type", "multipart/form-data;boundary=*****");
            huc.connect();

            OutputStream out = new DataOutputStream(huc.getOutputStream());

            out.write(file);//将文件发送给 fileServer

            BufferedReader reader = new BufferedReader(new InputStreamReader(huc.getInputStream()));
            uuid = reader.readLine();
            return uuid;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uuid;
    }

    /**
     * user post请求发文件给 fileClient
     * fileClient post请求将文件发送给 fileServer
     * @param multipartFile
     * @return uuid
     * @throws Exception
     */
    public static String uploadFile(MultipartFile multipartFile) throws Exception {
        String uuid = "";

        //先将文件写到磁盘
        File file = new File("D://logincat//clientTemp//"+multipartFile.getOriginalFilename());
        multipartFile.transferTo(file);

        //将文件发送给fileServer

        //设置请求头
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("multipart/form-data; charset=UTF-8");
        headers.setContentType(type);

        RestTemplate restTemplate = new RestTemplate();



        MultiValueMap<String, Object>  mv = new LinkedMultiValueMap();

        FileSystemResource fileSystemResource = new FileSystemResource("D://logincat//clientTemp" +
                "//"+multipartFile.getOriginalFilename());

        mv.add("file",fileSystemResource);
        //随机生成生成签名的字符串
        String X_SID = RandomKeyUtils.getSID();
        String sign = Arrays.toString(RSAUtils.signature(X_SID));
        mv.add("X_SID",X_SID);//串
        mv.add("X_Signature",sign);//签名
        mv.add("filename",multipartFile.getOriginalFilename());//下下策解决中文乱码

        HttpEntity files = new HttpEntity(mv, headers);

        String url = "http://127.0.0.1:8088/fileupload";
        uuid = restTemplate.postForObject(url, files, String.class);
        file.delete();//删除本地缓存
        return uuid;
    }

    /**
     * 通过uuid获取文件元数据信息json
     * @param uuid
     * @return 元数据信息json
     * @throws Exception
     */
    public static String getMataInfo(String uuid) throws Exception {
        String mataJson = "";

        RestTemplate restTemplate = new RestTemplate();
        Map<String,String> map = new HashMap();
        //随机生成生成签名的字符串
        String X_SID = RandomKeyUtils.getSID();
        map.put("X_SID",X_SID);//串
        String sign = Arrays.toString(RSAUtils.signature(X_SID));
        map.put("X_Signature",sign);//签名
        map.put("uuid",uuid);

        String url = "http://127.0.0.1:8088/show?uuid={uuid}&X_SID={X_SID}&X_Signature={X_Signature}";
        mataJson = restTemplate.getForObject(url,String.class,map);

        return mataJson;
    }

    /**
     * 通过uuid获取加密后的文件
     * @param uuid
     * @return 解密的文件
     * @throws Exception
     */
    public static void download(String uuid, HttpServletResponse resp) throws Exception {

        RestTemplate restTemplate = new RestTemplate();
        Map<String,String> map = new HashMap();
        map.put("uuid",uuid);//
        //随机生成生成签名的字符串
        String X_SID = RandomKeyUtils.getSID();
        map.put("X_SID",X_SID);//串
        String sign = Arrays.toString(RSAUtils.signature(X_SID));
        map.put("X_Signature",sign);//签名

        String keyUrl = "http://127.0.0.1:8088/show?uuid={uuid}&X_SID={X_SID}&X_Signature" +
                "={X_Signature}";
        //从server端获取 元数据对象 json 字符串
        String MataJson = restTemplate.getForObject(keyUrl,String.class,map);

        MataJson = MataJson.replace("[","");
        MataJson = MataJson.replace("]","");
        FileMataInfo fileMataInfo = new Gson().fromJson(MataJson, FileMataInfo.class);
        //从server端获取信封
        byte[] encryptFileFromServer = null;
        //从server端获取加密的文件
        String fileUrl = "http://127.0.0.1:8088/download?uuid={uuid}&X_SID={X_SID}&X_Signature" +
                "={X_Signature}";
        encryptFileFromServer = restTemplate.getForObject(fileUrl, byte[].class,map);

        String decryptKey = RSAUtils.decrypt(fileMataInfo.getFileencrypt());//使用 私钥 对 key进行RSA解密

        //对 加密的文件 使用AES解密
        byte[] decryptFile = AESUtils.decrypt(encryptFileFromServer, decryptKey);

        resp.setContentType("application/octet-stream; charset=UTF-8");
        resp.setCharacterEncoding("utf-8");
        resp.setHeader("Content-Disposition",
                "attachment;filename="+ URLEncoder.encode(fileMataInfo.getName(), "utf-8"));
        //下载文件的名称
        ServletOutputStream output = resp.getOutputStream();
        output.write(decryptFile);
        output.flush();
        output.close();

    }
}
