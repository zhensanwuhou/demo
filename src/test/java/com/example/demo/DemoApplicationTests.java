package com.example.demo;

import com.example.demo.controller.HelloWorldController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Resource
	private HelloWorldController helloWorldController;

	@Test
	public void getHello() throws Exception {
		System.out.println(helloWorldController.index());
	}

}
