package com.joyme.util;


import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;

/**
 * Created by IntelliJ IDEA.
 * User: ericliu
 * Date: 13-7-4
 * Time: 下午1:31
 * To change this template use File | Settings | File Templates.
 */
public class DESUtil {


    // //////////////////////////////////////////////////////////////////////////
    public static String desEncrypt(String key, String info) throws Exception {
        SecureRandom sr = new SecureRandom();
        DESKeySpec dks = new DESKeySpec(key.getBytes());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, sr);
        byte encryptedData[] = cipher.doFinal(info.getBytes("UTF-8"));
        return byte2hex(encryptedData);
    }

    public static String desDecrypt(String key, String info) throws Exception {
        SecureRandom sr = new SecureRandom();
        DESKeySpec dks = new DESKeySpec(key.getBytes());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, sr);
        byte decryptedData[] = cipher.doFinal(hex2byte(info));
        return new String(decryptedData, "utf-8");
    }


    /**
     * 将二进制转化为16进制字符串
     *
     * @param b 二进制字节数组
     * @return String
     */
    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
    }

    /**
     * 十六进制字符串转化为2进制
     *
     * @return
     */
    public static byte[] hex2byte(String b) throws UnsupportedEncodingException {
        byte[] bytes = b.getBytes("UTF-8");
        if ((bytes.length % 2) != 0) {
            throw new IllegalArgumentException("长度不是偶数");
        }

        byte[] b2 = new byte[bytes.length / 2];
        for (int n = 0; n < bytes.length; n += 2) {
            String item = new String(bytes, n, 2);
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }

        return b2;
    }

    /**
     * 将两个ASCII字符合成一个字节； 如："EF"--> 0xEF
     *
     * @param src0 byte
     * @param src1 byte
     * @return byte
     */
    public static byte uniteBytes(byte src0, byte src1) {
        byte _b0 = Byte.decode("0x" + new String(new byte[]{src0}))
                .byteValue();
        _b0 = (byte) (_b0 << 4);
        byte _b1 = Byte.decode("0x" + new String(new byte[]{src1}))
                .byteValue();
        byte ret = (byte) (_b0 ^ _b1);
        return ret;
    }

    public static void main(String[] args) throws Exception {
        String time = System.currentTimeMillis() + "";
        String uno = "cc25f842-b280-4644-9ce5-be73e4656605";

        String appkey = "c80e236f0db6d04e8a0ae0002737df39";// 2AuLF0BeJceb2wHoTsccWb
        String encryptParam2 = DESUtil.desEncrypt(appkey, uno + time);//加密

        System.out.println(time);
        System.out.println(encryptParam2);//加密后的字符串

        // String realPwd = DESUtil.desDecrypt(appkey, encryptParam2).replaceAll(longTime, "").trim();//解密
        //  System.out.println(realPwd);
    }
}
