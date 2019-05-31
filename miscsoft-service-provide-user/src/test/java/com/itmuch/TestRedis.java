package com.itmuch;

import java.util.Stack;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.itmuch.util.JedisUtil;

public class TestRedis extends BaseTests{

	@Autowired
	private JedisUtil jedisUtil;
	@Test
	public void test() throws Exception {
		Stack<Integer> stack = new Stack<Integer>();
		stack.add(1);
		stack.add(2);
		stack.add(3);
		stack.add(4);
		stack.add(5);
		jedisUtil.mapPut("zuul", "zuul_1", JSON.toJSONString(stack));
		Stack<Integer> stack1 = new Stack<Integer>();
		stack1.add(1);
		stack1.add(2);
		stack1.add(3);
		stack1.add(4);
		stack1.add(5);
		stack1.add(42);
		stack1.add(52);
		jedisUtil.mapPut("zuul", "zuul_1", JSON.toJSONString(stack1));
	}
}
