package com.eaglesakura.andriders;

import android.content.Context;

import com.eaglesakura.andriders.protocol.AcesProtocol;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 環境を指定する
 */
public class AcesEnvironment {

    private static final String BASIC_PACKAGE_NAME = "com.eaglesakura.andriders";

    /**
     * ACE Extension Packのファイル拡張子
     */
    public static final String ACE_EXTENTION_PACK_FILE_EXT = ".aep";

    private static String APPLICATION_PACKAGE_NAME = BASIC_PACKAGE_NAME;

    static {
        {
            AcesProtocol.VersionInfo.Builder builder = AcesProtocol.VersionInfo.newBuilder();
            builder.setProtocolVersion(0);
        }
    }


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

        APPLICATION_PACKAGE_NAME = getSelfPackageName(context);
    }

    public static String getSelfPackageName(Context context) {
        String packageName = context.getPackageName();
        if (!packageName.startsWith(BASIC_PACKAGE_NAME)) {
            // 違うアプリの場合はそのまま返す
            return packageName;
        }

        if (packageName.endsWith(".debug")) {
            // debug build
            return BASIC_PACKAGE_NAME + ".debug";
        } else {
            // release build
            return BASIC_PACKAGE_NAME;
        }
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
        if (!appPackageName.startsWith(BASIC_PACKAGE_NAME)) {
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
