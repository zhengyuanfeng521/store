package com.zzl.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zzl.common.pojo.TaotaoResult;
import com.zzl.common.util.CookieUtils;
import com.zzl.common.util.JsonUtils;
import com.zzl.sso.service.UserLoginService;


@Controller
public class UserLoginController {

	@Autowired
	private UserLoginService userLoginService;
	
	@Value("${TT_TOKEN_KEY}")
	private String TT_TOKEN_KEY;
	
	/**
	 * 登录 将返回的 token储存在cookie中
	 * @param request
	 * @param response
	 * @param username
	 * @param password
	 * @return
	 * 
	 */
	@RequestMapping(value="/user/login",method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult login(HttpServletRequest request,HttpServletResponse response,
			String username,String password){
		TaotaoResult result = userLoginService.login(username, password);
		if(result.getStatus() == 200){
			System.out.println("设置了cookie  token   "+ result.getData().toString());
			CookieUtils.setCookie(request, response,TT_TOKEN_KEY, result.getData().toString());
		}
		return result;
	}
	
	/**
	 * @param token    用户登录后存储在cookie的uuid
	 * @param callback 解决跨域问题
	 * @return
	 */
	@RequestMapping(value="/user/token/{token}",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String getUserByToken(@PathVariable String token,String callback){
		
		if(StringUtils.isNotBlank(callback)){
			TaotaoResult result = userLoginService.getUserByToken(token);
			String jsonstr = callback+"("+JsonUtils.objectToJson(result)+")";
			return jsonstr; 
		}
		return JsonUtils.objectToJson(userLoginService.getUserByToken(token));
	}
	
	@RequestMapping(value="/user/logout/{token}")
	@ResponseBody
	public TaotaoResult loginOut(@PathVariable String token,HttpServletRequest request){
		return userLoginService.loginOut(token);
	}
	
}
