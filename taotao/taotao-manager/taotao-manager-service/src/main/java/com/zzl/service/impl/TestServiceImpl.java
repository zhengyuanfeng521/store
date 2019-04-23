package com.zzl.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zzl.mapper.TestMapper;
import com.zzl.service.TestService;

@Service
public class TestServiceImpl implements TestService{
	@Autowired
	private TestMapper mapper;

	@Override
	public String queryNow() {
		// TODO Auto-generated method stub
		return mapper.queryNow();
	}

}
