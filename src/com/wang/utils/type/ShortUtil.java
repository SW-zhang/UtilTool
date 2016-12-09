package com.wang.utils.type;

public class ShortUtil {

	/**
	 * int转byte[]
	 * 
	 * @param num
	 * @return
	 */
	public static byte[] toBytes(short num) {
		byte[] abyte0 = new byte[2];
		for (int i = 0; i < abyte0.length; i++) {
			abyte0[i] = (byte) (0xFFL & (num >> i * 8));
		}
		return abyte0;
	}

}
