package com.zzl.search.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class GlobalExceptionReslover implements HandlerExceptionResolver {

	
	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
//		1 写入日志文件
		System.out.println(ex.getMessage());
		ex.printStackTrace();
//		2 及时通知开发人员
		System.out.println("发邮件");
//		3 给用户一个友好的提示
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("error/exception");
		modelAndView.addObject("message", "搜索出现错误");
		return modelAndView;
	}

}
