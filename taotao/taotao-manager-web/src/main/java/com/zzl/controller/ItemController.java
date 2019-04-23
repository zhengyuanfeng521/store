package com.zzl.controller;

import java.util.Date;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zzl.common.pojo.EasyUIDataGridResult;
import com.zzl.common.pojo.TaotaoResult;
import com.zzl.common.util.IDUtils;
import com.zzl.pojo.TbItem;
import com.zzl.pojo.TbItemDesc;
import com.zzl.service.ItemService;


@Controller
@RequestMapping("/item")
public class ItemController {
	
	@Value("${TAOTAO_IMAGE_SERVER_URL}")
	private String TAOTAO_IMAGE_SERVER_URL;
	
	@Autowired
	private ItemService itemService;
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	@ResponseBody
	public EasyUIDataGridResult getItemList(Integer page,Integer rows){
		
		EasyUIDataGridResult result = itemService.getItemList(page, rows);
		return result;
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public TaotaoResult save(TbItem item,String desc){
		item.setId(IDUtils.genItemId());
		item.setStatus((byte)1);
		item.setCreated(new Date());
		item.setUpdated(new Date());
		
		TbItemDesc desc2 = new TbItemDesc();
		desc2.setItemDesc(desc);
		desc2.setItemId(item.getId());
		desc2.setCreated(item.getCreated());
		desc2.setUpdated(item.getCreated());
		// 4.插入商品描述数据
		// 注入tbitemdesc的mapper
		System.out.println("需要储存的item =   "+item);
		TaotaoResult result = itemService.save(item,desc2);
//		通知 activemq
		itemService.sendMessage(item);
		System.out.println("已通知 activeMQ---------> "+item);
		return result;
	}
	
	
}
