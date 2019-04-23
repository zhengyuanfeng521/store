package com.zzl.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zzl.mapper.TbItemCatMapper;
import com.zzl.pojo.TbItemCat;
import com.zzl.pojo.TbItemCatExample;
import com.zzl.pojo.TbItemCatExample.Criteria;
import com.zzl.service.ItemCatService;

@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper mapper;
	
	@Override
	public List<TbItemCat> getItemCatList(Long parentId) throws Exception {
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbItemCat> list = mapper.selectByExample(example);
		return list;
	}

}
