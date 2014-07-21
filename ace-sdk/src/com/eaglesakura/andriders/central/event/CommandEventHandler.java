package com.eaglesakura.andriders.central.event;

import com.eaglesakura.andriders.central.AcesProtocolReceiver;
import com.eaglesakura.andriders.protocol.AcesProtocol.MasterPayload;
import com.eaglesakura.andriders.protocol.CommandProtocol.CommandPayload;
import com.eaglesakura.andriders.protocol.CommandProtocol.TriggerPayload;

/**
 * ACEsからコマンドを受け取った場合のコマンドハンドリング
 */
public class CommandEventHandler {

    public CommandEventHandler() {
    }

    /**
     * 近接コマンドを受け取った
     * @param receiver
     * @param master
     * @param trigger トリガー本体
     * @param commandSec コマンドの受付秒数
     */
    public void onProximityCommandReceived(AcesProtocolReceiver receiver, MasterPayload master, TriggerPayload trigger, int commandSec) {

    }

    /**
     * 不明なコマンドを受け取った
     * @param receiver
     * @param master
     * @param command
     */
    public void onUnknownCommandReceived(AcesProtocolReceiver receiver, MasterPayload master, CommandPayload command) {

    }
}
