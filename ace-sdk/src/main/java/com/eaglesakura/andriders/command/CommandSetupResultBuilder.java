package com.eaglesakura.andriders.command;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;

/**
 * 各種コマンドコマンド完了設定を行う
 */
public class CommandSetupResultBuilder {

    /**
     * サムネイル指定
     */
    public static final String RESULT_EXTRA_THUMBNAIL_ICON = "COMMAND_RESULT_EXTRA_THUMBNAIL_ICON";

    /**
     * アプリごとに一意に指定できる拡張キーを指定する
     */
    public static final String RESULT_EXTRA_APPEXTRAKEY = "COMMAND_RESULT_EXTRA_APPEXTRAKEY";

    /**
     * 識別IDを指定する
     */
    public static final String RESULT_EXTRA_PACKAGE_NAME = "COMMAND_RESULT_EXTRA_PACKAGE_NAME";

    /**
     * コマンドキー
     */
    public static final String RESULT_EXTRA_COMMAND_KEY = "COMMAND_RESULT_EXTRA_COMMAND_KEY";

    /**
     * 格納データ
     */
    private final Intent data = new Intent();

    /**
     * 表示アイコン
     */
    private Bitmap icon;

    private final int resultCode;

    private final Activity activity;

    private String appExtraKey = "nil";

    public CommandSetupResultBuilder(Activity activity, boolean commitOk) {
        this.activity = activity;
        if (commitOk) {
            resultCode = Activity.RESULT_OK;
        } else {
            resultCode = Activity.RESULT_CANCELED;
        }
    }

    /**
     * アイコン設定を行う
     *
     * @param resourceId
     */
    public void icon(int resourceId) {
        try {
            this.icon = BitmapFactory.decodeResource(activity.getResources(), resourceId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * アイコン設定を行う
     *
     * @param image
     */
    public void icon(Bitmap image) {
        this.icon = image;
    }

    /**
     * 識別用のID
     * option
     *
     * @param id
     */
    public void appExtraKey(String id) {
        this.appExtraKey = id;
    }

    private static byte[] encode(Bitmap image) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            image.compress(CompressFormat.PNG, 100, os);
            return os.toByteArray();
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * ビルドを完了し、アプリを終了する
     */
    public void finish() {
        if (resultCode != Activity.RESULT_OK) {
            activity.setResult(resultCode);
            activity.finish();
            return;
        }

        // 不足分を補う
        if (icon == null) {
            PackageManager packageManager = activity.getPackageManager();
            icon = ((BitmapDrawable) activity.getApplicationInfo().loadIcon(packageManager)).getBitmap();
        }

        // アイコン指定
        data.putExtra(RESULT_EXTRA_THUMBNAIL_ICON, encode(icon));
        // 識別ID指定
        data.putExtra(RESULT_EXTRA_APPEXTRAKEY, appExtraKey);
        // パッケージ名
        data.putExtra(RESULT_EXTRA_PACKAGE_NAME, activity.getPackageName());
        // コマンドキー
        data.putExtra(RESULT_EXTRA_COMMAND_KEY, CommandSettingUtil.getKey(activity.getIntent()));

        // リザルト指定
        activity.setResult(resultCode, data);
        activity.finish();
    }
}
