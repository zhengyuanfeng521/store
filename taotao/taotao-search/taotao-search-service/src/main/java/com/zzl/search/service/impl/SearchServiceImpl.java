package com.zzl.search.service.impl;

import java.io.IOException;
import java.util.List;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zzl.common.pojo.SearchItem;
import com.zzl.common.pojo.SearchResult;
import com.zzl.common.pojo.TaotaoResult;
import com.zzl.search.dao.SearchDao;
import com.zzl.search.mapper.SearchItemMapper;
import com.zzl.search.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService{

	@Autowired
	private SearchItemMapper searchItemMapper;
	
	@Autowired
	private SolrServer solrServer;
	
	
	/* (non-Javadoc)
	 * 导入索引库
	 */
	@Override
	public TaotaoResult ImportAllSearchItems() throws SolrServerException, IOException {
		int i=1;
		List<SearchItem> list = searchItemMapper.getSearchItemList();
		for(SearchItem item:list){
			SolrInputDocument document = new SolrInputDocument();
			document.addField("id", item.getId());
			document.addField("item_title", item.getTitle());
			document.addField("item_sell_point", item.getSell_point());
			document.addField("item_price", item.getPrice());
			document.addField("item_image", item.getImage());
			document.addField("item_category_name", item.getCategory_name());
			document.addField("item_desc", item.getItem_desc());
			System.out.println(i++);
			solrServer.add(document);
		}
		solrServer.commit();
		System.out.println("导入完成");

		return TaotaoResult.ok();
	}

	@Autowired
	private SearchDao searchDao;
	
	@Override
	public SearchResult search(String queryString, Integer page, Integer rows)
			throws Exception {
		SolrQuery solrQuery = new SolrQuery();
		if(null == queryString || queryString.trim().length() == 0){
//			****************************************************************************************
			solrQuery.setQuery("*:*");
		}else{
			solrQuery.setQuery(queryString);
		}
		
//		分页设置
		if(page == null) page = 1;
		if(rows == null) page = 60;
		solrQuery.setStart((page-1) * rows);
		solrQuery.setRows(rows);
//		默认搜索域
		solrQuery.set("df", "item_keywords");
		solrQuery.setHighlight(true);
//		solrQuery.addHighlightField("item_title");
		solrQuery.setHighlightSimplePre("<em style=\"color:red\">");
		solrQuery.setHighlightSimplePost("</em>");
		solrQuery.addHighlightField("item_title");
		
		SearchResult result = searchDao.search(solrQuery);
		
		long recordCount = result.getRecordCount();
		long pageCount = recordCount / rows;
		if (recordCount % rows > 0) {
			pageCount++;
		}
		result.setPageCount(pageCount);
		// 8、返回SearchResult
		return result;
	}

	@Override
	public TaotaoResult updateSearchItemById(Long itemId) throws Exception {
		return searchDao.updateSearchItemById(itemId);
	}
}
