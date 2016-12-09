package com.wang.utils.type;

public class DoubleUtil {

	/**
	 * 将对象的字符串转换成长浮点型,如果失败则返回null;
	 * 
	 * @param o
	 * @return
	 */
	public static Double parseDouble(Object o) {
		if (o == null) return null;
		try {
			return Double.parseDouble(o.toString());
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 将对象的字条串转换成长浮点型，如果失败则返回0D
	 * 
	 * @param o
	 * @return
	 */
	public static Double parseDouble_Null20(Object o) {
		if (o == null) return 0D;
		try {
			return Double.parseDouble(o.toString());
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 转换成byte数组
	 * 
	 * @param o
	 * @return
	 */
	public static byte[] toBytes(double d) {
		long l = Double.doubleToLongBits(d);
		return LongUtil.toBytes(l);
	}
}
