package com.zzl.service.impl;

import java.util.List;


import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.Destination;
import javax.jms.TextMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzl.common.pojo.EasyUIDataGridResult;
import com.zzl.common.pojo.TaotaoResult;
import com.zzl.common.util.JsonUtils;
import com.zzl.manager.jedis.JedisClient;
import com.zzl.mapper.TbItemDescMapper;
import com.zzl.mapper.TbItemMapper;
import com.zzl.pojo.TbItem;
import com.zzl.pojo.TbItemDesc;
import com.zzl.pojo.TbItemExample;
import com.zzl.pojo.TbItemExample.Criteria;
import com.zzl.service.ItemService;

/**
 * @author HP
 *
 */
@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private TbItemMapper tbItemmapper;
	
	@Autowired
	private TbItemDescMapper tbItemDescMapper;
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Autowired
	private JedisClient client;
	
	@Value("${ITEM_INFO_KEY}")
	private String ITEM_INFO_KEY;
	
	@Value("${ITEM_INFO_KEY_EXPIRE}")
	private Integer ITEM_INFO_KEY_EXPIRE;
	
	@Autowired
	private Destination topicDestination;
	/* (non-Javadoc)
	 * 查询所有商品
	 */
	@Override
	public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
		
		if(page == null) page = 1;
		if(rows == null) rows = 30;
		PageHelper.startPage(page, rows);
		
		TbItemExample example = new TbItemExample();
		List<TbItem> list = tbItemmapper.selectByExample(example);
		
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		
		EasyUIDataGridResult easyUIDataGridResult = new EasyUIDataGridResult();
		easyUIDataGridResult.setTotal((int)pageInfo.getTotal());
		easyUIDataGridResult.setRows(pageInfo.getList());
		return easyUIDataGridResult;
	}
	
	/* 
	 * 添加商品
	 */
	@Override
	public TaotaoResult save(TbItem item,TbItemDesc tbItemDesc) {
		tbItemmapper.insertSelective(item);
		tbItemDescMapper.insert(tbItemDesc);
		return TaotaoResult.ok();
	}
	
	//activemq 的发布
	@Override
	public void sendMessage(final TbItem item) {
		jmsTemplate.send( topicDestination,new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage textMessage = session.createTextMessage(item.getId()+"");
				return textMessage;
			}
		});
	}

	@Override
	public TbItem getTbItemById(Long itemId) {
//		查询缓存 不能影响正常逻辑
		try {
			if(itemId !=null){
				String jsonString = client.get(ITEM_INFO_KEY+":"+itemId+":BASE");
				if(StringUtils.isNoneBlank(jsonString)){
					System.out.println("");
					client.expire(ITEM_INFO_KEY+":"+itemId+"BASE",ITEM_INFO_KEY_EXPIRE);
					return JsonUtils.jsonToPojo(jsonString, TbItem.class);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		TbItem tbItem = tbItemmapper.selectByPrimaryKey(itemId);
		
//		添加缓存
		try {
			if(tbItem != null){
				client.set(ITEM_INFO_KEY+":"+itemId+"BASE", JsonUtils.objectToJson(tbItem));
				client.expire(ITEM_INFO_KEY+":"+itemId+"BASE",ITEM_INFO_KEY_EXPIRE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return tbItem;
	}

	@Override
	public TbItemDesc getTbItemDescById(Long itemId) {
//		查询缓存
		try {
			if(itemId !=null){
				String jsonString = client.get(ITEM_INFO_KEY+":"+itemId+":DESC");
				if(StringUtils.isNoneBlank(jsonString)){
					System.out.println("");
					client.expire(ITEM_INFO_KEY+":"+itemId+"DESC",ITEM_INFO_KEY_EXPIRE);
					return JsonUtils.jsonToPojo(jsonString, TbItemDesc.class);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		TbItemDesc desc =  tbItemDescMapper.selectByPrimaryKey(itemId);
//		添加缓存
		try {
			if(desc != null){
				client.set(ITEM_INFO_KEY+":"+itemId+"DESC", JsonUtils.objectToJson(desc));
				client.expire(ITEM_INFO_KEY+":"+itemId+"DESC",ITEM_INFO_KEY_EXPIRE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return desc;
	}

	@Override
	public TaotaoResult delete(Long[] ids) {
		for(Long i:ids){
			//两个表都要删除
			tbItemmapper.deleteByPrimaryKey(i);
			tbItemDescMapper.deleteByPrimaryKey(i);
		}
		return TaotaoResult.ok();
	}
	
	/* (non-Javadoc)
	 * 有现货的
	 * @see com.zzl.service.ItemService#instock(java.lang.Long[])
	 */
	@Override
	public TaotaoResult instock(Long[] ids) {
		for(Long i:ids){
			TbItem record = tbItemmapper.selectByPrimaryKey(i);
			record.setStatus((byte)2);
			tbItemmapper.updateByPrimaryKey(record);
		}
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult reshelf(Long[] ids) {
		for(Long i:ids){
			TbItem record = tbItemmapper.selectByPrimaryKey(i);
			record.setStatus((byte)1);
			tbItemmapper.updateByPrimaryKey(record);
		}
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult update(TbItem tbItem) {
		tbItemmapper.updateByPrimaryKey(tbItem);
		return TaotaoResult.ok();
	}

}
