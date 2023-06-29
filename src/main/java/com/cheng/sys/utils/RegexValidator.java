package com.cheng.sys.utils;

import java.util.regex.Pattern;

/**
 * @author : Aaron
 * @date : 2023/6/29 11:41
 */
public class RegexValidator {
    public static boolean matches(String input, String regex) {
        return Pattern.matches(regex, input);
    }

    // 校验用户名
    private static final String USERNAME_PATTERN = "^[a-zA-Z0-9_-]{3,16}$";

    public static boolean isValidUsername(String username) {
        return Pattern.matches(username,USERNAME_PATTERN);
    }

    // 校验手机号
    public static boolean isValidPhoneNumber(String phoneNumber) {
        String regex = "^1[3456789]\\d{9}$";
        return matches(phoneNumber, regex);
    }

    // 校验邮箱
    public static boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return matches(email, regex);
    }

    // 校验密码（包含大小写字母、数字和特殊字符，长度至少8位）
    public static boolean isValidPassword(String password) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$";
        return matches(password, regex);
    }

}
