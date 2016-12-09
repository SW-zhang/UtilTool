package com.wang.utils.string;

public class StringBufferUtil {

	public static void replaceStrAll(StringBuffer sb, String strSrc, String strDes) {
		if (StringUtil.isBlank(strSrc)) { return; }
		int index;
		while ((index = sb.indexOf(strSrc)) != -1) {
			sb.replace(index, index + strSrc.length(), strDes);
		}
	}

	public static void replaceStrAllNotBack(StringBuffer sb, String strSrc, String strDes) {
		int index = 0;
		while ((index = sb.indexOf(strSrc, index)) != -1) {
			sb.replace(index, index + strSrc.length(), strDes);
			index += strDes.length();
		}
	}

	public static String replaceStrAll(String str, String strSrc, String strDes) {
		StringBuffer sb = new StringBuffer(str);
		replaceStrAll(sb, strSrc, strDes);
		return sb.toString();
	}

	public static String replaceStrAllNotBack(String str, String strSrc, String strDes) {
		StringBuffer sb = new StringBuffer(str);
		replaceStrAllNotBack(sb, strSrc, strDes);
		return sb.toString();
	}


	public static void main(String[] args) {
		String str = "权限管理 > 用户管理";
		System.out.println(replaceStrAllNotBack(str, ">", "</td><td><img src='images/bullet_go.png'/></td><td>"));
	}

}
