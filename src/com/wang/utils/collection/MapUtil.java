package com.wang.utils.collection;

import com.wang.utils.object.FieldUtil;

import java.util.LinkedHashMap;
import java.util.Map;

public class MapUtil {

    public static Map<String, Object> parseMap(Object obj) {
        if (null == obj) {
            return null;
        }
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        String[] pros = FieldUtil.propertys(obj.getClass());
        for (String pro : pros) {
            map.put(pro, FieldUtil.get(obj, pro));
        }
        return map;
    }

    public static Map<String, String> parseStringMap(Object obj) {
        if (null == obj) {
            return null;
        }
        Map<String, String> map = new LinkedHashMap<String, String>();
        String[] pros = FieldUtil.propertys(obj.getClass());
        Object o;
        for (String pro : pros) {
            o = FieldUtil.get(obj, pro);
            if (null == o)
                map.put(pro, null);
            else
                map.put(pro, o.toString());
        }
        return map;
    }

    public static Map<String, String> parseStringMapNoNullValue(Object obj) {
        if (null == obj) {
            return null;
        }
        Map<String, String> map = new LinkedHashMap<String, String>();
        String[] pros = FieldUtil.propertys(obj.getClass());
        Object o;
        for (String pro : pros) {
            o = FieldUtil.get(obj, pro);
            if (null != o) map.put(pro, o.toString());
        }
        return map;
    }

    public static <T> T parseObject(Map map, Class<T> objClass) {
        if (null == map) {
            return null;
        }
        T t;
        try {
            t = objClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (Map.class.isAssignableFrom(objClass)) {
            ((Map) t).putAll(map);
        } else {
            String[] pros = FieldUtil.propertys(objClass);
            for (String pro : pros) {
                FieldUtil.set(t, pro, map.get(pro));
            }
        }
        return t;
    }

    public static void fillObject(Map map, Object obj) {
        if (null == map || null == obj) {
            return;
        }
        if (obj instanceof Map) {
            ((Map) obj).putAll(map);
        } else {
            String[] pros = FieldUtil.propertys(obj.getClass());
            for (String pro : pros) {
                if (map.get(pro) instanceof Map) {
                    fillObject((Map) map.get(pro), FieldUtil.get(obj, pro));
                }
                FieldUtil.set(obj, pro, map.get(pro));
            }
        }
    }

    public static class Obj {
        private String name;
        private Obj son;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Obj getSon() {
            return son;
        }

        public void setSon(Obj son) {
            this.son = son;
        }
    }

}
