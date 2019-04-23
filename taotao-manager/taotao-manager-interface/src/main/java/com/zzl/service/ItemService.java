package com.zzl.service;

import com.zzl.common.pojo.EasyUIDataGridResult;
import com.zzl.common.pojo.TaotaoResult;
import com.zzl.pojo.TbItem;
import com.zzl.pojo.TbItemDesc;

/**
 * @author HP
 *	分页的接口 一个
 */
public interface ItemService {
	
	public EasyUIDataGridResult getItemList(Integer page,Integer rows);
	
	public TaotaoResult save(TbItem item,TbItemDesc tbItemDesc);
	
	public TaotaoResult delete(Long[] ids);
	
	public TaotaoResult instock(Long[] ids);
	
	public TaotaoResult reshelf(Long[] ids);
	
	public TaotaoResult update(TbItem tbItem);
	
	/**
	 * 向activeMQ发送消息
	 * @param item
	 */
	public void sendMessage(TbItem item);
	
	public TbItem getTbItemById(Long itemId);
	
	public TbItemDesc getTbItemDescById(Long itemId);
}
