package com.eaglesakura.andriders.extension.data;

import com.eaglesakura.andriders.extension.ExtensionSession;
import com.eaglesakura.andriders.extension.internal.CentralDataCommand;
import com.eaglesakura.andriders.extension.internal.ExtensionServerImpl;
import com.eaglesakura.andriders.protocol.SensorProtocol;
import com.eaglesakura.andriders.protocol.internal.InternalData;
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
    public String getGadgetAddress(SensorProtocol.SensorType type) {
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

        InternalData.IdlLocation.Builder idl = InternalData.IdlLocation.newBuilder();
        idl.setLatitude(loc.getLatitude());
        idl.setLongitude(loc.getLongitude());
        idl.setAltitude(loc.getAltitude());
        idl.setAccuracyMeter(loc.getAccuracy());

        try {
            mServerImpl.postToClient(CentralDataCommand.CMD_setLocation, idl);
        } catch (Exception e) {

        }
    }

    /**
     * 心拍を更新する
     */
    public void setHeartrate(int bpm) {
        mServerImpl.validAcesSession();

        InternalData.IdlHeartrate.Builder idl = InternalData.IdlHeartrate.newBuilder();
        idl.setBpm(bpm);

        try {
            mServerImpl.postToClient(CentralDataCommand.CMD_setHeartrate, idl);
        } catch (Exception e) {
            LogUtil.log(e);
        }
    }

    /**
     * S&Cセンサーの情報を更新する
     */
    public void setSpeedAndCadence(float crankRpm, int crankRevolution, float wheelRpm, int wheelRevolution) {
        mServerImpl.validAcesSession();

        InternalData.IdlSpeedAndCadence.Builder idl = InternalData.IdlSpeedAndCadence.newBuilder();
        idl.setCrankRpm(crankRpm);
        idl.setCrankRevolution(crankRevolution);
        idl.setWheelRpm(wheelRpm);
        idl.setWheelRevolution(wheelRevolution);

        try {
            mServerImpl.postToClient(CentralDataCommand.CMD_setSpeedAndCadence, idl);
        } catch (Exception e) {
            LogUtil.log(e);
        }
    }
}
