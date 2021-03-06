package com.eaglesakura.andriders.plugin.data;

import com.eaglesakura.andriders.AceSdkUtil;
import com.eaglesakura.andriders.plugin.connection.PluginConnection;
import com.eaglesakura.andriders.plugin.internal.CentralDataCommand;
import com.eaglesakura.andriders.plugin.internal.PluginServerImpl;
import com.eaglesakura.andriders.sensor.SensorType;
import com.eaglesakura.andriders.serialize.PluginProtocol;
import com.eaglesakura.android.service.data.Payload;

import android.location.Location;
import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * ACEの持つデータを送受信する。
 */
public class CentralEngineSessionData {

    final PluginServerImpl mServerImpl;

    final PluginConnection mConnection;

    public CentralEngineSessionData(@NonNull PluginConnection connection, @NonNull PluginServerImpl serverImpl) {
        mConnection = connection;
        mServerImpl = serverImpl;
    }

    /**
     * 接続先のMACアドレスを取得する
     * <p/>
     * このアドレスにしたがってデータを得る
     */
    @Nullable
    public String getGadgetAddress(@NonNull SensorType type) {
        try {
            return mServerImpl.postToClientAsString(CentralDataCommand.CMD_setBleGadgetAddress, type.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * ホイールの外周サイズ（mm）を取得する。
     *
     * デフォルトは700x23cに合わせ、 2096.0 となる。
     */
    @FloatRange(from = 0.0)
    public float getWheelOuterLength() {
        try {
            return Float.parseFloat(mServerImpl.postToClientAsString(CentralDataCommand.CMD_getWheelOuterLength, null));
        } catch (Exception e) {
            return 2096;
        }
    }

    /**
     * ユーザーのGPS座標を更新する
     */
    public void setLocation(@NonNull Location loc) {
        mServerImpl.validAcesSession();

        try {
            PluginProtocol.SrcLocation idl = new PluginProtocol.SrcLocation(loc);
            Payload payload = new Payload(AceSdkUtil.serializeToByteArray(idl));
            mServerImpl.postToClient(CentralDataCommand.CMD_setLocation, payload);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 心拍を更新する
     */
    public void setHeartrate(@IntRange(from = 1) int bpm) {
        mServerImpl.validAcesSession();

        try {
            PluginProtocol.SrcHeartrate idl = new PluginProtocol.SrcHeartrate((short) bpm);
            Payload payload = new Payload(AceSdkUtil.serializeToByteArray(idl));
            mServerImpl.postToClient(CentralDataCommand.CMD_setHeartrate, payload);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * S&Cセンサーの情報を更新する
     */
    public void setSpeedAndCadence(@FloatRange(from = 0) float crankRpm, @IntRange(from = 0) int crankRevolution, @FloatRange(from = 0) float wheelRpm, @IntRange(from = 0) int wheelRevolution) {
        mServerImpl.validAcesSession();

        try {
            PluginProtocol.SrcSpeedAndCadence idl = new PluginProtocol.SrcSpeedAndCadence();
            idl.crankRpm = crankRpm;
            idl.crankRevolution = crankRevolution;
            idl.wheelRpm = wheelRpm;
            idl.wheelRevolution = wheelRevolution;

            Payload payload = new Payload(AceSdkUtil.serializeToByteArray(idl));
            mServerImpl.postToClient(CentralDataCommand.CMD_setSpeedAndCadence, payload);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
