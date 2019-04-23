package com.zzl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zzl.common.pojo.TaotaoResult;
import com.zzl.content.service.ContentService;
import com.zzl.pojo.TbContent;

@Controller
public class ContentController {

	@Autowired
	private ContentService contentService;
	
	@RequestMapping("/content/save")
	@ResponseBody
	public TaotaoResult addContent(TbContent tbContent){
		return contentService.saveContent(tbContent);
	}
}
