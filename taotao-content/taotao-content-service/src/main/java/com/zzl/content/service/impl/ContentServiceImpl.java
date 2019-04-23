package com.zzl.content.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sun.jarsigner.ContentSigner;
import com.zzl.common.pojo.TaotaoResult;
import com.zzl.common.util.JsonUtils;
import com.zzl.content.jedis.JedisClient;
import com.zzl.content.service.ContentService;
import com.zzl.mapper.TbContentMapper;
import com.zzl.pojo.TbContent;
import com.zzl.pojo.TbContentCategoryExample.Criteria;
import com.zzl.pojo.TbContentExample;

@Service
public class ContentServiceImpl implements ContentService{
	
	@Autowired
	private JedisClient jedisClient;
	
	@Autowired
	private TbContentMapper mapper;
	
	@Value("${CONTENT_KEY}")
	private String CONTENT_KEY;
	
	@Override
	public TaotaoResult saveContent(TbContent tbContent) {
		
		tbContent.setCreated(new Date());
		tbContent.setUpdated(new Date());
		
		
		mapper.insertSelective(tbContent);
		return TaotaoResult.ok();
	}

	@Override
	public List<TbContent> getContentList(long cid) {
		
		try {
			String json = jedisClient.hget(CONTENT_KEY, cid+"");
			if(null != json && json.trim().toString().length() > 0 ){
				System.out.println("有缓存...");
				List<TbContent> list = JsonUtils.jsonToList(json, TbContent.class);
				return list;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		System.out.println("没有缓存...");
		//根据cid查询内容列表
		TbContentExample example = new TbContentExample();
		//设置查询条件
		com.zzl.pojo.TbContentExample.Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(cid);
		//执行查询
		List<TbContent> list = mapper.selectByExample(example);
		
		try {
			jedisClient.hset(CONTENT_KEY, cid+"", JsonUtils.objectToJson(list));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	
	
}
