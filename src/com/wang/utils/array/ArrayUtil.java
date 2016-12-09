package com.wang.utils.array;

import com.wang.utils.type.IntegerUtil;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * array工具类
 * 合并数组，复制数组，删除指定值。。。
 */
public class ArrayUtil {

    public static <T> T[] linkArray(T[] t1, T[] t2) {
        ArrayList<T> arr = new ArrayList<T>(t1.length + t2.length);
        for (int i = 0; i < t1.length + t2.length; i++) {
            if (i < t1.length) {
                arr.add(t1[i]);
            } else {
                arr.add(t2[i - t1.length]);
            }
        }
        return arr.toArray(t1);
    }

    public static <T> T[] removeItem(T[] src, T[] items) {
        if (null == src || src.length == 0) {
            return src;
        }
        if (null == items || items.length == 0) {
            return src;
        }
        ArrayList<T> arr = new ArrayList<T>(src.length);
        i:
        for (int i = 0; i < src.length; i++) {
            for (int j = 0; j < items.length; j++) {
                if (items[j].equals(src[i])) {
                    continue i;
                }
            }
            arr.add(src[i]);
        }
        T[] tt = Arrays.copyOf(src, arr.size());
        return arr.toArray(tt);
    }

    public static <T> void copy(T[] arr1, T[] arr2, int from, int len) {
        for (int i = from; i < len; i++) {
            arr2[i] = arr1[i];
        }
    }

    public static <T> void copy(T[] arr1, T[] arr2) {
        copy(arr1, arr2, 0, arr1.length);
    }

    public static <T> boolean in(T t, T[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (t == arr[i] || (null != t && t.equals(arr[i]))) {
                return true;
            }
        }
        return false;
    }

    public static Integer[] toIntArray(Object[] arr) {
        if (null == arr) {
            return null;
        }
        Integer[] rarr = new Integer[arr.length];
        for (int i = 0; i < arr.length; i++) {
            rarr[i] = IntegerUtil.parseInt(arr[i]);
        }
        return rarr;
    }

    public static String join(Object[] arr, String s) {
        String str = "";
        int len = arr.length;
        for (int i = 0; i < len; i++) {
            str += String.valueOf(arr[i]);
            if (i < len - 1) {
                str += s;
            }
        }
        return str;
    }

    public static boolean isEmpty(Object[] arr) {
        return arr == null || arr.length == 0;
    }
}
