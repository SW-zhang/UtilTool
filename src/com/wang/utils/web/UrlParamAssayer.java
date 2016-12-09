package com.wang.utils.web;

import com.wang.utils.string.StringUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;


/**
 * URI分析器
 * 
 */
public class UrlParamAssayer {

	/** 地址 */
	private String url;

	/** 参数列表 */
	private final Map<String, String> params = new HashMap<String, String>();

	private boolean ignoreCase = false;

	/**
	 * 构造即初始化
	 * 
	 * @param uri
	 * @throws UnsupportedEncodingException
	 */
	public UrlParamAssayer(String uri, String encode) {
		if (null == uri || uri.trim().equals("")) {
			return;
		}

		// 转码
		try {
			uri = URLDecoder.decode(uri, encode);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}

		String paramstr = StringUtil.getRight(uri, "?");
		url = StringUtil.getLeft(uri, "?");

		if (null == paramstr) {
			return;
		}
		String[] ss = paramstr.split("&");
		for (String s : ss) {
			try {
				String key = StringUtil.getLeft(s, "=");
				params.put(key, s.replaceFirst(key + "=", ""));
			} catch (Exception ex) {
			}
		}
	}

	/**
	 * 取得参数值(如果没有则返回null)
	 * 
	 * @param name
	 * @return
	 */
	public String getParam(String name) {
		return params.get(name);
	}

	/**
	 * 获得所有的参数
	 * 
	 * @return
	 */
	public Map<String, String> getParams() {
		return params;
	}

	/**
	 * 获得地址
	 * 
	 * @return
	 */
	public String getUrl() {
		return url;
	}

	public boolean isIgnoreCase() {
		return ignoreCase;
	}

	public void setIgnoreCase(boolean ignoreCase) {
		this.ignoreCase = ignoreCase;
	}
}
