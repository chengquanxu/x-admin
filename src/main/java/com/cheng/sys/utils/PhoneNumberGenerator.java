package com.cheng.sys.utils;

import java.util.Random;

public class PhoneNumberGenerator {
    public static String generateRandomPhoneNumber() {
        Random random = new Random();
        // 生成手机号前三位
        String[] prefixList = {
                "130", "131", "132", "133", "134", "135", "136", "137", "138", "139",
                "150", "151", "152", "153", "155", "156", "157", "158", "159",
                "180", "181", "182", "183", "184", "185", "186", "187", "188", "189"
        };
        String prefix = prefixList[random.nextInt(prefixList.length)];

        // 生成后八位随机数字
        StringBuilder phoneNumber = new StringBuilder(prefix);
        for (int i = 0; i < 8; i++) {
            phoneNumber.append(random.nextInt(10));
        }

        return phoneNumber.toString();
    }
}

