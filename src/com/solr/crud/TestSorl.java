package com.solr.crud;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.SolrParams;
import org.junit.Test;

import com.solr.bean.Article;

public class TestSorl {

	/**
	 * 添加索引
	 * **/
	@Test
	public void testSolrJ() throws Exception{
		String baseURL = "http://localhost:8983/solr";
		SolrServer solrServer = new HttpSolrServer(baseURL);
		List<Article> list = new ArrayList<Article>();
		Article article = null;
		for (int i = 0; i < 25; i++) {
			article = new Article();
			article.setId(i);
			article.setTitle("是的飞洒的丰盛的");
			article.setContent("dsdfsdf是打发是打发第三方第三方的");
		}
		
		solrServer.addBeans(list);
		//第一种添加方式
//		SolrInputDocument docs = new SolrInputDocument();
//		docs.addField("id", "123");
//		docs.addField("title", "何金荣");
//		docs.addField("description", "榮哥是一个爱学习的孩子");
//		solrServer.add(docs);
		
		//第二种添加方式
//		Article article = new Article();
//		article.setId(123);
//		article.setTitle("高富帅");
//		article.setContent("白富美");
//		article.setPrice(12);
//		solrServer.addBean(article);
		
		
		
		solrServer.commit();
	}
	
	@Test
	public void delIndex() throws SolrServerException, IOException{
		String baseURL = "http://localhost:8983/solr";
		SolrServer solr = new HttpSolrServer(baseURL);
		
		solr.deleteById("123");
		solr.commit();
	}
	
	//更新，如果是id相同，直接按id来更新，因为id是唯一的标识
	@Test
	public void testFind() throws SolrServerException, IOException{
		String baseURL = "http://localhost:8983/solr";
		SolrServer solr = new HttpSolrServer(baseURL);
		//以后参数都是通过这个对象来构造
		SolrQuery params = new SolrQuery();
		params.setQuery("title:高富帅");
		//分页 每次10条
//		params.setStart(0);
//		params.setRows(10);
		//开启高亮
		params.setHighlight(true);
		//高亮显示的格式
		params.setHighlightSimplePre("<font color='red'>");
		params.setHighlightSimplePost("</font>");
		//需要进行高亮的字段
		params.setParam("hl.fl", "title");
		
		//放回所有的结果
		QueryResponse queryResponse = solr.query(params);
		SolrDocumentList solrDocumentList = queryResponse.getResults();
		//第一个map的key代表id,第二个map的key代表高亮的字段，list代表高亮的关键词，一个字段可能对应多个关键词（高亮词）
		Map<String, Map<String, List<String>>> map = queryResponse.getHighlighting();
		
		
		//返回高亮之后的结果
		
		
		
		for(SolrDocument solrDocument: solrDocumentList){
			Object id = solrDocument.get("id");
//			Object title = solrDocument.get("title");
//			Object content = solrDocument.get("content");
//			
//			System.out.println(id);
//			System.out.println(title);
//			System.out.println(content);
			
			Map<String, List<String>> fieldMap = map.get(id);
			List<String> stringList = fieldMap.get("title");
			System.out.println(stringList);
			
		}
		
	}
}
