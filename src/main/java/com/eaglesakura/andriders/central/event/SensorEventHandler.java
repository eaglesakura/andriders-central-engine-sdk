package com.eaglesakura.andriders.central.event;

import com.eaglesakura.andriders.central.AcesProtocolReceiver;
import com.eaglesakura.andriders.protocol.AcesProtocol.MasterPayload;
import com.eaglesakura.andriders.protocol.SensorProtocol.RawCadence;
import com.eaglesakura.andriders.protocol.SensorProtocol.RawHeartrate;
import com.eaglesakura.andriders.protocol.SensorProtocol.RawSpeed;
import com.eaglesakura.andriders.protocol.SensorProtocol.SensorPayload;

/**
 * センサーイベントに対するハンドリングを行う。
 * 必要なメソッドをオーバーライドして、適宜ハンドリングする。
 */
public abstract class SensorEventHandler {

    public SensorEventHandler() {
    }

    /**
     * スピード処理を行う
     * @param receiver
     * @param master
     * @param speed
     */
    public void onSpeedReceived(AcesProtocolReceiver receiver, MasterPayload master, RawSpeed speed) {

    }

    /**
     * ケイデンス処理を行う
     * @param receiver
     * @param master
     * @param cadence
     */
    public void onCadenceReceived(AcesProtocolReceiver receiver, MasterPayload master, RawCadence cadence) {

    }

    /**
     * 心拍処理を行う
     * @param receiver
     * @param master
     * @param heartrate
     */
    public void onHeartrateReceived(AcesProtocolReceiver receiver, MasterPayload master, RawHeartrate heartrate) {

    }

    /**
     * ハンドリング出来ないセンサーイベントを受け取った
     * @param receiver
     * @param master
     * @param payload
     */
    public void onUnknownSensorReceived(AcesProtocolReceiver receiver, MasterPayload master, SensorPayload payload) {

    }
}
