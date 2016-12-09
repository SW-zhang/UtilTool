package com.wang.utils.param;

public class Param {

	private String name;
	private String value;

	public Param() {
		super();
	}

	public Param(String key, String value) {
		super();
		this.name = key;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String key) {
		this.name = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String toString() {
		return name + "=" + value;
	}

}
