package com.eaglesakura.andriders.central;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import com.eaglesakura.andriders.command.CommandKey;
import com.eaglesakura.andriders.protocol.AcesProtocol.MasterPayload;
import com.eaglesakura.andriders.protocol.CommandProtocol;
import com.eaglesakura.andriders.protocol.CommandProtocol.CommandPayload;
import com.eaglesakura.andriders.protocol.CommandProtocol.TriggerPayload;

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
     * コマンド設定
     *
     * @param commandTimeSec
     * @param appExtraKey       コマンドに事前指定した一意のID
     */
    public AcesCommandBuilder addProximityCommand(int commandTimeSec, String appExtraKey) {
        return addCommand(CommandKey.fromProximity(commandTimeSec), appExtraKey);
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
        masterBuilder.setCreatedDate(dateToString(new Date()));
        masterBuilder.setSenderPackage(context.getPackageName());
        if (targetPackage != null) {
            masterBuilder.setTargetPackage(targetPackage);
        }

        for (CommandPayload cmd : commands) {
            masterBuilder.addCommandPayloads(cmd);
        }

        masterPayload = masterBuilder.build().toByteArray();

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
        Intent intent = AcesProtocolReceiver.newBroadcastIntent();
        if (masterPayload == null) {
            build();
        }

        // ステータスを付与する
        intent.putExtra(AcesProtocolReceiver.INTENT_EXTRA_MASTER, masterPayload);
        context.sendBroadcast(intent);
    }

    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd-hh:mm:ss.SS");

    /**
     * 指定時刻を文字列に変換する
     * 内容はyyyyMMdd-hh:mm:ss.SSとなる。
     *
     * @param date
     * @return
     */
    public static String dateToString(Date date) {
        return formatter.format(date);
    }

}
