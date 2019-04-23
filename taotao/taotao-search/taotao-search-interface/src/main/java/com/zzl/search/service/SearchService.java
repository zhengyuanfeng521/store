package com.zzl.search.service;

import com.zzl.common.pojo.SearchResult;
import com.zzl.common.pojo.TaotaoResult;

public interface SearchService {
	
	public TaotaoResult ImportAllSearchItems() throws Exception;
	
	public SearchResult search(String queryString,Integer page,Integer rows) throws Exception;
	
	public TaotaoResult updateSearchItemById(Long itemId) throws Exception;
}
