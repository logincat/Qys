package org.april.web;

import org.april.utils.HttpUtils;
import org.april.utils.RSAUtils;
import org.april.utils.RandomKeyUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.*;


@Controller
public class IndexController {
    /**
     * 当user访问 fileClient时 将SID 和 signature返回给 user
     * 由user再去访问 fileServer
     * 保留在这吧 是我之前没仔细看题目要求
     * @param model
     * @return
     */
    @GetMapping("/index")
    public String index2(Model model){
        //随机生成生成签名的字符串
        String X_SID = RandomKeyUtils.getSID();

        byte[] X_Signature = null;

        try {
            X_Signature = RSAUtils.signature(X_SID);
        } catch (Exception e) {
            e.printStackTrace();
        }

        model.addAttribute("X_SID",X_SID);
        model.addAttribute("X_Signature",X_Signature);

        return "index";
    }

    /**
     * user访问 fileClient
     * @return 主页
     */
    @GetMapping("/")
    public String index(){

        return "index";
    }

    /**
     * 上传文件
     * @return uuid
     */
    @PostMapping("/upload")
    public void upload(@RequestParam("file") MultipartFile multipartFile, HttpServletResponse resp) {
        try {
            String uuid = HttpUtils.uploadFile(multipartFile);
            PrintWriter out = resp.getWriter();
            out.write(uuid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 下载文件
     * @return uuid
     */
    @GetMapping("/download")
    public void download(@RequestParam("uuid") String uuid,HttpServletResponse resp){
        //从fileSever下载加密的文件 并解密 返回给客户端
        try {
            HttpUtils.download(uuid,resp);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 显示文件元数据
     * @return uuid
     */
    @PostMapping("/show")
    public void show(@RequestParam("uuid") String uuid,HttpServletResponse resp){

        //从fileServer获取文件元数据
        try {
            resp.setContentType("text/html; charset=UTF-8");
            resp.setCharacterEncoding("utf-8");
            PrintWriter out = resp.getWriter();
            String mataJson = HttpUtils.getMataInfo(uuid);
            out.write(mataJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
