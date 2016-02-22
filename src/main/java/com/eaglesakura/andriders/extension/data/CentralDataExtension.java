package com.eaglesakura.andriders.extension.data;

import com.eaglesakura.andriders.extension.ExtensionSession;
import com.eaglesakura.andriders.extension.internal.CentralDataCommand;
import com.eaglesakura.andriders.extension.internal.ExtensionServerImpl;
import com.eaglesakura.andriders.internal.protocol.IdlExtension;
import com.eaglesakura.andriders.sensor.SensorType;
import com.eaglesakura.android.service.data.Payload;
import com.eaglesakura.util.LogUtil;

import android.location.Location;

/**
 * サイコンのコアデータ生成を拡張する
 */
public class CentralDataExtension {

    final ExtensionServerImpl mServerImpl;

    final ExtensionSession mSession;

    public CentralDataExtension(ExtensionSession session, ExtensionServerImpl serverImpl) {
        mSession = session;
        mServerImpl = serverImpl;
    }

    /**
     * 接続先のMACアドレスを取得する
     * <p/>
     * このアドレスにしたがってデータを得る
     */
    public String getGadgetAddress(SensorType type) {
        mServerImpl.validAcesSession();
        try {
            return mServerImpl.postToClientAsString(CentralDataCommand.CMD_setBleGadgetAddress, type.toString());
        } catch (Exception e) {
            LogUtil.log(e);
        }

        return null;
    }

    /**
     * ユーザーのGPS座標を更新する
     */
    public void setLocation(Location loc) {
        mServerImpl.validAcesSession();

        try {
            IdlExtension.Location idl = new IdlExtension.Location(loc);
            mServerImpl.postToClient(CentralDataCommand.CMD_setLocation, Payload.fromPublicField(idl));
        } catch (Exception e) {
        }
    }

    /**
     * 心拍を更新する
     */
    public void setHeartrate(int bpm) {
        mServerImpl.validAcesSession();

        try {
            IdlExtension.Heartrate idl = new IdlExtension.Heartrate((short) bpm);
            mServerImpl.postToClient(CentralDataCommand.CMD_setHeartrate, Payload.fromPublicField(idl));
        } catch (Exception e) {
            LogUtil.log(e);
        }
    }

    /**
     * S&Cセンサーの情報を更新する
     */
    public void setSpeedAndCadence(float crankRpm, int crankRevolution, float wheelRpm, int wheelRevolution) {
        mServerImpl.validAcesSession();

        try {
            IdlExtension.SpeedAndCadence idl = new IdlExtension.SpeedAndCadence();
            idl.crankRpm = crankRpm;
            idl.crankRevolution = crankRevolution;
            idl.wheelRpm = wheelRpm;
            idl.wheelRevolution = wheelRevolution;
            mServerImpl.postToClient(CentralDataCommand.CMD_setSpeedAndCadence, Payload.fromPublicField(idl));
        } catch (Exception e) {
            LogUtil.log(e);
        }
    }
}
