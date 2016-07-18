package com.eaglesakura.andriders.central;

import com.eaglesakura.andriders.command.CommandKey;
import com.eaglesakura.andriders.serialize.NotificationProtocol;
import com.eaglesakura.andriders.serialize.RawCentralData;
import com.eaglesakura.andriders.serialize.RawIntent;
import com.eaglesakura.andriders.serialize.RawLocation;
import com.eaglesakura.andriders.serialize.RawSensorData;
import com.eaglesakura.serialize.error.SerializeException;
import com.eaglesakura.util.SerializeUtil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * ACEからの情報をハンドリングする
 */
public class CentralDataReceiver {
    /**
     * Intent経由で送られるCentralData
     */
    public static final String EXTRA_CENTRAL_DATA = "EXTRA_CENTRAL_DATA";

    /**
     * 通知データを乗せるExtra
     */
    public static final String EXTRA_NOTIFICATION_DATA = "EXTRA_NOTIFICATION_DATA";

    /**
     * コマンドKey
     */
    public static final String EXTRA_COMMAND_KEY = "EXTRA_COMMAND_KEY";

    /**
     * RawIntent情報
     */
    public static final String EXTRA_COMMAND_INTERNAL_EXTRAS = "EXTRA_COMMAND_INTERNAL_EXTRAS";

    /**
     * 送受信用Action
     */
    public static final String ACTION_UPDATE_CENTRAL_DATA = "com.eaglesakura.andriders.ACTION_UPDATE_CENTRAL_DATA_v2";

    /**
     * 通知を受信した
     */
    public static final String ACTION_RECEIVED_NOTIFICATION = "com.eaglesakura.andriders.ACTION_RECEIVED_NOTIFICATION";

    /**
     * コマンドの起動が行われた
     */
    public static final String ACTION_COMMAND_BOOTED = "com.eaglesakura.andriders.ACTION_COMMAND_BOOTED";

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

    /**
     * セントラルデータハンドリング
     */
    final Set<CentralDataHandler> mCentralDataHandlers = new HashSet<>();

    final SensorDataReceiver<RawSensorData.RawHeartrate> mHeartrateReceiver = new SensorDataReceiver<>(this);

    final SensorDataReceiver<RawSensorData.RawSpeed> mSpeedReceiver = new SensorDataReceiver<>(this);

    final SensorDataReceiver<RawSensorData.RawCadence> mCadenceReceiver = new SensorDataReceiver<>(this);

    final SensorDataReceiver<RawLocation> mLocationReceiver = new SensorDataReceiver<>(this);

    /**
     * 通知ハンドリング
     */
    final Set<CentralNotificationHandler> mNotificationHandlers = new HashSet<>();

    public CentralDataReceiver(@NonNull Context context) {
        mContext = context.getApplicationContext();
    }

    @NonNull
    public Context getContext() {
        return mContext;
    }

    public void addHandler(@NonNull CentralNotificationHandler handler) {
        synchronized (lock) {
            mNotificationHandlers.add(handler);
        }
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
            final String action = intent.getAction();
            if (ACTION_UPDATE_CENTRAL_DATA.equals(action)) {
                byte[] centralBuffer = intent.getByteArrayExtra(EXTRA_CENTRAL_DATA);
                try {
                    if (centralBuffer != null) {
                        onReceivedCentralData(centralBuffer);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (ACTION_RECEIVED_NOTIFICATION.equals(action)) {
                byte[] notificationBuffer = intent.getByteArrayExtra(EXTRA_NOTIFICATION_DATA);
                try {
                    if (notificationBuffer != null) {
                        onReceivedNotificationData(notificationBuffer);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (ACTION_COMMAND_BOOTED.equals(action)) {
                try {
                    CommandKey key = intent.getParcelableExtra(EXTRA_COMMAND_KEY);
                    byte[] extra = intent.getByteArrayExtra(EXTRA_COMMAND_INTERNAL_EXTRAS);
                    RawIntent rawIntent = extra != null ? SerializeUtil.deserializePublicFieldObject(RawIntent.class, extra) : null;
                    if (key != null) {
                        onReceived(key, rawIntent != null ? rawIntent.extras : null);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    };

    public void connect() {
        if (isConnected()) {
            throw new IllegalStateException();
        }

        // ブロードキャスト登録
        {
            IntentFilter filter = new IntentFilter();
            filter.addAction(ACTION_UPDATE_CENTRAL_DATA);
            filter.addAction(ACTION_RECEIVED_NOTIFICATION);
            filter.addAction(ACTION_COMMAND_BOOTED);
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
    public void onReceivedCentralData(byte[] buffer) throws SerializeException {
        RawCentralData data = SerializeUtil.deserializePublicFieldObject(RawCentralData.class, buffer);
        onReceived(data);
    }

    /**
     * セントラルの通知情報をハンドリングする
     *
     * @param buffer シリアライズ化されたデータ
     */
    public void onReceivedNotificationData(byte[] buffer) throws SerializeException {
        NotificationProtocol.RawNotification data = SerializeUtil.deserializePublicFieldObject(NotificationProtocol.RawNotification.class, buffer);
        onReceived(data);
    }

    /**
     * コマンドの情報をハンドリングする
     */
    public void onReceived(@NonNull CommandKey key, @Nullable List<RawIntent.Extra> aceInternalExtra) {
        synchronized (lock) {
            for (CentralNotificationHandler handler : mNotificationHandlers) {
                handler.onReceivedCommandBoot(key, aceInternalExtra);
            }
        }
    }

    /**
     * 通知情報をハンドリングする
     */
    public void onReceived(@NonNull NotificationProtocol.RawNotification notification) {
        synchronized (lock) {
            for (CentralNotificationHandler handler : mNotificationHandlers) {
                handler.onReceivedNotification(notification);
            }
        }
    }

    /**
     * セントラル情報をハンドリングする
     */
    public void onReceived(@NonNull RawCentralData central) {
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
