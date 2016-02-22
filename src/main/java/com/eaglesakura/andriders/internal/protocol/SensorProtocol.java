package com.eaglesakura.andriders.internal.protocol;

import com.eaglesakura.serialize.Serialize;

public class SensorProtocol {
    public static class RawCadence {

        /**
         * 低ケイデンス
         */
        public static final byte ZONE_SLOW = 0;

        /**
         * 理想値
         */
        public static final byte ZONE_IDEAL = 1;

        /**
         * 高ケイデンス
         */
        public static final byte ZONE_HIGH = 2;

        /**
         * 回転数
         */
        @Serialize(id = 1)
        public short rpm;

        /**
         * ゾーン
         */
        @Serialize(id = 2)
        public byte cadenceZone = ZONE_SLOW;

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
        /**
         * 安静
         */
        public static final byte ZONE_REPOSE = 0;

        /**
         * 軽度
         */
        public static final byte ZONE_EASY = 1;

        /**
         * 脂肪燃焼
         */
        public static final byte ZONE_FAT_COMBUSTION = 2;

        /**
         * 有酸素運動
         */
        public static final byte ZONE_POSSENSION_OXYGEN = 3;

        /**
         * 無酸素運動
         */
        public static final byte ZONE_NON_OXYGENATED = 4;

        /**
         * オーバーワーク
         */
        public static final byte ZONE_OVERWORK = 5;

        @Serialize(id = 1)
        public short bpm;

        /**
         * 心拍ゾーン
         */
        @Serialize(id = 2)
        public byte zone = ZONE_REPOSE;

        /**
         * センサー日時
         */
        @Serialize(id = 3)
        public long date;
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
