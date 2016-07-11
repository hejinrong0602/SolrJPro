package com.solr.bean;

import org.apache.solr.client.solrj.beans.Field;

public class Article {

	@Field(value="id")
	private int id;
	@Field(value="title")
	private String title;
	@Field(value="content")
	private String content;
	@Field(value="price")
	private double price;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	
}
