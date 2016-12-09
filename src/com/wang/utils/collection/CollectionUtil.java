package com.wang.utils.collection;

import com.wang.utils.object.FieldUtil;

import java.lang.reflect.Array;
import java.util.*;

public class CollectionUtil {

    public static <T> void fillCollection(Collection<T> collection, T[] objs) {
        fillCollection(collection, objs, null);
    }

    public static <T> void fillCollection(Collection<T> collection, T[] objs, Filter<T> filter) {
        if (null == objs || null == collection) {
            return;
        }
        for (T t : objs) {
            if (null == filter || filter.accept(t)) {
                collection.add(t);
            }
        }
    }

    /**
     * 将一个集合转换成索引map
     *
     * @param c
     * @param keyField 在集合中的对象中的属性名，map的key将取这个属性的值
     * @return
     */
    public static <T> Map<Object, T> toIndexMap(Collection<T> c, String keyField) {
        Map<Object, T> map = new LinkedHashMap<Object, T>();
        for (T o : c) {
            Object keyValue = FieldUtil.get(o, keyField);
            map.put(keyValue, o);
        }
        return map;
    }

    /**
     * 将一个集合转换成集合中对象的指定属性的集合
     *
     * @param c
     * @param keyField 在集合中的对象中的属性名
     * @return
     */
    public static <T> Set<?> toFieldSet(Collection<T> c, String keyField) {
        Set set = new LinkedHashSet();
        for (T o : c) {
            Object keyValue = FieldUtil.get(o, keyField);
            set.add(keyValue);
        }
        return set;
    }

    /**
     * 将一个集合中的对象都转换成Map并装到一个List中返回
     *
     * @param c
     * @return
     */
    public static <T> List<Map> toMapItemList(Collection<T> c) {
        return toMapItemList(c, Obj2Map.simple);
    }

    /**
     * 将一个集合中的对象都转换成Map并装到一个List中返回
     *
     * @param c
     * @param o2m 对象转换成map的逻辑
     * @return
     */
    public static <T> List<Map> toMapItemList(Collection<T> c, Obj2Map o2m) {
        List<Map> set = new LinkedList<Map>();
        for (T o : c) {
            set.add(o2m.object2Map(o));
        }
        return set;
    }

    /**
     * 将集合按照limit个数进行拆分，每个子集里只有limit个元素，最后一个为余下的个数
     *
     * @param c     集合
     * @param limit 每个集合的大小
     * @return
     */
    public static <T> Collection<T>[] split(Collection<T> c, int limit) {
        int size = c.size();
        int arraySize = size % limit == 0 ? size / limit : size / limit + 1;
        Collection<T>[] arr = (Collection<T>[]) Array.newInstance(c.getClass(), arraySize);
        int index = 0;
        Iterator<T> it = c.iterator();
        for (int i = 0; i < arraySize; i++) {
            Collection<T> list;
            try {
                list = c.getClass().newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            for (int j = 0; j < limit && index < size; j++) {
                list.add(it.next());
                index++;
            }
            arr[i] = list;
        }
        return arr;
    }

    public static <T> Set<T> intersect(Collection<T> c1, Collection<T> c2) {
        Set<T> c = new LinkedHashSet<T>();
        for (T t1 : c1) {
            if (c2.contains(t1)) {
                c.add(t1);
            }
        }
        return c;
    }

    public static boolean isEmpty(Collection c1) {
        return c1 == null || c1.size() == 0;
    }

    public static interface Filter<T> {
        public boolean accept(T t);
    }
}
