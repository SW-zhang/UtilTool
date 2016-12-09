package com.wang.utils.encrypt;

import com.wang.utils.string.StringUtil;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

public class AES {

    public static final String VIPARA = "1234567890123456";
    public static final String bm = "GBK";

    public static void main(String[] args) throws Exception {

        System.out.println(StringUtil.linkString(Security.getProviders(), ", "));

        String rs = "123456";
        String pwd = "111111";
        AES aes = new AES();
        String s = aes.encrypt(rs, pwd);

        System.out.println(s);

        s = aes.decrypt(rs, s);
        System.out.println(s);
    }

    public String encrypt(String dataPassword, String cleartext) throws Exception {
        IvParameterSpec zeroIv = new IvParameterSpec(VIPARA.getBytes());

        SecretKeySpec key = new SecretKeySpec(getBytes(dataPassword), "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
        byte[] encryptedData = cipher.doFinal(cleartext.getBytes(bm));

        return Base64.encode(encryptedData);
    }

    public String decrypt(String dataPassword, String encrypted) throws Exception {
        byte[] byteMi = Base64.decode(encrypted);
        IvParameterSpec zeroIv = new IvParameterSpec(VIPARA.getBytes());
        SecretKeySpec key = new SecretKeySpec(getBytes(dataPassword), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
        byte[] decryptedData = cipher.doFinal(byteMi);

        return new String(decryptedData, bm);
    }

    private String md5(String plainText) {
        String result = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) i += 256;
                if (i < 16) buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            // result = buf.toString(); //md5 32bit
            // result = buf.toString().substring(8, 24))); //md5 16bit
            result = buf.toString().substring(8, 24);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }

    private byte[] getBytes(String password) {
        return md5(password).getBytes();
    }
}
