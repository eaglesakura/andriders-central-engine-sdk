package com.eaglesakura.andriders.command;

import com.eaglesakura.andriders.serialize.RawIntent;
import com.eaglesakura.serialize.error.SerializeException;
import com.eaglesakura.util.SerializeUtil;
import com.eaglesakura.util.StringUtil;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

/**
 * デバイス間通信可能なIntent情報を構築する
 */
public class SerializableIntent {
    RawIntent mRawIntent = new RawIntent();

    public static SerializableIntent newActivity(@NonNull Context context, @NonNull Class<? extends Activity> clazz) {
        SerializableIntent result = new SerializableIntent();
        result.mRawIntent.intentType = RawIntent.IntentType.Activity;
        result.mRawIntent.componentName = StringUtil.format("%s/%s", context.getPackageName(), clazz.getName());
        return result;
    }
    public static SerializableIntent newActivity(@NonNull Context context, @NonNull ComponentName componentName) {
        SerializableIntent result = new SerializableIntent();
        result.mRawIntent.intentType = RawIntent.IntentType.Activity;
        result.mRawIntent.componentName = StringUtil.format("%s/%s", componentName.getPackageName(), componentName.getClassName());
        return result;
    }

    public static SerializableIntent newService(@NonNull Context context, @NonNull Class<? extends Service> clazz) {
        SerializableIntent result = new SerializableIntent();
        result.mRawIntent.intentType = RawIntent.IntentType.Activity;
        result.mRawIntent.componentName = StringUtil.format("%s/%s", context.getPackageName(), clazz.getName());
        return result;
    }

    public static SerializableIntent newBroadcast(@NonNull Context context, String action) {
        SerializableIntent result = new SerializableIntent();
        result.mRawIntent.intentType = RawIntent.IntentType.Broadcast;
        result.setAction(action);
        return result;
    }

    public SerializableIntent addCategory(String category) {
        mRawIntent.categories.add(category);
        return this;
    }

    public SerializableIntent setFlags(int flags) {
        mRawIntent.flags = flags;
        return this;
    }

    public SerializableIntent setAction(@NonNull String action) {
        mRawIntent.action = action;
        return this;
    }

    public SerializableIntent setData(@NonNull Uri uri) {
        mRawIntent.data = uri.toString();
        return this;
    }

    public SerializableIntent setAction(String action, Uri uri) {
        setAction(action);
        setData(uri);
        return this;
    }

    public SerializableIntent putExtra(String key, boolean value) {
        mRawIntent.extras.add(new RawIntent.Extra(key, value));
        return this;
    }

    public SerializableIntent putExtra(String key, int value) {
        mRawIntent.extras.add(new RawIntent.Extra(key, value));
        return this;
    }

    public SerializableIntent putExtra(String key, long value) {
        mRawIntent.extras.add(new RawIntent.Extra(key, value));
        return this;
    }

    public SerializableIntent putExtra(String key, float value) {
        mRawIntent.extras.add(new RawIntent.Extra(key, value));
        return this;
    }

    public SerializableIntent putExtra(String key, double value) {
        mRawIntent.extras.add(new RawIntent.Extra(key, value));
        return this;
    }

    public SerializableIntent putExtra(String key, byte[] value) {
        mRawIntent.extras.add(new RawIntent.Extra(key, value));
        return this;
    }

    public SerializableIntent putExtra(String key, String value) {
        mRawIntent.extras.add(new RawIntent.Extra(key, value));
        return this;
    }

    /**
     * データをシリアライズする
     */
    public byte[] serialize() throws SerializeException {
        return SerializeUtil.serializePublicFieldObject(mRawIntent, true);
    }

    /**
     * Intentを得る
     *
     * @param intent シリアライズされたIntent
     */
    public static Intent newIntent(RawIntent intent) {
        Intent result = new Intent();

        if (!StringUtil.isEmpty(intent.componentName)) {
            String[] component = intent.componentName.split("/");
            result.setComponent(new ComponentName(component[0], component[1]));
        }
        if (!StringUtil.isEmpty(intent.action)) {
            result.setAction(intent.action);
        }
        if (!StringUtil.isEmpty(intent.data)) {
            result.setData(Uri.parse(intent.data));
        }

        if (intent.flags != 0) {
            result.addFlags(intent.flags);
        }
        
        for (RawIntent.Extra extra : intent.extras) {
            switch (extra.type) {
                case Boolean:
                    result.putExtra(extra.key, Boolean.valueOf(extra.value));
                    break;
                case String:
                    result.putExtra(extra.key, String.valueOf(extra.value));
                    break;
                case Integer:
                    result.putExtra(extra.key, Integer.valueOf(extra.value));
                    break;
                case Long:
                    result.putExtra(extra.key, Long.valueOf(extra.value));
                    break;
                case Float:
                    result.putExtra(extra.key, Float.valueOf(extra.value));
                    break;
                case Double:
                    result.putExtra(extra.key, Double.valueOf(extra.value));
                    break;
                case ByteArray:
                    result.putExtra(extra.key, StringUtil.toByteArray(extra.value));
                    break;
                default:
                    throw new IllegalStateException();
            }
        }

        return result;
    }
}
