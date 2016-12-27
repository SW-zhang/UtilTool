package com.services.utils.net;

import com.services.utils.type.BytesUtil;

/**
 * IP进制转换工具
 *
 */
public class IpUtil {

    /**
     * 把IP地址转化为字节数组
     *
     * @param ipAddr
     * @return byte[]
     */
    public static byte[] ipToBytes(String ipAddr) {
        byte[] ret = new byte[4];
        try {
            String[] ipArr = ipAddr.split("\\.");
            ret[0] = (byte) (Integer.parseInt(ipArr[0]) & 0xFF);
            ret[1] = (byte) (Integer.parseInt(ipArr[1]) & 0xFF);
            ret[2] = (byte) (Integer.parseInt(ipArr[2]) & 0xFF);
            ret[3] = (byte) (Integer.parseInt(ipArr[3]) & 0xFF);
            return ret;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 字节数组转化为IP
     *
     * @param bytes
     * @return int
     */
    public static String bytesToIp(byte[] bytes) {
        return new StringBuffer().append(bytes[0] & 0xFF)//
                .append('.').append(bytes[1] & 0xFF)//
                .append('.').append(bytes[2] & 0xFF)//
                .append('.').append(bytes[3] & 0xFF)//
                .toString();
    }

    /**
     * 把IP地址转化为int
     *
     * @param ipAddr
     * @return int
     */
    public static int ipToInt(String ipAddr) {
        try {
            return BytesUtil.toInt(ipToBytes(ipAddr));
        } catch (Exception e) {
            throw new IllegalArgumentException(ipAddr + " is invalid IP");
        }
    }

    /**
     * 把int->ip地址
     *
     * @param ipInt
     * @return String
     */
    public static String intToIp(int ipInt) {
        return new StringBuilder().append(((ipInt >> 24) & 0xff))//
                .append('.').append((ipInt >> 16) & 0xff)//
                .append('.').append((ipInt >> 8) & 0xff)//
                .append('.').append((ipInt & 0xff)).toString();
    }

    public static void main(String[] args) {
        System.out.println(ipToInt("156.156.156.156"));
        System.out.println(intToIp(ipToInt("255.255.255.255")));
    }
}
