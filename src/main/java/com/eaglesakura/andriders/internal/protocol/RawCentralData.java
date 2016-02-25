package com.eaglesakura.andriders.internal.protocol;

import com.eaglesakura.andriders.internal.util.InternalSdkUtil;
import com.eaglesakura.serialize.Serialize;

import java.util.Random;

/**
 * Centralのデータ本体を構築するデータ
 */
public class RawCentralData {
    @Serialize(id = 1)
    public RawSpecs specs;

    @Serialize(id = 2)
    public RawCentralStatus centralStatus;

    /**
     * センサー情報
     */
    @Serialize(id = 3)
    public RawSensorData sensor;

    /**
     * セッション統計情報
     */
    @Serialize(id = 4)
    public RawSessionData session;

    /**
     * 今日の統計情報
     */
    @Serialize(id = 5)
    public RawSessionData today;

    public RawCentralData() {
    }

    @Deprecated
    public RawCentralData(Class<Random> dummy) {
        specs = new RawSpecs(dummy);
        centralStatus = new RawCentralStatus(dummy);
        sensor = new RawSensorData(dummy);
        session = new RawSessionData(dummy);
        today = new RawSessionData(dummy);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RawCentralData that = (RawCentralData) o;

        if (specs != null ? !specs.equals(that.specs) : that.specs != null) return false;
        if (centralStatus != null ? !centralStatus.equals(that.centralStatus) : that.centralStatus != null)
            return false;
        if (sensor != null ? !sensor.equals(that.sensor) : that.sensor != null) return false;
        if (session != null ? !session.equals(that.session) : that.session != null) return false;
        return !(today != null ? !today.equals(that.today) : that.today != null);

    }

    @Override
    public int hashCode() {
        int result = specs != null ? specs.hashCode() : 0;
        result = 31 * result + (centralStatus != null ? centralStatus.hashCode() : 0);
        result = 31 * result + (sensor != null ? sensor.hashCode() : 0);
        result = 31 * result + (session != null ? session.hashCode() : 0);
        result = 31 * result + (today != null ? today.hashCode() : 0);
        return result;
    }

    public static class RawCentralStatus {
        /**
         * 心拍計に接続されている
         */
        public static final int CONNECTED_FLAG_HEARTRATE_SENSOR = 0x1 << 0;

        /**
         * スピードセンサーに接続されている
         */
        public static final int CONNECTED_FLAG_SPEED_SENSOR = 0x1 << 1;

        /**
         * ケイデンスセンサーに接続されている
         */
        public static final int CONNECTED_FLAG_CADENCE_SENSOR = 0x1 << 2;

        /**
         *
         */
        public static final int CONNECTED_FLAG_GPS = 0x1 << 3;

        /**
         * セッションID
         */
        @Serialize(id = 1)
        public String sessionId;

        /**
         * 接続状態フラグ
         */
        @Serialize(id = 2)
        public int connectedFlags;

        /**
         * デバッグ状態である場合はtrue
         */
        @Serialize(id = 3)
        public boolean debug;

        public RawCentralStatus() {
        }

        @Deprecated
        public RawCentralStatus(Class<Random> dummy) {
            sessionId = InternalSdkUtil.randString();
            connectedFlags = InternalSdkUtil.randInteger();
            debug = InternalSdkUtil.randBool();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            RawCentralStatus that = (RawCentralStatus) o;

            if (connectedFlags != that.connectedFlags) return false;
            if (debug != that.debug) return false;
            return !(sessionId != null ? !sessionId.equals(that.sessionId) : that.sessionId != null);

        }

        @Override
        public int hashCode() {
            int result = sessionId != null ? sessionId.hashCode() : 0;
            result = 31 * result + connectedFlags;
            result = 31 * result + (debug ? 1 : 0);
            return result;
        }
    }
}
