package com.services.utils.type;

public class IntegerUtil {

	/**
	 * 是否是整数型数据
	 * 
	 * @param o
	 * @return
	 */
	public static boolean isInt(Object o) {
		if (null == o) return false;
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
	 * 将对象的字符串转换成整形,如果失败则返回null;
	 * 
	 * @param o
	 * @return
	 */
	public static Integer parseInt(Object o) {
		if (o == null) return null;
		try {
			return DoubleUtil.parseDouble(o).intValue();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 将一个对象数组转换成Integer[]
	 * 
	 * @param os
	 * @return
	 */
	public static Integer[] parseIntArray(Object[] os) {
		if (os == null) return null;
		Integer[] ns = new Integer[os.length];
		for (int i = 0; i < ns.length; i++) {
			try {
				ns[i] = DoubleUtil.parseDouble(os[i]).intValue();
			} catch (Exception e) {
				ns[i] = null;
			}
		}
		return ns;
	}

	/**
	 * 将对象的字条串转换成整形，如果失败则返回0
	 * 
	 * @param o
	 * @return
	 */
	public static Integer parseInt_Null20(Object o) {
		if (o == null) return 0;
		try {
			return DoubleUtil.parseDouble(o).intValue();
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * int转byte[]
	 * 
	 * @param num
	 * @return
	 */
	public static byte[] toBytes(int num) {
		byte[] abyte0 = new byte[4];
		for (int i = 0; i < abyte0.length; i++) {
			abyte0[i] = (byte) (0xFFL & (num >> i * 8));
		}
		return abyte0;
	}

	public static void main(String[] args) {
		System.out.println(BytesUtil.toInt(toBytes(Integer.MAX_VALUE)));
		System.out.println(Integer.MAX_VALUE);
	}
}
