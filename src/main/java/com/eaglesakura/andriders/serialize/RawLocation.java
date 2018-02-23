package com.eaglesakura.andriders.serialize;

import com.eaglesakura.andriders.sensor.InclinationType;
import com.eaglesakura.util.RandomUtil;

import java.util.Random;

/**
 * 詳細な情報を含んだロケーション情報を構築する
 */
public class RawLocation extends RawGeoPoint {

    /**
     * 位置精度（メートル単位）
     */

    public float locationAccuracy;

    /**
     * ユーザーがこの精度を信頼すると認めている
     */

    public boolean locationReliance;

    /**
     * 打刻した時刻
     */

    public long date;

    /**
     * 勾配(%単位、下り坂の場合は負の値)
     *
     * 不明の場合は0となる
     */

    public float inclinationPercent;

    /**
     * 勾配の種類
     */

    public InclinationType inclinationType;

    public RawLocation() {
    }

    @Deprecated
    public RawLocation(Class<Random> dummy) {
        super(dummy);
        locationAccuracy = RandomUtil.randFloat();
        locationReliance = RandomUtil.randBool();
        date = RandomUtil.randUInt64();
        inclinationPercent = RandomUtil.randFloat();
        inclinationType = RandomUtil.randEnumWithNull(InclinationType.class);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        RawLocation that = (RawLocation) o;

        if (Float.compare(that.locationAccuracy, locationAccuracy) != 0) return false;
        if (locationReliance != that.locationReliance) return false;
        if (date != that.date) return false;
        if (Float.compare(that.inclinationPercent, inclinationPercent) != 0) return false;
        return inclinationType == that.inclinationType;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (locationAccuracy != +0.0f ? Float.floatToIntBits(locationAccuracy) : 0);
        result = 31 * result + (locationReliance ? 1 : 0);
        result = 31 * result + (int) (date ^ (date >>> 32));
        result = 31 * result + (inclinationPercent != +0.0f ? Float.floatToIntBits(inclinationPercent) : 0);
        result = 31 * result + (inclinationType != null ? inclinationType.hashCode() : 0);
        return result;
    }
}
