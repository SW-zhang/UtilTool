package com.services.utils.cache;

class CacheBean<E> {

	private E obj;
	private long expTime;
	private int expMSec;

	CacheBean(E obj, int mSec) {
		super();
		this.obj = obj;
		this.expMSec = mSec;
		reSetExpTime();
	}

	void mergeExpMSec(int msec) {
		if (expTime < System.currentTimeMillis() + msec) {
			expMSec = msec;
			reSetExpTime();
		}
	}

	void reSetExpTime() {
		expTime = System.currentTimeMillis() + expMSec;
	}

	boolean isExpired() {
		return expMSec != 0 && expTime < System.currentTimeMillis();
	}

	E getObj() {
		return obj;
	}

}