package com.eaglesakura.andriders.central.event;

import com.eaglesakura.andriders.central.AcesProtocolReceiver;
import com.eaglesakura.andriders.command.CommandKey;
import com.eaglesakura.andriders.notification.NotificationData;
import com.eaglesakura.andriders.notification.SoundData;
import com.eaglesakura.andriders.protocol.AcesProtocol.MasterPayload;
import com.eaglesakura.andriders.protocol.CommandProtocol;
import com.eaglesakura.andriders.protocol.CommandProtocol.CommandPayload;
import com.eaglesakura.andriders.protocol.CommandProtocol.TriggerPayload;

/**
 * ACEsからコマンドを受け取った場合のコマンドハンドリング
 */
public class CommandEventHandler {

    /**
     * 近接コマンドで送信対象が見つからなかった場合、EXTRA-UID指定される
     */
    public static final String PROXIMITY_COMMAND_UID_NO_TARGET = "PROXIMITY_COMMAND_UID_NO_TARGET";

    public CommandEventHandler() {
    }

    /**
     * トリガーコマンドを受け取った
     *
     * @param receiver
     * @param master
     * @param key      コマンド識別キー
     * @param trigger  トリガー本体
     */
    public void onTriggerReceived(AcesProtocolReceiver receiver, MasterPayload master, CommandKey key, TriggerPayload trigger) {

    }

    /**
     * 通知リクエストを受け取った
     *
     * @param receiver
     * @param master
     * @param notificationData
     * @param payload
     */
    public void onNotificationReceived(AcesProtocolReceiver receiver, MasterPayload master, NotificationData notificationData, CommandProtocol.NotificationRequestPayload payload) {

    }

    /**
     * サウンドリクエストを受け取った
     *
     * @param receiver
     * @param master
     * @param soundData
     * @param payload
     */
    public void onSoundNotificationReceived(AcesProtocolReceiver receiver, MasterPayload master, SoundData soundData, CommandProtocol.SoundNotificationPayload payload) {

    }

    /**
     * 不明なコマンドを受け取った
     *
     * @param receiver
     * @param master
     * @param command
     */
    public void onUnknownCommandReceived(AcesProtocolReceiver receiver, MasterPayload master, CommandPayload command) {

    }
}
