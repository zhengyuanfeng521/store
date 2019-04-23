package com.zzl.search.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrResponse;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zzl.common.pojo.SearchItem;
import com.zzl.common.pojo.SearchResult;
import com.zzl.common.pojo.TaotaoResult;
import com.zzl.search.mapper.SearchItemMapper;

/**
 * @author HP
 * 这个dao和solr服务器 不是数据库的
 */
@Repository //可用于注释任何类
public class SearchDao {
	
	@Autowired
	private SolrServer solrServer;
	
	@Autowired
	private SearchItemMapper mapper;
	
	public SearchResult search(SolrQuery query) throws Exception{
		
		QueryResponse response = solrServer.query(query);
		SolrDocumentList solrDocumentList = response.getResults();
		List<SearchItem> itemList = new ArrayList<>();
		for(SolrDocument solrDocument:solrDocumentList){
			SearchItem item = new SearchItem();
			item.setId( Long.valueOf((String) solrDocument.get("id")));
			item.setCategory_name((String) solrDocument.get("item_category_name"));
			item.setImage((String) solrDocument.get("item_image"));
			item.setPrice((long) solrDocument.get("item_price"));
			item.setSell_point((String) solrDocument.get("item_sell_point"));
			//取高亮显示
			Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
			List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
			String itemTitle = "";
			//有高亮显示的内容时。
			if (list != null && list.size() > 0) {
				itemTitle = list.get(0);
			} else {
				itemTitle = (String) solrDocument.get("item_title");
			}
			item.setTitle(itemTitle);
			//添加到商品列表
			itemList.add(item);
		}
		SearchResult result = new SearchResult();
		
		result.setItemList(itemList);
		result.setRecordCount(solrDocumentList.getNumFound());
		return result;
	}
	
	
	
	public TaotaoResult updateSearchItemById(Long itemId) throws Exception{
		SearchItem item = mapper.getSearchItemById(itemId);
//		更新索引库
		SolrInputDocument document = new SolrInputDocument();
		document.addField("id", item.getId());
		document.addField("item_title", item.getTitle());
		document.addField("item_sell_point", item.getSell_point());
		document.addField("item_price", item.getPrice());
		document.addField("item_image", item.getImage());
		document.addField("item_category_name", item.getCategory_name());
		document.addField("item_desc", item.getItem_desc());
		solrServer.add(document);
		
		solrServer.commit();
		return TaotaoResult.ok();
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
