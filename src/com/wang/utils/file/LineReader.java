package com.wang.utils.file;

import java.io.*;
import java.nio.charset.Charset;

/**
 * 文件本文件行读入器
 * 
 */
public class LineReader {

	/** 文件 */
	private File file;

	/** 编码 */
	private String encoding;

	/** 读取器 */
	private LineNumberReader lineNr;

	/**
	 * 构造一个循环文件读取类
	 * 
	 * @param fileName
	 * @param encoding
	 * @throws IOException
	 */
	public LineReader(String fileName, String encoding) throws IOException {
		this.file = new File(fileName);
		this.encoding = encoding;
		init();
	}

	/**
	 * 构造一个循环文件读取类(用默认字符集编码)
	 * 
	 * @param fileName
	 * @throws IOException
	 */
	public LineReader(String fileName) throws IOException {
		this(fileName, Charset.defaultCharset().name());
	}

	/**
	 * 构造一个循环文件读取类
	 * 
	 * @param file
	 * @param encoding
	 * @throws IOException
	 */
	public LineReader(File file, String encoding) throws IOException {
		this.file = file;
		this.encoding = encoding;
		init();
	}

	/**
	 * 构造一个循环文件读取类(用默认字符集编码)
	 * 
	 * @param file
	 * @throws IOException
	 */
	public LineReader(File file) throws IOException {
		this(file, Charset.defaultCharset().name());
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
		this.lineNr = new LineNumberReader(new BufferedReader(new InputStreamReader(new FileInputStream(this.file), this.encoding), 10240));
	}

	/**
	 * 再读(循环读取)
	 * 
	 * @return
	 * @throws IOException
	 */
	public String next() throws IOException {
		return this.lineNr.readLine();
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
			if (this.lineNr.getLineNumber() >= lineNumber)
				break;
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
	 * 拿到文件
	 * 
	 * @return
	 */
	public File getFile() {
		return file;
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

	public static void main(String[] args) throws IOException {
		LineReader lr = new LineReader("C:/a.txt");
		String line;
		while (null != (line = lr.next())) {
			System.out.println(line);
		}
	}
}
