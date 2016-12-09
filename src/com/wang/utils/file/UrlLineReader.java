package com.wang.utils.file;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * 文件本文件行读入器
 * 
 */
public class UrlLineReader {

	/** url */
	private URL url;

	/** 编码 */
	private String encoding;

	/** 读取器 */
	private LineNumberReader lineNr;

	/**
	 * 构造一个循环文件读取类
	 * 
	 * @param url
	 * @param encoding
	 * @throws IOException
	 */
	public UrlLineReader(String url, String encoding) throws IOException {
		this.url = new URL(url);
		this.encoding = encoding;
		init();
	}

	/**
	 * 构造一个循环文件读取类(用默认字符集编码)
	 * 
	 * @param url
	 * @throws IOException
	 */
	public UrlLineReader(String url) throws IOException {
		this(url, Charset.defaultCharset().name());
	}

	/**
	 * 构造一个循环文件读取类
	 * 
	 * @param url
	 * @param encoding
	 * @throws IOException
	 */
	public UrlLineReader(URL url, String encoding) throws IOException {
		this.url = url;
		this.encoding = encoding;
		init();
	}

	/**
	 * 构造一个循环文件读取类(用默认字符集编码)
	 * 
	 * @param url
	 * @throws IOException
	 */
	public UrlLineReader(URL url) throws IOException {
		this(url, Charset.defaultCharset().name());
	}

	/**
	 * 加载文件
	 * 
	 * @throws IOException
	 */
	private void init() throws IOException {
		if (this.lineNr != null) {
			this.lineNr.close();
		}
		this.lineNr = new LineNumberReader(new BufferedReader(new InputStreamReader(this.url.openStream(), this.encoding)));
	}

	/**
	 * 再读(循环读取)
	 * 
	 * @return
	 * @throws IOException
	 */
	public String next() throws IOException {
		String str = this.lineNr.readLine();
		return str;
	}

	/**
	 * 定指针到指定行
	 * 
	 * @param lineNumber
	 *            指定的行号(从0开始)
	 * 
	 * @throws IOException
	 */
	public int gotoLine(int lineNumber) throws IOException {
		if (this.lineNr.getLineNumber() > lineNumber) {
			init();
		}
		do {
			if (this.lineNr.getLineNumber() >= lineNumber) break;
		} while (this.next() != null);

		return this.lineNr.getLineNumber();
	}

	/**
	 * 关闭当前文件读取流
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException {
		this.lineNr.close();
	}

	/**
	 * 拿到URL
	 * 
	 * @return
	 */
	public URL getUrl() {
		return url;
	}

	/**
	 * 拿到字符集编码
	 * 
	 * @return
	 */
	public String getEncoding() {
		return encoding;
	}

	/**
	 * 拿到当前行号
	 * 
	 * @return 行号
	 */
	public int getLineNumber() {
		return this.lineNr.getLineNumber();
	}
}
