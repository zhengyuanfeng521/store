package com.zzl.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zzl.common.pojo.EasyUITreeNode;
import com.zzl.common.pojo.TaotaoResult;
import com.zzl.content.service.ContentCategoryService;
import com.zzl.mapper.TbContentCategoryMapper;
import com.zzl.pojo.TbContentCategory;
import com.zzl.pojo.TbContentCategoryExample;
import com.zzl.pojo.TbContentCategoryExample.Criteria;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService{
	
	@Autowired
	private TbContentCategoryMapper mapper;

	@Override
	public List<EasyUITreeNode> getContentCategoryList(Long parentId) {
		
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> list = mapper.selectByExample(example);
		List<EasyUITreeNode> resultList = new ArrayList<>();
		for(TbContentCategory tbContentCategory:list){
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(tbContentCategory.getId());
			node.setText(tbContentCategory.getName());
			node.setState(tbContentCategory.getIsParent()?"closed":"open");
			resultList.add(node);
		}
		return resultList;
	}

	@Override
	public TaotaoResult addContentCategory(Long parentId, String name) {
		
		TbContentCategory tbContentCategory = new TbContentCategory();
		tbContentCategory.setIsParent(false);
		tbContentCategory.setName(name);
		tbContentCategory.setParentId(parentId);
		tbContentCategory.setSortOrder(1);
		tbContentCategory.setStatus(1);
		tbContentCategory.setCreated(new Date());
		tbContentCategory.setUpdated(new Date());
		
		mapper.insert(tbContentCategory);
		TbContentCategory parentNode = mapper.selectByPrimaryKey(parentId);
		if(!parentNode.getIsParent()){
			parentNode.setIsParent(true);
			mapper.updateByPrimaryKey(tbContentCategory);
		}
		return TaotaoResult.ok(tbContentCategory);
	}

	@Override
	public TaotaoResult deleteContentCategory(Long id) {
		mapper.deleteByPrimaryKey(id);
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult updateCointentCategory(Long id, String name) {
		
		TbContentCategory tbContentCategory = mapper.selectByPrimaryKey(id);
		tbContentCategory.setName(name);
		mapper.updateByPrimaryKeySelective(tbContentCategory);
		
		return TaotaoResult.ok();
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
