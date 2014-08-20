package com.eaglesakura.andriders;

import android.content.Context;

/**
 * 環境を指定する
 */
public class Environment {
    private static String APPLICATION_PACKAGE_NAME = "com.eaglesakura.andriders";

    /**
     * 実行しているACE package nameを変更する。
     * ただし、指定のpackage名から始まらない場合はエラーとする。
     *
     * @param context app context
     */
    static void initialize(Context context) {
        if (!context.getPackageName().startsWith("com.eaglesakura.andriders")) {
            throw new IllegalStateException();
        }

        APPLICATION_PACKAGE_NAME = context.getPackageName();
    }

    public static String getApplicationPackageName() {
        return APPLICATION_PACKAGE_NAME;
    }
}
