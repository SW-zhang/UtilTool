package com.wang.utils.type;


public class BooleanUtil {
	
	public static boolean isNotNullAndTRUE(Boolean v) {
		return null != v && v;
	}

	public static boolean isNotNullAndFalse(Boolean v) {
		return null != v && !v;
	}

	public static boolean isNullOrTrue(Boolean v) {
		return null == v || v;
	}

	public static boolean isNullOrFalse(Boolean v) {
		return null == v || !v;
	}


}
