package com.zzl.sso.service;

import com.zzl.common.pojo.TaotaoResult;

public interface UserLoginService {
	
	
	public TaotaoResult login(String username,String password);
	
	public TaotaoResult getUserByToken(String token);
	
	public TaotaoResult loginOut(String token);
}
