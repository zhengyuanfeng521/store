package com.zzl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zzl.service.TestService;


@Controller
public class TestController {
	@Autowired
	private TestService testService;
	
	@RequestMapping("/test/queryNow")
	@ResponseBody
	public String queryNow(){
		return testService.queryNow();
	}
}
