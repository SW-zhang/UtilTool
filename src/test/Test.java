package test;


import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wang on 2016/12/7.
 */
public class Test {
    private static final int sum = 5;

    public static void main(String[] args) throws Exception {
//        System.out.println(MD5Util.getMD5("adminsap#@!"));
        List<String> list = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11");

        for (int i = 0; i < list.size() / sum + 1; i++) {
            List<String> group = new ArrayList<>(sum);
            int end = (i * sum + sum) <= list.size() ? (i * sum + sum) : list.size();
            group.addAll(list.subList(i * sum, end));
            System.out.println(JSON.toJSONString(group));
        }
    }
}


