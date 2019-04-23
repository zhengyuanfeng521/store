package com.zzl.controller;

import java.util.Date;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zzl.common.pojo.TaotaoResult;
import com.zzl.pojo.TbItem;
import com.zzl.pojo.TbItemDesc;
import com.zzl.service.ItemService;


@Controller
//http://localhost:8081/rest/item/delete
//localhost:8081/rest/item/instock 404 (Not Found)
@RequestMapping("/rest")
public class ItemCRUDController {

	@Autowired
	private ItemService itemService;
	
	@RequestMapping(value="/item/delete",method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult delete(String ids){
		return itemService.delete(stringToLong(ids));
	}
	
	
	@RequestMapping(value="/item/instock",method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult instock(String ids){
		return itemService.instock(stringToLong(ids));
	}
	
	@RequestMapping(value="/item/reshelf",method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult reshelf(String ids){
		return itemService.reshelf(stringToLong(ids));
	}
	//http://localhost:8081/rest/page/item-edit?_=1543553261205

	@RequestMapping(value="/page/item-edit",method=RequestMethod.GET)
	public String edit(){
		
		return "item-edit";
	}
	
//	/rest/item/update
	@RequestMapping(value="/item/update")
	@ResponseBody
	public TaotaoResult update(TbItem item){
		TbItem item1 = itemService.getTbItemById(item.getId());
		item.setStatus(item1.getStatus());
		item.setCreated(item1.getCreated());
		item.setUpdated(new Date());
		itemService.update(item);
		return TaotaoResult.ok();
	}
	
//	http://localhost:8081/rest/item/query/item/desc/691300
//	/rest/item/query/item/desc/'+data.id
	@RequestMapping(value="/item/query/item/desc/{id}",method=RequestMethod.GET)
	@ResponseBody
	public TaotaoResult getDesc(@RequestParam(value="id",required=true,defaultValue="1") Long id){
		TbItemDesc desc = itemService.getTbItemDescById(id);
		System.out.println(desc);
		return TaotaoResult.ok(desc);
	}
	
	/**
	 * 将传过来的 string变为Long型数组
	 * @param ids
	 * @return
	 */
	private Long[] stringToLong(String ids){
		String[] idss = ids.split(",");
		Long[] ids2 = new Long[idss.length];
		for(int i=0;i<idss.length;i++){
			ids2[i] = Long.valueOf(idss[i]);
		}
		return ids2;
	}
}
