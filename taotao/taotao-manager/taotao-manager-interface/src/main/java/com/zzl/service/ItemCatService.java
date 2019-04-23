package com.zzl.service;

import java.util.List;

import com.zzl.pojo.TbItemCat;

public interface ItemCatService {
	
	List<TbItemCat> getItemCatList(Long parentId) throws Exception; 
}
