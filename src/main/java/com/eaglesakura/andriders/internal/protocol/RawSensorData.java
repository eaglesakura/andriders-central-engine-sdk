package com.eaglesakura.andriders.internal.protocol;

import com.eaglesakura.andriders.sensor.CadenceZone;
import com.eaglesakura.andriders.sensor.HeartrateZone;
import com.eaglesakura.andriders.sensor.SpeedZone;
import com.eaglesakura.serialize.Serialize;
import com.eaglesakura.util.RandomUtil;

import java.util.Random;

public class RawSensorData {

    /**
     * 現在の心拍
     */
    @Serialize(id = 1)
    public RawHeartrate heartrate;

    /**
     * 現在のケイデンス
     */
    @Serialize(id = 2)
    public RawCadence cadence;

    /**
     * 現在の速度
     */
    @Serialize(id = 3)
    public RawSpeed speed;

    /**
     * 位置情報
     */
    @Serialize(id = 4)
    public RawLocation location;

    public RawSensorData() {
    }

    @Deprecated
    public RawSensorData(Class<Random> dummy) {
        heartrate = new RawHeartrate(dummy);
        cadence = new RawCadence(dummy);
        speed = new RawSpeed(dummy);
        location = new RawLocation(dummy);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RawSensorData that = (RawSensorData) o;

        if (heartrate != null ? !heartrate.equals(that.heartrate) : that.heartrate != null)
            return false;
        if (cadence != null ? !cadence.equals(that.cadence) : that.cadence != null) return false;
        if (speed != null ? !speed.equals(that.speed) : that.speed != null) return false;
        return !(location != null ? !location.equals(that.location) : that.location != null);

    }

    @Override
    public int hashCode() {
        int result = heartrate != null ? heartrate.hashCode() : 0;
        result = 31 * result + (cadence != null ? cadence.hashCode() : 0);
        result = 31 * result + (speed != null ? speed.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        return result;
    }

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
        public CadenceZone zone;

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
            rpm = RandomUtil.randUInt8();
            zone = RandomUtil.randEnumWithNull(CadenceZone.class);
            crankRevolution = RandomUtil.randUInt32();
            date = RandomUtil.randUInt64();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            RawCadence that = (RawCadence) o;

            if (rpm != that.rpm) return false;
            if (crankRevolution != that.crankRevolution) return false;
            if (date != that.date) return false;
            return zone == that.zone;

        }

        @Override
        public int hashCode() {
            int result = (int) rpm;
            result = 31 * result + (zone != null ? zone.hashCode() : 0);
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
            bpm = RandomUtil.randUInt8();
            zone = RandomUtil.randEnumWithNull(HeartrateZone.class);
            date = RandomUtil.randInt64();
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
         * GPS由来の速度情報
         */
        public static final int SPEEDSENSOR_TYPE_GPS = 0x1 << 0;

        /**
         * 何かしらのセンサー由来の速度情報
         */
        public static final int SPEEDSENSOR_TYPE_SENSOR = 0x1 << 1;

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
        public int wheelRevolution = -1;

        @Serialize(id = 4)
        public SpeedZone zone;

        @Serialize(id = 5)
        public long date;

        @Serialize(id = 6)
        public int flags;

        public RawSpeed() {
        }

        @Deprecated
        public RawSpeed(Class<Random> dummy) {
            speedKmPerHour = RandomUtil.randFloat();
            wheelRpm = RandomUtil.randFloat();
            wheelRevolution = RandomUtil.randUInt32();
            zone = RandomUtil.randEnumWithNull(SpeedZone.class);
            date = RandomUtil.randUInt64();
            flags = RandomUtil.randInt32();
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
            if (flags != rawSpeed.flags) return false;
            return zone == rawSpeed.zone;

        }

        @Override
        public int hashCode() {
            int result = (speedKmPerHour != +0.0f ? Float.floatToIntBits(speedKmPerHour) : 0);
            result = 31 * result + (wheelRpm != +0.0f ? Float.floatToIntBits(wheelRpm) : 0);
            result = 31 * result + (wheelRevolution != +0.0f ? Float.floatToIntBits(wheelRevolution) : 0);
            result = 31 * result + (zone != null ? zone.hashCode() : 0);
            result = 31 * result + (int) (date ^ (date >>> 32));
            result = 31 * result + flags;
            return result;
        }
    }

    public static int getHash(RawHeartrate data) {
        if (data == null) {
            return 0;
        }
        return data.bpm;
    }

    public static int getHash(RawCadence data) {
        if (data == null) {
            return 0;
        }
        return data.crankRevolution ^ (int) data.rpm;
    }

    public static int getHash(RawSpeed data) {
        if (data == null) {
            return 0;
        }
        return Float.floatToIntBits(data.speedKmPerHour) ^ data.flags ^ data.wheelRevolution;
    }

    public static int getHash(RawLocation data) {
        if (data == null) {
            return 0;
        }
        return Float.floatToIntBits((float) data.latitude)
                ^ Float.floatToIntBits((float) data.latitude)
                ^ Float.floatToIntBits((float) data.latitude)
                ^ Float.floatToIntBits(data.locationAccuracy);
    }
}
