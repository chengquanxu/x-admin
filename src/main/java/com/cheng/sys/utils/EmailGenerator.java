package com.cheng.sys.utils;

import java.util.Random;

public class EmailGenerator {
    public static String generateRandomEmail(String username) {
        Random random = new Random();
        String[] domains = {"qq.com", "gmail.com", "yahoo.com", "hotmail.com", "outlook.com", "example.com"};

        // 生成用户名部分
        String[] usernameParts = username.split("\\s+"); // 如果用户名包含空格，以空格为分隔符
        StringBuilder email = new StringBuilder();
        for (String part : usernameParts) {
            email.append(part.toLowerCase());
        }

        // 生成随机数字
        for (int i = 0; i < 3; i++) {
            email.append(random.nextInt(10));
        }

        // 生成域名部分
        String domain = domains[random.nextInt(domains.length)];
        email.append("@").append(domain);

        return email.toString();
    }
}

