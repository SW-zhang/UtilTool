package com.wang.utils.http;

import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

/**
 * 注册机
 * 
 */
public class SchemeRegistrys {

	/**
	 * 得到一个无难的HTTPS的协议注册机
	 * 
	 * @return
	 */
	public static SchemeRegistry getNoVerifierSSLSchemeRegistry() {
		SchemeRegistry registry = new SchemeRegistry();// 创建schema
		SSLContext sslContext = null;// https类型的消息访问
		try {
			X509TrustManager tm = new X509TrustManager() {
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				public void checkServerTrusted(X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {
				}

				public void checkClientTrusted(X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {
				}
			};
			sslContext = SSLContext.getInstance("SSL");
			sslContext.init(null, new TrustManager[] { tm }, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		SSLSocketFactory sslFactory = new SSLSocketFactory(sslContext, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		// http 80端口
		registry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
		// https 443端口
		registry.register(new Scheme("https", 443, sslFactory));
		return registry;
	}

	/**
	 * 得到一个HTTP注册机
	 * 
	 * @return
	 */
	public static SchemeRegistry getDefaultSchemeRegistry() {
		SchemeRegistry registry = new SchemeRegistry();// 创建schema
		// 注册 http 80端口
		registry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
		return registry;
	}

}
