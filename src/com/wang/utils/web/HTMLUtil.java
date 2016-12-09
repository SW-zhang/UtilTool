package com.wang.utils.web;


import com.wang.utils.string.StringBufferUtil;

public class HTMLUtil {

    public static String toHTML(String s) {
        StringBuffer sb = new StringBuffer(s);
        StringBufferUtil.replaceStrAllNotBack(sb, "&", "&amp;");
        StringBufferUtil.replaceStrAllNotBack(sb, "&", "&amp;");
        StringBufferUtil.replaceStrAllNotBack(sb, " ", "&nbsp;");
        StringBufferUtil.replaceStrAllNotBack(sb, "\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
        StringBufferUtil.replaceStrAllNotBack(sb, ">", "&gt;");
        StringBufferUtil.replaceStrAllNotBack(sb, "<", "&lt;");
        StringBufferUtil.replaceStrAllNotBack(sb, "\"", "&quot;");
        StringBufferUtil.replaceStrAllNotBack(sb, "\r\n", "<br/>");
        StringBufferUtil.replaceStrAllNotBack(sb, "\r", "<br/>");
        StringBufferUtil.replaceStrAllNotBack(sb, "\n", "<br/>");
        return sb.toString();
    }

    public static String toRichHTML(String s) {
        StringBuffer sb = new StringBuffer(s);
        StringBufferUtil.replaceStrAllNotBack(sb, "&", "&amp;");
        StringBufferUtil.replaceStrAllNotBack(sb, " ", "&nbsp;");
        StringBufferUtil.replaceStrAllNotBack(sb, "\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
        StringBufferUtil.replaceStrAllNotBack(sb, ">", "&gt;");
        StringBufferUtil.replaceStrAllNotBack(sb, "<", "&lt;");
        StringBufferUtil.replaceStrAllNotBack(sb, "\"", "&quot;");
        StringBufferUtil.replaceStrAllNotBack(sb, "\r\n", "\n");
        StringBufferUtil.replaceStrAllNotBack(sb, "\r", "<br/>");
        String[] ss = s.split("\\\n");
        StringBuffer sb2 = new StringBuffer();
        for (String _ : ss) {
            sb2.append("<p>").append(_).append("</p>");
        }

        return sb2.toString();
    }

    public static String toQMValue(String s) {
        StringBuffer sb = new StringBuffer(s);
        StringBufferUtil.replaceStrAllNotBack(sb, "'", "\\'");
        StringBufferUtil.replaceStrAllNotBack(sb, "\"", "&quot;");
        return sb.toString();
    }

}
