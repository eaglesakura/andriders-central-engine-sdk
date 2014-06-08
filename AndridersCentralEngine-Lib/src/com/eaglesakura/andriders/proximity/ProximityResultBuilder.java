package com.eaglesakura.andriders.proximity;

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
 * 近接コマンド完了設定を行う
 */
public class ProximityResultBuilder {

    /**
     * サムネイル指定
     */
    public static final String RESULT_EXTRA_THUMBNAIL_ICON = "PROXIMITY_RESULT_EXTRA_THUMBNAIL_ICON";

    /**
     * 識別IDを指定する
     */
    public static final String RESULT_EXTRA_UNIQUE_ID = "PROXIMITY_RESULT_EXTRA_UNIQUE_ID";

    /**
     * 識別IDを指定する
     */
    public static final String RESULT_EXTRA_PACKAGE_NAME = "PROXIMITY_RESULT_EXTRA_PACKAGE_NAME";

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

    private String uniqueId = UUID.randomUUID().toString();

    public ProximityResultBuilder(Activity activity, boolean commitOk) {
        this.activity = activity;
        if (commitOk) {
            resultCode = Activity.RESULT_OK;
        } else {
            resultCode = Activity.RESULT_CANCELED;
        }
    }

    /**
     * アイコン設定を行う
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
     * @param image
     */
    public void icon(Bitmap image) {
        this.icon = image;
    }

    /**
     * 識別用のID
     * option
     * @param id
     */
    public void uniqueId(String id) {
        this.uniqueId = id;
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
        data.putExtra(RESULT_EXTRA_UNIQUE_ID, uniqueId);
        // パッケージ名
        data.putExtra(RESULT_EXTRA_PACKAGE_NAME, activity.getPackageName());

        // リザルト指定
        activity.setResult(resultCode, data);
        activity.finish();
    }
}
