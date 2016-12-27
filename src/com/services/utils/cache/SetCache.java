package com.services.utils.cache;

public class SetCache<T> {

	private MapCache<T, Object> cache;

	public SetCache(int checkSec) {
		cache = new MapCache<T, Object>(checkSec);
	}

	public SetCache(int checkSec, int initSize) {
		cache = new MapCache<T, Object>(checkSec, initSize);
	}

	public void set(T key) {
		cache.put(key, null);
	}

	public void set(T key, int expSec) {
		cache.put(key, null, expSec);
	}

	public boolean contains(T key) {
		return cache.contains(key);
	}

	public int size() {
		return cache.size();
	}

	public void clear() {
		cache.clear();
	}

}
