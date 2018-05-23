package test;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.cyc.common.pojo.EUDataResult;
import cn.cyc.mapper.TbItemMapper;
import cn.cyc.pojo.TbItem;
import cn.cyc.pojo.TbItemExample;
import cn.cyc.pojo.TbItemExample.Criteria;

public class TestPageHelper {

	@Test
	public void test(){
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
		TbItemMapper bean = ac.getBean(TbItemMapper.class);
		TbItemExample example = new TbItemExample();
		//测试新版本的PageHelper可不可以进行条件查询
		Criteria createCriteria = example.createCriteria();
		PageHelper.startPage(1, 10);
		List<TbItem> selectByExample = bean.selectByExample(example);
		for(TbItem x:selectByExample)
		System.out.println(x.getTitle());
		
		PageInfo<TbItem> pageInfo = new PageInfo<>(selectByExample);
		System.out.println(pageInfo);
	}
}
