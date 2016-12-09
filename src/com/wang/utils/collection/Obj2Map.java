package com.wang.utils.collection;

import java.util.Map;

public interface Obj2Map {

	public static final Obj2Map simple = new Obj2Map() {
		@Override
		public Map<String, Object> object2Map(Object o) {
			return MapUtil.parseMap(o);
		}
	};

	public Map<String, Object> object2Map(Object o);
}
