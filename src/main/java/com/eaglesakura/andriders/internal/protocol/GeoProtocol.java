package com.eaglesakura.andriders.internal.protocol;

import com.eaglesakura.serialize.Serialize;

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
    }

    public static class GeoPayload {

        /**
         * 平地
         */
        public static final int INCLINATION_NONE = 0;

        /**
         * 坂道
         */
        public static final int INCLINATION_HILL = 1;

        /**
         * 激坂
         */
        public static final int INCLINATION_INTENSE_HILL = 2;

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
        public int inclinationType = INCLINATION_NONE;
    }
}
