package com.eaglesakura.andriders.central.event;

import com.eaglesakura.andriders.central.CentralDataReceiver;
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
     * 速度情報を受け取った
     * <br>
     * 速度はGPS由来である場合と、ケイデンスセンサー（ホイール回転数）由来である場合がある。これはユーザーの設定に依存する。
     *
     * @param receiver 受信したレシーバ
     * @param master   マスターデータ
     * @param speed    速度情報
     */
    public void onSpeedReceived(CentralDataReceiver receiver, MasterPayload master, RawSpeed speed) {
    }

    /**
     * ケイデンス処理を行う
     *
     * @param receiver 受信したレシーバ
     * @param master   マスターデータ
     * @param cadence  ケイデンス情報
     */
    public void onCadenceReceived(CentralDataReceiver receiver, MasterPayload master, RawCadence cadence) {

    }

    /**
     * 心拍処理を行う
     *
     * @param receiver  受信したレシーバ
     * @param master    マスターデータ
     * @param heartrate 心拍情報
     */
    public void onHeartrateReceived(CentralDataReceiver receiver, MasterPayload master, RawHeartrate heartrate) {

    }

    /**
     * ハンドリング出来ないセンサーイベントを受け取った
     *
     * @param receiver 受信したレシーバ
     * @param master   マスターデータ
     * @param payload  センサー情報
     */
    public void onUnknownSensorReceived(CentralDataReceiver receiver, MasterPayload master, SensorPayload payload) {

    }
}
