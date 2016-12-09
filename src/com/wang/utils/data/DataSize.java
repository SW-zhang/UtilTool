package com.wang.utils.data;

import java.math.BigDecimal;

public class DataSize {

	public static String longToString(long size) {
		return longToString(size, 2);
	}

	public static String longToString(long size, int round) {
		float temp = 0F;
		String str = "";
		if (size >= 1024 * 1024 * 1024) {
			temp = (float) size / (1024 * 1024 * 1024);
			str = getRound(temp, round) + "GB";
		} else if (size >= 1024 * 1024) {
			temp = (float) size / (1024 * 1024);
			str = getRound(temp, round) + "MB";
		} else if (size >= 1024) {
			temp = (float) size / (1024);
			str = getRound(temp, round) + "KB";
		} else {
			str = size + "B";
		}
		return str;
	}

	public static float getRound(double tax, int rount) {
		try {
			BigDecimal value = new BigDecimal(tax);
			return value.setScale(rount, BigDecimal.ROUND_HALF_UP).floatValue();
		} catch (Exception e) {
			System.out.println(tax);
			return 0;
		}

	}
}
