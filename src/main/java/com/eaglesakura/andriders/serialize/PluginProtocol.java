package com.eaglesakura.andriders.serialize;

import com.eaglesakura.util.RandomUtil;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class PluginProtocol {

    public static class SrcLocation {
        /**
         * 緯度
         */
        public double latitude;

        /**
         * 経度
         */
        public double longitude;

        /**
         * 高度
         */
        public double altitude;

        /**
         * 精度（メートル）
         */
        public double accuracyMeter;

        public SrcLocation() {
        }

        @Deprecated
        public SrcLocation(Class<Random> dummy) {
            latitude = RandomUtil.randFloat();
            longitude = RandomUtil.randFloat();
            altitude = RandomUtil.randFloat();
        }

        public SrcLocation(android.location.Location raw) {
            this.latitude = raw.getLatitude();
            this.longitude = raw.getLongitude();
            this.altitude = raw.getAltitude();
            this.accuracyMeter = raw.getAccuracy();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            SrcLocation location = (SrcLocation) o;

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

    public static class SrcHeartrate {
        /**
         * 心拍値
         */

        public short bpm;

        @Deprecated
        public SrcHeartrate() {
        }

        @Deprecated
        public SrcHeartrate(Class<Random> dummy) {
            bpm = RandomUtil.randUInt16();
        }

        public SrcHeartrate(short bpm) {
            this.bpm = bpm;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            SrcHeartrate heartrate = (SrcHeartrate) o;

            return bpm == heartrate.bpm;

        }

        @Override
        public int hashCode() {
            return (int) bpm;
        }
    }

    public static class SrcSpeedAndCadence {
        /**
         * クランク回転数 / 分
         */

        public float crankRpm;

        /**
         * クランク合計回転数
         */

        public int crankRevolution;

        /**
         * ホイール回転数 / 分
         */

        public float wheelRpm;

        /**
         * ホイール合計回転数
         */

        public int wheelRevolution;

        public SrcSpeedAndCadence() {
        }

        @Deprecated
        public SrcSpeedAndCadence(Class<Random> dummy) {
            crankRpm = RandomUtil.randFloat();
            crankRevolution = RandomUtil.randUInt32();
            wheelRpm = RandomUtil.randFloat();
            wheelRevolution = RandomUtil.randUInt32();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            SrcSpeedAndCadence that = (SrcSpeedAndCadence) o;

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
    public static class RawPluginInfo {
        /**
         * 一意に識別するためのID
         */

        public String id;

        /**
         * 説明テキスト
         */

        public String summary;

        /**
         * カテゴリ情報, default=other
         */

        public String category;

        /**
         * 設定画面を持つならばtrue
         */

        public boolean hasSetting;

        /**
         * 使用可能な状態である場合はtrue, falseの場合、拡張を有効化できなくする
         */

        public boolean activated = true;

        /**
         * ビルドされているSDKバージョン
         * 基本的にはACE本体と同じバージョンでビルドされているのが望ましい。
         */

        public String sdkVersion;

        /**
         * プロトコルバージョン
         * 番号が異なる場合、互換切りが発生している。
         */

        public int sdkProtocolVersion;

        public RawPluginInfo() {
        }

        @Deprecated
        public RawPluginInfo(Class<Random> dummy) {
            id = RandomUtil.randString();
            summary = RandomUtil.randString();
            category = RandomUtil.randString();
            hasSetting = RandomUtil.randBool();
            activated = RandomUtil.randBool();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            RawPluginInfo that = (RawPluginInfo) o;

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

    public static class RawCycleDisplayInfo {
        /**
         * 一意に識別するためのID
         */
        @NonNull

        public String id;

        /**
         * 表示タイトル
         */
        @NonNull

        public String title;

        /**
         * 説明テキスト
         */
        @Nullable

        public String summary;

        public RawCycleDisplayInfo() {
        }

        @Deprecated
        public RawCycleDisplayInfo(Class<Random> dummy) {
            id = RandomUtil.randString();
            title = RandomUtil.randShortString();
            summary = RandomUtil.randLargeString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            RawCycleDisplayInfo that = (RawCycleDisplayInfo) o;

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

    public static class RawCycleDisplayValue {
        /**
         * 一意に識別するためのID
         */

        public String id;

        /**
         * 値が有効であることを保証できる期間, それを過ぎるとN/A扱いとなる
         */

        public int timeoutMs;


        public BasicValue basicValue;


        public List<KeyValue> keyValues;

        public RawCycleDisplayValue() {
        }

        @Deprecated
        public RawCycleDisplayValue(Class<Random> dummy) {
            id = RandomUtil.randString();
            timeoutMs = RandomUtil.randInt32();
            basicValue = new BasicValue(dummy);
            keyValues = Arrays.asList(
                    new KeyValue(dummy), new KeyValue(dummy), new KeyValue(dummy)
            );
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            RawCycleDisplayValue that = (RawCycleDisplayValue) o;

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

            public String main;
            /**
             * 表示タイトル（メインテキストの下に差し込まれる）
             */

            public String title;

            /**
             * バーの色（A)
             */

            public short barColorA = 255;

            /**
             * バーの内側に表示されるゾーン情報
             */

            public String zoneText;

            /**
             * バーの色（R)
             */

            public short barColorR = 128;

            /**
             * バーの色（G)
             */

            public short barColorG = 128;

            /**
             * バーの色（B)
             */

            public short barColorB = 128;

            public BasicValue() {
            }

            @Deprecated
            public BasicValue(Class<Random> dummy) {
                main = RandomUtil.randString();
                title = RandomUtil.randString();
                barColorA = RandomUtil.randInt8();
                barColorR = RandomUtil.randInt8();
                barColorG = RandomUtil.randInt8();
                barColorB = RandomUtil.randInt8();
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;

                BasicValue that = (BasicValue) o;

                if (barColorA != that.barColorA) return false;
                if (barColorR != that.barColorR) return false;
                if (barColorG != that.barColorG) return false;
                if (barColorB != that.barColorB) return false;
                if (main != null ? !main.equals(that.main) : that.main != null) return false;
                if (title != null ? !title.equals(that.title) : that.title != null) return false;
                return !(zoneText != null ? !zoneText.equals(that.zoneText) : that.zoneText != null);

            }

            @Override
            public int hashCode() {
                int result = main != null ? main.hashCode() : 0;
                result = 31 * result + (title != null ? title.hashCode() : 0);
                result = 31 * result + (int) barColorA;
                result = 31 * result + (zoneText != null ? zoneText.hashCode() : 0);
                result = 31 * result + (int) barColorR;
                result = 31 * result + (int) barColorG;
                result = 31 * result + (int) barColorB;
                return result;
            }
        }

        public static class KeyValue {

            public String title;


            public String value;

            public KeyValue() {
            }

            public KeyValue(Class<Random> dummy) {
                title = RandomUtil.randString();
                value = RandomUtil.randString();
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;

                KeyValue keyValue = (KeyValue) o;

                if (title != null ? !title.equals(keyValue.title) : keyValue.title != null)
                    return false;
                return !(value != null ? !value.equals(keyValue.value) : keyValue.value != null);

            }

            @Override
            public int hashCode() {
                int result = title != null ? title.hashCode() : 0;
                result = 31 * result + (value != null ? value.hashCode() : 0);
                return result;
            }
        }
    }
}
