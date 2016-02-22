package com.eaglesakura.andriders.internal.protocol;

import com.eaglesakura.serialize.Serialize;

import java.util.List;

public class IdlExtension {

    public static class Location {
        /**
         * 緯度
         */
        @Serialize(id = 1)
        public double latitude;

        /**
         * 経度
         */
        @Serialize(id = 2)
        public double longitude;

        /**
         * 高度
         */
        @Serialize(id = 3)
        public double altitude;

        /**
         * 精度（メートル）
         */
        @Serialize(id = 4)
        public double accuracyMeter;

        public Location() {
        }

        public Location(android.location.Location raw) {
            this.latitude = raw.getLatitude();
            this.longitude = raw.getLongitude();
            this.altitude = raw.getAltitude();
            this.accuracyMeter = raw.getAccuracy();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Location location = (Location) o;

            if (Double.compare(location.latitude, latitude) != 0) return false;
            if (Double.compare(location.longitude, longitude) != 0) return false;
            if (Double.compare(location.altitude, altitude) != 0) return false;
            return Double.compare(location.accuracyMeter, accuracyMeter) == 0;

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
            temp = Double.doubleToLongBits(accuracyMeter);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            return result;
        }
    }

    public static class Heartrate {
        /**
         * 心拍値
         */
        @Serialize(id = 1)
        public short bpm;

        public Heartrate() {
        }

        public Heartrate(short bpm) {
            this.bpm = bpm;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Heartrate heartrate = (Heartrate) o;

            return bpm == heartrate.bpm;

        }

        @Override
        public int hashCode() {
            return (int) bpm;
        }
    }

    public static class SpeedAndCadence {
        /**
         * クランク回転数 / 分
         */
        @Serialize(id = 10)
        public float crankRpm;

        /**
         * クランク合計回転数
         */
        @Serialize(id = 11)
        public int crankRevolution;

        /**
         * ホイール回転数 / 分
         */
        @Serialize(id = 20)
        public float wheelRpm;

        /**
         * ホイール合計回転数
         */
        @Serialize(id = 21)
        public int wheelRevolution;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            SpeedAndCadence that = (SpeedAndCadence) o;

            if (Float.compare(that.crankRpm, crankRpm) != 0) return false;
            if (crankRevolution != that.crankRevolution) return false;
            if (Float.compare(that.wheelRpm, wheelRpm) != 0) return false;
            return wheelRevolution == that.wheelRevolution;

        }

        @Override
        public int hashCode() {
            int result = (crankRpm != +0.0f ? Float.floatToIntBits(crankRpm) : 0);
            result = 31 * result + crankRevolution;
            result = 31 * result + (wheelRpm != +0.0f ? Float.floatToIntBits(wheelRpm) : 0);
            result = 31 * result + wheelRevolution;
            return result;
        }
    }

    /**
     * 拡張機能自体の情報
     */
    public static class ExtensionInfo {
        /**
         * 一意に識別するためのID
         */
        @Serialize(id = 1)
        public String id;

        /**
         * 説明テキスト
         */
        @Serialize(id = 2)
        public String summary;

        /**
         * カテゴリ情報, default=other
         */
        @Serialize(id = 3)
        public String category;

        /**
         * 設定画面を持つならばtrue
         */
        @Serialize(id = 4)
        public boolean hasSetting;

        /**
         * 使用可能な状態である場合はtrue, falseの場合、拡張を有効化できなくする
         */
        @Serialize(id = 5)
        public boolean activated = true;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ExtensionInfo that = (ExtensionInfo) o;

            if (hasSetting != that.hasSetting) return false;
            if (activated != that.activated) return false;
            if (id != null ? !id.equals(that.id) : that.id != null) return false;
            if (summary != null ? !summary.equals(that.summary) : that.summary != null)
                return false;
            return !(category != null ? !category.equals(that.category) : that.category != null);

        }

        @Override
        public int hashCode() {
            int result = id != null ? id.hashCode() : 0;
            result = 31 * result + (summary != null ? summary.hashCode() : 0);
            result = 31 * result + (category != null ? category.hashCode() : 0);
            result = 31 * result + (hasSetting ? 1 : 0);
            result = 31 * result + (activated ? 1 : 0);
            return result;
        }
    }

    public static class CycleDisplayInfo {
        /**
         * 一意に識別するためのID
         */
        @Serialize(id = 1)
        public String id;

        /**
         * 表示タイトル
         */
        @Serialize(id = 2)
        public String title;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            CycleDisplayInfo that = (CycleDisplayInfo) o;

            if (id != null ? !id.equals(that.id) : that.id != null) return false;
            return !(title != null ? !title.equals(that.title) : that.title != null);

        }

        @Override
        public int hashCode() {
            int result = id != null ? id.hashCode() : 0;
            result = 31 * result + (title != null ? title.hashCode() : 0);
            return result;
        }
    }

    public static class CycleDisplayValue {
        /**
         * 一意に識別するためのID
         */
        @Serialize(id = 1)
        public String id;

        /**
         * 値が有効であることを保証できる期間, それを過ぎるとN/A扱いとなる
         */
        @Serialize(id = 2)
        public int timeoutMs;

        @Serialize(id = 10)
        public BasicValue basicValue;

        @Serialize(id = 11)
        public List<KeyValue> keyValues;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            CycleDisplayValue that = (CycleDisplayValue) o;

            if (timeoutMs != that.timeoutMs) return false;
            if (id != null ? !id.equals(that.id) : that.id != null) return false;
            if (basicValue != null ? !basicValue.equals(that.basicValue) : that.basicValue != null)
                return false;
            return !(keyValues != null ? !keyValues.equals(that.keyValues) : that.keyValues != null);

        }

        @Override
        public int hashCode() {
            int result = id != null ? id.hashCode() : 0;
            result = 31 * result + timeoutMs;
            result = 31 * result + (basicValue != null ? basicValue.hashCode() : 0);
            result = 31 * result + (keyValues != null ? keyValues.hashCode() : 0);
            return result;
        }

        public static class BasicValue {
            /**
             * メイン表示テキスト（心拍等）
             */
            @Serialize(id = 1)
            public String main;
            /**
             * 表示タイトル（メインテキストの下に差し込まれる）
             */
            @Serialize(id = 2)
            public String title;

            /**
             * バーの内側に表示されるゾーン情報
             */
            @Serialize(id = 3)
            public String zoneText;

            /**
             * バーの色（A)
             */
            @Serialize(id = 4)
            public short barColorA = 255;

            /**
             * バーの色（R)
             */
            @Serialize(id = 5)
            public short barColorR = 128;

            /**
             * バーの色（G)
             */
            @Serialize(id = 6)
            public short barColorG = 128;

            /**
             * バーの色（B)
             */
            @Serialize(id = 7)
            public short barColorB = 128;
        }

        public static class KeyValue {
            @Serialize(id = 1)
            public String title;

            @Serialize(id = 2)
            public String value;
        }
    }
}
