package com.zzl.content.service;

import java.util.List;

import com.zzl.common.pojo.TaotaoResult;
import com.zzl.pojo.TbContent;

public interface ContentService {
	
	
	public TaotaoResult saveContent(TbContent tbContent);
	
	List<TbContent> getContentList(long cid);
	
}
