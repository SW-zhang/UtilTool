package com.services.utils.file.obj;

import java.io.*;

/**
 * Object 输入对象
 * 
 */
public class ObjectReader {

	/** 输入流 */
	private ObjectInputStream ois;

	/** 行计数 */
	private int count;

	/**
	 * 构造一个Object输入对象
	 * 
	 * @param fileName
	 *            文件名
	 * @throws IOException
	 */
	public ObjectReader(String fileName) throws IOException {
		this(new File(fileName));
	}

	/**
	 * 构造一个Object输入对象
	 * 
	 * @param file
	 *            文件
	 * @throws IOException
	 */
	public ObjectReader(File file) throws IOException {
		this(new FileInputStream(file));
	}

	/**
	 * 构造一个Object输入对象
	 * 
	 * @param inputStream
	 *            输入流
	 * @throws IOException
	 */
	public ObjectReader(InputStream inputStream) throws IOException {
		this.ois = new ObjectInputStream(inputStream);
	}

	/**
	 * 下一个对象
	 * 
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public Object next() throws IOException, ClassNotFoundException {
		try {
			Object o = this.ois.readObject();
			count++;
			return o;
		} catch (java.io.EOFException e) {
			return new EOF(count);
		}
	}

	/**
	 * 关闭流
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException {
		this.ois.close();
	}
}
