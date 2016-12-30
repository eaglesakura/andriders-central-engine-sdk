package com.eaglesakura.andriders.serialize;

import com.eaglesakura.serialize.Serialize;
import com.eaglesakura.util.RandomUtil;

import android.support.annotation.NonNull;

import java.util.Random;

/**
 * Centralのデータ本体を構築するデータ
 */
public class RawCentralData {
    @NonNull
    @Serialize(id = 1)
    public RawSpecs specs;

    @NonNull
    @Serialize(id = 2)
    public RawCentralStatus centralStatus;

    /**
     * センサー情報
     */
    @NonNull
    @Serialize(id = 3)
    public RawSensorData sensor;

    /**
     * セッション統計情報
     */
    @NonNull
    @Serialize(id = 4)
    public RawSessionData session;

    /**
     * 今日の統計情報
     */
    @NonNull
    @Serialize(id = 5)
    public RawSessionData today;

    /**
     * ユーザーレコード値
     */
    @NonNull
    @Serialize(id = 6)
    public RawRecord record;

    public RawCentralData() {
    }

    @Deprecated
    public RawCentralData(Class<Random> dummy) {
        specs = new RawSpecs(dummy);
        centralStatus = new RawCentralStatus(dummy);
        sensor = new RawSensorData(dummy);
        session = new RawSessionData(dummy);
        today = new RawSessionData(dummy);
        record = new RawRecord(dummy);
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
         * デバッグ状態である場合はtrue
         */
        @Serialize(id = 3)
        public boolean debug;

        /**
         * 現在時刻
         */
        @Serialize(id = 4)
        public long date;

        public RawCentralStatus() {
        }

        @Deprecated
        public RawCentralStatus(Class<Random> dummy) {
            debug = RandomUtil.randBool();
            date = RandomUtil.randInt64();
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            RawCentralStatus that = (RawCentralStatus) o;

            if (debug != that.debug) return false;
            return date == that.date;

        }

        @Override
        public int hashCode() {
            int result = (debug ? 1 : 0);
            result = 31 * result + (int) (date ^ (date >>> 32));
            return result;
        }
    }
}
