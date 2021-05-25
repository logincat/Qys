package org.april.Controller;

import org.april.Service.FileDownloadService;
import org.april.Service.impl.FileDownloadServiceImpl;
import org.april.Utils.CheckUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * fileServer端文件下载接口
 */
public class FileDownloadController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        req.setCharacterEncoding("utf-8");
        res.setContentType("text/html; charset=UTF-8");
        ServletOutputStream output = null;
        //调用 文件下载接口 需要校验
        if(CheckUtils.check(req)){
            String fileUUID =  req.getParameter("uuid");
            FileDownloadService fileDownloadService = new FileDownloadServiceImpl();
            byte[] bytes = fileDownloadService.getFileById(fileUUID);//通过UUID获取文件并存储为字节数组
            output = res.getOutputStream();
            output.write(bytes);
            output.flush();//响应给Client
        }else{
        }

    }
}
