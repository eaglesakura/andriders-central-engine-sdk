package com.eaglesakura.andriders.internal.util;

import java.lang.reflect.Method;
import java.util.UUID;

public class InternalSdkUtil {
    public static boolean randBool() {
        return randInteger() % 2 == 0;
    }

    public static byte randInteger() {
        return (byte) ((Math.random() * 255) - 128);
    }

    public static byte randUnsignedInteger() {
        return (byte) (Math.random() * 127);
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

    public static <T extends Enum> T randEnum(Class<T> clazz) {
        try {
            Method valuesMethod = clazz.getMethod("values");
            T[] values = (T[]) valuesMethod.invoke(clazz);
            if (randUnsignedInteger() % (values.length + 1) == 0) {
                return null;
            } else {
                return values[randUnsignedInteger() % values.length];
            }
        } catch (Exception e) {
            return null;
        }
    }
}
