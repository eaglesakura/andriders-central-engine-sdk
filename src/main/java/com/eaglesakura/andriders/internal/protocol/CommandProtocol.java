package com.eaglesakura.andriders.internal.protocol;

import com.eaglesakura.andriders.notification.NotificationLength;
import com.eaglesakura.serialize.Serialize;

import android.graphics.Color;

public class CommandProtocol {
    public static class NotificationRequestPayload {

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
        public NotificationLength length;

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
    }
}
