package com.services.utils.encrypt;

import com.services.utils.Date.DateUtil;
import com.services.utils.md5.MD5Util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * 使用DES加密与解密
 */
public class AESEncryptSafe implements Encrypter {

    public static final String VIPARA = "8*kd)2A5$k/s9Fd5";// "8*kd)2A5$k/s9Fd5";
    public static final String charset = "UTF-8";

    private final IvParameterSpec zeroIv = new IvParameterSpec(VIPARA.getBytes());
    private final SecretKeySpec key;
    private final String type = "AES";

    public static void main(String[] args) throws EncryptException {
        Date d = new Date(1445156508585L);
        System.out.printf(DateUtil.format(d));
        AESEncryptSafe aesEncryptSafe = new AESEncryptSafe("Kijhr3(dwjnJR6");
        System.out.println(aesEncryptSafe.encrypt("{\"device_code\":\"web\",\"mobile\":\"18917001878\",\"password\":\"6ffb60cbf47d332ae9cb8cebf058b47a\",\"rs\":1}"));
        System.out.println(aesEncryptSafe.decrypt("lt+9bWThszOx1sg6kynWdfzgaBa/ONBk9t1DZ2JzHQfcoICCLbPO+6zvsSxAYo764pXzjP0r2JGgfHmP7hzFUK5a96BtR1+uLS7KeZKn7tGeQhVurnxFSvgAEvPBYLKrVbv3Aw4TA2zQoVNL7aAIAg=="));
    }

    // 根据参数生成KEY
    public AESEncryptSafe(String strKey) {
        try {
            key = new SecretKeySpec(MD5Util.getMD516(strKey).toLowerCase().getBytes(charset), type);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String encrypt(String cleartext) {
        try {
            Cipher cipher;
            cipher = Cipher.getInstance("AES/CBC/NoPadding");
            int blockSize = cipher.getBlockSize();
            cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
            byte[] encrypted = cipher.doFinal(fullZero(cleartext, blockSize));
            return Base64.encode(encrypted).trim();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public String decrypt(String encrypted) throws EncryptException {
        try {
            Cipher cipher;
            byte[] byteMi = Base64.decode(encrypted);
            cipher = Cipher.getInstance("AES/CBC/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
            byte[] decryptedData = cipher.doFinal(byteMi);
            return new String(decryptedData, charset).trim();
        } catch (Exception e) {
            throw new EncryptException(e);
        }
    }

    private static byte[] fullZero(String data, int blockSize) throws UnsupportedEncodingException {
        byte[] dataBytes = data.getBytes(charset);
        int plaintextLength = dataBytes.length;
        if (plaintextLength % blockSize != 0) {
            plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
        }
        byte[] plaintext = new byte[plaintextLength];
        System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
        return plaintext;
    }
}
