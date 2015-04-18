package com.eaglesakura.andriders.central.event;

import com.eaglesakura.andriders.central.AcesProtocolReceiver;
import com.eaglesakura.andriders.protocol.AcesProtocol;
import com.eaglesakura.andriders.protocol.ActivityProtocol;

/**
 * ユーザーの活動によって処理を行う
 */
public abstract class ActivityEventHandler {

    /**
     * フィットネスデータを受け取った
     *
     * @param receiver 受信したレシーバ
     * @param master   マスターデータ
     * @param fitness  フィットネスデータ
     */
    public void onFitnessDataReceived(AcesProtocolReceiver receiver, AcesProtocol.MasterPayload master, ActivityProtocol.FitnessPayload fitness) {
    }
}
