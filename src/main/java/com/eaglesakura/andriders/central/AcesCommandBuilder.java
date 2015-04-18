package com.eaglesakura.andriders.central;

import android.content.Context;
import android.content.Intent;

import com.eaglesakura.andriders.AcesEnvironment;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * コマンド送信用Util
 */
public class AcesCommandBuilder {

    protected final Context context;

    /**
     * 送信したいコマンド一覧
     */
    protected List<CommandPayload> commands = new ArrayList<>();

    /**
     * 送信対象のPackage
     */
    protected String targetPackage;

    /**
     * ビルドされたMasterデータ
     */
    protected byte[] masterPayload;

    /**
     * @param context
     */
    public AcesCommandBuilder(Context context) {
        this.context = context.getApplicationContext();
    }

    /**
     * コマンドを直接生成する
     *
     * @param key         コマンドのキー
     * @param appExtraKey コマンドに付与する情報
     *
     * @return this
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
     * @param notificationData 追加する通知データ
     *
     * @return this
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
     * @param soundData 追加するサウンドデータ
     *
     * @return this
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
     * 送信対象のPackage名を指定する。
     * <br>
     * 指定されたpackage以外ではこのBuilderで生成されたマスターデータを処理しないが、{@link AcesProtocolReceiver#setCheckSelfPackage(boolean)}にfalseを指定した場合は強制的にマスターデータを受け取れる。
     *
     * @param targetPackage 送信対象のパッケージ名
     *
     * @return this
     */
    public AcesCommandBuilder setTargetPackage(String targetPackage) {
        this.targetPackage = targetPackage;
        return this;
    }

    /**
     * 送信用のマスターデータを生成する。
     *
     * @return this
     */
    public AcesCommandBuilder build() {
        MasterPayload.Builder masterBuilder = MasterPayload.newBuilder();
        {

        }
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
     * マスターデータを取得する。
     * <br>
     * {@link MasterPayload#toByteArray()}したbyte配列が返却される。
     *
     * @return マスターデータのprotobuf
     */
    public byte[] getMasterPayload() {
        return masterPayload;
    }

    /**
     * 端末内にMasterPayloadをbroadcastする。
     * <br>
     * 送信されたデータは{@link AcesProtocolReceiver}で受信できる。
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
     * @param context app context
     *
     * @return ACEsが受診するように設定されたbuilder
     */
    public static AcesCommandBuilder newNotificationBuilder(Context context) {
        AcesCommandBuilder builder = new AcesCommandBuilder(context);
        builder.setTargetPackage(AcesEnvironment.getApplicationPackageName());
        return builder;
    }

    /**
     * サウンドを指定してビルダーを生成する
     *
     * @param context  app context
     * @param key      鳴らすサウンドのキー
     * @param reqQueue サウンドキューに登録する場合はtrue、すぐさま鳴らす場合はfalse
     *
     * @return builder
     */
    public static AcesCommandBuilder newSoundBuilder(Context context, SoundKey key, boolean reqQueue) {
        SoundData data = new SoundData();
        data.setQueue(reqQueue);
        data.setSoundKey(key.getKey());
        return newNotificationBuilder(context).addSound(data);
    }

    /**
     * サウンドを指定してビルダーを生成する。
     * <br>
     * 強制的にサウンドキューに登録される。
     *
     * @param context app context
     * @param key     鳴らすサウンドのキー
     *
     * @return builder
     */
    public static AcesCommandBuilder newSoundBuilder(Context context, SoundKey key) {
        return newSoundBuilder(context, key, true);
    }
}
