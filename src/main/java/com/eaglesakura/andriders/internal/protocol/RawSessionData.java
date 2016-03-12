package com.eaglesakura.andriders.internal.protocol;

import com.eaglesakura.serialize.Serialize;
import com.eaglesakura.util.RandomUtil;

import java.util.Random;

/**
 * セッション情報を格納する
 */
public class RawSessionData {
    /**
     * 現在の状態が"自走"である
     */
    public static final int FLAG_ACTIVE = 0x1 << 0;

    /**
     * セッションを一意に識別するID
     */
    @Serialize(id = 1)
    public String sessionId;

    /**
     * このセッションでのフィットネス情報
     */
    @Serialize(id = 3)
    public RawFitnessStatus fitness;

    /**
     * 開始時刻
     */
    @Serialize(id = 4)
    public long startTime;

    /**
     * 走行距離(km)
     * <p>
     * 検出不可能な場合は0kmとなる
     */
    @Serialize(id = 5)
    public float distanceKm;

    /**
     * セッション中に自走した時間
     */
    @Serialize(id = 6)
    public int activeTimeMs;

    /**
     * セッション中に自走した時間
     */
    @Serialize(id = 7)
    public float activeDistanceKm;

    /**
     * 状態フラグ
     */
    @Serialize(id = 8)
    public int flags;

    /**
     * セッションの継続時間（ミリ秒）
     */
    @Serialize(id = 9)
    public int durationTimeMs;

    public RawSessionData() {
    }

    public RawSessionData(String sessionId) {
        this.sessionId = sessionId;
    }

    @Deprecated
    public RawSessionData(Class<Random> dummy) {
        sessionId = RandomUtil.randString();
        fitness = new RawFitnessStatus(dummy);
        startTime = RandomUtil.randUInt64();
        durationTimeMs = RandomUtil.randUInt32();
        distanceKm = RandomUtil.randFloat();
        activeTimeMs = RandomUtil.randUInt32();
        activeDistanceKm = RandomUtil.randFloat();
        flags = RandomUtil.randInt32();
    }

    public boolean isActiveMoving() {
        return (flags & FLAG_ACTIVE) != 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RawSessionData that = (RawSessionData) o;

        if (startTime != that.startTime) return false;
        if (Float.compare(that.distanceKm, distanceKm) != 0) return false;
        if (activeTimeMs != that.activeTimeMs) return false;
        if (Float.compare(that.activeDistanceKm, activeDistanceKm) != 0) return false;
        if (flags != that.flags) return false;
        if (durationTimeMs != that.durationTimeMs) return false;
        if (sessionId != null ? !sessionId.equals(that.sessionId) : that.sessionId != null)
            return false;
        return fitness != null ? fitness.equals(that.fitness) : that.fitness == null;

    }

    @Override
    public int hashCode() {
        int result = sessionId != null ? sessionId.hashCode() : 0;
        result = 31 * result + (fitness != null ? fitness.hashCode() : 0);
        result = 31 * result + (int) (startTime ^ (startTime >>> 32));
        result = 31 * result + (distanceKm != +0.0f ? Float.floatToIntBits(distanceKm) : 0);
        result = 31 * result + activeTimeMs;
        result = 31 * result + (activeDistanceKm != +0.0f ? Float.floatToIntBits(activeDistanceKm) : 0);
        result = 31 * result + flags;
        result = 31 * result + durationTimeMs;
        return result;
    }

    /**
     * 現在のフィットネス状態
     */
    public static class RawFitnessStatus {
        /**
         * 現在のMETs値
         */
        @Serialize(id = 1)
        public float mets;

        /**
         * 消費カロリー
         */
        @Serialize(id = 2)
        public float calorie;

        /**
         * エクササイズ値
         */
        @Serialize(id = 3)
        public float exercise;

        public RawFitnessStatus() {
        }

        public RawFitnessStatus(Class<Random> dummy) {
            mets = RandomUtil.randFloat();
            calorie = RandomUtil.randFloat();
            exercise = RandomUtil.randFloat();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            RawFitnessStatus that = (RawFitnessStatus) o;

            if (Float.compare(that.mets, mets) != 0) return false;
            if (Float.compare(that.calorie, calorie) != 0) return false;
            return Float.compare(that.exercise, exercise) == 0;

        }

        @Override
        public int hashCode() {
            int result = (mets != +0.0f ? Float.floatToIntBits(mets) : 0);
            result = 31 * result + (calorie != +0.0f ? Float.floatToIntBits(calorie) : 0);
            result = 31 * result + (exercise != +0.0f ? Float.floatToIntBits(exercise) : 0);
            return result;
        }
    }
}
