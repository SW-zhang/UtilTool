package com.services.utils.encrypt;
/**
 * bcprov-jdk15-146.jar
 * commons-codec-1.4.jar
 * slf4j-api-1.6.1.jar
 * slf4j-log4j12-1.6.1.jar
 * log4j-1.2.15.jar
 */

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

public class TripleDES {

    private static final Logger LOGGER = LoggerFactory.getLogger(TripleDES.class);

    private static final String ALGORITHM = "DESede/ECB/PKCS5Padding";
    private static final String PROVIDER = "BC";

    /**
     * 加密
     *
     * @param hexString
     * @param key
     * @return
     * @throws DecoderException
     * @throws BadPaddingException
     */
    public static String encryptHex(String hexString, byte[] key) throws DecoderException, BadPaddingException {
        byte[] strDES = encrypt(hexString.getBytes(), key);//原文3DES加密
        String passStr = Hex.encodeHexString(strDES);//转换为16进制
        return passStr;
    }

    /**
     * 解密
     *
     * @param hexString
     * @param key
     * @return
     * @throws DecoderException
     * @throws UnsupportedEncodingException
     */
    public static String decryptHex(String hexString, byte[] key) throws DecoderException, UnsupportedEncodingException {
        byte[] src = Hex.decodeHex(hexString.toCharArray());//将16进制转换回来
        byte[] result = decrypt(src, key);//解密
        return new String(result, "UTF-8");
    }

    public static byte[] encrypt(byte[] source, byte[] key) throws BadPaddingException {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM, PROVIDER);
            SecretKey skey = new SecretKeySpec(key, ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, skey);
            return cipher.doFinal(source);
        } catch (GeneralSecurityException exception) {
            LOGGER.error("Encrypt Error:" + exception.getMessage(), exception);
            throw new RuntimeException(exception);
        }
    }

    public static byte[] decrypt(byte[] source, byte[] key) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM, PROVIDER);
            SecretKey skey = new SecretKeySpec(key, ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, skey);
            return cipher.doFinal(source);
        } catch (GeneralSecurityException exception) {
            LOGGER.error("Descrpt Error:" + exception.getMessage(), exception);
            throw new RuntimeException(exception);
        }
    }

    /**
     * SHA-1加密
     *
     * @param str
     * @return
     */
    public static String encodePassword(String str) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            byte[] result = digest.digest(str.getBytes());
            return new String(Hex.encodeHex(result, false));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static void main(String[] args) throws Exception {
        String str = "zhangshuwang1234";//原文

        Security.addProvider(new BouncyCastleProvider());
        byte[] desKey = Hex.decodeHex("e3261d40759b88fc5ba4b15e6b6d5343382dc9536ebe407c".toCharArray());//秘钥

        String passStr = encryptHex(str, desKey);//加密
        String result = decryptHex(passStr, desKey);//解密
        System.out.println("ResultLength:" + result.length() + " id:" + result);

        System.out.println(encodePassword(str));

//		byte[] desKey = Hex.decodeHex("e3261d40759b88fc5ba4b15e6b6d5343382dc9536ebe407c".toCharArray());//秘钥
//		byte[] strDES = encrypt(str.getBytes(), desKey);//原文3DES加密
//		String passStr = Hex.encodeHexString(strDES);//转换为16进制
//		byte[] miStr2 = Hex.decodeHex(passStr.toCharArray());//将16进制转换回来
//		byte[] result = decrypt(miStr2, desKey);//解密
//		System.out.println("ResultLength:" + result.length + " id:" + new String(result, "UTF-8"));
//		result = decryptHex(passStr,desKey);
//		System.out.println(new String(result,"UTF-8"));
    }
}
