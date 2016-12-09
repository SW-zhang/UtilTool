package com.wang.utils.type;

public class ByteUtil {

	public static Byte parseByte(Object o) {
		if (o == null)
			return null;
		try {
			return Byte.parseByte(o.toString());
		} catch (Exception e) {
			return null;
		}
	}

	public static int toInt(byte paramByte) {
		int i = paramByte & 0x7F;
		if (paramByte < 0)
			i |= 128;
		return i;
	}

}
