package com.zzl.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zzl.common.pojo.SearchResult;
import com.zzl.search.service.SearchService;


@Controller
public class SearchController {
	
	@Value("${ITEM_ROWS}")
	private Integer ITEM_ROWS;
	
	@Autowired
	private SearchService searchService;
	
	@RequestMapping("/search")
	public String search(@RequestParam("q")String queryString,
			@RequestParam(defaultValue="1")Integer page,Model model) throws Exception{
//		GET请求乱码
		queryString = new String(queryString.getBytes("iso8859-1"), "utf-8");
		SearchResult result = searchService.search(queryString, page, ITEM_ROWS);
		
		for(int i=0;i<result.getItemList().size();i++){
			System.out.println(result.getItemList().get(i));
		}
		
		model.addAttribute("query",queryString);
		model.addAttribute("totalPages", result.getPageCount());
		model.addAttribute("itemList",result.getItemList());
		model.addAttribute("page",page);
		return "search";
	}
}
