package com.wang.utils.file.obj;

public final class EOF {

	public final int COUNT;

	EOF(int count) {
		super();
		COUNT = count;
	}

	@Override
	public String toString() {
		return "END OF FILE";
	}
}
