package com.wang.lucene;

import java.io.Serializable;

/**
 * 搜索引擎创建索引所用对象
 * 
 * @author wang
 *
 */
@SuppressWarnings("serial")
public class Item implements Serializable {

	private String id;
	private String title;
	private String content;
	private String keywords;

	private String date;
	private String dept_name;
	private String response_content;

	private String file_content;

	public Item() {
		super();
	}

	// 制度item
	public Item(String id, String title, String content, String keyWords, String dept_name, String date, String file_content) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.keywords = keyWords;
		this.date = date;
		this.dept_name = dept_name;
		this.response_content = "";
		this.file_content = file_content;
	}

	// 问题item
	public Item(String id, String content, String dept_name, String date, String response_content, String file_content) {
		super();
		this.id = id;
		this.title = "";
		this.content = content;
		this.keywords = "";
		this.date = date;
		this.dept_name = dept_name;
		this.response_content = response_content;
		this.file_content = file_content;
	}

	// 办事人
	public Item(String id, String keywords) {
		super();
		this.id = id;
		this.title = "";
		this.content = "";
		this.keywords = keywords;
		this.date = "";
		this.dept_name = "";
		this.response_content = "";
		this.file_content = "";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getResponse_content() {
		return response_content;
	}

	public void setResponse_content(String response_content) {
		this.response_content = response_content;
	}

	public String getFile_content() {
		return file_content;
	}

	public void setFile_content(String file_content) {
		this.file_content = file_content;
	}

}
