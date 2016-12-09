package com.wang.utils.web;

import com.wang.utils.collection.MapUtil;
import com.wang.utils.param.Param;
import com.wang.utils.param.ParamMap;
import com.wang.utils.regex.RegexUtil;
import com.wang.utils.string.StringUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

/**
 * URL 工具类
 *
 */
public class UrlUtil {

    public static final UrlUtil DEFAULT = new UrlUtil();
    public static final UrlUtil UTF8 = new UrlUtil("utf-8");
    public static final UrlUtil GBK = new UrlUtil("gbk");
    public static final UrlUtil GB2312 = new UrlUtil("gb2312");
    public static final UrlUtil ISO8859 = new UrlUtil("iso8859-1");

    private String encoding;

    public UrlUtil(String encoding) {
        this.encoding = encoding;
    }

    public UrlUtil() {
        try {
            encoding = Charset.defaultCharset().name();
            URLDecoder.decode(" ", encoding);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * UNCODE转成指定码
     *
     * @param str
     * @return
     * @throws UnsupportedEncodingException
     */
    public String decode(String str) {
        try {
            return URLDecoder.decode(str, encoding);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 从指定码转成UNCODE码
     *
     * @param str
     * @return
     * @throws UnsupportedEncodingException
     */
    public String encode(String str) {
        try {
            return URLEncoder.encode(str, encoding);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 从GET请求中来的中文
     *
     * @param value
     * @return
     * @throws UnsupportedEncodingException
     */
    public String parseRequestValue(String value) {
        try {
            return new String(value.getBytes("iso-8859-1"), encoding);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 拼接URL
     *
     * @param url    地址
     * @param params 参数
     * @return
     * @throws UnsupportedEncodingException
     */
    public String parseUrl(String url, List<Param> params) {
        StringBuffer sb = new StringBuffer(url);
        int t = sb.indexOf("?");
        if (t != -1) {
            if (t == url.length() - 1) {
                sb.append(parseQueryString(params));
            } else {
                sb.append("&").append(parseQueryString(params));
            }
        } else {
            sb.append("?").append(parseQueryString(params));
        }
        return sb.toString();
    }

    /**
     * 拼接URL参数串：例如 a=12&b=5473&c=457
     *
     * @param params List
     * @return String
     * @throws UnsupportedEncodingException
     */
    public String parseQueryString(List<Param> params) {
        if (null == params || params.size() == 0) {
            return "";
        }
        StringBuffer url = new StringBuffer();

        boolean first = true;
        for (Iterator<Param> i = params.iterator(); i.hasNext(); ) {
            Param param = i.next();
            if (null == param.getValue()) {
                continue;
            }
            String key = param.getName();
            String val = encode(param.getValue());
            if (first) {
                first = false;
            } else {
                url.append("&");
            }
            url.append(key).append("=").append(val);
        }

        return url.toString();
    }

    public String parseQueryString(Map<?, ?> params) {
        if (null == params || params.size() == 0) {
            return "";
        }
        StringBuffer url = new StringBuffer();

        boolean first = true;
        for (Entry<?, ?> ent : params.entrySet()) {
            if (null == ent.getValue()) {
                continue;
            }
            String key = ent.getKey().toString();
            String val = encode(ent.getValue().toString());
            if (first) {
                first = false;
            } else {
                url.append("&");
            }
            url.append(key).append("=").append(val);
        }

        return url.toString();
    }

    public String parseQueryString(Object obj) {
        return parseQueryString(MapUtil.parseMap(obj));
    }

    /**
     * 将地址查询串转换成MAP
     *
     * @param queryString
     * @return
     * @throws UnsupportedEncodingException
     */
    public ParamMap parseQueryParams(String queryString) {
        return parseQueryParams(queryString, false);
    }

    /**
     * 将地址查询串转换成MAP（参数名称转换成小写）
     *
     * @param queryString
     * @param paramNameToLower
     * @return
     * @throws UnsupportedEncodingException
     */
    public ParamMap parseQueryParams(String queryString, boolean paramNameToLower) {
        if (StringUtil.isTrimEmpty(queryString)) {
            return new ParamMap();
        }
        String[] param = queryString.split("&");

        ParamMap map = new ParamMap();
        for (String keyvalue : param) {
            String key = StringUtil.getLeft(keyvalue, "=");
            map.add(new Param(paramNameToLower ? StringUtil.toLowerCase(key) : key, decode(keyvalue.replaceFirst(key + "=", ""))));
        }
        return map;
    }

    /**
     * 从一个URL中提取参数
     *
     * @param url
     * @return
     * @throws UnsupportedEncodingException
     */
    public ParamMap parseQueryMapFromFullUrl(String url) {
        if (StringUtil.isTrimEmpty(url)) {
            return new ParamMap();
        }

        String queryString = StringUtil.getRightOuter(url, "?");
        if (StringUtil.isTrimEmpty(queryString)) {
            return new ParamMap();
        }

        return parseQueryParams(queryString);
    }

    /**
     * 连接URL与页面
     *
     * @param path URL路径
     * @param page 页面
     * @return
     */
    public static String concatUrl(String path, String page) {
        if (StringUtil.endBy(path, "/")) {
            return path + page;
        }
        return path + "/" + page;
    }

    public static String concatQuery(String path, String query) {
        if (path.contains("?")) {
            return path + "&" + query;
        }
        return path + "?" + query;
    }

    public static String removeQueryString(String url) {
        String newUrl = StringUtil.getLeft(url, "?");
        if (null != newUrl) {
            return newUrl;
        }
        return url;
    }

    public static String getQueryString(String url) {
        String query = StringUtil.getRightOuter(url, "?");
        return query;
    }

    /**
     * 是否是正确的URL
     *
     * @param url 链接
     * @return
     */
    public static boolean isUrl(String url) {
        String urlRex = "^(http|https):\\/\\/[a-z0-9_\\-=+\\/#$@\\.,\\(\\)%&\\?]+";
        return RegexUtil.matches(urlRex, url, Pattern.CASE_INSENSITIVE);
    }

    /**
     * 从URL中取得域名
     *
     * @param url
     * @return
     */
    public static String getDomain(String url) {
        String redirect_uri_domain = StringUtil.getSection(url, "://", "/");
        if (null == redirect_uri_domain) {
            redirect_uri_domain = StringUtil.getRight(url, "://");
        }
        if (null == redirect_uri_domain) {
            return null;
        }
        String _redirect_uri_domain_ = StringUtil.getLeft(redirect_uri_domain, ":");
        if (null != _redirect_uri_domain_) {
            return _redirect_uri_domain_;
        }
        return redirect_uri_domain.trim();
    }


    /**
     * 从URL中取得action名
     *
     * @param url
     * @return
     */
    public static String getAction(String url) {
        return StringUtil.getRight(url, "/");
    }

    /**
     * 判断链接是否出自域名列表
     *
     * @param url
     * @param domains
     * @return
     */
    public static boolean urlDomainInArray(String url, String[] domains) {
        String domain = getDomain(url);
        if (null == domain) {
            return false;
        }
        boolean goodUri = false;
        if (null != domain && null != domains) {
            for (String _domain : domains) {
                if (domain.endsWith(_domain)) {
                    goodUri = true;
                    break;
                }
            }
        }
        return goodUri;
    }

    public static void main(String[] args) {
        System.out.println(getDomain("http://weibo.com/a"));
        System.out.println(getAction("http://weibo.com/dqwd/qwd/aasd.wn"));
    }

    /**
     * 编码
     *
     * @return
     */
    public String getEncoding() {
        return encoding;
    }

}
