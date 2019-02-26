package com.itmuch.concurrent;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Test;

import com.itmuch.BaseTests;
/**
 * 即如果高并发情况下,要将事务加锁,必须将整个事务都加上,
 * 而不能在对方法进行添加事务的同时,又对方法加锁,这样会导致事务失效.
 * @author 86185
 *
 */
public class TestConcurrent extends BaseTests {
	
	
	@Test
	private void startThread() {
		for (int i = 0; i < 5000; i++) {
			ThreadConnector connector = new ThreadConnector();
			connector.start();
		}
	}


	public static class ThreadConnector extends Thread {
		@Override
		public void run() {
			super.run();
			try {
				URL url = new URL("http://localhost/index/addAge");
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.connect();
//				String info = Urle
	
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
				StringBuffer sb = new StringBuffer();
				String line = "";
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
				reader.close();
				// 断开连接
				connection.disconnect();
				System.out.println(Thread.currentThread().getName()+"-------"+sb);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
