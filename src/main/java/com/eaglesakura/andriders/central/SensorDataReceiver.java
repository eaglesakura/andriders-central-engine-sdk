package com.eaglesakura.andriders.central;

import com.eaglesakura.andriders.internal.protocol.RawCentralData;
import com.eaglesakura.andriders.internal.protocol.RawLocation;
import com.eaglesakura.andriders.internal.protocol.RawSensorData;
import com.eaglesakura.util.Util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.HashSet;
import java.util.Set;

public class SensorDataReceiver<T> {
    @NonNull
    CentralDataReceiver mParent;

    @Nullable
    Set<SensorDataHandler<T>> mHandlers;

    /**
     * 最後に受信したデータ
     *
     * nullを許容する
     */
    @Nullable
    T mLastReceivedValue;

    /**
     * 最後に受信したデータのハッシュ
     */
    int mLastReceivedHash;

    SensorDataReceiver(@NonNull CentralDataReceiver parent) {
        mParent = parent;
    }

    /**
     * ハンドラを追加する
     */
    public void addHandler(@NonNull SensorDataHandler<T> handler) {
        if (mHandlers == null) {
            mHandlers = new HashSet<>();
        }

        mHandlers.add(handler);
    }

    /**
     * ハンドラを削除する
     */
    public void removeHandler(@NonNull SensorDataHandler<T> handler) {
        mHandlers.remove(handler);
    }

    /**
     * @param master   rootデータ
     * @param value    更新された値
     * @param dataHash データの更新状態をチェックするためのハッシュ値
     */
    void onReceivedValue(@NonNull RawCentralData master, @Nullable T value, int dataHash) {
        final T oldValue = mLastReceivedValue;
        final int oldHash = mLastReceivedHash;

        mLastReceivedValue = value;
        mLastReceivedHash = value != null ? dataHash : 0;


        if (mHandlers == null || mHandlers.isEmpty()) {
            // 受け取るべきハンドラが存在しない
            return;
        }

        for (SensorDataHandler<T> handler : mHandlers) {
            if (oldValue == null && value != null) {
                // センサーに接続された
                handler.onConnectedSensor(master, value);
            } else if (oldValue != null && value == null) {
                // センサーから切断された
                handler.onDisconnectedSensor(master);
            }

            if (value != null) {
                // 値が取得できたらコールバック
                handler.onReceived(master, value);
                if (oldHash != dataHash) {
                    // データが更新されたらコールバック
                    handler.onUpdated(master, oldValue, value);
                }
            }
        }
    }

    public static abstract class HeartrateHandler extends SensorDataHandler<RawSensorData.RawHeartrate> {
    }

    public static abstract class SpeedHandler extends SensorDataHandler<RawSensorData.RawSpeed> {
    }

    public static abstract class CadenceHandler extends SensorDataHandler<RawSensorData.RawCadence> {
    }

    public static abstract class LocationHandler extends SensorDataHandler<RawLocation> {
    }
}
