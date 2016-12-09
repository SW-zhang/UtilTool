package com.wang.utils.type;

public class FloatUtil {

	/**
	 * 是否是小数型数据
	 * 
	 * @param o
	 * @return
	 */
	public static boolean isFloat(Object o) {
		try {
			if (o.toString().matches("[+\\-]?[0-9]+"))
				return true;
			else
				return false;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 将对象的字符串转换成浮点型,如果失败则返回null;
	 * 
	 * @param o
	 * @return
	 */
	public static Float parseFloat(Object o) {
		if (o == null) return null;
		try {
			return Float.parseFloat(o.toString());
		} catch (Exception e) {
			return null;
		}
	}

	public static Float parseFloat_Null20(Object o) {
		if (o == null) return 0F;
		try {
			return Float.parseFloat(o.toString());
		} catch (Exception e) {
			return 0F;
		}
	}

	/**
	 * 转换成byte数组
	 * 
	 * @param o
	 * @return
	 */
	public static byte[] toBytes(float f) {
		int i = Float.floatToIntBits(f);
		return IntegerUtil.toBytes(i);
	}
}
