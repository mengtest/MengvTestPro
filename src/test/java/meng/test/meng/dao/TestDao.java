package meng.test.meng.dao;

import java.util.List;
import java.util.Map;

import meng.dao.base.BaseDaoI;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml", "classpath:spring-hibernate.xml", "classpath:spring-druid.xml" })
public class TestDao {
	@Autowired
	private BaseDaoI baseDao;
	
	@Test
	@Transactional
	public void test(){
//		List<Map> l = baseDao.findBySql("select t.DESCRIPTION dd from tresource t");
//		System.out.println(JSON.toJSONString(l));
//		
//		List<Map> l2 = baseDao.findBySql("select t.* from tresource t");
//		System.out.println(JSON.toJSONString(l2));
	}

}
