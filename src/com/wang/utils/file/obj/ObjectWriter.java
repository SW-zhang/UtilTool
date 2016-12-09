package com.wang.utils.file.obj;

import java.io.*;

/**
 * Object输出对象
 * 
 */
public class ObjectWriter {

	/** 输出流 */
	private ObjectOutputStream oos;

	/**
	 * 构造一个Object输出对象
	 * 
	 * @param fileName
	 *            文件名
	 * @throws IOException
	 */
	public ObjectWriter(String fileName) throws IOException {
		this(fileName, false);
	}

	/**
	 * 构造一个Object输出对象
	 * 
	 * @param fileName
	 *            文件名
	 * @param append
	 *            是否追加(默认不追加)
	 * @throws IOException
	 */
	public ObjectWriter(String fileName, boolean append) throws IOException {
		this(new File(fileName), append);
	}

	/**
	 * 构造一个Object输出对象
	 * 
	 * @param file
	 *            文件
	 * @throws IOException
	 */
	public ObjectWriter(File file) throws IOException {
		this(file, false);
	}

	/**
	 * 构造一个Object输出对象
	 * 
	 * @param file
	 *            文件
	 * @param append
	 *            是否追加(默认不追加)
	 * @throws IOException
	 */
	public ObjectWriter(File file, boolean append) throws IOException {
		this(new FileOutputStream(file, append));
	}

	/**
	 * 构造一个Object输出对象
	 * 
	 * @param outputStream
	 *            输出流
	 * @throws IOException
	 */
	public ObjectWriter(OutputStream outputStream) throws IOException {
		this.oos = new ObjectOutputStream(outputStream);
	}

	/**
	 * 写一个对象到文件
	 * 
	 * @param obj
	 *            对象
	 * @throws IOException
	 */
	public void writeObject(Object obj) throws IOException {
		this.oos.writeObject(obj);
		this.oos.flush();
	}

	/**
	 * 关闭输出流对象
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException {
		this.oos.close();
	}
}
