package com.eaglesakura.andriders.serialize;

import com.eaglesakura.serialize.Serialize;
import com.eaglesakura.util.RandomUtil;

import java.util.Random;

/**
 * ユーザーの最大記録を保持する
 */
public class RawRecord {

    /**
     * 自己最高速度
     */
    @Serialize(id = 1)
    public float maxSpeedKmh;

    /**
     * 今日の最高速度
     */
    @Serialize(id = 2)
    public float maxSpeedKmhToday;

    /**
     * このセッションでの最高速度
     */
    @Serialize(id = 3)
    public float maxSpeedKmhSession;

    /**
     * 今日の最高心拍
     */
    @Serialize(id = 4)
    public short maxHeartrateToday;

    /**
     * このセッションでの最高心拍
     */
    @Serialize(id = 5)
    public short maxHeartrateSession;

    public RawRecord() {
    }

    @Deprecated
    public RawRecord(Class<Random> dummy) {
        maxSpeedKmh = RandomUtil.randFloat();
        maxSpeedKmhToday = RandomUtil.randFloat();
        maxSpeedKmhSession = RandomUtil.randFloat();
        maxHeartrateToday = RandomUtil.randUInt16();
        maxHeartrateSession = RandomUtil.randUInt16();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RawRecord rawRecord = (RawRecord) o;

        if (Float.compare(rawRecord.maxSpeedKmh, maxSpeedKmh) != 0) return false;
        if (Float.compare(rawRecord.maxSpeedKmhToday, maxSpeedKmhToday) != 0) return false;
        if (Float.compare(rawRecord.maxSpeedKmhSession, maxSpeedKmhSession) != 0) return false;
        if (maxHeartrateToday != rawRecord.maxHeartrateToday) return false;
        return maxHeartrateSession == rawRecord.maxHeartrateSession;

    }

    @Override
    public int hashCode() {
        int result = (maxSpeedKmh != +0.0f ? Float.floatToIntBits(maxSpeedKmh) : 0);
        result = 31 * result + (maxSpeedKmhToday != +0.0f ? Float.floatToIntBits(maxSpeedKmhToday) : 0);
        result = 31 * result + (maxSpeedKmhSession != +0.0f ? Float.floatToIntBits(maxSpeedKmhSession) : 0);
        result = 31 * result + (int) maxHeartrateToday;
        result = 31 * result + (int) maxHeartrateSession;
        return result;
    }
}
