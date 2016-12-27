package com.services.utils.cache;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class MapCache<T, E> {

	private final LinkedHashMap<T, CacheBean<E>> map;
	private final Object obj = new Object();
	private final int checkSec;

	public MapCache(int checkSec) {
		this(checkSec, 100);
	}

	public MapCache(int checkSec, int initSize) {
		this.checkSec = checkSec;
		if (checkSec < 1) {
			checkSec = 1;
		}
		map = new LinkedHashMap<T, CacheBean<E>>(initSize);
		init();
	}

	private void init() {
		final Thread t = new Thread() {
			@Override
			public void run() {
				while (true) {
					synchronized (obj) {
						Iterator<Entry<T, CacheBean<E>>> its = map.entrySet().iterator();
						while (its.hasNext()) {
							Entry<T, CacheBean<E>> ent = its.next();
							if (ent.getValue().isExpired()) {
								its.remove();
							}
						}
					}
					try {
						sleep(checkSec * 1000);
					} catch (InterruptedException e) {
						break;
					}
				}
			}
		};
		t.setDaemon(true);
		t.start();
	}

	public void put(T key, E value) {
		put(key, value, 0);
	}

	public void put(T key, E value, int expSec) {
		if (expSec <= 0) {
			expSec = 0;
		}
		CacheBean<E> cb = new CacheBean<E>(value, expSec * 1000);
		synchronized (obj) {
			map.put(key, cb);
		}
	}

	public void mergeExpSec(T key, int expSec) {
		if (expSec <= 0) {
			expSec = 0;
		}
		synchronized (obj) {
			CacheBean<E> cb = map.get(key);
			if (null == cb) return;
			cb.mergeExpMSec(expSec * 1000);
		}
	}

	public E remove(T key) {
		CacheBean<E> cb;
		synchronized (obj) {
			cb = map.remove(key);
		}
		if (null == cb) { return null; }
		return cb.getObj();
	}

	public E get(T key) {
		CacheBean<E> cb;
		synchronized (obj) {
			cb = map.get(key);
		}
		if (null == cb) { return null; }
		return cb.getObj();
	}

	public boolean contains(T key) {
		synchronized (obj) {
			return map.containsKey(key);
		}
	}

	public int size() {
		synchronized (obj) {
			return map.size();
		}
	}

	public void clear() {
		synchronized (obj) {
			map.clear();
		}
	}

}
