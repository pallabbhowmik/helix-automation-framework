package com.helix.automation.framework.utils;

import java.util.UUID;

public class TestDataGenerator {
    public static String uniqueEmail(String prefix) {
        return prefix + "+" + UUID.randomUUID().toString().substring(0, 8) + "@example.com";
    }

    public static String randomString(int len) {
        StringBuilder b = new StringBuilder();
        for (int i=0;i<len;i++) b.append((char)('a' + (int)(Math.random()*26)));
        return b.toString();
    }
}
