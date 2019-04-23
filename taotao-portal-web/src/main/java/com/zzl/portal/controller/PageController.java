package com.zzl.portal.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.zzl.common.util.JsonUtils;
import com.zzl.content.service.ContentService;
import com.zzl.pojo.TbContent;
import com.zzl.portal.pojo.Ad1Node;

@Controller
public class PageController {
	
	@Value("${AD1_CATEGORY_ID}")
	private Long categoryId;
	
	@Autowired
	private ContentService contentService;
	
	
	@RequestMapping("/index")
	public String showIndex(Model model){
		List<TbContent> list = contentService.getContentList(categoryId);
		List<Ad1Node> nodes = new ArrayList<>();
		for(TbContent tbContent :list){
			Ad1Node node = new Ad1Node();
			node.setAlt(tbContent.getSubTitle());
			node.setHref(tbContent.getUrl());
			node.setSrc(tbContent.getPic());
			node.setSrcB(tbContent.getPic2());
			node.setHeight("240");
			node.setHeightB("240");
			node.setWidth("240");
			node.setWidthB("240");
			nodes.add(node);
		}
		model.addAttribute("ad1",JsonUtils.objectToJson(nodes));
		return "index";
	}
}
