package com.x97.util.encode;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

/**
 * Created by yanggch on 2015/6/29.
 */
public class EncryptUtil {
    /**
     * 字符串加密算法
     *
     * @param value     要加密的字符串
     * @param algorithm 算法 MD5 SHA-1 SHA
     * @return
     */
    public static String encrypt(String value, String algorithm) {
        try {
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(algorithm);
            digest.update(value.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String encryptOther(String value, String algorithm) {
        try {
            MessageDigest crypt = MessageDigest.getInstance(algorithm);
            crypt.reset();
            crypt.update(value.getBytes("UTF-8"));
            return byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

}
