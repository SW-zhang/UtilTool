package com.services.utils.http;

import java.io.UnsupportedEncodingException;
import java.util.*;

public class HTTPResponse {

	private int status;
	private String encoding;

	private Map<String, Set<Header>> headers = new LinkedHashMap<String, Set<Header>>();
	private Set<String> names = new LinkedHashSet<String>();
	private byte[] content;

	public HTTPResponse(int status, String encoding, byte[] content) {
		super();
		this.status = status;
		this.encoding = encoding;
		this.content = content;
	}

	void setHeaders(Header[] headers) {
		for (Header h : headers) {
			Set<Header> hSet = this.headers.get(h.getName().toLowerCase());
			if (null == hSet) {
				hSet = new LinkedHashSet<Header>();
				this.headers.put(h.getName().toLowerCase(), hSet);
			}
			hSet.add(h);
			this.names.add(h.getName());
		}
	}

	public Header getHeader(String name) {
		if (null == headers.get(name.toLowerCase())) {
			return null;
		}
		return headers.get(name.toLowerCase()).iterator().next();
	}

	public Set<Header> getHeaders(String name) {
		if (null == headers.get(name.toLowerCase())) {
			return null;
		}
		return Collections.unmodifiableSet(headers.get(name.toLowerCase()));
	}

	public Set<String> getHeaderNames() {
		return Collections.unmodifiableSet(names);
	}

	public byte[] getContent() {
		return content;
	}

	public String getString() throws UnsupportedEncodingException {
		return new String(content, encoding);
	}

	public int getStatus() {
		return status;
	}

}
