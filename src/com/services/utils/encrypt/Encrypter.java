package com.services.utils.encrypt;

public interface Encrypter {

	public String encrypt(String content);

	public String decrypt(String content) throws EncryptException;
}
