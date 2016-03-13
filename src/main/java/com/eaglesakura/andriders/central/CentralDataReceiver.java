package com.eaglesakura.andriders.central;

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
    static final String INTENT_ACTION = "com.eaglesakura.andriders.ACTION_CENTRAL_DATA_v2";

    /**
     * 送受信用カテゴリ
     */
    static final String INTENT_CATEGORY = "com.eaglesakura.andriders.CATEGORY_ACE_DATA";

    @NonNull
    final Context mContext;

    final Object lock = new Object();

    final Set<CentralDataHandler> mCentralDataHandlers = new HashSet<>();

    final SensorDataReceiver<RawSensorData.RawHeartrate> mHeartrateReceiver = new SensorDataReceiver<>(this);

    final SensorDataReceiver<RawSensorData.RawSpeed> mSpeedReceiver = new SensorDataReceiver<>(this);

    final SensorDataReceiver<RawSensorData.RawCadence> mCadenceReceiver = new SensorDataReceiver<>(this);

    final SensorDataReceiver<RawLocation> mLocationReceiver = new SensorDataReceiver<>(this);

    public CentralDataReceiver(@NonNull Context context) {
        mContext = context.getApplicationContext();
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


    public void connect() {

    }
}
