package com.itmuch.test;

import com.cloud.util.HttpClientUtil;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BufferedHeader;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class TestDownloadImage {


    public static void main(String args[]) throws Exception{
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        InputStream inputStream = null;
        OutputStream outputStream = new FileOutputStream("D:/sykj.png");
        try{
            HttpGet httpGet = new HttpGet(new URIBuilder("http://62.234.95.108/framework").build());

            // 执行请求
            response = httpclient.execute(httpGet);
            inputStream = response.getEntity().getContent();
            byte [] buffer = new byte[1024];
            int length;
            while((length = inputStream.read(buffer)) != -1){
                outputStream.write(buffer, 0, length);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            inputStream.close();
            outputStream.close();
            response.close();
        }

    }
}
