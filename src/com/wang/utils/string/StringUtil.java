package com.wang.utils.string;

import com.wang.utils.encrypt.AESEncryptSafe;
import com.wang.utils.encrypt.EncryptException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 功能描述：关于字符串的一些实用操作
 */
public class StringUtil {

    /**
     * 功能描述：分割字符串
     *
     * @param str       String 原始字符串
     * @param splitsign String 分隔符
     * @return String[] 分割后的字符串数组
     */
    @SuppressWarnings("unchecked")
    public static String[] split(String str, String splitsign) {
        int index;
        if (str == null || splitsign == null) {
            return null;
        }
        ArrayList al = new ArrayList();
        while ((index = str.indexOf(splitsign)) != -1) {
            al.add(str.substring(0, index));
            str = str.substring(index + splitsign.length());
        }
        al.add(str);
        return (String[]) al.toArray(new String[0]);
    }

    /**
     * 功能描述：替换字符串
     *
     * @param from   String 原始字符串
     * @param to     String 目标字符串
     * @param source String 母字符串
     * @return String 替换后的字符串
     */
    public static String replace(String from, String to, String source) {
        if (source == null || from == null || to == null)
            return null;
        StringBuffer str = new StringBuffer("");
        int index = -1;
        while ((index = source.indexOf(from)) != -1) {
            str.append(source.substring(0, index) + to);
            source = source.substring(index + from.length());
            index = source.indexOf(from);
        }
        str.append(source);
        return str.toString();
    }

    /**
     * 替换字符串，能能够在HTML页面上直接显示(替换双引号和小于号)
     *
     * @param str String 原始字符串
     * @return String 替换后的字符串
     */
    public static String htmlencode(String str) {
        if (str == null) {
            return null;
        }
        return replace("\"", "&quot;", replace("<", "&lt;", str));
    }

    /**
     * 替换字符串，将被编码的转换成原始码（替换成双引号和小于号）
     *
     * @param str String
     * @return String
     */
    public static String htmldecode(String str) {
        if (str == null) {
            return null;
        }

        return replace("&quot;", "\"", replace("&lt;", "<", str));
    }

    private static final String _BR = "<br/>";

    /**
     * 功能描述：在页面上直接显示文本内容，替换小于号，空格，回车，TAB
     *
     * @param str String 原始字符串
     * @return String 替换后的字符串
     */
    public static String htmlshow(String str) {
        if (str == null) {
            return null;
        }

        str = replace("<", "&lt;", str);
        str = replace(" ", "&nbsp;", str);
        str = replace("\r\n", _BR, str);
        str = replace("\n", _BR, str);
        str = replace("\t", "&nbsp;&nbsp;&nbsp;&nbsp;", str);
        return str;
    }


    /**
     * 功能描述：判断是否为整数
     *
     * @param str 传入的字符串
     * @return 是整数返回true, 否则返回false
     */
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]+$");
        return pattern.matcher(str).matches();
    }

    /**
     * 判断是否为浮点数，包括double和float
     *
     * @param str 传入的字符串
     * @return 是浮点数返回true, 否则返回false
     */
    public static boolean isDouble(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?\\d+\\.\\d+$");
        return pattern.matcher(str).matches();
    }

    /**
     * 判断是不是合法字符 c 要判断的字符
     */
    public static boolean isLetter(String str) {
        if (str == null || str.length() < 0) {
            return false;
        }
        Pattern pattern = Pattern.compile("[\\w\\.-_]*");
        return pattern.matcher(str).matches();
    }

    /**
     * 从指定的字符串中提取Email content 指定的字符串
     *
     * @param content
     * @return
     */
    public static String parse(String content) {
        String email = null;
        if (content == null || content.length() < 1) {
            return email;
        }
        // 找出含有@
        int beginPos;
        int i;
        String token = "@";
        String preHalf = "";
        String sufHalf = "";

        beginPos = content.indexOf(token);
        if (beginPos > -1) {
            // 前项扫描
            String s = null;
            i = beginPos;
            while (i > 0) {
                s = content.substring(i - 1, i);
                if (isLetter(s))
                    preHalf = s + preHalf;
                else
                    break;
                i--;
            }
            // 后项扫描
            i = beginPos + 1;
            while (i < content.length()) {
                s = content.substring(i, i + 1);
                if (isLetter(s))
                    sufHalf = sufHalf + s;
                else
                    break;
                i++;
            }
            // 判断合法性
            email = preHalf + "@" + sufHalf;
            if (isEmail(email)) {
                return email;
            }
        }
        return null;
    }

    /**
     * 功能描述：判断输入的字符串是否符合Email样式.
     *
     * @param email 传入的字符串
     * @return 是Email样式返回true, 否则返回false
     */
    public static boolean isEmail(String email) {
        if (email == null || email.length() < 1 || email.length() > 256) {
            return false;
        }
        Pattern pattern = Pattern
                .compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
        return pattern.matcher(email).matches();
    }

    /**
     * 功能描述：判断输入的字符串是否为纯汉字
     *
     * @param str 传入的字符窜
     * @return 如果是纯汉字返回true, 否则返回false
     */
    public static boolean isChinese(String str) {
        Pattern pattern = Pattern.compile("[\u0391-\uFFE5]+$");
        return pattern.matcher(str).matches();
    }

    /**
     * 功能描述：是否为空白,包括null和""
     *
     * @param str
     * @return
     */
    public static boolean isBlank(String str) {
        return str == null || str.trim().length() == 0;
    }

    /**
     * 功能描述：判断是否为质数
     *
     * @param x
     * @return
     */
    public static boolean isPrime(int x) {
        if (x <= 7) {
            if (x == 2 || x == 3 || x == 5 || x == 7)
                return true;
        }
        int c = 7;
        if (x % 2 == 0)
            return false;
        if (x % 3 == 0)
            return false;
        if (x % 5 == 0)
            return false;
        int end = (int) Math.sqrt(x);
        while (c <= end) {
            if (x % c == 0) {
                return false;
            }
            c += 4;
            if (x % c == 0) {
                return false;
            }
            c += 2;
            if (x % c == 0) {
                return false;
            }
            c += 4;
            if (x % c == 0) {
                return false;
            }
            c += 2;
            if (x % c == 0) {
                return false;
            }
            c += 4;
            if (x % c == 0) {
                return false;
            }
            c += 6;
            if (x % c == 0) {
                return false;
            }
            c += 2;
            if (x % c == 0) {
                return false;
            }
            c += 6;
        }
        return true;
    }

    /**
     * 功能描述：人民币转成大写
     *
     * @param str 数字字符串
     * @return String 人民币转换成大写后的字符串
     */
    public static String hangeToBig(String str) {
        double value;
        try {
            value = Double.parseDouble(str.trim());
        } catch (Exception e) {
            return null;
        }
        char[] hunit = {'拾', '佰', '仟'}; // 段内位置表示
        char[] vunit = {'万', '亿'}; // 段名表示
        char[] digit = {'零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖'}; // 数字表示
        long midVal = (long) (value * 100); // 转化成整形
        String valStr = String.valueOf(midVal); // 转化成字符串

        String head = valStr.substring(0, valStr.length() - 2); // 取整数部分
        String rail = valStr.substring(valStr.length() - 2); // 取小数部分

        String prefix = ""; // 整数部分转化的结果
        String suffix = ""; // 小数部分转化的结果
        // 处理小数点后面的数
        if (rail.equals("00")) { // 如果小数部分为0
            suffix = "整";
        } else {
            suffix = digit[rail.charAt(0) - '0'] + "角"
                    + digit[rail.charAt(1) - '0'] + "分"; // 否则把角分转化出来
        }
        // 处理小数点前面的数
        char[] chDig = head.toCharArray(); // 把整数部分转化成字符数组
        char zero = '0'; // 标志'0'表示出现过0
        byte zeroSerNum = 0; // 连续出现0的次数
        for (int i = 0; i < chDig.length; i++) { // 循环处理每个数字
            int idx = (chDig.length - i - 1) % 4; // 取段内位置
            int vidx = (chDig.length - i - 1) / 4; // 取段位置
            if (chDig[i] == '0') { // 如果当前字符是0
                zeroSerNum++; // 连续0次数递增
                if (zero == '0') { // 标志
                    zero = digit[0];
                } else if (idx == 0 && vidx > 0 && zeroSerNum < 4) {
                    prefix += vunit[vidx - 1];
                    zero = '0';
                }
                continue;
            }
            zeroSerNum = 0; // 连续0次数清零
            if (zero != '0') { // 如果标志不为0,则加上,例如万,亿什么的
                prefix += zero;
                zero = '0';
            }
            prefix += digit[chDig[i] - '0']; // 转化该数字表示
            if (idx > 0)
                prefix += hunit[idx - 1];
            if (idx == 0 && vidx > 0) {
                prefix += vunit[vidx - 1]; // 段结束位置应该加上段名如万,亿
            }
        }

        if (prefix.length() > 0)
            prefix += '圆'; // 如果整数部分存在,则有圆的字样
        return prefix + suffix; // 返回正确表示
    }

    /**
     * 功能描述：去掉字符串中重复的子字符串
     *
     * @param str 原字符串，如果有子字符串则用空格隔开以表示子字符串
     * @return String 返回去掉重复子字符串后的字符串
     */
    @SuppressWarnings("unused")
    private static String removeSameString(String str) {
        Set<String> mLinkedSet = new LinkedHashSet<String>();// set集合的特征：其子集不可以重复
        String[] strArray = str.split(" ");// 根据空格(正则表达式)分割字符串
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < strArray.length; i++) {
            if (!mLinkedSet.contains(strArray[i])) {
                mLinkedSet.add(strArray[i]);
                sb.append(strArray[i] + " ");
            }
        }
        System.out.println(mLinkedSet);
        return sb.toString();
    }

    /**
     * 功能描述：过滤特殊字符
     *
     * @param src
     * @return
     */
    public static String encoding(String src) {
        if (src == null)
            return "";
        StringBuilder result = new StringBuilder();
        if (src != null) {
            src = src.trim();
            for (int pos = 0; pos < src.length(); pos++) {
                switch (src.charAt(pos)) {
                    case '\"':
                        result.append("&quot;");
                        break;
                    case '<':
                        result.append("&lt;");
                        break;
                    case '>':
                        result.append("&gt;");
                        break;
                    case '\'':
                        result.append("&apos;");
                        break;
                    case '&':
                        result.append("&amp;");
                        break;
                    case '%':
                        result.append("&pc;");
                        break;
                    case '_':
                        result.append("&ul;");
                        break;
                    case '#':
                        result.append("&shap;");
                        break;
                    case '?':
                        result.append("&ques;");
                        break;
                    default:
                        result.append(src.charAt(pos));
                        break;
                }
            }
        }
        return result.toString();
    }

    /**
     * 功能描述：判断是不是合法的手机号码
     *
     * @param handset
     * @return boolean
     */
    public static boolean isHandset(String handset) {
        try {
            String regex = "^1[\\d]{10}$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(handset);
            return matcher.matches();

        } catch (RuntimeException e) {
            return false;
        }
    }

    /**
     * 功能描述：反过滤特殊字符
     *
     * @param src
     * @return
     */
    public static String decoding(String src) {
        if (src == null)
            return "";
        String result = src;
        result = result.replace("&quot;", "\"").replace("&apos;", "\'");
        result = result.replace("&lt;", "<").replace("&gt;", ">");
        result = result.replace("&amp;", "&");
        result = result.replace("&pc;", "%").replace("&ul", "_");
        result = result.replace("&shap;", "#").replace("&ques", "?");
        return result;
    }

    /**
     * 截取选定字符<br>
     * 调用: getSection("这个方法用来|截取指定|字符串!","|","|")返回结果是: "截取指定"
     *
     * @param string 字符串
     * @param start  开始字符串
     * @param end    结尾字符串
     * @return
     */
    public static String getSection(String string, String start, String end) {
        try {
            if (!allNotEmpty(new String[]{string, start, end})) {
                return null;
            }
            int startPoint = string.indexOf(start);
            if (startPoint == -1) return null;
            startPoint += start.length();
            int endPoint = string.indexOf(end, startPoint);
            if (endPoint == -1 || endPoint < startPoint) return null;
            return string.substring(startPoint, endPoint);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 根据位置数组得到一组字符串，返回数组中的项为从位置开始到下一个位置的字符串。
     *
     * @param str
     * @param points
     * @return
     */
    public static String[] getSections(String str, int[] points) {
        Arrays.sort(points);
        String[] ss = new String[points.length];
        for (int i = 0; i < points.length; i++) {
            if (i + 1 < points.length) {
                ss[i] = str.substring(points[i], points[i + 1]);
            } else {
                ss[i] = str.substring(points[i]);
            }
        }
        return ss;
    }

    /**
     * 截取选定字符（最大长度）<br>
     * 调用: getSection("这个方法用来|截取指定|字符|串!","|","|")返回结果是: "截取指定|字符"
     *
     * @param string 字符串
     * @param start  开始字符串
     * @param end    结尾字符串
     * @return
     */
    public static String getSectionOuter(String string, String start, String end) {
        try {
            if (!allNotEmpty(new String[]{string, start, end})) {
                return null;
            }
            int startPoint = string.indexOf(start);
            if (startPoint == -1) return null;
            startPoint += start.length();
            int endPoint = string.lastIndexOf(end);
            if (endPoint == -1) return null;
            return string.substring(startPoint, endPoint);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 截取选定字符<br>
     * 调用: getLeft("这个方法用来|截取指定|字符串!","|")返回结果是: "这个方法用来"
     *
     * @param string
     * @param point
     * @return
     */
    public static String getLeft(String string, String point) {
        int start = string.indexOf(point);
        if (start == -1) {
            return null;
        }
        return string.substring(0, start);
    }

    public static String getLeft(String string, String[] points) {
        String str = string;
        for (int i = 0; i < points.length; i++) {
            String s = getLeft(string, points[i]);
            if (compare(str, s) < 0) {
                str = s;
            }
        }
        if (str == string) {
            return null;
        }
        return str;
    }

    public static int compare(String s1, String s2) {
        if (null == s1 && null == s2) return 0;
        if (s1 == null) return s2.length();
        if (s2 == null) return s1.length();
        return s2.length() - s1.length();
    }

    /**
     * 截取选定字符<br>
     * 调用: getLeftOuter("这个方法用来|截取指定|字符串!","|")返回结果是: "这个方法用来|截取指定"
     *
     * @param string
     * @param point
     * @return
     */
    public static String getLeftOuter(String string, String point) {
        if (StringUtil.isEmpty(point)) {
            return string;
        }
        int start = string.lastIndexOf(point);
        if (start == -1) {
            return null;
        }
        return string.substring(0, start);
    }

    /**
     * 截取选定字符<br>
     * 调用: getRight("这个方法用来|截取指定|字符串!","|")返回结果是: "字符串!"
     *
     * @param string
     * @param point
     * @return
     */
    public static String getRight(String string, String point) {
        int start = string.lastIndexOf(point);
        if (start == -1) {
            return null;
        }
        start += point.length();
        return string.substring(start);
    }

    /**
     * 截取选定字符<br>
     * 调用: getRightOuter("这个方法用来|截取指定|字符串!","|")返回结果是: "截取指定|字符串!"
     *
     * @param string
     * @param point
     * @return
     */
    public static String getRightOuter(String string, String point) {
        if (StringUtil.isEmpty(point)) {
            return string;
        }
        int start = string.indexOf(point);
        if (start == -1) {
            return null;
        }
        start += point.length();
        return string.substring(start);
    }

    /**
     * 是否为null或""
     *
     * @param string
     * @return
     */
    public static boolean isEmpty(String string) {
        if (null == string) return true;
        return string.equals("");
    }

    /**
     * 是否为 null 或 空格字符
     *
     * @param string
     * @return
     */
    public static boolean isTrimEmpty(String string) {
        return String.valueOf(string).trim().equals("");
    }

    /**
     * 判断两个字符串是否相同（不区分大小写）
     *
     * @param s1
     * @param s2
     * @return
     */
    public static boolean equalsIgnoreCase(String s1, String s2) {
        if (s1 == s2) {
            return true;
        } else if (s1 == null) {
            return false;
        } else if (s2 == null) {
            return false;
        }
        return s1.equalsIgnoreCase(s2);
    }

    private static Map<String, String> Smap = new HashMap<String, String>();

    static {
        Smap.put("\\n", "\n");
        Smap.put("\\t", "\t");
        Smap.put("\\b", "\b");
        Smap.put("\\f", "\f");
        Smap.put("\\r", "\r");

        Smap.put("\\\\", "\\");
        Smap.put("\\'", "\'");
        Smap.put("\\\"", "\"");

        Smap.put("\\0", "\0");
    }

    /**
     * 将实际字符串转换为规范的JAVA代码字符串<br />
     * 其中包括的转义对象有：<br />
     * <li>("\\n", "\n");</li> <li>("\\t", "\t");</li> <li>("\\b", "\b");</li>
     * <li>("\\f", "\f");</li> <li>("\\r", "\r");</li> <li>("\\\\", "\\");</li>
     * <li>("\\'", "\'");</li> <li>("\\\"", "\"");</li> <li>("\\0", "\0");</li>
     *
     * @param s 对象 null 返回 null
     * @return
     */
    public static String toCodeString(String s) {
        if (null == s) return null;
        try {
            StringBuffer sb = new StringBuffer(s);
            for (Map.Entry<String, String> ent : Smap.entrySet()) {
                StringBufferUtil.replaceStrAllNotBack(sb, ent.getValue(), ent.getKey());
            }
            sb.append("\"");
            sb.insert(0, "\"");
            return sb.toString();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 将对象转换成字符串后，执行toCodeString(String s) ;
     *
     * @param o
     * @return
     */
    public static String toCodeString(Object o) {
        if (null == o) return null;
        return toCodeString(o.toString());
    }

    /**
     * 将规范的JAVA代码字符串转为实际字符串<br />
     * 与toCodeString(Object o)正好相反
     *
     * @param s
     * @return
     */
    public static String fromCodeString(String s) {
        if (null == s) return null;
        if (s.equals("null")) return null;
        try {
            StringBuffer sb = new StringBuffer(s);
            for (Map.Entry<String, String> ent : Smap.entrySet()) {
                StringBufferUtil.replaceStrAllNotBack(sb, ent.getKey(), ent.getValue());
            }
            return StringUtil.getSectionOuter(sb.toString(), "\"", "\"");
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 重复字符串
     *
     * @param s
     * @param count
     * @return
     */
    public static String repeat(String s, int count) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < count; i++) {
            sb.append(s);
        }
        return sb.toString();
    }

    /**
     * 用字符串填充数组
     *
     * @param ss 等填充的数组
     * @param s  填充用的字符串
     */
    public static String[] fillArray(String[] ss, String s) {
        for (int i = 0; i < ss.length; i++) {
            ss[i] = s;
        }
        return ss;
    }

    /**
     * 转换成大写
     *
     * @param str
     * @return 非NULL
     */
    public static String toUpperCase(String str) {
        return toString(str).toUpperCase();
    }

    /**
     * 转换成小写
     *
     * @param str
     * @return 非NULL
     */
    public static String toLowerCase(String str) {
        return toString(str).toLowerCase();
    }

    /**
     * 数组连接组装成字符串
     *
     * @param objs 对象数组
     * @return
     */
    public static String linkString(Object[] objs) {
        return linkString(objs, "");
    }

    /**
     * 数组连接组装成字符串
     *
     * @param objs     对象数组
     * @param splitStr 分隔字符串
     * @return
     */
    public static String linkString(Object[] objs, String splitStr) {
        return linkString(objs, splitStr, Object2StringHandler.SimpleHandler);
    }

    /**
     * 数组连接组装成字符串
     *
     * @param objs
     * @param splitStr
     * @param handler  Object转字符串处理器
     * @return
     */
    public static String linkString(Object[] objs, String splitStr, Object2StringHandler handler) {
        if (null == objs) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        boolean first = true;
        for (Object o : objs) {
            if (first) {
                first = false;
            } else {
                sb.append(splitStr);
            }
            sb.append(handler.toString(o));
        }
        return sb.toString();
    }

    /**
     * 集合连接组装成字符串
     *
     * @param objs 对象集合
     * @return
     */
    public static String linkString(Collection<?> objs) {
        return linkString(objs, "");
    }

    /**
     * 集合连接组装成字符串
     *
     * @param objs     对象集合
     * @param splitStr 分隔字符串
     * @return
     */
    public static String linkString(Collection<?> objs, String splitStr) {
        return linkString(objs, splitStr, Object2StringHandler.SimpleHandler);
    }

    /**
     * 集合连接组装成字符串
     *
     * @param colls
     * @param splitStr
     * @param handler  Object转字符串处理器
     * @return
     */
    public static String linkString(Collection<?> colls, String splitStr, Object2StringHandler handler) {
        if (null == colls) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        boolean first = true;
        for (Object s : colls) {
            if (first) {
                first = false;
            } else {
                sb.append(splitStr);
            }
            sb.append(handler.toString(s));
        }
        return sb.toString();
    }

    /**
     * 数组连接组装成字符串
     *
     * @param chars    字符数组
     * @param splitStr 分隔字符串
     * @return
     */
    public static String linkString(char[] chars, String splitStr) {
        if (null == chars) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        boolean first = true;
        for (Object s : chars) {
            if (first) {
                first = false;
            } else {
                sb.append(splitStr);
            }
            sb.append(s);
        }
        return sb.toString();
    }

    /**
     * Map拼接字符串
     *
     * @param map
     * @return
     */
    public static String linkString(Map<?, ?> map) {
        if (null == map) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        sb.append(map.getClass().getSimpleName()).append("{");
        boolean first = true;
        for (Map.Entry<?, ?> ent : map.entrySet()) {
            if (first) {
                first = false;
            } else {
                sb.append(",");
            }
            sb.append(" ").append(ent.getKey()).append(" = ").append(ent.getValue());
        }
        sb.append("}");
        return sb.toString();
    }

    /**
     * 将数组转换成字符串数组
     *
     * @param objs
     * @param ac
     * @return
     */
    public static String[] convert2StringArray(Object[] objs, ArrayConverter ac) {
        if (null == objs) {
            return null;
        }
        String[] strs = new String[objs.length];
        for (int i = 0; i < objs.length; i++) {
            strs[i] = ac.convert(objs[i]);
        }
        return strs;
    }

    /**
     * 将集合转换成字符串数组
     *
     * @param objs
     * @param ac
     * @return
     */
    public static String[] convert2StringArray(Collection<?> objs, ArrayConverter ac) {
        if (null == objs) {
            return null;
        }
        String[] strs = new String[objs.size()];
        int i = 0;
        for (Object obj : objs) {
            strs[i++] = ac.convert(obj);
        }
        return strs;
    }

    /**
     * 去掉前后的空字符,空串返回""
     *
     * @param string
     * @return
     */
    public static String trim(String string) {
        if (isTrimEmpty(string)) {
            return "";
        }
        return string.trim();
    }

    public static String trimByChar(String string, char c) {
        if (isTrimEmpty(string)) {
            return "";
        }
        int start = 0;
        int end = string.length();
        for (; start < string.length(); start++) {
            if (string.charAt(start) != c) {
                break;
            }
        }
        for (; end > 0; end--) {
            if (string.charAt(end - 1) != c) {
                break;
            }
        }
        if (start > end) {
            return "";
        }
        return string.substring(start, end);
    }

    /**
     * 判断所有对象的字符串是否均不为 null 和 ""
     *
     * @param objs
     * @return
     */
    public static boolean allNotEmpty(Object[] objs) {
        for (Object str : objs) {
            if (isEmpty(toString(str))) return false;
        }
        return true;
    }

    /**
     * 判断所有对象的字符串是否均不为 null 和 ""
     *
     * @param objs
     * @return
     */
    public static boolean allNotEmpty(Collection<?> objs) {
        for (Object str : objs) {
            if (isEmpty(toString(str))) return false;
        }
        return true;
    }

    /**
     * 判断所有对象的字符串是否均不为 null 和 空格字符串
     *
     * @param strs
     * @return
     */
    public static boolean allNotTrimEmpty(Object[] strs) {
        for (Object str : strs) {
            if (isTrimEmpty(toString(str))) return false;
        }
        return true;
    }

    /**
     * 判断所有对象的字符串是否均不为 null 和 空格字符串
     *
     * @param strs
     * @return
     */
    public static boolean allNotTrimEmpty(Collection<?> strs) {
        for (Object str : strs) {
            if (isTrimEmpty(toString(str))) return false;
        }
        return true;
    }

    /**
     * 获取字符串长度，包括中文（中文2字符）
     *
     * @param value
     * @return
     */
    public static int lengthOfGBK(String value) {
        if (value == null) return 0;
        StringBuffer buff = new StringBuffer(value);
        int length = 0;
        String stmp;
        for (int i = 0; i < buff.length(); i++) {
            stmp = buff.substring(i, i + 1);
            try {
                stmp = new String(stmp.getBytes("utf8"));
            } catch (Exception e) {
            }
            if (stmp.getBytes().length > 1) {
                length += 2;
            } else {
                length += 1;
            }
        }
        return length;
    }

    /**
     * 将数组中的字符串进行trim操作，不操作null对象
     *
     * @param strs
     */
    public static String[] trimArrayItems(String[] strs) {
        if (null == strs) {
            return null;
        }
        for (int i = 0; i < strs.length; i++) {
            if (null != strs[i]) {
                strs[i] = strs[i].trim();
            }
        }
        return strs;
    }

    /**
     * 解密字符串数组
     *
     * @param encryptSafe
     * @param strs
     * @return
     */
    public static String[] decryptArray(AESEncryptSafe encryptSafe, String[] strs) throws EncryptException {
        if (null == strs) {
            return null;
        }
        for (int i = 0; i < strs.length; i++) {
            if (null != strs[i]) {
                strs[i] = encryptSafe.decrypt(strs[i]);
            }
        }
        return strs;
    }

    public static interface ArrayConverter {
        public String convert(Object source);
    }

    public static String toGBKLength(String src, int destLen, char fill) {
        StringBuffer sb = new StringBuffer(src);
        int i = lengthOfGBK(sb.toString());
        if (i > destLen) {
            while (i > destLen - 3) {
                sb.delete(sb.length() - 1, sb.length());
                i = lengthOfGBK(sb.toString());
            }
            sb.append("...");
        }
        i = lengthOfGBK(sb.toString());
        while (i < destLen) {
            sb.append(fill);
            i = lengthOfGBK(sb.toString());
        }
        return sb.toString();
    }

    public static String toGBKLength(String src, int destLen) {
        return toGBKLength(src, destLen, ' ');
    }

    public static String toLength(String src, int destLen, char fill, boolean suspensionPoints) {
        if (src.length() == destLen) {
            return src;
        } else if (src.length() > destLen) {
            if (suspensionPoints) {
                src = src.substring(0, destLen - 3) + "...";
            } else {
                src = src.substring(0, destLen);
            }
        } else {
            src = src + repeat(fill + "", destLen - src.length());
        }
        return src;
    }

    public static String toLength(String src, int destLen, char fill) {
        return toLength(src, destLen, fill, true);
    }

    public static String toLength(String src, int destLen) {
        return toLength(src, destLen, ' ');
    }

    /**
     * 将字符串填充或截断成指定长度，从左边进行
     *
     * @param string
     * @param c
     * @param destLen
     * @return
     */
    public static String toLengthByLeft(String string, int destLen, char c) {
        if (string.length() == destLen) {
            return string;
        } else if (string.length() > destLen) {
            return string.substring(string.length() - destLen);
        } else {
            return repeat(String.valueOf(c), destLen - string.length()) + string;
        }
    }

    /**
     * 将字符串填充或截断成指定长度，从左边进行（如果需要将使用空格符填充）
     *
     * @param string
     * @param destLen
     * @return
     */
    public static String toLengthByLeft(String string, int destLen) {
        return toLengthByLeft(string, destLen, ' ');
    }

    public static boolean endBy(String str, String strShort) {
        return (str.lastIndexOf(strShort) == str.length() - 1);
    }

    public static boolean startBy(String str, String strShort) {
        return (str.indexOf(strShort) == 0);
    }

    public static String[] splitByString(String src, String s) {
        if (StringUtil.isEmpty(src)) {
            return new String[]{""};
        }
        StringBuffer sb = new StringBuffer(src);
        ArrayList<String> list = new ArrayList<String>();
        while (sb.length() > 0) {
            int pos = sb.indexOf(s);
            if (pos == -1) {
                list.add(sb.toString());
                break;
            } else if (pos == 0) {
                list.add("");
                sb.delete(0, pos + 1);
            } else {
                list.add(sb.substring(0, pos));
                sb.delete(0, pos + 1);
            }
        }
        return list.toArray(new String[]{});
    }

    public static String handPerLine(String content, Handler handler) {
        StringBuffer sb = new StringBuffer();
        StringReader sr = new StringReader(content);
        BufferedReader br = new BufferedReader(sr);
        String line;
        try {
            while (null != (line = br.readLine())) {
                sb.append(handler.hand(line)).append("\n");
            }
        } catch (IOException e) {
        }
        return sb.toString();
    }

    public static String[] handPerItem(String[] strArr, Handler handler) {
        String[] _strArr = new String[strArr.length];
        for (int i = 0; i < strArr.length; i++)
            _strArr[i] = handler.hand(strArr[i]);
        return _strArr;
    }

    /**
     * 将unicode转成中文
     *
     * @param s
     * @return
     */
    public static String unicodeToGB(String s) {
        StringBuffer sb = new StringBuffer();
        StringTokenizer st = new StringTokenizer(s, "\\u");
        while (st.hasMoreTokens()) {
            sb.append((char) Integer.parseInt(st.nextToken(), 16));
        }
        return sb.toString();
    }

    /**
     * 将字符串中的中文转成unicode
     *
     * @param s
     * @return
     */
    public static String toUnicodeString(String s) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 0 && c <= 255) {
                sb.append(c);
            } else {
                sb.append("\\u" + Integer.toHexString(c).toUpperCase());
            }
        }
        return sb.toString();
    }

    /**
     * 调用obj的toString方法，如果异常则返回""
     *
     * @param o
     * @return
     */
    public static String toString(Object o) {
        if (null == o) return "";
        try {
            return o.toString();
        } catch (Exception e) {
            return "";
        }
    }

    public static String nullTotrim(Object str) {
        return str == null ? "" : String.valueOf(str);
    }
}
