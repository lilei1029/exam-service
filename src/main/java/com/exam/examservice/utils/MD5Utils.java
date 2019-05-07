package com.exam.examservice.utils;

import java.security.MessageDigest;

/**
 * 功能说明:MD5工具类
 * @author  xfgeng
 * @date 2017-04-24
 */
public class MD5Utils {

    /**
     * 私有构造方法,将该工具类设为单例模式.
     */
    private MD5Utils() {}

    private static final String[] HEX = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

    public static String encode(String signData) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] byteArray = md5.digest(signData.getBytes("utf-8"));
            String passwordMD5 = byteArrayToHexString(byteArray);
            return passwordMD5;
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        return signData;
    }

    /**
     * 将指定byte数组转换成16进制字符串
     * @param byteArray
     */
    private static String byteArrayToHexString(byte[] byteArray) {
        StringBuffer sb = new StringBuffer();
        for (byte b : byteArray) {
            sb.append(byteToHexChar(b));
        }
        return sb.toString();
    }

    private static Object byteToHexChar(byte b) {
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return HEX[d1] + HEX[d2];
    }

    public static void main(String[] args) {
        //String str = "MERCHANTID=123456789&POSID=000000000&BRANCHID=110000000&ORDERID=19991101234&PAYMENT=500.00&CURCODE=01&TXCODE=520100&REMARK1=&REMARK2=&PUB32=30819d300d06092a864886f70d0101";

        String str = "123456";
        System.out.println(encode(str));
        //     594f803b380a41396ed63dca39503542

    }
}
