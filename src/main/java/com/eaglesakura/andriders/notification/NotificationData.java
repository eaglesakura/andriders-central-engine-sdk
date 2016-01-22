package com.eaglesakura.andriders.notification;

import com.google.protobuf.ByteString;

import com.eaglesakura.andriders.media.SoundKey;
import com.eaglesakura.andriders.protocol.CommandProtocol;
import com.eaglesakura.android.graphics.Graphics;
import com.eaglesakura.android.util.ImageUtil;
import com.eaglesakura.util.StringUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;

import java.util.Date;

/**
 * 通知用データ
 */
public class NotificationData {

    public enum IconCompressLevel {
        /**
         * 非圧縮
         */
        Raw {
            @Override
            byte[] compress(Bitmap image) {
                return ImageUtil.encodePng(image);
            }
        },

        /**
         * 圧縮するが、余り劣化を行わない
         */
        HighQuality {
            @Override
            byte[] compress(Bitmap image) {
                return ImageUtil.encodeJpeg(image, 90);
            }
        },

        /**
         * 圧縮し、可能な限りサイズを小さくする
         */
        LowQuality {
            @Override
            byte[] compress(Bitmap image) {
                return ImageUtil.encodeJpeg(image, 60);
            }
        };

        abstract byte[] compress(Bitmap image);
    }

    /**
     * 通知アイコンの大きさ
     */
    public static final int NOTIFICATION_ICON_SIZE = 64;

    /**
     * アイコン
     * <br>
     * 適当な大きさよりも大きい場合は縮小される
     */
    Bitmap icon;

    /**
     * 圧縮レベル
     */
    IconCompressLevel iconCompressLevel = IconCompressLevel.Raw;


    /**
     * 表示メッセージ
     */
    String message;

    /**
     * 識別ID
     */
    String uniqueId = String.format("notify(%d)", System.currentTimeMillis());

    /**
     * 通知日
     */
    Date date = new Date();

    /**
     * 背景カラー
     */
    int backgroundColor = Color.WHITE;

    /**
     * 通知の長さ
     */
    CommandProtocol.NotificationLength notificationLength = CommandProtocol.NotificationLength.Normal;

    /**
     * 通知サウンド
     */
    SoundData sound;

    public NotificationData(CommandProtocol.NotificationRequestPayload requestPayload) {
        if (requestPayload.hasIconPath()) {
            icon = ImageUtil.decode(requestPayload.getIconPath());
        } else if (requestPayload.hasIconFile()) {
            icon = ImageUtil.decode(requestPayload.getIconFile().toByteArray());
        }
        if (requestPayload.hasBackgroundXRGB()) {
            backgroundColor = requestPayload.getBackgroundXRGB();
        }

        message = requestPayload.getMessage();
        uniqueId = requestPayload.getUniqueId();
        date = StringUtil.toDate(requestPayload.getDate());

        if (requestPayload.hasSound()) {
            sound = new SoundData(requestPayload.getSound());
        }
    }

    public NotificationData() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public void setNotificationLength(CommandProtocol.NotificationLength notificationLength) {
        this.notificationLength = notificationLength;
    }

    public CommandProtocol.NotificationLength getNotificationLength() {
        return notificationLength;
    }

    public SoundData getSound() {
        return sound;
    }

    public void setSound(SoundData sound) {
        this.sound = sound;
    }

    public void setSound(SoundKey key) {
        this.sound = new SoundData();
        sound.setSoundKey(key.getKey());
        sound.setQueue(true);
    }

    /**
     * アイコンを指定する
     * <br>
     * アイコンは自動的にサムネイルサイズに縮小される
     */
    public void setIcon(Bitmap icon) {
        this.icon = Graphics.toSquareImage(icon, NOTIFICATION_ICON_SIZE);
    }

    /**
     * アイコンを指定する。
     * <br>
     * アイコンは自動的にサムネイルサイズに縮小される。
     */
    public void setIcon(Bitmap icon, IconCompressLevel level) {
        this.icon = Graphics.toSquareImage(icon, NOTIFICATION_ICON_SIZE);
        this.iconCompressLevel = level;
    }

    /**
     * アイコンを指定する
     */
    public void setIcon(Context context, int drawableid) {
        setIcon(ImageUtil.decode(context, drawableid));
    }

    public Bitmap getIcon() {
        return icon;
    }

    /**
     * 背景色を指定する。ただし、アルファは削除される。
     */
    public void setBackgroundColor(int color) {
        this.backgroundColor = (0xFF000000 | color);
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * payloadを構築する
     */
    public CommandProtocol.NotificationRequestPayload buildPayload() {
        CommandProtocol.NotificationRequestPayload.Builder builder = CommandProtocol.NotificationRequestPayload.newBuilder();
        builder.setUniqueId(uniqueId);
        if (icon != null) {
            builder.setIconFile(ByteString.copyFrom(iconCompressLevel.compress(icon)));
        }
        builder.setMessage(message);
        builder.setDate(StringUtil.toString(date));
        builder.setLength(notificationLength);
        builder.setBackgroundXRGB(backgroundColor);
        if (sound != null) {
            builder.setSound(sound.buildPayload());
        }

        return builder.build();
    }
}
