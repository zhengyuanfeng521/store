package com.zzl.cart.service;

import java.util.List;

import com.zzl.common.pojo.TaotaoResult;
import com.zzl.pojo.TbItem;



public interface CartService {
	
	public TaotaoResult addItemCart(TbItem item,Integer num,Long userId);
	
	public List<TbItem> getCartList(Long userId);
	/**
	 * 根据商品的ID 更新数量
	 */
	public TaotaoResult updateItemCartByItemId(Long userId,Long itemId,Integer num);
	
	public TaotaoResult deleteItemCartByItemId(Long userId,Long itemId);
}
