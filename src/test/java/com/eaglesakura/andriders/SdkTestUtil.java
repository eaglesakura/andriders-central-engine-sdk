package com.eaglesakura.andriders;

import java.util.UUID;

public class SdkTestUtil {

    public static boolean randBool() {
        return randInteger() % 2 == 0;
    }

    public static byte randInteger() {
        return (byte) ((Math.random() * 255) - 128);
    }

    public static float randFloat() {
        return (float) Math.random();
    }

    public static String randString() {
        return UUID.randomUUID().toString();
    }

    public static String randLargeString() {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < 256; ++i) {
            result.append(randString());
            result.append("-");
        }
        return result.toString();
    }

    public static byte[] randBytes() {
        return new byte[]{
                randInteger(),
                randInteger(),
                randInteger(),
                randInteger(),
                randInteger(),
                randInteger(),
                randInteger(),
                randInteger(),
                randInteger(),
                randInteger(),
                randInteger(),
                randInteger(),
                randInteger(),
        };
    }
}
