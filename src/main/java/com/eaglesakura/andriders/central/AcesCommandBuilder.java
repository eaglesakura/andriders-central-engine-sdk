package com.eaglesakura.andriders.central;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import com.eaglesakura.andriders.Environment;
import com.eaglesakura.andriders.command.CommandKey;
import com.eaglesakura.andriders.media.SoundKey;
import com.eaglesakura.andriders.notification.NotificationData;
import com.eaglesakura.andriders.notification.SoundData;
import com.eaglesakura.andriders.protocol.AcesProtocol.MasterPayload;
import com.eaglesakura.andriders.protocol.CommandProtocol;
import com.eaglesakura.andriders.protocol.CommandProtocol.CommandPayload;
import com.eaglesakura.andriders.protocol.CommandProtocol.TriggerPayload;
import com.eaglesakura.android.thread.UIHandler;
import com.eaglesakura.android.util.AndroidUtil;
import com.eaglesakura.util.StringUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * コマンド送信用Util
 */
public class AcesCommandBuilder {

    private final Context context;

    /**
     * 送信したいコマンド一覧
     */
    List<CommandPayload> commands = new ArrayList<CommandProtocol.CommandPayload>();

    /**
     * 送信対象のPackage
     */
    String targetPackage;

    /**
     * ビルドされたMasterデータ
     */
    byte[] masterPayload;

    /**
     * @param context
     */
    public AcesCommandBuilder(Context context) {
        this.context = context.getApplicationContext();
    }

    /**
     * コマンドを直接生成する
     *
     * @param key
     * @param appExtraKey
     * @return
     */
    public AcesCommandBuilder addCommand(CommandKey key, String appExtraKey) {
        CommandPayload.Builder builder = CommandPayload.newBuilder();

        builder.setCommandType(CommandProtocol.CommandType.ExtensionTrigger.name());
        // トリガー情報を指定する
        {
            TriggerPayload.Builder triggerBuilder = TriggerPayload.newBuilder();
            triggerBuilder.setAppExtraKey(appExtraKey);
            triggerBuilder.setKey(key.getKey());
            builder.setExtraPayload(triggerBuilder.build().toByteString());
        }

        commands.add(builder.build());

        return this;
    }

    /**
     * 通知データを追加する
     *
     * @param notificationData
     * @return
     */
    public AcesCommandBuilder addNotification(NotificationData notificationData) {
        CommandPayload.Builder builder = CommandPayload.newBuilder();

        builder.setCommandType(CommandProtocol.CommandType.AcesNotification.name());
        // 通知情報を指定する
        builder.setExtraPayload(notificationData.buildPayload().toByteString());
        commands.add(builder.build());
        return this;
    }

    /**
     * 通知用のサウンドを追加
     *
     * @param soundData
     * @return
     */
    public AcesCommandBuilder addSound(SoundData soundData) {
        CommandPayload.Builder builder = CommandPayload.newBuilder();

        builder.setCommandType(CommandProtocol.CommandType.SoundNotification.name());
        // 通知情報を指定する
        builder.setExtraPayload(soundData.buildPayload().toByteString());
        commands.add(builder.build());
        return this;
    }

    /**
     * 送信対象のPackage名を指定
     *
     * @param targetPackage
     * @return
     */
    public AcesCommandBuilder setTargetPackage(String targetPackage) {
        this.targetPackage = targetPackage;
        return this;
    }

    /**
     * 送信用のマスターデータを生成する
     *
     * @return
     */
    public AcesCommandBuilder build() {
        MasterPayload.Builder masterBuilder = MasterPayload.newBuilder();
        masterBuilder.setUniqueId(UUID.randomUUID().toString());
        masterBuilder.setCreatedDate(StringUtil.toString(new Date()));
        masterBuilder.setSenderPackage(context.getPackageName());
        if (targetPackage != null) {
            masterBuilder.setTargetPackage(targetPackage);
        }

        for (CommandPayload cmd : commands) {
            masterBuilder.addCommandPayloads(cmd);
        }
        masterPayload = masterBuilder.build().toByteArray();

        // compress
        masterPayload = AcesProtocolReceiver.compressMasterPayload(masterPayload);

        return this;
    }

    /**
     * マスターデータを取得する
     *
     * @return
     */
    public byte[] getMasterPayload() {
        return masterPayload;
    }

    /**
     * 他のアプリに投げる
     */
    public void send() {
        if (!AndroidUtil.isUIThread()) {
            UIHandler.postUI(new Runnable() {
                @Override
                public void run() {
                    send();
                }
            });
            return;
        }

        Intent intent = AcesProtocolReceiver.newBroadcastIntent();
        if (masterPayload == null) {
            build();
        }

        // ステータスを付与する
        intent.putExtra(AcesProtocolReceiver.INTENT_EXTRA_MASTER, masterPayload);
        context.sendBroadcast(intent);
    }

    /**
     * 通知用Builderを生成する
     *
     * @param context
     * @return
     */
    public static AcesCommandBuilder newNotificationBuilder(Context context) {
        AcesCommandBuilder builder = new AcesCommandBuilder(context);
        builder.setTargetPackage(Environment.getApplicationPackageName());
        return builder;
    }

    /**
     * サウンドを指定してビルダーを生成する
     *
     * @param context
     * @param key
     * @param reqQueue
     * @return
     */
    public static AcesCommandBuilder newSoundBuilder(Context context, SoundKey key, boolean reqQueue) {
        SoundData data = new SoundData();
        data.setQueue(reqQueue);
        data.setSoundKey(key.getKey());
        return newNotificationBuilder(context).addSound(data);
    }

    /**
     * サウンドを指定してビルダーを生成する
     *
     * @param context
     * @param key
     * @return
     */
    public static AcesCommandBuilder newSoundBuilder(Context context, SoundKey key) {
        return newSoundBuilder(context, key, true);
    }
}