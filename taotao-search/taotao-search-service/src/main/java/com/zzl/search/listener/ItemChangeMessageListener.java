package com.zzl.search.listener;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;

import com.zzl.common.pojo.SearchItem;
import com.zzl.common.pojo.TaotaoResult;
import com.zzl.search.dao.SearchDao;
import com.zzl.search.service.SearchService;

public class ItemChangeMessageListener implements MessageListener {
	
	@Autowired
	private SearchService service;
	
	@Override
	public void onMessage(Message message) {
		System.out.println("收到acticeMQ的东西...**********************************************************");
		//判断消息的类型是否为textmessage
		if(message instanceof TextMessage){
			//如果是 获取商品的id 
			TextMessage message2 = (TextMessage)message;
			String itemidstr;
			try {
				//获取的就是商品的id的字符串
				itemidstr = message2.getText();
				Long itemId = Long.parseLong(itemidstr);
				//通过商品的id查询数据   需要开发mapper 通过id查询商品(搜索时)的数据
				//更新索引库
				TaotaoResult taotaoResult = service.updateSearchItemById(itemId);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
