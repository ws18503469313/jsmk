package com.itmuch.util;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

public class ImageUtils {

    public static void writeImage(HttpServletResponse resp, String filename)  throws Exception {
        String path = "";
        if(System.getProperty("os.name").toLowerCase().contains("windows")) path = "d:/" + filename;
        else path = "/usr/"+ filename;

        File file = new File(path);
        resp.setContentType("image/jpeg"); // 设置返回的文件类型
        OutputStream outStream = resp.getOutputStream();// 得到向客户端输出二进制数据的对象
        FileInputStream fis = new FileInputStream(file); // 以byte流的方式打开文件
        // 读数据
        byte data[] = new byte[1000];
        while (fis.read(data) > 0) {
            outStream.write(data);
        }
        fis.close();

        outStream.write(data); // 输出数据
        outStream.close();
    }
}
