package com.eaglesakura.andriders.central;


import com.eaglesakura.andriders.serialize.RawCentralData;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * センサー情報が更新された場合に呼び出される
 */
public abstract class SensorDataHandler<T> {
    /**
     * センサーが接続された
     */
    public void onConnectedSensor(@NonNull RawCentralData master, @NonNull T sensor) {

    }

    /**
     * センサーが切断された
     */
    public void onDisconnectedSensor(@NonNull RawCentralData master) {

    }

    /**
     * データを取得したらコールバックされる
     */
    public void onReceived(@NonNull RawCentralData master, @NonNull T sensor) {

    }

    /**
     * データが更新されたらコールバックされる
     *
     * @param oldValue 古いデータ
     * @param newValue 新しいデータ
     */
    public void onUpdated(@NonNull RawCentralData master, @Nullable T oldValue, @NonNull T newValue) {
    }
}
