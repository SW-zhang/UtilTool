package com.wang.utils.string;

public interface Object2StringHandler {

	public String toString(Object obj);

	public static final Object2StringHandler SimpleHandler = new Object2StringHandler() {
		public String toString(Object obj) {
			return StringUtil.toString(obj);
		}
	};
}