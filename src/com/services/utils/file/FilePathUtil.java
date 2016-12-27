package com.services.utils.file;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;

public class FilePathUtil {

	public static String linkFilePath(String dir, String fileName) {
		return linkFilePath(dir, fileName, File.separatorChar);
	}

	public static String linkFilePath(String dir, String fileName, char separatorChar) {
		if (dir.lastIndexOf('/') == dir.length() - 1) {
			return dir + fileName;
		}
		if (dir.lastIndexOf('\\') == dir.length() - 1) {
			return dir + fileName;
		}
		return dir + separatorChar + fileName;
	}

	public static File resource2file(String name) {
		URL url = FileUtil.class.getResource(name);
		try {
			String filename = URLDecoder.decode(url.getPath(), "utf8");
			return new File(filename);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		System.out.println(linkFilePath("E:\\", "a.txt"));
		System.out.println(linkFilePath("E:\\aaa", "a.txt"));
		System.out.println(linkFilePath("/opt/", "a.txt"));
		System.out.println(linkFilePath("/opt", "a.txt", '/'));
	}
}
