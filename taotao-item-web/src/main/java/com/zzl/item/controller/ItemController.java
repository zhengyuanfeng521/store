package com.zzl.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zzl.item.pojo.Item;
import com.zzl.pojo.TbItemDesc;
import com.zzl.service.ItemService;

@Controller
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	
	@RequestMapping("/item/{itemId}")
	public String getItem(@PathVariable Long itemId, Model model){
		
		Item item = new Item(itemService.getTbItemById(itemId));
		
		TbItemDesc desc = itemService.getTbItemDescById(itemId);
		
		model.addAttribute("item", item);
		model.addAttribute("itemDesc", desc);
		return "item";
	}
}
