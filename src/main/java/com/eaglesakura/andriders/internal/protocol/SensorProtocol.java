package com.eaglesakura.andriders.internal.protocol;

import com.eaglesakura.andriders.internal.util.InternalSdkUtil;
import com.eaglesakura.andriders.sensor.HeartrateZone;
import com.eaglesakura.andriders.sensor.SpeedZone;
import com.eaglesakura.serialize.Serialize;

import java.util.Random;

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

        public RawCadence() {
        }

        @Deprecated
        public RawCadence(Class<Random> dummy) {
            rpm = InternalSdkUtil.randInteger();
            cadenceZone = InternalSdkUtil.randInteger();
            crankRevolution = InternalSdkUtil.randInteger();
            date = InternalSdkUtil.randInteger();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            RawCadence that = (RawCadence) o;

            if (rpm != that.rpm) return false;
            if (cadenceZone != that.cadenceZone) return false;
            if (crankRevolution != that.crankRevolution) return false;
            return date == that.date;

        }

        @Override
        public int hashCode() {
            int result = (int) rpm;
            result = 31 * result + (int) cadenceZone;
            result = 31 * result + crankRevolution;
            result = 31 * result + (int) (date ^ (date >>> 32));
            return result;
        }
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

        public RawHeartrate() {
        }

        @Deprecated
        public RawHeartrate(Class<Random> dummy) {
            bpm = InternalSdkUtil.randInteger();
            zone = InternalSdkUtil.randEnum(HeartrateZone.class);
            date = InternalSdkUtil.randInteger();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            RawHeartrate that = (RawHeartrate) o;

            if (bpm != that.bpm) return false;
            if (date != that.date) return false;
            return zone == that.zone;

        }

        @Override
        public int hashCode() {
            int result = (int) bpm;
            result = 31 * result + (zone != null ? zone.hashCode() : 0);
            result = 31 * result + (int) (date ^ (date >>> 32));
            return result;
        }
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

        public RawSpeed() {
        }

        @Deprecated
        public RawSpeed(Class<Random> dummy) {
            speedKmPerHour = InternalSdkUtil.randFloat();
            wheelRpm = InternalSdkUtil.randFloat();
            wheelRevolution = InternalSdkUtil.randInteger();
            zone = InternalSdkUtil.randEnum(SpeedZone.class);
            date = InternalSdkUtil.randInteger();
        }

        public boolean hasWheelRpm() {
            return wheelRpm >= 0;
        }

        public boolean hasWheelRevolution() {
            return wheelRevolution >= 0;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            RawSpeed rawSpeed = (RawSpeed) o;

            if (Float.compare(rawSpeed.speedKmPerHour, speedKmPerHour) != 0) return false;
            if (Float.compare(rawSpeed.wheelRpm, wheelRpm) != 0) return false;
            if (Float.compare(rawSpeed.wheelRevolution, wheelRevolution) != 0) return false;
            if (date != rawSpeed.date) return false;
            return zone == rawSpeed.zone;

        }

        @Override
        public int hashCode() {
            int result = (speedKmPerHour != +0.0f ? Float.floatToIntBits(speedKmPerHour) : 0);
            result = 31 * result + (wheelRpm != +0.0f ? Float.floatToIntBits(wheelRpm) : 0);
            result = 31 * result + (wheelRevolution != +0.0f ? Float.floatToIntBits(wheelRevolution) : 0);
            result = 31 * result + (zone != null ? zone.hashCode() : 0);
            result = 31 * result + (int) (date ^ (date >>> 32));
            return result;
        }
    }
}
