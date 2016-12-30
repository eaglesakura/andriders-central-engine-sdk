package com.eaglesakura.andriders.central;

import com.eaglesakura.andriders.serialize.RawCentralData;
import com.eaglesakura.andriders.serialize.RawLocation;
import com.eaglesakura.andriders.serialize.RawSensorData;

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

    static final int RECEIVE_HANDLE_ABORT = 0x01 << 0;
    static final int RECEIVE_HANDLE_CONNECTED = 0x01 << 1;
    static final int RECEIVE_HANDLE_DISCONNECTED = 0x01 << 2;
    static final int RECEIVE_HANDLE_RECEIVED = 0x01 << 3;
    static final int RECEIVE_HANDLE_UPDATED = 0x01 << 4;

    /**
     * @param master   rootデータ
     * @param value    更新された値
     * @param dataHash データの更新状態をチェックするためのハッシュ値
     * @return ハンドリングを行った処理内容
     */
    int onReceivedValue(@NonNull RawCentralData master, @Nullable T value, int dataHash) {
        dataHash = (value != null ? dataHash : 0x00);

        final T oldValue = mLastReceivedValue;
        final int oldHash = mLastReceivedHash;

        mLastReceivedValue = value;
        mLastReceivedHash = dataHash;


        if (mHandlers == null || mHandlers.isEmpty()) {
            // 受け取るべきハンドラが存在しない
            return RECEIVE_HANDLE_ABORT;
        }

        int result = 0;

        for (SensorDataHandler<T> handler : mHandlers) {
            if (oldValue == null && value != null) {
                // センサーに接続された
                handler.onConnectedSensor(master, value);
                result |= RECEIVE_HANDLE_CONNECTED;
            } else if (oldValue != null && value == null) {
                // センサーから切断された
                handler.onDisconnectedSensor(master);
                result |= RECEIVE_HANDLE_DISCONNECTED;
            }

            if (value != null) {
                // 値が取得できたらコールバック
                handler.onReceived(master, value);
                result |= RECEIVE_HANDLE_RECEIVED;
                if (oldHash != dataHash) {
                    // データが更新されたらコールバック
                    handler.onUpdated(master, oldValue, value);
                    result |= RECEIVE_HANDLE_UPDATED;
                }
            }
        }

        return result;
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
