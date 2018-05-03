package com.sdyijia.wxapp.util;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.Sha512Hash;

/**
 * Created by sunxyz on 2017/3/17.
 */
public final class EncryptionUtils {

    public static final String salt = "LLOIP_OP_1688@SJ";

    private EncryptionUtils() {
    }

    /**
     * 获取source的MD5加密
     * @param source    要加密的字符串
     * @return
     */
    public static String getMd5(String source) {
        String hex = new Md5Hash(source + salt).toHex();
        return hex;
    }

    /**
     * 获取source 的MD5加密
     * @param source    要加密的字符串
     * @param salt      掩码
     * @return
     */
    public static String getMd5(String source,String salt) {
        String hex = new Md5Hash(source + salt).toHex();
        return hex;
    }

    /**
     * 获取password的Sha512Hash加密
     * @param password  要加密的字符串
     * @return
     */
    public static String getSha512Hash(String password) {
        String encodedPassword = new Sha512Hash(password, salt).toBase64();
        return encodedPassword;
    }

    /**
     * 获取password的Sha512Hash加密
     * @param password      要加密的字符串
     * @param salt          掩码
     * @return
     */
    public static String getSha512Hash(String password,String salt) {
        String encodedPassword = new Sha512Hash(password, salt).toBase64();
        return encodedPassword;
    }


    public static void main(String[] args) {
        System.out.println("   ".trim().equals(""));
    }
}
