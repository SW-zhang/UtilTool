package com.wang.utils.web;

import javax.servlet.http.Cookie;

/**
 * Cookie工具类
 * 
 */
public class CookieUtil {

	public static String getCookieValue(String name, Cookie[] cooks) {
		if (cooks != null) {
			for (Cookie cook : cooks) {
				if (cook.getName().equals(name)) { return cook.getValue(); }
			}
		}
		return null;
	}
}
