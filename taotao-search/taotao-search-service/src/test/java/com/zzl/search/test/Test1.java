package com.zzl.search.test;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class Test1 {
	
	@Test
	public void m1() throws SolrServerException, IOException{
		SolrServer server = new HttpSolrServer("http://118.24.194.103:8080/solr");
		SolrInputDocument document = new SolrInputDocument();
		document.addField("id", "hahahaha");
		document.addField("item_title", "这是一个测试");
		
		server.add(document);
		server.commit();
	}
	@Test
	public void m2(){
		
		
	}
}
