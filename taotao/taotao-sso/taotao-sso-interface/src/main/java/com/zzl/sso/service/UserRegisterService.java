package com.zzl.sso.service;

import com.zzl.common.pojo.TaotaoResult;
import com.zzl.pojo.TbUser;

public interface UserRegisterService {
	
	public TaotaoResult checkDate(String param,Integer type);

	public TaotaoResult register(TbUser tbUser);
	
}
