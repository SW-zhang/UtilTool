package com.wang.utils.regex;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class PhoneChecker {

    private final static Set<String> mobilehead = new HashSet<String>(
            Arrays.asList("130", "131", "132", "133", "134", "135", "136", "137", "138", "139",
                    "145", "147",
                    "150", "151", "152", "153", "155", "156", "157", "158", "159",
                    "180", "181", "182", "183", "185", "186", "187", "188", "189"
            ));

    /**
     * 是否正规的中国手机号码（可包含区号：86,0086,+86）
     * 中国移动：134,135,136,137,138,139,150,151,152,157,158,159,182,183,187,188,147<br/>
     * 中国联通：130,131,132,155,156,185(3G),186(3G),145<br/>
     * 中国电信：133,153,180,181,189<br/>
     * 中国卫星通信：1349
     *
     * @param mobile
     * @return
     */
    public static boolean isMobile(String mobile) {
        if (mobile.startsWith("86")) {
            mobile = mobile.substring(2);
        }
        if (mobile.startsWith("+86")) {
            mobile = mobile.substring(3);
        }
        if (mobile.startsWith("0086")) {
            mobile = mobile.substring(4);
        }
        if (mobile.length() != 11) {
            return false;
        }
        String start = mobile.substring(0, 3);
        if (!mobilehead.contains(start)) {
            return false;
        }
        if (!mobile.matches("[0-9]+")) {
            return false;
        }
        return true;
    }

    /**
     * 验证email地址
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        String regex = "(\\w)+[@]{1}(\\w)+[.]{1}(\\w)+";
        return RegexUtil.matches(regex, email);
    }

    public static void main(String[] args) {
        System.out.println(isEmail("zhangshuwang1990@163.com"));
    }

}
