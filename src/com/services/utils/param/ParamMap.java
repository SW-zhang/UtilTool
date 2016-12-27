package com.services.utils.param;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class ParamMap extends LinkedHashMap<String, List<Param>> {

	private static final long serialVersionUID = 2262792348171074506L;

	public List<Param> add(Param value) {
		List<Param> list = get(value.getName());
		if (null == list) {
			list = new LinkedList<Param>();
			put(value.getName(), list);
		}
		list.add(value);
		return list;
	}

	public Param getParam(String name) {
		if (null == get(name)) {
			return null;
		}
		return get(name).get(0);
	}

	public String getValue(String name) {
		if (null == getParam(name)) {
			return null;
		}
		return getParam(name).getValue();
	}

	public String[] getValues(String name) {
		if (null == get(name)) {
			return null;
		}
		String[] vs = new String[get(name).size()];
		int i = 0;
		for (Param nv : get(name)) {
			vs[i] = nv.getValue();
			i++;
		}
		return vs;
	}
}
