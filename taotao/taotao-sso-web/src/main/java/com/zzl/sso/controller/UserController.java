package com.zzl.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class UserController {

	@RequestMapping("/page/{page}")
	public String login(@PathVariable String page,String redirect,Model model){
		
		model.addAttribute("redirect", redirect);
		return page;
	}
}
