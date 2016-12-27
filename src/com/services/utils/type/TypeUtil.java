package com.services.utils.type;

import java.util.Date;

public class TypeUtil {

	public static boolean isPrimitive(Class<?> c) {
		if (c.isPrimitive()) {
			return true;
		}
		if (c.equals(Boolean.class) //
				|| c.equals(Byte.class)//
				|| c.equals(Character.class)//
				|| c.equals(Short.class)//
				|| c.equals(Integer.class)//
				|| c.equals(Long.class)//
				|| c.equals(Float.class)//
				|| c.equals(Double.class)//
		) {
			return true;
		}
		return false;
	}

	public static boolean isBoolean(Class<?> c) {
		return c.getName().equals("boolean") || c.equals(Boolean.class);
	}

	public static boolean isBytes(Class<?> c) {
		return c.isArray() && c.getCanonicalName().equals("java.lang.Byte[]") || c.getCanonicalName().equals("byte[]");
	}

	public static boolean isByte(Class<?> c) {
		return c.getName().equals("byte") || c.equals(Byte.class);
	}

	public static boolean isCharacter(Class<?> c) {
		return c.getName().equals("char") || c.equals(Character.class);
	}

	public static boolean isInteger(Class<?> c) {
		return c.getName().equals("int") || c.equals(Integer.class);
	}

	public static boolean isLong(Class<?> c) {
		return c.getName().equals("long") || c.equals(Long.class);
	}

	public static boolean isFloat(Class<?> c) {
		return c.getName().equals("float") || c.equals(Float.class);
	}

	public static boolean isDouble(Class<?> c) {
		return c.getName().equals("double") || c.equals(Double.class);
	}

	public static boolean isString(Class<?> c) {
		return c.equals(String.class);
	}

	public static boolean isDate(Class<?> c) {
		return c.equals(Date.class);
	}

	public static Object toTypeObject(Object o, String type) {
		if (null == o)
			return o;
		if (Boolean.class.getSimpleName().equalsIgnoreCase(type)) {
			return Boolean.parseBoolean(o.toString());
		}
		if ("char".equalsIgnoreCase(type) || Character.class.getSimpleName().equalsIgnoreCase(type)) {
			if (o.toString().length() == 0) {
				return new Character('\0');
			}
			return new Character(o.toString().charAt(0));
		}
		if (Byte.class.getSimpleName().equalsIgnoreCase(type)) {
			return Byte.parseByte(o.toString());
		}
		if ("int".equalsIgnoreCase(type) || Integer.class.getSimpleName().equalsIgnoreCase(type)) {
			return Integer.parseInt(o.toString());
		}
		if (Long.class.getSimpleName().equalsIgnoreCase(type)) {
			return Long.parseLong(o.toString());
		}
		if (Float.class.getSimpleName().equalsIgnoreCase(type)) {
			return Float.parseFloat(o.toString());
		}
		if (Double.class.getSimpleName().equalsIgnoreCase(type)) {
			return Double.parseDouble(o.toString());
		}
		if (String.class.getSimpleName().equalsIgnoreCase(type)) {
			return o.toString();
		}
		return o;
	}

}
