package com.zzl.test.pagehepler;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.PageHelper;
import com.zzl.mapper.TbItemMapper;
import com.zzl.pojo.TbItem;
import com.zzl.pojo.TbItemExample;

public class TestPageHelper {
	@Test
	public void testhelper(){
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
		TbItemMapper itemMapper = applicationContext.getBean(TbItemMapper.class);
		
		PageHelper.startPage(1, 3);
//		
		TbItemExample example = new TbItemExample();
		
		List<TbItem> list = itemMapper.selectByExample(example);
		List<TbItem> list2 = itemMapper.selectByExample(example);
		
		System.out.println("list size = >>>>>>>>>>>>>>>>"+list.size());
		System.out.println("list2 size = >>>>>>>>>>>>>>>"+list2.size());
	}
	
	
}
