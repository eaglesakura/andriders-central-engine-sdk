package com.eaglesakura.andriders.command;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.eaglesakura.andriders.AcesEnvironment;
import com.eaglesakura.andriders.protocol.CommandProtocol;
import com.eaglesakura.util.StringUtil;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

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
     * 起動対象Intent
     */
    public static final String RESULT_EXTRA_INTENTDATA = "COMMAND_RESULT_EXTRA_INTENTDATA";

    /**
     * Intentを起動させる
     */
    private static final String DEFAULT_COMMAND_LAUNCH_INTENT = "DEFAULT_COMMAND_LAUNCHINTENT";

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

    private CommandProtocol.IntentPayload.Builder intentPayload;
    private List<CommandProtocol.IntentExtra> extras = new ArrayList<>();

    public CommandSetupResultBuilder(Activity activity, boolean commitOk) {
        this.activity = activity;
        if (commitOk) {
            resultCode = Activity.RESULT_OK;
        } else {
            resultCode = Activity.RESULT_CANCELED;
        }

        intentPayload = CommandProtocol.IntentPayload.newBuilder();
        intentPayload.setType(CommandProtocol.IntentPayload.BootType.DataOnly);
    }

    /**
     * アイコン設定を行う
     *
     * @param resourceId
     */
    public CommandSetupResultBuilder icon(int resourceId) {
        try {
            this.icon = BitmapFactory.decodeResource(activity.getResources(), resourceId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * アイコン設定を行う
     *
     * @param image
     */
    public CommandSetupResultBuilder icon(Bitmap image) {
        this.icon = image;
        return this;
    }

    /**
     * 識別用のID
     * option
     *
     * @param id
     */
    public CommandSetupResultBuilder appExtraKey(String id) {
        this.appExtraKey = id;
        return this;
    }

    /**
     * Activityの起動を行わせる
     *
     * @param activityClass 起動対象のActivity
     * @param action        対象Action
     * @return
     */
    public CommandSetupResultBuilder bootActivity(Class<? extends Activity> activityClass, @Nullable String action) {
        intentPayload = CommandProtocol.IntentPayload.newBuilder();
        intentPayload.setType(CommandProtocol.IntentPayload.BootType.Activity);

        if (!StringUtil.isEmpty(action)) {
            intentPayload.setAction(action);
        }
        intentPayload.setComponentName(String.format("%s/%s", this.activity.getPackageName(), activityClass.getName()));
        return this;
    }

    /**
     * Activityの起動を行わせる
     *
     * @param packageName   起動対象のPackage
     * @param activityClass 起動対象のActivity
     * @param action        対象Action
     * @return
     */
    public CommandSetupResultBuilder bootActivity(String packageName, String activityClass, @Nullable String action) {
        intentPayload = CommandProtocol.IntentPayload.newBuilder();
        intentPayload.setType(CommandProtocol.IntentPayload.BootType.Activity);

        if (!StringUtil.isEmpty(action)) {
            intentPayload.setAction(action);
        }
        intentPayload.setComponentName(String.format("%s/%s", packageName, activityClass));
        return this;
    }

    /**
     * Serviceを起動する
     *
     * @param serviceClass
     * @param action
     * @return
     */
    public CommandSetupResultBuilder bootService(Class<? extends Service> serviceClass, @Nullable String action) {
        intentPayload = CommandProtocol.IntentPayload.newBuilder();
        intentPayload.setType(CommandProtocol.IntentPayload.BootType.Service);

        if (!StringUtil.isEmpty(action)) {
            intentPayload.setAction(action);
        }
        intentPayload.setComponentName(String.format("%s/%s", this.activity.getPackageName(), serviceClass.getName()));
        return this;
    }


    /**
     * Broadcastを投げる
     *
     * @param action
     * @return
     */
    public CommandSetupResultBuilder bootBroadcast(@Nullable String action) {
        intentPayload = CommandProtocol.IntentPayload.newBuilder();
        intentPayload.setType(CommandProtocol.IntentPayload.BootType.Broadcast);
        if (!StringUtil.isEmpty(action)) {
            intentPayload.setAction(action);
        }
        return this;
    }

    /**
     * Intentのフラグを指定する
     * <p/>
     * 事前にbootXXXX系メソッドを呼び出さなければならない。
     *
     * @param flags
     */
    public CommandSetupResultBuilder intentFlags(int flags) {
        intentPayload.setFlags(flags);
        return this;
    }

    /**
     * Intent.setDataに渡すUriを指定する
     *
     * @param uri
     */
    public CommandSetupResultBuilder intentData(Uri uri) {
        intentPayload.setDataUri(uri.toString());
        return this;
    }

    public CommandSetupResultBuilder putExtra(String key, boolean value) {
        CommandProtocol.IntentExtra.Builder extra = CommandProtocol.IntentExtra.newBuilder();
        extra.setKey(key);
        extra.setType(CommandProtocol.IntentExtra.ValueType.Boolean);
        extra.setValue(String.valueOf(value));
        this.extras.add(extra.build());
        return this;
    }

    public CommandSetupResultBuilder putExtra(String key, String value) {
        CommandProtocol.IntentExtra.Builder extra = CommandProtocol.IntentExtra.newBuilder();
        extra.setKey(key);
        extra.setType(CommandProtocol.IntentExtra.ValueType.String);
        extra.setValue(String.valueOf(value));
        this.extras.add(extra.build());
        return this;
    }

    public CommandSetupResultBuilder putExtra(String key, int value) {
        CommandProtocol.IntentExtra.Builder extra = CommandProtocol.IntentExtra.newBuilder();
        extra.setKey(key);
        extra.setType(CommandProtocol.IntentExtra.ValueType.Integer);
        extra.setValue(String.valueOf(value));
        this.extras.add(extra.build());
        return this;
    }

    public CommandSetupResultBuilder putExtra(String key, long value) {
        CommandProtocol.IntentExtra.Builder extra = CommandProtocol.IntentExtra.newBuilder();
        extra.setKey(key);
        extra.setType(CommandProtocol.IntentExtra.ValueType.Long);
        extra.setValue(String.valueOf(value));
        this.extras.add(extra.build());
        return this;
    }

    public CommandSetupResultBuilder putExtra(String key, float value) {
        CommandProtocol.IntentExtra.Builder extra = CommandProtocol.IntentExtra.newBuilder();
        extra.setKey(key);
        extra.setType(CommandProtocol.IntentExtra.ValueType.Float);
        extra.setValue(String.valueOf(value));
        this.extras.add(extra.build());
        return this;
    }

    public CommandSetupResultBuilder putExtra(String key, double value) {
        CommandProtocol.IntentExtra.Builder extra = CommandProtocol.IntentExtra.newBuilder();
        extra.setKey(key);
        extra.setType(CommandProtocol.IntentExtra.ValueType.Double);
        extra.setValue(String.valueOf(value));
        this.extras.add(extra.build());
        return this;
    }

    public CommandSetupResultBuilder putExtra(String key, byte[] value) {
        CommandProtocol.IntentExtra.Builder extra = CommandProtocol.IntentExtra.newBuilder();
        extra.setKey(key);
        extra.setType(CommandProtocol.IntentExtra.ValueType.ByteArray);
        extra.setValue(StringUtil.toString(value));
        this.extras.add(extra.build());
        return this;
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
        // コマンドキー
        data.putExtra(RESULT_EXTRA_COMMAND_KEY, AcesTriggerUtil.getKey(activity.getIntent()));

        // Intent
        if (intentPayload.getType() != CommandProtocol.IntentPayload.BootType.DataOnly) {
            if (!extras.isEmpty()) {
                // Intent用データを保存する
                intentPayload.addAllExtras(extras);
            }

            // Intentを処理させる場合には、ACEsが直接処理する必要がある
            data.putExtra(RESULT_EXTRA_INTENTDATA, intentPayload.build().toByteArray());
            data.putExtra(RESULT_EXTRA_APPEXTRAKEY, DEFAULT_COMMAND_LAUNCH_INTENT);
            data.putExtra(RESULT_EXTRA_PACKAGE_NAME, AcesEnvironment.getApplicationPackageName());
        } else {

            if (!extras.isEmpty()) {
                // Intent用データを保存する
                intentPayload.addAllExtras(extras);
                data.putExtra(RESULT_EXTRA_INTENTDATA, intentPayload.build().toByteArray());
            }

            // 識別ID指定
            data.putExtra(RESULT_EXTRA_APPEXTRAKEY, appExtraKey);
            // パッケージ名
            data.putExtra(RESULT_EXTRA_PACKAGE_NAME, activity.getPackageName());
        }

        // リザルト指定
        activity.setResult(resultCode, data);
        activity.finish();
    }
}
