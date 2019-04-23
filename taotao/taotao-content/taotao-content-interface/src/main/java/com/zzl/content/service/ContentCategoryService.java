package com.zzl.content.service;

import java.util.List;

import com.zzl.common.pojo.EasyUITreeNode;
import com.zzl.common.pojo.TaotaoResult;

/**
 * @author HP
 * 内容分类Service
 */
public interface ContentCategoryService {
	
	
	/**
	 * 获取内容分类
	 * @param parentId
	 * @return
	 */
	public List<EasyUITreeNode> getContentCategoryList(Long parentId );
	
	/**
	 * 增加内容分类
	 * @param parentId
	 * @param name
	 * @return
	 */
	public TaotaoResult addContentCategory(Long parentId,String name);
	
	public TaotaoResult deleteContentCategory(Long id);
	
	public TaotaoResult updateCointentCategory(Long id,String name);
}
