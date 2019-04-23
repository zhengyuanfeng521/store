package com.zzl.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zzl.common.pojo.TaotaoResult;
import com.zzl.search.service.SearchService;


@Controller
public class ImportItemsController {

	@Autowired
	private SearchService searchService;
	
	@RequestMapping("/index/importAll")
	@ResponseBody
	public TaotaoResult importItems(){
		System.out.println("------------------------------------------------------------------");
		try {
			TaotaoResult result = searchService.ImportAllSearchItems();
			System.out.println(result.getStatus());
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.ok("导入异常");
		}
	}
}
