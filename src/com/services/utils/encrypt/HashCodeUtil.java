package com.services.utils.encrypt;

public class HashCodeUtil {

	public static int hashCode(String value, int prime) {
		int h = 0;
		char[] cs = value.toCharArray();
		if (h == 0 && cs.length > 0) {
			for (int i = 0; i < cs.length; i++) {
				h = prime * h + cs[i];
			}
		}
		return h;
	}

}
