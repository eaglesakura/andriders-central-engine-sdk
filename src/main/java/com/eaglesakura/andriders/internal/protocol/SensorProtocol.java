package com.eaglesakura.andriders.internal.protocol;

import com.eaglesakura.andriders.sensor.HeartrateZone;
import com.eaglesakura.andriders.sensor.SpeedZone;
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
        @Serialize(id = 2)
        public HeartrateZone zone;

        /**
         * センサー日時
         */
        @Serialize(id = 3)
        public long date;
    }

    public static class RawSpeed {
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
        public SpeedZone zone;

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
