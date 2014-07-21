package com.eaglesakura.andriders.central.event;

import com.eaglesakura.andriders.central.AcesProtocolReceiver;
import com.eaglesakura.andriders.protocol.AcesProtocol.MasterPayload;
import com.eaglesakura.andriders.protocol.ActivityProtocol.ActivityPayload;
import com.eaglesakura.andriders.protocol.ActivityProtocol.MaxSpeedActivity;

/**
 * ユーザーの活動によって処理を行う
 */
public class ActivityEventHandler {

    /**
     * 最高速度更新メッセージを受信する
     * @param receiver
     * @param maxspeed
     */
    public void onMaxSpeedActivityReceived(AcesProtocolReceiver receiver, MasterPayload master, MaxSpeedActivity maxSpeed) {
    }

    /**
     * 不明な活動イベントを受け取った
     * @param receiver
     * @param master
     * @param activity
     */
    public void onUnknownActivityEventReceived(AcesProtocolReceiver receiver, MasterPayload master, ActivityPayload activity) {

    }
}
