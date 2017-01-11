package com.eaglesakura.andriders;

import com.eaglesakura.andriders.sdk.BuildConfig;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.support.annotation.NonNull;

/**
 * 環境を指定する
 */
public class AceEnvironment {

    public static final String ANDRIDERS_CENTRAL_ENGINE_PACKAGE_NAME = BuildConfig.ACE_APPLICATION_ID;

    /**
     * 実行しているACE package nameを変更する。
     * ただし、指定のpackage名から始まらない場合はエラーとする。
     *
     * @param context app context
     */
    static void initialize(Context context) {
        if (!context.getPackageName().startsWith(ANDRIDERS_CENTRAL_ENGINE_PACKAGE_NAME)) {
            throw new IllegalStateException();
        }
    }

    /**
     * 動作に必要なバージョンがインストールされている
     */
    public static final int INSTALL_ACE_OK = 0;

    /**
     * インストールされているが、バージョンが古いため動作に支障がある
     */
    public static final int INSTALL_ACE_VERSION_ERROR = 1;

    /**
     * アプリがインストールされていない
     */
    public static final int INSTALL_ACE_NOT_INSTALLED = -1;

    /**
     * Andriders Central Engineがインストールされているかチェックする
     */
    public static int isInstalledACE(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(ANDRIDERS_CENTRAL_ENGINE_PACKAGE_NAME, 0x00);
            // 初回リリースなのでバージョンは関係なくインストールされていればよい。
            return (packageInfo != null ? INSTALL_ACE_OK : INSTALL_ACE_NOT_INSTALLED);
        } catch (Exception e) {
            return INSTALL_ACE_NOT_INSTALLED;
        }
    }

    /**
     * Andriders Central Engineインストール用のIntentを生成する
     * Google Playへのリンクとなる
     */
    public static Intent getAceInstallIntent(@NonNull Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + BuildConfig.ACE_APPLICATION_ID));
        return intent;
    }
}
