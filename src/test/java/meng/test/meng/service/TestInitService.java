package meng.test.meng.service;

import meng.service.InitServiceI;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml", "classpath:spring-hibernate.xml"})

public class TestInitService {
	@Autowired
	private InitServiceI initService;
	
	@Test
	public void initDb(){
		initService.initDb();
	}

}
