package com.eaglesakura.andriders.notification;

import com.eaglesakura.andriders.internal.protocol.CommandProtocol;
import com.eaglesakura.android.graphics.Graphics;
import com.eaglesakura.android.util.ImageUtil;
import com.eaglesakura.serialize.error.SerializeException;
import com.eaglesakura.util.LogUtil;
import com.eaglesakura.util.SerializeUtil;
import com.eaglesakura.util.StringUtil;
import com.eaglesakura.util.Util;

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
    NotificationLength notificationLength = NotificationLength.Normal;

    public NotificationData(Context context, byte[] buffer) {
        CommandProtocol.NotificationRequestPayload raw = null;
        try {
            raw = SerializeUtil.deserializePublicFieldObject(CommandProtocol.NotificationRequestPayload.class, buffer);
        } catch (SerializeException e) {
            LogUtil.log(e);
            throw new IllegalStateException(e);
        }

        if (Color.alpha(raw.backgroundXRGB) != 0) {
            backgroundColor = raw.backgroundXRGB;
        }

        message = raw.message;
        uniqueId = raw.uniqueId;
        date = new Date(raw.date);
        if (!Util.isEmpty(raw.iconFile)) {
            // アイコン指定でのデコード
            setIcon(ImageUtil.decode(raw.iconFile));
        } else if (!StringUtil.isEmpty(raw.iconUri)) {
            // TODO URI指定でのデコード
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

    public void setNotificationLength(NotificationLength notificationLength) {
        this.notificationLength = notificationLength;
    }

    public NotificationLength getNotificationLength() {
        return notificationLength;
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
     * 値をシリアライズする
     */
    public byte[] serialize() {
        CommandProtocol.NotificationRequestPayload raw = new CommandProtocol.NotificationRequestPayload();
        raw.uniqueId = uniqueId;
        if (icon != null) {
            raw.iconFile = iconCompressLevel.compress(icon);
        }
        raw.message = message;
        raw.date = date.getTime();
        raw.setLength(notificationLength);
        raw.backgroundXRGB = backgroundColor;
        try {
            return SerializeUtil.serializePublicFieldObject(raw);
        } catch (SerializeException e) {
            LogUtil.log(e);
            throw new IllegalStateException();
        }
    }
}
