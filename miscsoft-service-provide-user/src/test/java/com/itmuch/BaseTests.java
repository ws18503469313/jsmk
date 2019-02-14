package com.itmuch;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {Application.class})
public class BaseTests {
	private static final Logger log = LoggerFactory.getLogger(BaseTests.class);
	
	
	@Test
	public void contextLoads() {
		log.info("------------------------test----------------------------");
	}

}
