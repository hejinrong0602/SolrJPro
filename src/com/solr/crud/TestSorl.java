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
	 * �������
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
			article.setTitle("�ǵķ����ķ�ʢ��");
			article.setContent("dsdfsdf�Ǵ��Ǵ򷢵�������������");
		}
		
		solrServer.addBeans(list);
		//��һ����ӷ�ʽ
//		SolrInputDocument docs = new SolrInputDocument();
//		docs.addField("id", "123");
//		docs.addField("title", "�ν���");
//		docs.addField("description", "�s����һ����ѧϰ�ĺ���");
//		solrServer.add(docs);
		
		//�ڶ�����ӷ�ʽ
//		Article article = new Article();
//		article.setId(123);
//		article.setTitle("�߸�˧");
//		article.setContent("�׸���");
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
	
	//���£������id��ͬ��ֱ�Ӱ�id�����£���Ϊid��Ψһ�ı�ʶ
	@Test
	public void testFind() throws SolrServerException, IOException{
		String baseURL = "http://localhost:8983/solr";
		SolrServer solr = new HttpSolrServer(baseURL);
		//�Ժ��������ͨ���������������
		SolrQuery params = new SolrQuery();
		params.setQuery("title:�߸�˧");
		//��ҳ ÿ��10��
//		params.setStart(0);
//		params.setRows(10);
		//��������
		params.setHighlight(true);
		//������ʾ�ĸ�ʽ
		params.setHighlightSimplePre("<font color='red'>");
		params.setHighlightSimplePost("</font>");
		//��Ҫ���и������ֶ�
		params.setParam("hl.fl", "title");
		
		//�Ż����еĽ��
		QueryResponse queryResponse = solr.query(params);
		SolrDocumentList solrDocumentList = queryResponse.getResults();
		//��һ��map��key����id,�ڶ���map��key����������ֶΣ�list��������Ĺؼ��ʣ�һ���ֶο��ܶ�Ӧ����ؼ��ʣ������ʣ�
		Map<String, Map<String, List<String>>> map = queryResponse.getHighlighting();
		
		
		//���ظ���֮��Ľ��
		
		
		
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
