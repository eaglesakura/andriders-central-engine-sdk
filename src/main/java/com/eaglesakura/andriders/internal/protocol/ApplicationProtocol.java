package com.eaglesakura.andriders.internal.protocol;

import com.eaglesakura.andriders.internal.util.InternalSdkUtil;
import com.eaglesakura.serialize.Serialize;

import java.util.Random;

public class ApplicationProtocol {
    public static class RawCentralSpec {
        @Serialize(id = 1)
        public int protocolVersion;

        @Serialize(id = 2)
        public String appVersionName;

        @Serialize(id = 3)
        public String appPackageName;

        public RawCentralSpec() {
        }

        @Deprecated
        public RawCentralSpec(Class<Random> dummy) {
            protocolVersion = InternalSdkUtil.randInteger();
            appVersionName = InternalSdkUtil.randString();
            appPackageName = InternalSdkUtil.randString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            RawCentralSpec that = (RawCentralSpec) o;

            if (protocolVersion != that.protocolVersion) return false;
            if (appVersionName != null ? !appVersionName.equals(that.appVersionName) : that.appVersionName != null)
                return false;
            return !(appPackageName != null ? !appPackageName.equals(that.appPackageName) : that.appPackageName != null);

        }

        @Override
        public int hashCode() {
            int result = protocolVersion;
            result = 31 * result + (appVersionName != null ? appVersionName.hashCode() : 0);
            result = 31 * result + (appPackageName != null ? appPackageName.hashCode() : 0);
            return result;
        }
    }

    public static class RawCentralStatus {
        /**
         * 心拍計に接続されている
         */
        public static final int CONNECTED_FLAG_HEARTRATE_SENSOR = 0x1 << 0;

        /**
         * スピードセンサーに接続されている
         */
        public static final int CONNECTED_FLAG_SPEED_SENSOR = 0x1 << 1;

        /**
         *
         */
        public static final int CONNECTED_FLAG_GPS = 0x1 << 2;

        /**
         * セッションID
         */
        @Serialize(id = 1)
        public String sessionId;

        /**
         * 接続状態フラグ
         */
        @Serialize(id = 2)
        public int connectedFlags;

        /**
         * デバッグ状態である場合はtrue
         */
        @Serialize(id = 3)
        public boolean debug;

        public RawCentralStatus() {
        }

        @Deprecated
        public RawCentralStatus(Class<Random> dummy) {
            sessionId = InternalSdkUtil.randString();
            connectedFlags = InternalSdkUtil.randInteger();
            debug = InternalSdkUtil.randBool();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            RawCentralStatus that = (RawCentralStatus) o;

            if (connectedFlags != that.connectedFlags) return false;
            if (debug != that.debug) return false;
            return !(sessionId != null ? !sessionId.equals(that.sessionId) : that.sessionId != null);

        }

        @Override
        public int hashCode() {
            int result = sessionId != null ? sessionId.hashCode() : 0;
            result = 31 * result + connectedFlags;
            result = 31 * result + (debug ? 1 : 0);
            return result;
        }
    }
}
