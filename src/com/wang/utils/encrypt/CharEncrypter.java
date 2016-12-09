package com.wang.utils.encrypt;

import java.util.Arrays;

/**
 * 简易加密（码表加密法） <br/>
 * 加密范围[0-9a-zA-Z]，其它字符不做加密
 * 
 */
public class CharEncrypter implements Encrypter {

	public static final String S_0to9 = "0123456789";
	public static final String S_a2z = "abcdefghijklmnopqrstuvwxyz";
	public static final String S_A2Z = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	// 需要转码的码表
	private final char[] baseChars;
	// 转码对应的字符（码）
	private final char[] encryptChars;
	// 码反查字符引用
	private final int[] decryptIndex;

	// 密码
	private String pwd;

	/**
	 * 构造一个简易加密类（无密码）
	 * 
	 * @param baseChars
	 *            基础字符串
	 */
	public CharEncrypter(String baseChars) {
		this(baseChars, "" + HashCodeUtil.hashCode(baseChars, 99));
	}

	/**
	 * 构造一个简易加密类
	 * 
	 * @param baseChars
	 *            基础字符串
	 * @param pwd
	 *            加密密码
	 */
	public CharEncrypter(String baseChars, String pwd) {
		this.baseChars = baseChars.toCharArray();
		this.pwd = pwd;
		this.encryptChars = new char[this.baseChars.length];
		this.decryptIndex = new int[this.baseChars.length];
		this.initDecryptChars();
		this.initDecryptIndex();
	}

	private void initDecryptChars() {
		Arrays.sort(this.baseChars);
		final boolean[] choosedPos = new boolean[baseChars.length];
		Arrays.fill(choosedPos, false);
		char[] ps = pwd.toCharArray();
		int[] hash = new int[pwd.length()];
		for (int i = 0; i < hash.length; i++) {
			hash[i] = HashCodeUtil.hashCode(pwd, ps[i]);
		}
		for (int i = 0; i < baseChars.length; i++) {
			int pos = baseChars[i] * i;
			for (int j = 0; j < hash.length; j++) {
				pos *= hash[j];
			}
			final int rpos = Math.abs(pos) % baseChars.length;
			int tempRpos = rpos;
			while (true) {
				if (tempRpos >= baseChars.length) {
					tempRpos = 0;
					continue;
				}
				if (choosedPos[tempRpos]) {
					tempRpos++;
					continue;
				}
				choosedPos[tempRpos] = true;
				break;
			}
			encryptChars[i] = baseChars[tempRpos];
		}
	}

	private void initDecryptIndex() {
		for (int i = 0; i < encryptChars.length; i++) {
			char c = encryptChars[i];
			decryptIndex[index(c)] = i;
		}
	}

	private int index(char c) {
		return Arrays.binarySearch(baseChars, c);
	}

	/**
	 * 字符对应码
	 * 
	 * @param c
	 * @return c:如果码不存在
	 */
	public char encryptChar(char c) {
		int i = index(c);
		if (i < 0) { return c; }
		return encryptChars[i];
	}

	/**
	 * 码反查
	 * 
	 * @param c
	 * @return c:如果反查不到字符
	 */
	public char decryptChar(char c) {
		int i = index(c);
		if (i < 0) { return c; }
		i = decryptIndex[i];
		return baseChars[i];
	}

	/**
	 * 字符串对应码
	 * 
	 * @param str
	 * @return
	 */
	public String encrypt(String str) {
		char[] chars = str.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			chars[i] = encryptChar(chars[i]);
		}
		return new String(chars);
	}

	/**
	 * 字符串反查
	 * 
	 * @param str
	 * @return
	 */
	public String decrypt(String str) {
		char[] chars = str.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			chars[i] = decryptChar(chars[i]);
		}
		return new String(chars);
	}

}
