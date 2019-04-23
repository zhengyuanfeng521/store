package com.zzl.sso.service;

import com.zzl.common.pojo.TaotaoResult;

public interface UserLoginService {
	
	
	/**
	 * 用户登录 如果登录成功 会储存在redis数据库中 并设置保存时间 30分钟
	 * @param username
	 * @param password
	 * @return
	 */
	public TaotaoResult login(String username,String password);
	
	/**
	 * 通过token获得用户储存在redis中的信息
	 * @param token
	 * @return
	 */
	public TaotaoResult getUserByToken(String token);
	
	public TaotaoResult loginOut(String token);
}
