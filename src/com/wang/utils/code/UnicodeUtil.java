package com.wang.utils.code;

public class UnicodeUtil {

	public static String toUnicode(String string) {
		StringBuffer unicode = new StringBuffer();
		for (int i = 0; i < string.length(); i++) {
			char c = string.charAt(i);
			unicode.append("\\u" + Integer.toHexString(c));
		}
		return unicode.toString();
	}

	public static String toString(String unicode) {
		StringBuffer string = new StringBuffer();
		String[] hex = unicode.split("\\\\u");
		for (int i = 1; i < hex.length; i++) { // 注意要从 1 开始，而不是从0开始。第一个是空。
			int data = Integer.parseInt(hex[i], 16); // 将16进制数转换为 10进制的数据。
			string.append((char) data); // 强制转换为char类型就是我们的中文字符了。
		}
		return string.toString();
	}

	public static void main(String args[]) {
		System.out.println(UnicodeUtil.toUnicode("测试"));
		System.out.println(UnicodeUtil.toString(UnicodeUtil.toUnicode("测试")));
	}
}