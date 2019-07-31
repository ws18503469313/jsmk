package com.itmuch.event;

import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
@Component
public class LogEventListener implements ApplicationListener<Logvent> {


    @Override
    @Async
    public void onApplicationEvent(Logvent event)     {
        FileChannel channel= null;
        RandomAccessFile logFile = null;
        try{
            logFile = new RandomAccessFile("D:/test"+ new SimpleDateFormat("yyyy_MM_dd").format(new Date()) +".log", "rw");
            channel = logFile.getChannel();
            CharBuffer buffer = CharBuffer.allocate(1024);
            Charset charset = Charset.forName("UTF-8");
            CharsetEncoder encoder = charset.newEncoder();
            buffer.put("\n");
            buffer.put(event.getMsg());
            buffer.flip();
            channel.position(channel.size());
            channel.write(encoder.encode(buffer));
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            try{
                channel.close();
            }catch(Exception ex){
                ex.printStackTrace();
            }

        }


    }


    public static void main(String args[]) throws Exception{
        FileChannel channel= null;
        RandomAccessFile logFile = null;
        try{
            logFile = new RandomAccessFile("D:/test"+ new SimpleDateFormat("yyyy_MM_dd").format(new Date()) +".log", "rw");
            channel = logFile.getChannel();
            CharBuffer buffer = CharBuffer.allocate(1024);
            Charset charset = Charset.forName("UTF-8");
            CharsetEncoder encoder = charset.newEncoder();
            buffer.put("消息内容");
            buffer.flip();
            channel.write(encoder.encode(buffer));
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            try{
                channel.close();
            }catch(Exception ex){
                ex.printStackTrace();
            }

        }
    }
}
