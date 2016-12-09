package com.wang.utils.http;

import org.apache.http.conn.scheme.SchemeRegistry;

/**
 * HTTPUtil工具设置
 *
 */
public class HTTPSetting {

    private SchemeRegistry schemeRegistry;// 注册scheme
    private int defaultMaxPerRoute;// 对每个指定连接的服务器（指定的ip）可以创建并发的socket数进行访问
    private int maxTotal; // 创建socket的上限
    private int soTimeout; // 读取超时
    private int connectionTimeout; // 连接超时
    private boolean tcpNoDelay;

    /**
     * 构造一个默认注册机的设置对象
     */
    public HTTPSetting() {
        this.defaultInit();
        this.schemeRegistry = SchemeRegistrys.getDefaultSchemeRegistry();
    }

    /**
     * 构造一个指定注册机的设置对象
     *
     * @param schemeRegistry 注册机(可能过SchemeRegistrys获得)
     */
    public HTTPSetting(SchemeRegistry schemeRegistry) {
        this.defaultInit();
        this.schemeRegistry = schemeRegistry;
    }

    private void defaultInit() {
        defaultMaxPerRoute = 20;
        maxTotal = 200;
        soTimeout = 15000;
        connectionTimeout = 15000;
        tcpNoDelay = true;
    }

    public SchemeRegistry getSchemeRegistry() {
        return schemeRegistry;
    }

    public void setSchemeRegistry(SchemeRegistry schemeRegistry) {
        this.schemeRegistry = schemeRegistry;
    }

    public int getDefaultMaxPerRoute() {
        return defaultMaxPerRoute;
    }

    public void setDefaultMaxPerRoute(int defaultMaxPerRoute) {
        this.defaultMaxPerRoute = defaultMaxPerRoute;
    }

    public int getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }

    public int getSoTimeout() {
        return soTimeout;
    }

    public void setSoTimeout(int soTimeout) {
        this.soTimeout = soTimeout;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public boolean isTcpNoDelay() {
        return tcpNoDelay;
    }

    public void setTcpNoDelay(boolean tcpNoDelay) {
        this.tcpNoDelay = tcpNoDelay;
    }
}
