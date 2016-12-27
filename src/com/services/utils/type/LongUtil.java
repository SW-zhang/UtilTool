package com.services.utils.type;

public class LongUtil {

	/**
	 * 将对象的字符串转换成长整形,如果失败则返回0L;
	 * 
	 * @param o
	 * @return
	 */
	public static Long parseLong(Object o) {
		if (o == null) return null;
		try {
			return DoubleUtil.parseDouble(o).longValue();
		} catch (Exception e) {
			return null;
		}
	}

	public static Long parseLong_Null20(Object o) {
		if (o == null) return 0L;
		try {
			return DoubleUtil.parseDouble(o).longValue();
		} catch (Exception e) {
			e.printStackTrace();
			return 0L;
		}
	}

	/**
	 * long转byte[]
	 * 
	 * @param num
	 * @return
	 */
	public static byte[] toBytes(long num) {
		byte[] abyte0 = new byte[8];
		for (int i = 0; i < abyte0.length; i++) {
			abyte0[i] = (byte) (0xFFL & (num >> i * 8));
		}
		return abyte0;
	}

	public static void main(String[] args) {
		System.out.println(Long.MAX_VALUE);
		System.out.println(BytesUtil.toLong(toBytes(Long.MAX_VALUE)));

		System.out.println(Integer.MAX_VALUE);
		System.out.println(BytesUtil.toInt(IntegerUtil.toBytes(Integer.MAX_VALUE)));
	}

}
