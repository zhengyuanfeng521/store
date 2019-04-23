package com.zzl.search.mapper;

import java.util.List;

import com.zzl.common.pojo.SearchItem;

public interface SearchItemMapper {
	
	public List<SearchItem> getSearchItemList();
	
//	根据Id查询商品 
	public SearchItem getSearchItemById(Long itemId);
}
