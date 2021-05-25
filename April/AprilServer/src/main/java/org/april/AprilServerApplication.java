package org.april;

import org.april.Controller.FileDownloadController;
import org.april.Controller.FileUploadController;
import org.april.Controller.GetFileMataInfoController;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.MultipartConfigElement;
import javax.servlet.annotation.MultipartConfig;

/**
 *
 */
@MultipartConfig
public class AprilServerApplication {
    public static void main(String[] args) {
        Server server = new Server(8088);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        ServletHolder fileUploadServletHolder = new ServletHolder(new FileUploadController());
        fileUploadServletHolder.getRegistration().setMultipartConfig(new MultipartConfigElement(
                "D://logincat//serverTemp", 1048576, 1048576, 262144));
        context.addServlet(fileUploadServletHolder, "/fileupload");
        context.addServlet(GetFileMataInfoController.class, "/show");
        context.addServlet(FileDownloadController.class, "/download");
        server.setHandler(context);

        try {
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
