package com.wang.utils.file;

import com.wang.utils.string.StringUtil;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * 文件行写入器
 *
 */
public class LineWriter {

    /**
     * 文件
     */
    private File file;

    /**
     * 是否追加
     */
    private boolean append;

    /**
     * 读取器
     */
    private BufferedOutputStream fos;

    /**
     * 文件编码 *
     */
    private String charset;

    /**
     * 换行符
     */
    private static final String lineSeparator = (String) java.security.AccessController.doPrivileged(new sun.security.action.GetPropertyAction("line.separator"));

    /**
     * 构造一个文件存储类
     *
     * @param file
     * @param append
     * @param charset
     * @throws IOException
     */
    public LineWriter(File file, boolean append, String charset) throws IOException {
        this.file = file;
        this.append = append;
        this.charset = charset;
        this.fos = new BufferedOutputStream(new FileOutputStream(this.file, this.append), 10240);
    }

    /**
     * 构造一个文件存储类
     *
     * @param file
     * @param append
     * @throws IOException
     */
    public LineWriter(File file, boolean append) throws IOException {
        this(file, append, Charset.defaultCharset().displayName());
    }

    /**
     * 构造一个文件存储类(默认为覆盖原文件)
     *
     * @param file
     * @param charset
     * @throws IOException
     */
    public LineWriter(File file, String charset) throws IOException {
        this(file, false, charset);
    }

    /**
     * 构造一个文件存储类(默认为覆盖原文件)
     *
     * @param file
     * @throws IOException
     */
    public LineWriter(File file) throws IOException {
        this(file, false);
    }

    /**
     * 构造一个文件存储类
     *
     * @param fileName
     * @param append
     * @param charset
     * @throws IOException
     */
    public LineWriter(String fileName, boolean append, String charset) throws IOException {
        this(new File(fileName), append, charset);
    }

    /**
     * 构造一个文件存储类
     *
     * @param fileName
     * @param append
     * @throws IOException
     */
    public LineWriter(String fileName, boolean append) throws IOException {
        this(new File(fileName), append);
    }

    /**
     * 构造一个文件存储类(默认为覆盖原文件)
     *
     * @param fileName
     * @param charset
     * @throws IOException
     */
    public LineWriter(String fileName, String charset) throws IOException {
        this(fileName, false, charset);
    }

    /**
     * 构造一个文件存储类(默认为覆盖原文件)
     *
     * @param fileName
     * @throws IOException
     */
    public LineWriter(String fileName) throws IOException {
        this(fileName, false);
    }

    /**
     * 写一行
     *
     * @param str
     * @throws IOException
     */
    public void writeLine(String str) throws IOException {
        if (!StringUtil.isTrimEmpty(str)) {
            this.fos.write(str.getBytes(charset));
            this.fos.write(lineSeparator.getBytes());
        }
    }

    /**
     * 写一个串
     *
     * @param str
     * @throws IOException
     */
    public void write(String str) throws IOException {
        this.fos.write(str.getBytes(charset));
    }

    /**
     * 写一个字符
     *
     * @param c
     * @throws IOException
     */
    public void write(char c) throws IOException {
        this.fos.write(c);
    }

    /**
     * 提交缓存
     *
     * @throws IOException
     */
    public void flush() throws IOException {
        this.fos.flush();
    }

    /**
     * 关闭
     *
     * @throws IOException
     */
    public void close() throws IOException {
        this.fos.close();
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
     * 是否是追加型
     *
     * @return
     */
    public boolean isAppend() {
        return append;
    }

    /**
     * 获得字符编码
     *
     * @return
     */
    public String getCharset() {
        return charset;
    }
}
