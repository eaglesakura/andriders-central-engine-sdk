package com.eaglesakura.andriders.serialize;

import com.eaglesakura.andriders.notification.NotificationData;
import com.eaglesakura.serialize.Serialize;
import com.eaglesakura.util.RandomUtil;

import android.graphics.Color;

import java.util.Arrays;
import java.util.Random;

public class NotificationProtocol {
    public static class RawNotification {

        /**
         * 表示されるテキストメッセージ
         *
         * 長すぎる場合は途中で端折られる
         */
        @Serialize(id = 1)
        public String message;

        /**
         * アイコンファイル情報
         */
        @Serialize(id = 2)
        public byte[] iconFile;

        /**
         * アイコンファイルURI
         *
         * iconFileと排他仕様となる。アイコンファイルのbyte[]優先となる。
         */
        @Serialize(id = 3)
        public String iconUri;

        /**
         * Notification Length
         *
         * シリアライズ用のため、直接使用することは推奨しない
         */
        @Serialize(id = 4)
        public NotificationData.Duration length;

        /**
         * 一意に識別するID
         * 同じIDで、かつ既に表示中の場合は上書き表示される
         */
        @Serialize(id = 5)
        public String uniqueId;

        /**
         * 通知時刻
         */
        @Serialize(id = 6)
        public long date;

        /**
         * 色情報
         */
        @Serialize(id = 7)
        public int backgroundXRGB = Color.WHITE;

        public RawNotification() {
        }

        @Deprecated
        public RawNotification(Class<Random> dummy) {
            message = RandomUtil.randString();
            iconFile = RandomUtil.randBytes();
            iconUri = RandomUtil.randLargeString();
            length = RandomUtil.randEnumWithNull(NotificationData.Duration.class);
            uniqueId = RandomUtil.randLargeString();
            date = RandomUtil.randInt64();
            backgroundXRGB = RandomUtil.randInt32();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            RawNotification payload = (RawNotification) o;

            if (date != payload.date) return false;
            if (backgroundXRGB != payload.backgroundXRGB) return false;
            if (message != null ? !message.equals(payload.message) : payload.message != null)
                return false;
            if (!Arrays.equals(iconFile, payload.iconFile)) return false;
            if (iconUri != null ? !iconUri.equals(payload.iconUri) : payload.iconUri != null)
                return false;
            if (length != payload.length) return false;
            return !(uniqueId != null ? !uniqueId.equals(payload.uniqueId) : payload.uniqueId != null);

        }

        @Override
        public int hashCode() {
            int result = message != null ? message.hashCode() : 0;
            result = 31 * result + (iconFile != null ? Arrays.hashCode(iconFile) : 0);
            result = 31 * result + (iconUri != null ? iconUri.hashCode() : 0);
            result = 31 * result + (length != null ? length.hashCode() : 0);
            result = 31 * result + (uniqueId != null ? uniqueId.hashCode() : 0);
            result = 31 * result + (int) (date ^ (date >>> 32));
            result = 31 * result + backgroundXRGB;
            return result;
        }
    }
}
