package com.wang.utils.encrypt;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.SecureRandom;

/**
 * 使用DES加密与解密
 */
public class DESEncrypt implements Encrypter {

	private static final BASE64Decoder base64De = new BASE64Decoder();
	private static final BASE64Encoder base64en = new BASE64Encoder();

	private final Key key;
	private final String type;

	// 根据参数生成KEY
	protected DESEncrypt(String strKey, String type) {
		this.type = type;
		try {
			KeyGenerator _generator = KeyGenerator.getInstance(type);
			_generator.init(new SecureRandom(strKey.getBytes()));
			this.key = _generator.generateKey();
			_generator = null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public DESEncrypt(String strKey) {
		this(strKey, "DES");
	}

	// 加密String明文输入,String密文输出
	public String encrypt(String strMing) {
		byte[] byteMing;
		try {
			byteMing = strMing.getBytes("UTF8");
			byte[] byteMi = this.getEncCode(byteMing);
			return base64en.encode(byteMi);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	// 解密:以String密文输入,String明文输出
	public String decrypt(String strMi) {
		try {
			byte[] byteMi = base64De.decodeBuffer(strMi);
			byte[] byteMing = this.getDesCode(byteMi);
			return new String(byteMing, "UTF8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// 加密以byte[]明文输入,byte[]密文输出
	private byte[] getEncCode(byte[] byteS) {
		byte[] byteFina = null;
		Cipher cipher;
		try {
			cipher = Cipher.getInstance(type);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byteFina = cipher.doFinal(byteS);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return byteFina;
	}

	// 解密以byte[]密文输入,以byte[]明文输出
	private byte[] getDesCode(byte[] byteD) {
		Cipher cipher;
		byte[] byteFina = null;
		try {
			cipher = Cipher.getInstance(type);
			cipher.init(Cipher.DECRYPT_MODE, key);
			byteFina = cipher.doFinal(byteD);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return byteFina;
	}

}
