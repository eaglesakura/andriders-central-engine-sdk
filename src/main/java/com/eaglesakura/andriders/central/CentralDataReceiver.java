package com.eaglesakura.andriders.central;

import com.eaglesakura.andriders.internal.protocol.RawCentralData;
import com.eaglesakura.andriders.internal.protocol.RawLocation;
import com.eaglesakura.andriders.internal.protocol.RawSensorData;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.HashSet;
import java.util.Set;

/**
 * ACEからの情報をハンドリングする
 */
public class CentralDataReceiver {
    /**
     * Intent経由で送られる場合のデータマスター
     */
    static final String INTENT_EXTRA_MASTER = "INTENT_EXTRA_CENTRAL_DATA";

    /**
     * ACEsの特殊イベント（Service起動、シャットダウン等）を受信する
     */
    static final String INTENT_EXTRA_EVENT = "INTENT_EXTRA_EVENT";

    /**
     * 送受信用Action
     */
    static final String INTENT_ACTION = "com.eaglesakura.andriders.ACTION_UPDATE_CENTRAL_DATA_v2";

    /**
     * 送受信用カテゴリ
     */
    static final String INTENT_CATEGORY = "com.eaglesakura.andriders.CATEGORY_CENTRAL_DATA";

    @NonNull
    final Context mContext;

    private final Object lock = new Object();

    /**
     * 接続済であればtrue
     */
    boolean mConnected;

    final Set<CentralDataHandler> mCentralDataHandlers = new HashSet<>();

    final SensorDataReceiver<RawSensorData.RawHeartrate> mHeartrateReceiver = new SensorDataReceiver<>(this);

    final SensorDataReceiver<RawSensorData.RawSpeed> mSpeedReceiver = new SensorDataReceiver<>(this);

    final SensorDataReceiver<RawSensorData.RawCadence> mCadenceReceiver = new SensorDataReceiver<>(this);

    final SensorDataReceiver<RawLocation> mLocationReceiver = new SensorDataReceiver<>(this);

    public CentralDataReceiver(@NonNull Context context) {
        mContext = context.getApplicationContext();
    }

    public void addHandler(@NonNull CentralDataHandler handler) {
        synchronized (lock) {
            mCentralDataHandlers.add(handler);
        }
    }

    public void addHandler(@NonNull SensorDataReceiver.HeartrateHandler handler) {
        synchronized (lock) {
            mHeartrateReceiver.addHandler(handler);
        }
    }

    public void addHandler(@NonNull SensorDataReceiver.SpeedHandler handler) {
        synchronized (lock) {
            mSpeedReceiver.addHandler(handler);
        }
    }

    public void addHandler(@NonNull SensorDataReceiver.CadenceHandler handler) {
        synchronized (lock) {
            mCadenceReceiver.addHandler(handler);
        }
    }

    public void addHandler(@NonNull SensorDataReceiver.LocationHandler handler) {
        synchronized (lock) {
            mLocationReceiver.addHandler(handler);
        }
    }

    public boolean isConnected() {
        return mConnected;
    }

    public void connect() {
        if (isConnected()) {
            throw new IllegalStateException();
        }

        // TODO 接続処理を実装する


        mConnected = true;
    }

    public void disconnect() {
        if (!isConnected()) {
            throw new IllegalStateException();
        }

        // TODO 切断処理を実装する

        mConnected = false;
    }

    /**
     * セントラル情報をハンドリングする
     */
    public void onReceived(RawCentralData central) {
        synchronized (lock) {
            for (CentralDataHandler handler : mCentralDataHandlers) {
                handler.onReceived(central);
            }

            mLocationReceiver.onReceivedValue(central, central.sensor.location, RawSensorData.getHash(central.sensor.location));
            mSpeedReceiver.onReceivedValue(central, central.sensor.speed, RawSensorData.getHash(central.sensor.speed));
            mHeartrateReceiver.onReceivedValue(central, central.sensor.heartrate, RawSensorData.getHash(central.sensor.heartrate));
            mCadenceReceiver.onReceivedValue(central, central.sensor.cadence, RawSensorData.getHash(central.sensor.cadence));
        }
    }
}
