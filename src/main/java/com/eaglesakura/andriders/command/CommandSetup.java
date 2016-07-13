package com.eaglesakura.andriders.command;

import com.eaglesakura.android.util.ImageUtil;
import com.eaglesakura.serialize.error.SerializeException;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

/**
 * コマンドデータの構築を行う
 */
public class CommandSetup {

    /**
     * コマンドアイコン指定
     */
    public static final String EXTRA_ICON = "EXTRA_ICON";

    /**
     * 起動対象Intent
     */
    public static final String EXTRA_SERIALIZED_INTENT = "EXTRA_SERIALIZED_INTENT";

    /**
     * コマンド対応アプリのpackage name
     */
    public static final String EXTRA_PACKAGE_NAME = "EXTRA_PACKAGE_NAME";

    /**
     * コマンドのUniqueKey
     */
    public static final String EXTRA_COMMAND_KEY = "EXTRA_COMMAND_KEY";


    public static class Builder {
        Context mContext;

        Bitmap mIcon;

        SerializableIntent mIntent;

        public static Builder makeActivity(@NonNull Context context, Class<? extends Activity> clazz) {
            return new Builder(context, SerializableIntent.fromActivity(context, clazz));
        }

        public static Builder makeService(@NonNull Context context, Class<? extends Service> clazz) {
            return new Builder(context, SerializableIntent.fromService(context, clazz));
        }

        public static Builder makeBroadcast(@NonNull Context context, String action) {
            return new Builder(context, SerializableIntent.fromBroadcast(context, action));
        }

        private Builder(@NonNull Context context, @NonNull SerializableIntent intent) {
            mContext = context;
            mIntent = intent;
        }

        public Builder setIcon(Bitmap icon) {
            mIcon = icon;
            return this;
        }

        public Builder setIcon(@DrawableRes int resId) {
            mIcon = ImageUtil.decode(mContext, resId);
            return this;
        }

        public Builder setFlags(int flags) {
            mIntent.setFlags(flags);
            return this;
        }

        public Builder setAction(@NonNull String action) {
            mIntent.setAction(action);
            return this;
        }

        public Builder setData(@NonNull Uri uri) {
            mIntent.setData(uri);
            return this;
        }

        public Builder setAction(String action, Uri uri) {
            mIntent.setAction(action, uri);
            return this;
        }

        public Builder putExtra(String key, boolean value) {
            mIntent.putExtra(key, value);
            return this;
        }

        public Builder putExtra(String key, int value) {
            mIntent.putExtra(key, value);
            return this;
        }

        public Builder putExtra(String key, long value) {
            mIntent.putExtra(key, value);
            return this;
        }

        public Builder putExtra(String key, float value) {
            mIntent.putExtra(key, value);
            return this;
        }

        public Builder putExtra(String key, double value) {
            mIntent.putExtra(key, value);
            return this;
        }

        public Builder putExtra(String key, byte[] value) {
            mIntent.putExtra(key, value);
            return this;
        }

        public Builder putExtra(String key, String value) {
            mIntent.putExtra(key, value);
            return this;
        }

        public Builder addCategory(String category) {
            mIntent.addCategory(category);
            return this;
        }

        /**
         * コマンドのセットアップを終了し、ACEの設定画面へ戻る
         */
        public void finish(@NonNull Activity activity) {
            Intent data = new Intent();
            // 不足分を補う
            if (mIcon == null) {
                PackageManager packageManager = mContext.getPackageManager();
                setIcon(((BitmapDrawable) mContext.getApplicationInfo().loadIcon(packageManager)).getBitmap());
            }

            data.putExtra(EXTRA_ICON, ImageUtil.encodePng(mIcon));  // アイコンを指定
            data.putExtra(EXTRA_PACKAGE_NAME, activity.getPackageName());
            data.putExtra(EXTRA_COMMAND_KEY, activity.getIntent().getStringExtra(EXTRA_COMMAND_KEY));

            // 生成されたIntentを指定
            try {
                data.putExtra(EXTRA_SERIALIZED_INTENT, mIntent.serialize());
            } catch (SerializeException e) {
                throw new RuntimeException(e);
            }

            activity.setResult(Activity.RESULT_OK, data);
            activity.finish();
        }
    }
}
