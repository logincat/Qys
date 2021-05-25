package org.april.Controller;

import org.april.Bean.FileMataInfo;
import org.april.Service.FileUploadService;
import org.april.Service.impl.FileUploadServiceImpl;
import org.april.Utils.CheckUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;


/**
 * 文件上传
 * String uuid
 * String name
 * Long size
 * String type
 * Date createTime
 * String saveFileAddr
 * String fileEncrypt
 *
 */
@MultipartConfig
public class FileUploadController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * fileServer端的文件下载接口
     * 响应fileClient的post请求
     * 返回 uuid
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html; charset=UTF-8");
        Part part = req.getPart("file");
        String filename = req.getParameter("filename");

        part.write(filename);//将文件内容写入到零时指定的磁盘位置 在主程序中定义了文件的根目录
        File fileFolder = new File("D://logincat//serverTemp");
        if(!fileFolder.exists()){
            fileFolder.mkdir();//如果文件夹不存在则创建
        }
        Path path = Paths.get(fileFolder+"//"+filename);
        File file = path.toFile();//把文件再取出来

        FileUploadService fileUploadService = new FileUploadServiceImpl();
        FileMataInfo fileMataInfo = null;
        PrintWriter out = resp.getWriter();
        //调用 文件上传接口 需要校验
        if(CheckUtils.check(req)){
            //校验成功才执行 将元数据存入数据库 以及 对文件进行加密存储
            fileMataInfo = fileUploadService.getFileMataInfo(part,filename);
            int result = fileUploadService.saveAndencrypt(fileMataInfo, file);//结果
            //需要返回 UUID给客户端
            out.write(fileMataInfo.getUuid());
            out.close();
        }else{
            out.write("文件上传error");
            out.close();
        }


    }

}
