package com.eaglesakura.andriders.command;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.eaglesakura.andriders.protocol.CommandProtocol;
import com.eaglesakura.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class RemoteIntentBuilder {

    List<CommandProtocol.IntentExtra> extras = new ArrayList<>();

    CommandProtocol.IntentPayload.Builder intent = CommandProtocol.IntentPayload.newBuilder();

    public RemoteIntentBuilder setAction(String action) {
        intent.setAction(action);
        return this;
    }

    public RemoteIntentBuilder setUri(Uri uri) {
        intent.setDataUri(uri.toString());
        return this;
    }


    /**
     * Intentのフラグを指定する
     * <br>
     * 事前にbootXXXX系メソッドを呼び出さなければならない。
     *
     * @param flags intentのフラグ
     *
     * @return this
     */
    public RemoteIntentBuilder setFlags(int flags) {
        intent.setFlags(flags);
        return this;
    }

    /**
     * Intent.setDataに渡すUriを指定する
     *
     * @param uri Intentに指定するURI
     *
     * @return this
     */
    public RemoteIntentBuilder intentData(Uri uri) {
        intent.setDataUri(uri.toString());
        return this;
    }

    /**
     * {@link Intent#putExtra(String, boolean)}を行う。
     *
     * @param key   Extra
     * @param value value
     *
     * @return this
     */
    public RemoteIntentBuilder putExtra(String key, boolean value) {
        CommandProtocol.IntentExtra.Builder extra = CommandProtocol.IntentExtra.newBuilder();
        extra.setKey(key);
        extra.setType(CommandProtocol.IntentExtra.ValueType.Boolean);
        extra.setValue(String.valueOf(value));
        this.extras.add(extra.build());
        return this;
    }

    /**
     * {@link Intent#putExtra(String, String)}を行う。
     *
     * @param key   Extra
     * @param value value
     *
     * @return this
     */
    public RemoteIntentBuilder putExtra(String key, String value) {
        CommandProtocol.IntentExtra.Builder extra = CommandProtocol.IntentExtra.newBuilder();
        extra.setKey(key);
        extra.setType(CommandProtocol.IntentExtra.ValueType.String);
        extra.setValue(String.valueOf(value));
        this.extras.add(extra.build());
        return this;
    }

    /**
     * {@link Intent#putExtra(String, int)}を行う。
     *
     * @param key   Extra
     * @param value value
     *
     * @return this
     */
    public RemoteIntentBuilder putExtra(String key, int value) {
        CommandProtocol.IntentExtra.Builder extra = CommandProtocol.IntentExtra.newBuilder();
        extra.setKey(key);
        extra.setType(CommandProtocol.IntentExtra.ValueType.Integer);
        extra.setValue(String.valueOf(value));
        this.extras.add(extra.build());
        return this;
    }

    /**
     * {@link Intent#putExtra(String, long)}を行う。
     *
     * @param key   Extra
     * @param value value
     *
     * @return this
     */
    public RemoteIntentBuilder putExtra(String key, long value) {
        CommandProtocol.IntentExtra.Builder extra = CommandProtocol.IntentExtra.newBuilder();
        extra.setKey(key);
        extra.setType(CommandProtocol.IntentExtra.ValueType.Long);
        extra.setValue(String.valueOf(value));
        this.extras.add(extra.build());
        return this;
    }

    /**
     * {@link Intent#putExtra(String, float)}を行う。
     *
     * @param key   Extra
     * @param value value
     *
     * @return this
     */
    public RemoteIntentBuilder putExtra(String key, float value) {
        CommandProtocol.IntentExtra.Builder extra = CommandProtocol.IntentExtra.newBuilder();
        extra.setKey(key);
        extra.setType(CommandProtocol.IntentExtra.ValueType.Float);
        extra.setValue(String.valueOf(value));
        this.extras.add(extra.build());
        return this;
    }

    /**
     * {@link Intent#putExtra(String, double)}を行う。
     *
     * @param key   Extra
     * @param value value
     *
     * @return this
     */
    public RemoteIntentBuilder putExtra(String key, double value) {
        CommandProtocol.IntentExtra.Builder extra = CommandProtocol.IntentExtra.newBuilder();
        extra.setKey(key);
        extra.setType(CommandProtocol.IntentExtra.ValueType.Double);
        extra.setValue(String.valueOf(value));
        this.extras.add(extra.build());
        return this;
    }

    /**
     * {@link Intent#putExtra(String, byte[])}を行う。
     *
     * @param key   Extra
     * @param value value
     *
     * @return this
     */
    public RemoteIntentBuilder putExtra(String key, byte[] value) {
        CommandProtocol.IntentExtra.Builder extra = CommandProtocol.IntentExtra.newBuilder();
        extra.setKey(key);
        extra.setType(CommandProtocol.IntentExtra.ValueType.ByteArray);
        extra.setValue(StringUtil.toString(value));
        this.extras.add(extra.build());
        return this;
    }

    public CommandProtocol.IntentPayload build() {
        if (!extras.isEmpty()) {
            // Intent用データを保存する
            intent.addAllExtras(extras);
        }
        return intent.build();
    }

    /**
     * Byte配列へ変換する
     *
     * @return
     */
    public byte[] toByteArray() {
        return build().toByteArray();
    }

    public static RemoteIntentBuilder newActivity(Context context, Class<? extends Activity> activityClass, @Nullable String action) {
        RemoteIntentBuilder result = new RemoteIntentBuilder();
        result.intent.setType(CommandProtocol.IntentPayload.BootType.Activity);
        if (!StringUtil.isEmpty(action)) {
            result.setAction(action);
        }
        result.intent.setComponentName(String.format("%s/%s", context.getPackageName(), activityClass.getName()));
        return result;
    }

    public static RemoteIntentBuilder newActivity(String packageName, String activityClass, @Nullable String action) {
        RemoteIntentBuilder result = new RemoteIntentBuilder();
        result.intent.setType(CommandProtocol.IntentPayload.BootType.Activity);
        if (!StringUtil.isEmpty(action)) {
            result.setAction(action);
        }
        result.intent.setComponentName(String.format("%s/%s", packageName, activityClass));
        return result;
    }

    public static RemoteIntentBuilder newService(Context context, Class<? extends Service> serviceClass, @Nullable String action) {
        RemoteIntentBuilder result = new RemoteIntentBuilder();
        result.intent.setType(CommandProtocol.IntentPayload.BootType.Service);

        if (!StringUtil.isEmpty(action)) {
            result.setAction(action);
        }
        result.intent.setComponentName(String.format("%s/%s", context.getPackageName(), serviceClass.getName()));
        return result;
    }

    public static RemoteIntentBuilder newBroadcast(String action) {
        RemoteIntentBuilder result = new RemoteIntentBuilder();
        result.intent.setType(CommandProtocol.IntentPayload.BootType.Broadcast);
        result.setAction(action);
        return result;
    }

}
