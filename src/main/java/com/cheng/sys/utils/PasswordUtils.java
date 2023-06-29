package com.cheng.sys.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author : Aaron
 * @date : 2023/6/29 10:33
 */
public class PasswordUtils {



    // 使用Spring Security加密
    private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    // 使用PasswordEncoder对密码进行加密
    public static String encryptPassword(String password) {
        return PASSWORD_ENCODER.encode(password);
    }

//    // 使用MD5算法对密码进行加密，加入随机盐值
//    public static String encryptPassword(String password) {
//        try {
//            // 生成随机盐值
//            SecureRandom random = new SecureRandom();
//            byte[] salt = new byte[16];
//            random.nextBytes(salt);
//
//            // 创建MD5消息摘要对象
//            MessageDigest md = MessageDigest.getInstance("MD5");
//
//            // 将盐值与密码进行组合
//            byte[] passwordBytes = password.getBytes();
//            byte[] saltedPasswordBytes = new byte[passwordBytes.length + salt.length];
//            System.arraycopy(passwordBytes, 0, saltedPasswordBytes, 0, passwordBytes.length);
//            System.arraycopy(salt, 0, saltedPasswordBytes, passwordBytes.length, salt.length);
//
//            // 计算摘要
//            byte[] encryptedBytes = md.digest(saltedPasswordBytes);
//            StringBuilder sb = new StringBuilder();
//
//            for (byte b : encryptedBytes) {
//                sb.append(String.format("%02x", b & 0xff));
//            }
//
//            return sb.toString();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
}
