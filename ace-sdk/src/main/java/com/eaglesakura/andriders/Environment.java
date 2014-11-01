package com.eaglesakura.andriders;

import android.content.Context;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    /**
     * ACEデータ保存用ディレクトリを取得する
     *
     * @return
     */
    public static File getAceDirectory(Context context) {
        // base
        File directory = new File(android.os.Environment.getExternalStorageDirectory(), "andriders");
        final String appPackageName = context.getPackageName();
        if (!appPackageName.equals(APPLICATION_PACKAGE_NAME)) {
            return new File(directory, "app/" + appPackageName);
        } else {
            return new File(directory, "central");
        }
    }

    /**
     * メディア保存用のディレクトリを取得する
     *
     * @param context
     * @return
     */
    public static File getMediaDirectory(Context context) {
        return new File(getAceDirectory(context), "media");
    }

    /**
     * 指定した日に保存するディレクトリを取得する
     *
     * @param context
     * @param date    保存する日付
     * @return
     */
    public static File getDateMediaDirectory(Context context, Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return new File(getMediaDirectory(context), formatter.format(date));
    }
}
