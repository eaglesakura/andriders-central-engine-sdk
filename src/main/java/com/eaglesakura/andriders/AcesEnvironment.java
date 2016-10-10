package com.eaglesakura.andriders;

import com.eaglesakura.andriders.sdk.BuildConfig;

import android.content.Context;

/**
 * 環境を指定する
 */
public class AcesEnvironment {

    private static final String BASIC_PACKAGE_NAME = BuildConfig.ACE_APPLICATION_ID;

    /**
     * 実行しているACE package nameを変更する。
     * ただし、指定のpackage名から始まらない場合はエラーとする。
     *
     * @param context app context
     */
    static void initialize(Context context) {
        if (!context.getPackageName().startsWith(BASIC_PACKAGE_NAME)) {
            throw new IllegalStateException();
        }
    }

    public static String getAcePackageName() {
        return BASIC_PACKAGE_NAME;
    }
}
