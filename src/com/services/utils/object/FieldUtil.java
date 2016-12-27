package com.services.utils.object;

import com.services.utils.Date.DateUtil;
import com.services.utils.type.TypeUtil;
import org.apache.log4j.Logger;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

public class FieldUtil {

	private final static Class<?>[] emptyClassArray = new Class[] {};
	private final static String[] emptyStringArray = new String[] {};

	/**
	 * 从对象中获得某一属性的值
	 * 
	 * @param obj
	 *            对象
	 * @param propertyPath
	 *            属性名称
	 * @return 属性值
	 */
	public static Object get(Object obj, String propertyPath) {
		String[] paths = propertyPath.split("\\.");
		for (int i = 0; i < paths.length; i++) {
			if (i == paths.length - 1) {
				propertyPath = paths[i];
				break;
			}
			obj = get(obj, paths[i]);
			if (obj == null) { throw new RuntimeException("the " + (i + 1) + "th property in " + propertyPath + " is null."); }
		}

		// 公共属性
		try {
			Field f = obj.getClass().getField(propertyPath);
			return f.get(obj);
		} catch (Exception e) {}

		// get方法
		String mm = "get" + propertyPath.substring(0, 1).toUpperCase() + propertyPath.substring(1);
		try {
			Method m = obj.getClass().getMethod(mm, emptyClassArray);
			return m.invoke(obj, new Object[] {});
		} catch (Throwable e) {
			throw new RuntimeException("Error in called method: " + obj.getClass().getName() + "." + mm + "()", e);
		}
	}

	public static Object get_err2null(Object obj, String propertyPath) {
		try {
			return get(obj, propertyPath);
		} catch (Throwable e) {
			return null;
		}
	}

	/**
	 * 给对象的某一属性赋值
	 * 
	 * @param obj
	 *            对象
	 * @param propertyPath
	 *            属性名称
	 * @param value
	 *            值
	 * @return 是否成功
	 */
	public static boolean set(Object obj, String propertyPath, Object value) {
		String[] paths = propertyPath.split("\\.");
		for (int i = 0; i < paths.length; i++) {
			if (i == paths.length - 1) {
				propertyPath = paths[i];
				break;
			}
			Object _obj;
			try {
				_obj = get(obj, paths[i]);
			} catch (Throwable e) {
				return false;
			}
			if (_obj == null) {
				try {
					_obj = propertyType(obj.getClass(), paths[i]).newInstance();
					set(obj, paths[i], _obj);
				} catch (Exception e) {
					return false;
				}
			}
			obj = _obj;
		}

		// 公开属性
		try {
			Field f = obj.getClass().getField(propertyPath);
			f.set(obj, value);
			return true;
		} catch (Exception e) {}

		// set方法
		String setMethodName = "set" + propertyPath.substring(0, 1).toUpperCase() + propertyPath.substring(1);

		Method[] ms = obj.getClass().getMethods();
		for (Method setMethod : ms) {
			if (!setMethod.getName().equals(setMethodName) || setMethod.getParameterTypes().length != 1) {
				continue;
			}
			Class<?> paramType = setMethod.getParameterTypes()[0];
			try {
				setMethod.invoke(obj, new Object[] { value });
				return true;
			} catch (Exception e) {}
			try {
				if (paramType.isArray() && value.getClass().isArray()) {
					Object tranceValue = Array.newInstance(paramType.getComponentType(), Array.getLength(value));
					for (int i = 0; i < Array.getLength(value); i++) {
						Object v;
						if (paramType.isPrimitive()) {
							v = TypeUtil.toTypeObject(value, paramType.getSimpleName());
						} else {
							Constructor<?> paramConstructor = paramType.getComponentType().getConstructor(value.getClass().getComponentType());
							try {
								v = paramConstructor.newInstance(Array.get(value, i));
							} catch (Exception e) {
								v = null;
							}
						}
						Array.set(tranceValue, i, v);
					}
					setMethod.invoke(obj, new Object[] { tranceValue });
					return true;
				} else {
					Object v;
					if (paramType.isPrimitive()) {
						v = TypeUtil.toTypeObject(value, paramType.getSimpleName());
					} else if (value.getClass().isAssignableFrom(paramType)) {
						v = value;
					} else if ((value instanceof Long || value.getClass().equals(long.class)) && paramType.equals(Date.class)) {
						v = new Date(Long.parseLong(value.toString()));
					} else if (paramType.equals(Date.class)) {
						DateFormat df = null;
						Field f = obj.getClass().getDeclaredField(propertyPath);
						if (null != f) df = f.getAnnotation(DateFormat.class);
						String format;
						if (null != df) {
							format = df.format();
						} else {
							format = "yyyy-MM-dd HH:mm:ss";
						}
						v = DateUtil.parse(String.valueOf(value), format);
					} else {
						Constructor<?> paramConstructor = paramType.getConstructor(value.getClass());
						v = paramConstructor.newInstance(value);
					}
					setMethod.invoke(obj, new Object[] { v });
					return true;
				}
			} catch (Exception e) {
				Logger.getLogger("yao_util_field").debug("Error in FieldUtil.set", e);
			}
		}
		return false;
	}

	/**
	 * 得到类中属性的类型
	 * 
	 * @param clazz
	 * @param propertyPath
	 * @return
	 */
	public static Class<?> propertyType(Class<?> clazz, String propertyPath) {
		if (null == clazz) { throw new NullPointerException("arg 'clazz' is null"); }
		String[] paths = propertyPath.split("\\.");
		for (int i = 0; i < paths.length; i++) {
			if (i == paths.length - 1) {
				propertyPath = paths[i];
				break;
			}
			clazz = propertyType(clazz, paths[i]);
			if (clazz == null) { return null; }
		}

		// 公开属性
		try {
			Field f = clazz.getField(propertyPath);
			return f.getType();
		} catch (Exception e) {}

		// set方法
		String setMethodName = "set" + propertyPath.substring(0, 1).toUpperCase() + propertyPath.substring(1);
		Method[] ms = clazz.getMethods();
		for (Method m : ms) {
			if (m.getName().equals(setMethodName) && m.getParameterTypes().length == 1) { return m.getParameterTypes()[0]; }
		}

		// get方法
		String getMethodName = "get" + propertyPath.substring(0, 1).toUpperCase() + propertyPath.substring(1);
		Method getMethod = null;
		try {
			getMethod = clazz.getMethod(getMethodName);
			return getMethod.getReturnType();
		} catch (Exception e) {}

		return null;
	}

	/**
	 * 从对象中获得一组属性值
	 * 
	 * @param obj
	 *            对象
	 * @param propertys
	 *            属性名数组
	 * @return
	 */
	public static Object[] values(Object obj, String[] propertys) {
		Object[] values = new Object[propertys.length];
		for (int i = 0; i < propertys.length; i++) {
			values[i] = get(obj, propertys[i]);
		}
		return values;
	}

	/**
	 * 得到对象中符合过滤条件的属性名称
	 * 
	 * @param clazz
	 *            对象
	 * @param filter
	 *            属性名称过滤器
	 * @return
	 */
	public static String[] propertys(Class<?> clazz, PropertyFilter filter) {
		Method[] ms = clazz.getMethods();
		ArrayList<String> list = new ArrayList<String>();
		for (Method m : ms) {
			String mname = m.getName();
			if (!mname.substring(0, 3).equals("set")) {
				continue;
			}
			try {
				Method get = clazz.getMethod("get" + mname.substring(3, mname.length()), emptyClassArray);
				if (null == get) {
					continue;
				}
				String property;
				if (mname.length() == 4) {
					property = mname.substring(3, 4).toLowerCase();
				} else {
					property = mname.substring(3, 4).toLowerCase() + mname.substring(4, mname.length());
				}
				// 无过滤器 或 过滤成功
				if (filter == null || filter.filte(property, propertyType(clazz, property))) {
					list.add(property);
				}
			} catch (Exception e) {
				// e.printStackTrace();
			}
		}
		return list.toArray(emptyStringArray);
	}

	/**
	 * 得到所有get方法对应的属性
	 * 
	 * @param clazz
	 * @return
	 */
	public static String[] getters(Class<?> clazz) {
		return getters(clazz, null);
	}

	/**
	 * 得到所有get方法对应的属性
	 * 
	 * @param clazz
	 * @param filter
	 * @return
	 */
	public static String[] getters(Class<?> clazz, PropertyFilter filter) {
		Method[] ms = clazz.getMethods();
		Set<String> list = new LinkedHashSet<String>();
		for (Method m : ms) {
			if (0 != m.getParameterTypes().length) {
				continue;
			}
			String mname = m.getName();
			if (!mname.substring(0, 3).equals("get")) {
				continue;
			}
			String property;
			if (mname.length() == 4) {
				property = mname.substring(3, 4).toLowerCase();
			} else {
				property = mname.substring(3, 4).toLowerCase() + mname.substring(4, mname.length());
			}
			if (property.equals("class")) {
				continue;
			}
			try {
				// 无过滤器 或 过滤成功
				if (filter == null || filter.filte(property, propertyType(clazz, property))) {
					list.add(property);
				}
			} catch (Exception e) {}
		}
		return list.toArray(emptyStringArray);
	}

	/**
	 * 得到所有set方法对应的属性
	 * 
	 * @param clazz
	 * @param filter
	 * @return
	 */
	public static String[] setters(Class<?> clazz, PropertyFilter filter) {
		Method[] ms = clazz.getMethods();
		ArrayList<String> list = new ArrayList<String>();
		for (Method m : ms) {
			if (1 != m.getParameterTypes().length) {
				continue;
			}
			String mname = m.getName();
			if (!mname.substring(0, 3).equals("set")) {
				continue;
			}
			String property;
			if (mname.length() == 4) {
				property = mname.substring(3, 4).toLowerCase();
			} else {
				property = mname.substring(3, 4).toLowerCase() + mname.substring(4, mname.length());
			}
			try {
				// 无过滤器 或 过滤成功
				if (filter == null || filter.filte(property, propertyType(clazz, property))) {
					list.add(property);
				}
			} catch (Exception e) {}
		}
		return list.toArray(emptyStringArray);
	}

	/**
	 * 得到对象中所有的属性名称（同时拥有get方法和set方法的属性）
	 * 
	 * @param obj
	 *            对象
	 * @return
	 */
	public static String[] propertys(Class<?> clazz) {
		return propertys(clazz, null);
	}

	public static interface PropertyFilter {
		public boolean filte(String propertyName, Class<?> propertyType);
	}

}
