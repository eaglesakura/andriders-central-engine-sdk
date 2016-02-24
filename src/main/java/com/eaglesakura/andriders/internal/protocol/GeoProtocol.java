package com.eaglesakura.andriders.internal.protocol;

import com.eaglesakura.andriders.internal.util.InternalSdkUtil;
import com.eaglesakura.andriders.sensor.InclinationType;
import com.eaglesakura.serialize.Serialize;

import java.util.Random;

public class GeoProtocol {

    /**
     * GPS座標を示す。
     */
    public static class GeoPoint {
        @Serialize(id = 1)
        public double latitude;

        @Serialize(id = 2)
        public double longitude;

        @Serialize(id = 3)
        public double altitude;

        public GeoPoint() {
        }

        public GeoPoint(double latitude, double longitude, double altitude) {
            this.latitude = latitude;
            this.longitude = longitude;
            this.altitude = altitude;
        }

        @Deprecated
        public GeoPoint(Class<Random> dummy) {
            latitude = InternalSdkUtil.randFloat();
            longitude = InternalSdkUtil.randFloat();
            altitude = InternalSdkUtil.randFloat();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            GeoPoint geoPoint = (GeoPoint) o;

            if (Double.compare(geoPoint.latitude, latitude) != 0) return false;
            if (Double.compare(geoPoint.longitude, longitude) != 0) return false;
            return Double.compare(geoPoint.altitude, altitude) == 0;

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

    public static class GeoPayload {
        // 現在のGPS座標
        @Serialize(id = 1)
        public GeoPoint location;

        // 位置精度（メートル単位）
        @Serialize(id = 2)
        public float locationAccuracy;

        // ユーザーがこの精度を信頼すると認めている
        @Serialize(id = 3)
        public boolean locationReliance;

        // 打刻した時刻
        @Serialize(id = 4)
        public long date;

        // 勾配(%単位、下り坂の場合は負の値)
        @Serialize(id = 5)
        public float inclinationPercent;

        // 勾配の種類
        @Serialize(id = 6)
        public InclinationType inclinationType;

        public GeoPayload() {
        }

        @Deprecated
        public GeoPayload(Class<Random> dummy) {
            location = new GeoPoint(dummy);
            locationAccuracy = InternalSdkUtil.randFloat();
            locationReliance = InternalSdkUtil.randBool();
            date = InternalSdkUtil.randInteger();
            inclinationPercent = InternalSdkUtil.randFloat();
            inclinationType = InternalSdkUtil.randEnum(InclinationType.class);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            GeoPayload that = (GeoPayload) o;

            if (Float.compare(that.locationAccuracy, locationAccuracy) != 0) return false;
            if (locationReliance != that.locationReliance) return false;
            if (date != that.date) return false;
            if (Float.compare(that.inclinationPercent, inclinationPercent) != 0) return false;
            if (location != null ? !location.equals(that.location) : that.location != null)
                return false;
            return inclinationType == that.inclinationType;

        }

        @Override
        public int hashCode() {
            int result = location != null ? location.hashCode() : 0;
            result = 31 * result + (locationAccuracy != +0.0f ? Float.floatToIntBits(locationAccuracy) : 0);
            result = 31 * result + (locationReliance ? 1 : 0);
            result = 31 * result + (int) (date ^ (date >>> 32));
            result = 31 * result + (inclinationPercent != +0.0f ? Float.floatToIntBits(inclinationPercent) : 0);
            result = 31 * result + (inclinationType != null ? inclinationType.hashCode() : 0);
            return result;
        }
    }
}
