package com.eaglesakura.andriders.serialize;

import com.eaglesakura.serialize.Serialize;
import com.eaglesakura.util.RandomUtil;

import java.util.Random;

/**
 * 位置情報のみを構築したクラス
 */
public class RawGeoPoint {
    @Serialize(id = 1)
    public double latitude;

    @Serialize(id = 2)
    public double longitude;

    @Serialize(id = 3)
    public double altitude;

    public RawGeoPoint() {
    }

    public RawGeoPoint(double latitude, double longitude, double altitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
    }

    @Deprecated
    public RawGeoPoint(Class<Random> dummy) {
        latitude = RandomUtil.randFloat();
        longitude = RandomUtil.randFloat();
        altitude = RandomUtil.randFloat();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RawGeoPoint that = (RawGeoPoint) o;

        if (Double.compare(that.latitude, latitude) != 0) return false;
        if (Double.compare(that.longitude, longitude) != 0) return false;
        if (Double.compare(that.altitude, altitude) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(latitude);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(altitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
