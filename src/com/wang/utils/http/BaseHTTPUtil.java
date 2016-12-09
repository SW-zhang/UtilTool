package com.wang.utils.http;

import com.wang.utils.param.Param;
import com.wang.utils.web.UrlUtil;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.RedirectStrategy;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * HTTP工具类
 *
 */
public class BaseHTTPUtil {

    private PoolingClientConnectionManager poolingClientConnectionManager;

    protected final HTTPSetting httpSetting;

    /**
     * 构造一下HTTP工具对象
     *
     * @param httpSetting 工具设置
     */
    public BaseHTTPUtil(HTTPSetting httpSetting) {
        this.httpSetting = httpSetting;
        poolingClientConnectionManager = new PoolingClientConnectionManager(httpSetting.getSchemeRegistry());
        poolingClientConnectionManager.setDefaultMaxPerRoute(httpSetting.getDefaultMaxPerRoute());
        poolingClientConnectionManager.setMaxTotal(httpSetting.getMaxTotal());
    }

    /**
     * 通过GET请求得到一个响应对象
     *
     * @param header   头设置
     * @param url      请求地址
     * @param params   参数
     * @param encoding 字符编码, 应用于参数封装和返回值
     * @return
     * @throws IOException
     */
    public HTTPResponse getByGet(List<Header> header, String url, List<Param> params, String encoding) throws IOException {
        HttpGet http = newHttpGet(header, url, params, encoding);
        return execute(http, encoding);
    }

    /**
     * 通过GET请求得到一个响应对象
     *
     * @param url      请求地址
     * @param params   参数
     * @param encoding 字符编码, 应用于参数封装和返回值
     * @return
     * @throws IOException
     */
    public HTTPResponse getByGet(String url, List<Param> params, String encoding) throws IOException {
        return getByGet(null, url, params, encoding);
    }

    /**
     * 通过GET请求得到一个响应对象
     *
     * @param url      请求地址
     * @param encoding 字符编码, 应用于参数封装和返回值
     * @return
     * @throws IOException
     */
    public HTTPResponse getByGet(String url, String encoding) throws IOException {
        return getByGet(null, url, null, encoding);
    }

    /**
     * 通过POST请求得到一个响应对象
     *
     * @param header   头设置
     * @param url      请求地址
     * @param params   参数
     * @param encoding 字符编码, 应用于参数封装和返回值
     * @return
     * @throws IOException
     */
    public HTTPResponse getByPost(List<Header> header, String url, List<Param> params, String encoding) throws IOException {
        HttpPost http = newHttpPost(header, url, params, encoding);
        return execute(http, encoding);
    }

    /**
     * 通过POST请求得到一个响应对象
     *
     * @param header   头设置
     * @param url      请求地址
     * @param body     参数
     * @param encoding 字符编码, 应用于参数封装和返回值
     * @return
     * @throws IOException
     */
    public HTTPResponse getByPost(List<Header> header, String url, String body, String encoding) throws IOException {
        HttpPost http = newHttpPost(header, url, body, encoding);
        return execute(http, encoding);
    }

    /**
     * 通过POST请求得到一个响应对象
     *
     * @param url      请求地址
     * @param params   参数
     * @param encoding 字符编码, 应用于参数封装和返回值
     * @return
     * @throws IOException
     */
    public HTTPResponse getByPost(String url, List<Param> params, String encoding) throws IOException {
        return getByPost(null, url, params, encoding);
    }

    /**
     * 通过POST请求得到一个响应对象
     *
     * @param url     请求地址
     * @param body    参数
     * @param charset 字符编码, 应用于参数封装和返回值
     * @return
     * @throws IOException
     */
    public HTTPResponse getByPost(String url, String body, String charset) throws IOException {
        return getByPost(null, url, body, charset);
    }

    /**
     * 通过POST请求得到一个响应对象
     *
     * @param url      请求地址
     * @param encoding 字符编码, 应用于参数封装和返回值
     * @return
     * @throws IOException
     */
    public HTTPResponse getByPost(String url, String encoding) throws IOException {
        return getByPost(null, url, (String) null, encoding);
    }

    private HttpGet newHttpGet(List<Header> header, String url, List<Param> params, String encoding) throws IOException {
        final UrlUtil urlUtil = new UrlUtil(encoding);
        HttpGet http = new HttpGet(urlUtil.parseUrl(url, params));
        // 添加头信息
        if (null != header) {
            for (Header ent : header) {
                http.addHeader(new BasicHeader(ent.getName(), ent.getValue()));
            }
        }
        return http;
    }

    private HttpPost newHttpPost(List<Header> header, String url, List<Param> params, String encoding) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        // 添加头信息
        if (null != header) {
            for (Header ent : header) {
                httpPost.addHeader(new BasicHeader(ent.getName(), ent.getValue()));
            }
        }
        // 封闭参数
        if (null != params) {
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            for (Param ent : params) {
                nvps.add(new BasicNameValuePair(ent.getName(), ent.getValue()));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, encoding));
        }
        return httpPost;
    }

    private HttpPost newHttpPost(List<Header> header, String url, String body, String charset) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        // 添加头信息
        if (null != header) {
            for (Header ent : header) {
                httpPost.addHeader(new BasicHeader(ent.getName(), ent.getValue()));
            }
        }
        // 封闭参数
        if (null != body) {
            httpPost.setEntity(new StringEntity(body, charset));
        }
        return httpPost;
    }

    private HTTPResponse execute(HttpRequestBase http, String encoding) throws IOException, ClientProtocolException {
        HttpClient httpClient = newHttpClient();
        // 请求
        try {
            HttpResponse response = httpClient.execute(http);

            HttpEntity entity = response.getEntity();
            HTTPResponse myResponse = new HTTPResponse(response.getStatusLine().getStatusCode(), encoding, EntityUtils.toByteArray(entity));
            org.apache.http.Header[] apacherHeaders = response.getAllHeaders();
            Header[] headers = new Header[apacherHeaders.length];
            for (int i = 0; i < apacherHeaders.length; i++) {
                headers[i] = new Header(apacherHeaders[i].getName(), apacherHeaders[i].getValue());
            }
            myResponse.setHeaders(headers);
            return myResponse;
        } finally {
            // 释放连接
            http.reset();
        }
    }

    private HttpClient newHttpClient() {
        DefaultHttpClient httpClient = new DefaultHttpClient(poolingClientConnectionManager);// 使用连接池创建连接
        HttpParams httpParams = httpClient.getParams();
        HttpConnectionParams.setSoTimeout(httpParams, httpSetting.getSoTimeout());// 设定连接等待时间
        HttpConnectionParams.setConnectionTimeout(httpParams, httpSetting.getConnectionTimeout());// 设定超时时间
        HttpConnectionParams.setTcpNoDelay(httpParams, httpSetting.isTcpNoDelay());
        httpClient.setRedirectStrategy(new RedirectStrategy() {
            public boolean isRedirected(HttpRequest arg0, HttpResponse arg1, HttpContext arg2) throws ProtocolException {
                return false;
            }

            public HttpUriRequest getRedirect(HttpRequest arg0, HttpResponse arg1, HttpContext arg2) throws ProtocolException {
                return null;
            }
        });
        return httpClient;
    }

    public HTTPSetting getHttpSetting() {
        return httpSetting;
    }


    public HTTPResponse getByPost1(List<Header> header, String url, InputStream inputStream, String encoding) throws IOException {
        HttpPost http = newHttpPost1(header, url, inputStream, encoding);
        return execute(http, encoding);
    }

    private HttpPost newHttpPost1(List<Header> header, String url, InputStream inputStream, String encoding) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        // 添加头信息
        if (null != header) {
            for (Header ent : header) {
                httpPost.addHeader(new BasicHeader(ent.getName(), ent.getValue()));
            }
        }
        // 封闭参数
        if (null != inputStream) {
            byte buffer[] = new byte[1024]; // 定义接受缓冲数组的大小为1Kb

            int len = 0;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((len = inputStream.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            byte[] inputStreanLenth = baos.toByteArray();


//			InputStreamEntity inputEntity = new InputStreamEntity(inputStream,inputStream.available());

//			httpPost.setEntity(inputEntity);
//			inputEntity.setContentType("binary/octet-stream");
            System.out.println("fileStream size11 : " + inputStream.available());
            httpPost.setEntity(new InputStreamEntity(new ByteArrayInputStream(inputStreanLenth), inputStreanLenth.length));
            System.out.println("fileStream size : " + inputStream.available());
        }
        return httpPost;
    }
}

