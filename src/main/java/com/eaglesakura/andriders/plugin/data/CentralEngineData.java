package com.eaglesakura.andriders.plugin.data;

import com.eaglesakura.andriders.plugin.CentralEngineConnection;
import com.eaglesakura.andriders.plugin.internal.CentralDataCommand;
import com.eaglesakura.andriders.plugin.internal.PluginServerImpl;
import com.eaglesakura.andriders.serialize.PluginProtocol;
import com.eaglesakura.andriders.sensor.SensorType;
import com.eaglesakura.android.service.data.Payload;

import android.location.Location;
import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * ACEの持つデータを送受信する。
 */
public class CentralEngineData {

    final PluginServerImpl mServerImpl;

    final CentralEngineConnection mConnection;

    public CentralEngineData(@NonNull CentralEngineConnection connection, @NonNull PluginServerImpl serverImpl) {
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
        mServerImpl.validAcesSession();
        try {
            return mServerImpl.postToClientAsString(CentralDataCommand.CMD_setBleGadgetAddress, type.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * ユーザーのGPS座標を更新する
     */
    public void setLocation(@NonNull Location loc) {
        mServerImpl.validAcesSession();

        try {
            PluginProtocol.SrcLocation idl = new PluginProtocol.SrcLocation(loc);
            mServerImpl.postToClient(CentralDataCommand.CMD_setLocation, Payload.fromPublicField(idl));
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
            mServerImpl.postToClient(CentralDataCommand.CMD_setHeartrate, Payload.fromPublicField(idl));
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
            mServerImpl.postToClient(CentralDataCommand.CMD_setSpeedAndCadence, Payload.fromPublicField(idl));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
