package com.services.utils.regex;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegexUtil {

    /**
     * 正则匹配
     *
     * @param regex
     * @param src
     * @param flags
     * @return
     */
    public static boolean matches(String regex, String src, int flags) {
        Pattern p = getPattern(regex, flags);
        Matcher m = p.matcher(src);
        return m.matches();
    }

    /**
     * 正则匹配
     *
     * @param regex
     * @param src
     * @return
     */
    public static boolean matches(String regex, String src) {
        return matches(regex, src, -1);
    }

    private static Pattern getPattern(String regex, int flags) {
        Pattern p;
        if (-1 == flags) {
            p = Pattern.compile(regex);
        } else {
            p = Pattern.compile(regex, flags);
        }
        return p;
    }

    /**
     * 返回单一的匹配
     *
     * @param regex 格式带“()”的正规表达式
     * @param src   搜索的源
     * @param flags 匹配模式
     * @return 如果没匹配到则返回null
     */
    public static String singleGroup(String regex, String src, int flags) {
        String[] ss = groups(regex, src, flags);
        return ss.length > 0 ? ss[0] : null;
    }

    /**
     * 返回单一的匹配
     *
     * @param regex 格式带“()”的正规表达式
     * @param src   搜索的源
     * @return 如果没匹配到则返回null
     */
    public static String singleGroup(String regex, String src) {
        return singleGroup(regex, src, -1);
    }

    /**
     * 返回匹配到的数据
     *
     * @param regex
     * @param src
     * @return
     */
    public static String[] groups(String regex, String src) {
        return groups(regex, src, -1);
    }

    /**
     * 返回匹配到的数据
     *
     * @param regex
     * @param src
     * @param flags
     * @return
     */
    public static String[] groups(String regex, String src, int flags) {
        Pattern p = getPattern(regex, flags);
        Matcher m = p.matcher(src);
        ArrayList<String> list = new ArrayList<String>();
        while (m.find()) {
            for (int i = 1; i <= m.groupCount(); i++) {
                list.add(m.group(i));
            }
        }
        return list.toArray(new String[]{});
    }

}
