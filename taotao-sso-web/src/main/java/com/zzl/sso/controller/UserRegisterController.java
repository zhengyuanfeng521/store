package com.zzl.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zzl.common.pojo.TaotaoResult;
import com.zzl.pojo.TbUser;
import com.zzl.sso.service.UserRegisterService;


@Controller
public class UserRegisterController {

	@Autowired
	private UserRegisterService userRegisterService;
	
	@RequestMapping("/user/check/{param}/{type}")
	@ResponseBody
	public TaotaoResult checkDate(@PathVariable String param,@PathVariable Integer type){
		return userRegisterService.checkDate(param, type); 
	}
	
	@RequestMapping(value="/user/register",method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult register(TbUser user){
		return userRegisterService.register(user);
	}
}
