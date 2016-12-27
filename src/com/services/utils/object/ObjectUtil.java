package com.services.utils.object;

import com.services.utils.string.StringUtil;
import com.services.utils.web.UrlUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ObjectUtil {

    public static <T> T newObject(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static <T> T newObject(Class<T> clazz, Object[] params) {
        try {
            Constructor<?>[] cs = clazz.getConstructors();
            for (Constructor<?> c : cs) {
                Class<?>[] types = c.getParameterTypes();
                boolean yes = true;
                for (int i = 0; i < types.length; i++) {
                    yes &= types[i].isInstance(params[i]);
                }
                if (yes) {
                    return (T) c.newInstance(params);
                }
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object newObject(String clazz) {
        try {
            return newObject(Class.forName(clazz));
        } catch (Exception e) {
        }
        return null;
    }

    public static Object newObject(String clazz, Object[] params) {
        try {
            return newObject(Class.forName(clazz), params);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 打印对象
     *
     * @param o
     */
    public static String toObjectString(Object o) {
        return toObjectString(o, 1, true);
    }

    public static String toObjectString(Object o, boolean recursion) {
        return toObjectString(o, 1, recursion);
    }

    private static String toObjectString(Object o, int dep, boolean recursion) {

        if (null == o) {
            return null;
        }

        if (!recursion && dep == 2) {
            return o.toString();
        }

        StringBuffer c = new StringBuffer();

        Class<?> clazz = o.getClass();
        Method[] methods = clazz.getMethods();

        c.append(o.getClass().getSimpleName()).append(" {").append("\n");

        for (Method method : methods) {
            String mname = method.getName();
            Class<?> type = method.getReturnType();
            if (mname.length() <= 3 || mname.equals("getClass")) {
                continue;
            }

            if (mname.substring(0, 3).equals("get")) {

                try {
                    Object returnO = method.invoke(o, new Object[]{});
                    mname = mname.replaceFirst("get", "");
                    mname = StringUtil.toLowerCase(mname.charAt(0) + "") + mname.substring(1);
                    c.append(StringUtil.repeat("    ", dep)).append(type.getSimpleName()).append(" ").append(mname);

                    if (type.isArray()) {
                        Object[] os = (Object[]) returnO;
                        c.append(" = [");
                        boolean first = true;
                        for (Object _o : os) {
                            if (!first) {
                                c.append(", ");
                            } else {
                                first = false;
                            }
                            c.append(StringUtil.toCodeString(_o));
                        }
                        c.append("]");
                    } else if (null == type.getPackage() || type.getPackage().equals(Package.getPackage("java.lang"))) {
                        c.append(" = ").append(StringUtil.toCodeString(returnO));
                    } else {
                        c.append(" = ").append(toObjectString(returnO, dep + 1, recursion));
                    }

                    c.append(";").append("\n");
                } catch (Exception e) {
                    // e.printStackTrace();
                }
            }
        }
        c.append(StringUtil.repeat("    ", dep - 1)).append("}");
        return c.toString();
    }

    /***************************************************************************
     * 将对象中的所有String属性都从ISO8859-1编码转成encode
     *
     * @param o1
     * @return
     */
    public static <T> T transPropertyFromISO8859_1(T o1, String encode) {
        UrlUtil urlUtil = new UrlUtil(encode);
        Class<?> clazz = o1.getClass();

        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            String mname = method.getName();
            if (mname.length() <= 3) {
                continue;
            }

            String first = mname.substring(0, 3);
            String end = mname.substring(3);
            if (first.equals("set")) {
                Method get;
                try {
                    get = clazz.getMethod("get" + end, new Class[]{});
                    Object o = get.invoke(o1, new Object[]{});
                    if (null != o && o instanceof String) {
                        method.invoke(o1, urlUtil.parseRequestValue((String) o));
                    }
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return o1;
    }

    /***************************************************************************
     * 复制所有不为空的属性
     *
     * @param src
     * @param dest
     * @return
     */
    public static void copyPropertyByNotNull(Object src, Object dest) {
        copyProperty(src, dest, true);
    }

    /**
     * 复制所有属性
     *
     * @param src
     * @param dest
     */
    public static void copyProperty(Object src, Object dest) {
        copyProperty(src, dest, false);
    }

    private static void copyProperty(Object src, Object dest, boolean not_null) {
        if (null == src || null == dest) {
            throw new IllegalArgumentException("Argument can't be null", new NullPointerException());
        }

        Class<?> srcClass = src.getClass();
        Class<?> destClass = dest.getClass();

        Method[] methods = destClass.getMethods();
        for (Method method : methods) {
            String mname = method.getName();
            if (mname.length() <= 3) {
                continue;
            }

            String first = mname.substring(0, 3);
            String end = mname.substring(3);
            if (first.equals("set")) {
                Method get;
                try {
                    get = srcClass.getMethod("get" + end, new Class[]{});
                    Object o = get.invoke(src, new Object[]{});
                    if (null != o || !not_null) {
                        method.invoke(dest, o);
                    }
                } catch (Exception e) {
                }
            }
        }
    }

    public static void fillPropertys(Object obj, Map<String, ?> valueMap) {
        if (null == obj || null == valueMap) {
            throw new IllegalArgumentException("Argument can't be null", new NullPointerException());
        }
        for (Entry<String, ?> ent : valueMap.entrySet()) {
            FieldUtil.set(obj, ent.getKey(), ent.getValue());
        }
    }

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", "yaoming");
        map.put("age", "29");
        map.put("hight", "165");
        map.put("lit", "789");
        map.put("bd", "2013-01-01 21:23:23");
        Test test = new Test();
        fillPropertys(test, map);
        System.out.println(toObjectString(test));
    }

    public static class Test {
        private String name;
        private int age;
        private Integer hight;
        private Long lit;
        private Date bd;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public Integer getHight() {
            return hight;
        }

        public void setHight(Integer hight) {
            this.hight = hight;
        }

        public Long getLit() {
            return lit;
        }

        public void setLit(Long lit) {
            this.lit = lit;
        }

        public Date getBd() {
            return bd;
        }

        public void setBd(Date bd) {
            this.bd = bd;
        }

    }
}
