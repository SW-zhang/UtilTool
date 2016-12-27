package com.services.utils.http;


import com.services.utils.param.Param;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * HTTP工具类
 *
 */
public class HTTPUtil extends BaseHTTPUtil {

    public static final HTTPUtil SimpleUTF8 = new HTTPUtil(new HTTPSetting());

    private final String defaultEncoding;

    /**
     * 构造一下HTTP工具对象
     *
     * @param httpSetting     工具设置
     * @param defaultencoding 默认字符编码
     */
    public HTTPUtil(HTTPSetting httpSetting, String defaultencoding) {
        super(httpSetting);
        this.defaultEncoding = defaultencoding;
    }

    /**
     * 构造一下HTTP工具对象(使用UTF8编码)
     *
     * @param httpSetting 工具设置
     */
    public HTTPUtil(HTTPSetting httpSetting) {
        this(httpSetting, "utf8");
    }

    /**
     * 通过GET请求得到一个响应对象
     *
     * @param header 头设置
     * @param url    请求地址
     * @param params 参数
     * @return
     * @throws IOException
     */
    public HTTPResponse getByGet(List<Header> header, String url, List<Param> params) throws IOException {
        return getByGet(header, url, params, defaultEncoding);
    }

    /**
     * 通过GET请求得到一个响应对象
     *
     * @param url    请求地址
     * @param params 参数
     * @return
     * @throws IOException
     */
    public HTTPResponse getByGet(String url, List<Param> params) throws IOException {
        return getByGet(null, url, params, defaultEncoding);
    }

    /**
     * 通过GET请求得到一个响应对象
     *
     * @param url 请求地址
     * @return
     * @throws IOException
     */
    public HTTPResponse getByGet(String url) throws IOException {
        return getByGet(null, url, null, defaultEncoding);
    }

    /**
     * 通过POST请求得到一个响应对象
     *
     * @param header 头设置
     * @param url    请求地址
     * @param params 参数
     * @return
     * @throws IOException
     */
    public HTTPResponse getByPost(List<Header> header, String url, List<Param> params) throws IOException {
        return getByPost(header, url, params, defaultEncoding);
    }

    /**
     * 通过POST请求得到一个响应对象
     *
     * @param url    请求地址
     * @param params 参数
     * @return
     * @throws IOException
     */
    public HTTPResponse getByPost(String url, List<Param> params) throws IOException {
        return getByPost(null, url, params, defaultEncoding);
    }

    /**
     * 通过POST请求得到一个响应对象
     *
     * @param url 请求地址
     * @return
     * @throws IOException
     */
    public HTTPResponse getByPost(String url) throws IOException {
        return getByPost(null, url, (String) null, defaultEncoding);
    }

    /**
     * 对URL进行GET请求，并返回字符串结果
     *
     * @param url 请求地址
     * @return
     * @throws IOException
     */
    public String getStringByGet(String url) throws IOException {
        return getStringByGet(null, url, null, defaultEncoding);
    }

    /**
     * 对URL进行GET请求，并返回字符串结果
     *
     * @param url    请求地址
     * @param params 参数集合
     * @return
     * @throws IOException
     */
    public String getStringByGet(String url, List<Param> params) throws IOException {
        return getStringByGet(null, url, params, defaultEncoding);
    }

    /**
     * 对URL进行GET请求，并返回字符串结果
     *
     * @param header 头集合
     * @param url    请求地址
     * @param params 参数集合
     * @return
     * @throws IOException
     */
    public String getStringByGet(List<Header> header, String url, List<Param> params) throws IOException {
        return getStringByGet(header, url, params, defaultEncoding);
    }

    /**
     * 对URL进行GET请求，并返回字符串结果
     *
     * @param url      请求地址
     * @param encoding 字符编码, 应用于参数封装和返回值
     * @return
     * @throws IOException
     */
    public String getStringByGet(String url, String encoding) throws IOException {
        return getStringByGet(null, url, null, encoding);
    }

    /**
     * 对URL进行GET请求，并返回字符串结果
     *
     * @param url      请求地址
     * @param params   参数集合
     * @param encoding 字符编码, 应用于参数封装和返回值
     * @return
     * @throws IOException
     */
    public String getStringByGet(String url, List<Param> params, String encoding) throws IOException {
        return getStringByGet(null, url, params, encoding);
    }

    /**
     * 对URL进行GET请求，并返回字符串结果
     *
     * @param header   头集合
     * @param url      请求地址
     * @param params   参数集合
     * @param encoding 字符编码, 应用于参数封装和返回值
     * @return
     * @throws IOException
     */
    public String getStringByGet(List<Header> header, String url, List<Param> params, String encoding) throws IOException {
        HTTPResponse response = getByGet(header, url, params, encoding);
        return response.getString();
    }

    /**
     * *************************************************************************
     * ******************************
     */
    /**
     * 对URL进行POST请求，并返回字符串结果
     *
     * @param url 请求地址
     * @return
     * @throws IOException
     */
    public String getStringByPost(String url) throws IOException {
        return getStringByPost(null, url, (String) null, defaultEncoding);
    }

    /**
     * 对URL进行POST请求，并返回字符串结果
     *
     * @param url    请求地址
     * @param params 参数集合
     * @return
     * @throws IOException
     */
    public String getStringByPost(String url, List<Param> params) throws IOException {
        return getStringByPost(null, url, params, defaultEncoding);
    }

    /**
     * 对URL进行POST请求，并返回字符串结果
     *
     * @param header 头集合
     * @param url    请求地址
     * @param params 参数集合
     * @return
     * @throws IOException
     */
    public String getStringByPost(List<Header> header, String url, List<Param> params) throws IOException {
        return getStringByPost(header, url, params, defaultEncoding);
    }

    /**
     * 对URL进行POST请求，并返回字符串结果
     *
     * @param url      请求地址
     * @param encoding 字符编码, 应用于参数封装和返回值
     * @return
     * @throws IOException
     */
    public String getStringByPost(String url, String encoding) throws IOException {
        return getStringByPost(null, url, (String) null, encoding);
    }

    /**
     * 对URL进行POST请求，并返回字符串结果
     *
     * @param url      请求地址
     * @param params   参数集合
     * @param encoding 字符编码, 应用于参数封装和返回值
     * @return
     * @throws IOException
     */
    public String getStringByPost(String url, List<Param> params, String encoding) throws IOException {
        return getStringByPost(null, url, params, encoding);
    }

    /**
     * 对URL进行POST请求，并返回字符串结果
     *
     * @param url      请求地址
     * @param body     参数集合
     * @param encoding 字符编码, 应用于参数封装和返回值
     * @return
     * @throws IOException
     */
    public String getStringByPost(String url, String body, String encoding) throws IOException {
        return getStringByPost(null, url, body, encoding);
    }

    /**
     * 对URL进行POST请求，并返回字符串结果
     *
     * @param header   头集合
     * @param url      请求地址
     * @param params   参数集合
     * @param encoding 字符编码, 应用于参数封装和返回值
     * @return
     * @throws IOException
     */
    public String getStringByPost(List<Header> header, String url, List<Param> params, String encoding) throws IOException {
        HTTPResponse response = getByPost(header, url, params, encoding);
        return response.getString();
    }

    public String getStringByPost(List<Header> header, String url, String body, String encoding) throws IOException {
        HTTPResponse response = getByPost(header, url, body, encoding);
        return response.getString();
    }


    /**
     * 对URL进行POST请求，并返回byte[]结果
     *
     * @param url 请求地址
     * @return
     * @throws IOException
     */
    public byte[] getBytesByPost(String url) throws IOException {
        return getBytesByPost(null, url, null, defaultEncoding);
    }

    /**
     * 对URL进行POST请求，并返回byte[]结果
     *
     * @param url    请求地址
     * @param params 参数集合
     * @return
     * @throws IOException
     */
    public byte[] getBytesByPost(String url, List<Param> params) throws IOException {
        return getBytesByPost(null, url, params, defaultEncoding);
    }

    /**
     * 对URL进行POST请求，并返回byte[]结果
     *
     * @param header 头集合
     * @param url    请求地址
     * @param params 参数集合
     * @return
     * @throws IOException
     */
    public byte[] getBytesByPost(List<Header> header, String url, List<Param> params) throws IOException {
        return getBytesByPost(header, url, params, defaultEncoding);
    }

    /**
     * 对URL进行POST请求，并返回byte[]结果
     *
     * @param url      请求地址
     * @param encoding 字符编码, 应用于参数封装和返回值
     * @return
     * @throws IOException
     */
    public byte[] getBytesByPost(String url, String encoding) throws IOException {
        return getBytesByPost(null, url, null, encoding);
    }

    /**
     * 对URL进行POST请求，并返回byte[]结果
     *
     * @param url      请求地址
     * @param params   参数集合
     * @param encoding 字符编码, 应用于参数封装和返回值
     * @return
     * @throws IOException
     */
    public byte[] getBytesByPost(String url, List<Param> params, String encoding) throws IOException {
        return getBytesByPost(null, url, params, encoding);
    }

    /**
     * 对URL进行POST请求，并返回byte[]结果
     *
     * @param header   头集合
     * @param url      请求地址
     * @param params   参数集合
     * @param encoding 字符编码, 应用于参数封装和返回值
     * @return
     * @throws IOException
     */
    public byte[] getBytesByPost(List<Header> header, String url, List<Param> params, String encoding) throws IOException {
        HTTPResponse response = getByPost(header, url, params, encoding);
        return response.getContent();
    }

    /**
     * *************************************************************************
     * ******************************
     */
    /**
     * 对URL进行GET请求，并返回byte[]结果
     *
     * @param url 请求地址
     * @return
     * @throws IOException
     */
    public byte[] getBytesByGet(String url) throws IOException {
        return getBytesByGet(null, url, null, defaultEncoding);
    }

    /**
     * 对URL进行GET请求，并返回byte[]结果
     *
     * @param url    请求地址
     * @param params 参数集合
     * @return
     * @throws IOException
     */
    public byte[] getBytesByGet(String url, List<Param> params) throws IOException {
        return getBytesByGet(null, url, params, defaultEncoding);
    }

    /**
     * 对URL进行GET请求，并返回byte[]结果
     *
     * @param header 头集合
     * @param url    请求地址
     * @param params 参数集合
     * @return
     * @throws IOException
     */
    public byte[] getBytesByGet(List<Header> header, String url, List<Param> params) throws IOException {
        return getBytesByGet(header, url, params, defaultEncoding);
    }

    /**
     * 对URL进行GET请求，并返回byte[]结果
     *
     * @param url      请求地址
     * @param encoding 字符编码, 应用于参数封装和返回值
     * @return
     * @throws IOException
     */
    public byte[] getBytesByGet(String url, String encoding) throws IOException {
        return getBytesByGet(null, url, null, encoding);
    }

    /**
     * 对URL进行GET请求，并返回byte[]结果
     *
     * @param url      请求地址
     * @param params   参数集合
     * @param encoding 字符编码, 应用于参数封装和返回值
     * @return
     * @throws IOException
     */
    public byte[] getBytesByGet(String url, List<Param> params, String encoding) throws IOException {
        return getBytesByGet(null, url, params, encoding);
    }

    /**
     * 对URL进行GET请求，并返回byte[]结果
     *
     * @param header   头集合
     * @param url      请求地址
     * @param params   参数集合
     * @param encoding 字符编码, 应用于参数封装和返回值
     * @return
     * @throws IOException
     */
    public byte[] getBytesByGet(List<Header> header, String url, List<Param> params, String encoding) throws IOException {
        HTTPResponse response = getByGet(header, url, params, encoding);
        return response.getContent();
    }

    public String getDefaultEncoding() {
        return defaultEncoding;
    }

    /**
     * 上传文件流
     *
     * @param header
     * @param url
     * @param inputStream
     * @return
     * @throws IOException
     */
    public String getStringByPost(List<Header> header, String url, InputStream inputStream) throws IOException {
        return getStringByPost(header, url, inputStream, defaultEncoding);
    }

    public String getStringByPost(List<Header> header, String url, InputStream inputStream, String encoding) throws IOException {
        HTTPResponse response = getByPost1(header, url, inputStream, encoding);
        return response.getString();

    }
}
