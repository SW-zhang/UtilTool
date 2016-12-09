package com.wang.utils.encrypt;

/**
 * 使用DES加密与解密
 */
public class AESEncrypt extends DESEncrypt {

	public AESEncrypt(String strKey) {
		super(strKey, "AES");
	}

}
