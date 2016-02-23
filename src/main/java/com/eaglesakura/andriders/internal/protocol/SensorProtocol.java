package com.eaglesakura.andriders.internal.protocol;

import com.eaglesakura.andriders.sensor.HeartrateZone;
import com.eaglesakura.serialize.Serialize;

public class SensorProtocol {
    public static class RawCadence {
        /**
         * 回転数
         */
        @Serialize(id = 1)
        public short rpm;

        /**
         * ゾーン
         */
        @Serialize(id = 2)
        public byte cadenceZone;

        /**
         * センサーへ接続してからの合計回転数（クランク）
         */
        @Serialize(id = 3)
        public int crankRevolution;

        /**
         * センサー日時
         */
        @Serialize(id = 4)
        public long date;
    }

    public static class RawHeartrate {

        @Serialize(id = 1)
        public short bpm;

        /**
         * 心拍ゾーン
         */
        @Deprecated
        @Serialize(id = 2)
        public byte _zone;

        /**
         * センサー日時
         */
        @Serialize(id = 3)
        public long date;

        public void setZone(HeartrateZone set) {
            _zone = (byte) set.ordinal();
        }

        public HeartrateZone getZone() {
            return HeartrateZone.values()[_zone];
        }
    }

    public static class RawSpeed {
        /**
         * 停止
         */
        public static final byte ZONE_STOP = 0;

        /**
         * 低速
         */
        public static final byte ZONE_SLOW = 1;

        /**
         * 巡航
         */
        public static final byte ZONE_CRUISE = 2;

        /**
         * スプリント
         */
        public static final byte ZONE_SPRINT = 3;

        /**
         * スピード km/h
         */
        @Serialize(id = 1)
        public float speedKmPerHour;

        /**
         * ホイールの回転数
         * S&Cセンサーを使用した場合は取得できるが、GPS由来は取得できないためoptional
         */
        @Serialize(id = 2)
        public float wheelRpm = -1;

        /**
         * センサーへ接続してからの合計回転数（ホイール）
         */
        @Serialize(id = 3)
        public float wheelRevolution = -1;

        @Serialize(id = 4)
        public byte zone = ZONE_STOP;

        @Serialize(id = 5)
        public long date;

        public boolean hasWheelRpm() {
            return wheelRpm >= 0;
        }

        public boolean hasWheelRevolution() {
            return wheelRevolution >= 0;
        }
    }
}
