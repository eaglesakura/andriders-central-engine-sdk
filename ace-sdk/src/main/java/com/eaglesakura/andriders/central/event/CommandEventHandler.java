package com.eaglesakura.andriders.central.event;

import com.eaglesakura.andriders.central.AcesProtocolReceiver;
import com.eaglesakura.andriders.command.CommandKey;
import com.eaglesakura.andriders.protocol.AcesProtocol.MasterPayload;
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
     * コマンドを受け取った
     *
     * @param receiver
     * @param master
     * @param key        コマンド識別キー
     * @param trigger    トリガー本体
     */
    public void onCommandReceived(AcesProtocolReceiver receiver, MasterPayload master, CommandKey key, TriggerPayload trigger) {

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
