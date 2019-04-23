package com.zzl.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zzl.common.pojo.EasyUITreeNode;
import com.zzl.common.pojo.TaotaoResult;
import com.zzl.content.service.ContentCategoryService;

/**
 * @author HP
 * 内容分类的 查询及crud
 */
@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {
	
	@Autowired
	private ContentCategoryService contentCategoryService;
	
	/**
	 * @param parentId
	 * @return
	 */
	@RequestMapping("list")
	@ResponseBody
	public List<EasyUITreeNode> getContentCatList(
			@RequestParam(value="id",defaultValue="0")Long parentId){
		return (List<EasyUITreeNode>)contentCategoryService.getContentCategoryList(parentId);
	}
	
	@RequestMapping("/create")
	@ResponseBody
	public TaotaoResult createCategory(Long parentId,String name){
		return contentCategoryService.addContentCategory(parentId, name);
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public TaotaoResult deleteCategory(Long id){
		return contentCategoryService.deleteContentCategory(id);
	}
	@RequestMapping("/update")
	@ResponseBody
	public TaotaoResult updateCategory(Long id,String name){
		return contentCategoryService.updateCointentCategory(id, name);
	}
	
}
