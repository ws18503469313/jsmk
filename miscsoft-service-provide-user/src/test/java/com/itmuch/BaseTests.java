package com.itmuch;

import java.util.Iterator;
import java.util.Set;
import java.util.Stack;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.google.gson.JsonSyntaxException;
import com.itmuch.util.JedisUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {Application.class})
public class BaseTests {
//	private static final Logger log = LoggerFactory.getLogger(BaseTests.class);
//	@Autowired
//	private JedisUtil jedisUtil;
//	
//	@Test
//	public void contextLoads() {
//		try {
//			log.info("------------------------test----------------------------");
//			Set<byte[]> keys = jedisUtil.keys("*");
//			System.out.println(keys.size());
//			Iterator<byte[]> it = keys.iterator();
//			StringBuffer sb = new StringBuffer();
//			while(it.hasNext()) {
//				byte[] key = it.next();
//				byte[] bytes = jedisUtil.get(key);
////				String abc = String.valueOf(SerializeUtils.deSerialize(key));
////				sb.append(abc);
////				sb.append("---");
////				sb.append(SerializeUtils.deSerialize(bytes));
////				sb.append("===");
//				
//				
//			}
//			System.out.println(sb.toString());
////			String[] arr = sb.toString().split("===");
////			for (int i = 0; i < arr.length; i++) {
////				String one = arr[i];
////				String [] ones = one.split("---");
////				System.out.println(ones[0]);
////				System.out.println(ones[1]);
////			}
//		} catch (JsonSyntaxException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	public static void main(String[] args) {
		Stack<Integer> stack = new Stack<Integer>();
		stack.add(1);
		stack.add(2);
		stack.add(3);
		stack.add(4);
		stack.add(5);
		Stack<Integer> stack1 = new Stack<Integer>();
		stack1.add(1);
		stack1.add(2);
		stack1.add(3);
		stack1.add(4);
		stack1.add(5);
		stack1.add(42);
		stack1.add(52);
		String json = JSON.toJSONString(stack);
		Stack<Integer> val = JSON.parseObject(json, Stack.class);
		
		System.out.println(val);
	}
}
