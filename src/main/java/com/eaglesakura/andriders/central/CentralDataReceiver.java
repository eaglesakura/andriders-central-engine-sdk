package com.eaglesakura.andriders.central;

import com.eaglesakura.andriders.serialize.RawCentralData;
import com.eaglesakura.andriders.serialize.RawLocation;
import com.eaglesakura.andriders.serialize.RawSensorData;
import com.eaglesakura.serialize.error.SerializeException;
import com.eaglesakura.util.SerializeUtil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;

import java.util.HashSet;
import java.util.Set;

/**
 * ACEからの情報をハンドリングする
 */
public class CentralDataReceiver {
    /**
     * Intent経由で送られるCentralData
     */
    public static final String INTENT_EXTRA_CENTRAL_DATA = "INTENT_EXTRA_CENTRAL_DATA";

    /**
     * 送受信用Action
     */
    public static final String INTENT_ACTION = "com.eaglesakura.andriders.ACTION_UPDATE_CENTRAL_DATA_v2";

    /**
     * 送受信用カテゴリ
     */
    public static final String INTENT_CATEGORY = "com.eaglesakura.andriders.CATEGORY_CENTRAL_DATA";

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

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!INTENT_ACTION.equals(intent.getAction())) {
                return;
            }

            byte[] centralBuffer = intent.getByteArrayExtra(INTENT_EXTRA_CENTRAL_DATA);
            try {
                if (centralBuffer != null) {
                    onReceivedCentral(centralBuffer);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    public void connect() {
        if (isConnected()) {
            throw new IllegalStateException();
        }

        // ブロードキャスト登録
        {
            IntentFilter filter = new IntentFilter(INTENT_ACTION);
            filter.addCategory(INTENT_CATEGORY);
            mContext.registerReceiver(mBroadcastReceiver, filter);
        }

        mConnected = true;
    }

    public void disconnect() {
        if (!isConnected()) {
            throw new IllegalStateException();
        }

        // ブロードキャストから削除
        mContext.unregisterReceiver(mBroadcastReceiver);

        mConnected = false;
    }

    /**
     * セントラル情報をハンドリングする
     *
     * @param buffer シリアライズ化されたデータ
     */
    public void onReceivedCentral(byte[] buffer) throws SerializeException {
        RawCentralData data = SerializeUtil.deserializePublicFieldObject(RawCentralData.class, buffer);
        onReceived(data);
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
