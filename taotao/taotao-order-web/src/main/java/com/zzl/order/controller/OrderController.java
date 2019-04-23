package com.zzl.order.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zzl.cart.service.CartService;
import com.zzl.common.pojo.TaotaoResult;
import com.zzl.common.util.CookieUtils;
import com.zzl.pojo.TbItem;
import com.zzl.pojo.TbUser;
import com.zzl.sso.service.UserLoginService;


@Controller
public class OrderController {
	public static final String TAG = "OrderController";
	
	@Autowired
	private UserLoginService userLoginService;
	
	@Autowired
	private CartService cartService;
	
	@Value("${TT_TOKEN_KEY}")
	private String TT_TOKEN_KEY;
	@Value("${TT_CART_KEY}")
	private String TT_CART_KEY;
	
	@RequestMapping("/order/order-cart")
	public String showOrder(HttpServletRequest request){
		System.out.println(TAG+"  :  "+TT_TOKEN_KEY);
		String token = CookieUtils.getCookieValue(request, TT_TOKEN_KEY);
		if(StringUtils.isNotBlank(token)){
			TaotaoResult result = userLoginService.getUserByToken(token);
			if(result.getStatus() == 200){
				/*
				 * 用户必须登录
				 * 送货地址
				 * 支付方式
				 * redis中获取购物车商品表
				 */
				TbUser user = (TbUser) result.getData();
				List<TbItem> cartList = cartService.getCartList(user.getId());
				System.out.println(TAG+"  :  "+cartList);
				request.setAttribute("cartList", cartList);
			}
			
		}
//		TbUser user = (TbUser) request.getAttribute("USER_INFO");
//		System.out.println(TAG+"   :    "+user);
//		List<TbItem> cartList = cartService.getCartList(user.getId());
//		System.out.println(TAG+"  :  "+cartList);
//		request.setAttribute("cartList", cartList);
		return "order-cart";
	}
}
