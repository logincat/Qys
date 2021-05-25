package org.april.Controller;

import org.april.Bean.FileMataInfo;
import org.april.Dao.impl.FileMataDaoImpl;
import org.april.Utils.CheckUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * fileServer端获取文件元数据接口
 */
public class GetFileMataInfoController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html; charset=UTF-8");
        FileMataInfo fileMataInfo = null;

        // 调用 获取文件元数据接口 需要校验
        if(CheckUtils.check(req)){
            fileMataInfo = new FileMataInfo();
            //因为功能简单 所以直接调用 DAO层
            fileMataInfo = new FileMataDaoImpl().getMataInfoById(req.getParameter("uuid"));
            PrintWriter out = resp.getWriter();
            out.write(fileMataInfo.toJson());
        }else{
            //不合法返回403状态码
        }

    }
}
