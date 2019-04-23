package com.zzl.order.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.zzl.common.pojo.TaotaoResult;
import com.zzl.common.util.CookieUtils;
import com.zzl.sso.service.UserLoginService;

public class LoginInterceptor implements HandlerInterceptor {
	
	
	@Value("${TT_TOKEN_KEY}")
	private String TT_TOKEN;
	@Value("${SSO_LOGIN_URL}")
	private String SSO_LOGIN_URL;
	
	@Autowired
	private UserLoginService userLoginService;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String token = CookieUtils.getCookieValue(request, TT_TOKEN);
		if(StringUtils.isEmpty(token)){
//			如果token为空 重定向到登录界面
			response.sendRedirect(SSO_LOGIN_URL+"/page/login?redirect="+request.getRequestURL().toString());
			return false;
		}
		TaotaoResult result = userLoginService.getUserByToken(token);
		if(result.getStatus() != 200){
			response.sendRedirect(SSO_LOGIN_URL+"/page/login?redirect="+request.getRequestURL().toString());
			return false;
		}
//		将用户信息设置到request域中
		request.setAttribute("USER_INFO", result.getData());
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
