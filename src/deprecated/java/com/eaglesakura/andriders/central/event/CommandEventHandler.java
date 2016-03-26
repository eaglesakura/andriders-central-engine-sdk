package com.eaglesakura.andriders.central.event;

import com.eaglesakura.andriders.central.CentralDataReceiver;
import com.eaglesakura.andriders.command.CommandKey;
import com.eaglesakura.andriders.notification.NotificationData;
import com.eaglesakura.andriders.notification.SoundData;
import com.eaglesakura.andriders.serialize.AcesProtocol.MasterPayload;
import com.eaglesakura.andriders.serialize.CommandProtocol;
import com.eaglesakura.andriders.serialize.CommandProtocol.CommandPayload;
import com.eaglesakura.andriders.serialize.CommandProtocol.TriggerPayload;

import android.content.Intent;

/**
 * ACEsからコマンドを受け取った場合のコマンドハンドリング
 */
public abstract class CommandEventHandler {

    /**
     * 近接コマンドで送信対象が見つからなかった場合、EXTRA-UID指定される
     */
    public static final String PROXIMITY_COMMAND_UID_NO_TARGET = "PROXIMITY_COMMAND_UID_NO_TARGET";

    public CommandEventHandler() {
    }

    /**
     * トリガーコマンドを受け取った
     *
     * @param receiver 受信したレシーバ
     * @param master   マスターデータ
     * @param key      コマンド識別キー
     * @param trigger  トリガー本体
     * @param data     CommandSetupResultBuilder.putExtraで保存した値
     * @see com.eaglesakura.andriders.command.CommandSetupResultBuilder
     */
    public void onTriggerReceived(CentralDataReceiver receiver, MasterPayload master, CommandKey key, TriggerPayload trigger, Intent data) {
    }

    /**
     * 通知リクエストを受け取った
     *
     * @param receiver         受信したレシーバ
     * @param master           マスターデータ
     * @param notificationData 通知データ
     * @param payload          全ペイロード
     */
    @Deprecated
    public void onNotificationReceived(CentralDataReceiver receiver, MasterPayload master, NotificationData notificationData, CommandProtocol.NotificationRequestPayload payload) {
    }

    /**
     * サウンドリクエストを受け取った
     *
     * @param receiver  受信したレシーバ
     * @param master    マスターデータ
     * @param soundData サウンドデータ
     * @param payload   全ペイロード
     */
    @Deprecated
    public void onSoundNotificationReceived(CentralDataReceiver receiver, MasterPayload master, SoundData soundData, CommandProtocol.SoundNotificationPayload payload) {
    }

    /**
     * 不明なコマンドを受け取った
     *
     * @param receiver 受信したレシーバ
     * @param master   マスターデータ
     * @param command  不明なコマンド
     */
    public void onUnknownCommandReceived(CentralDataReceiver receiver, MasterPayload master, CommandPayload command) {
    }
}
