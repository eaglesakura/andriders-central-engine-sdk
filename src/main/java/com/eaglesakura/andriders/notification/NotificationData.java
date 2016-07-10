package com.eaglesakura.andriders.notification;

import com.eaglesakura.andriders.serialize.NotificationProtocol;
import com.eaglesakura.android.graphics.Graphics;
import com.eaglesakura.android.util.ImageUtil;
import com.eaglesakura.serialize.error.SerializeException;
import com.eaglesakura.util.CollectionUtil;
import com.eaglesakura.util.LogUtil;
import com.eaglesakura.util.SerializeUtil;
import com.eaglesakura.util.StringUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.DrawableRes;

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
    NotificationDuration duration = NotificationDuration.Normal;

    public NotificationData(Context context, byte[] buffer) {
        NotificationProtocol.RawNotification raw;
        try {
            raw = SerializeUtil.deserializePublicFieldObject(NotificationProtocol.RawNotification.class, buffer);
        } catch (SerializeException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }

        if (Color.alpha(raw.backgroundXRGB) != 0) {
            backgroundColor = raw.backgroundXRGB;
        }

        message = raw.message;
        uniqueId = raw.uniqueId;
        date = new Date(raw.date);
        if (!CollectionUtil.isEmpty(raw.iconFile)) {
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

    public NotificationData setDate(Date date) {
        this.date = date;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public NotificationData setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public NotificationData setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
        return this;
    }

    public NotificationData setDuration(NotificationDuration notificationLength) {
        this.duration = notificationLength;
        return this;
    }

    public NotificationDuration getDuration() {
        return duration;
    }

    /**
     * アイコンを指定する
     * <br>
     * アイコンは自動的にサムネイルサイズに縮小される
     */
    public NotificationData setIcon(Bitmap icon) {
        this.icon = Graphics.toSquareImage(icon, NOTIFICATION_ICON_SIZE);
        return this;
    }

    /**
     * アイコンを指定する。
     * <br>
     * アイコンは自動的にサムネイルサイズに縮小される。
     */
    public NotificationData setIcon(Bitmap icon, IconCompressLevel level) {
        this.icon = Graphics.toSquareImage(icon, NOTIFICATION_ICON_SIZE);
        this.iconCompressLevel = level;
        return this;
    }

    /**
     * アイコンを指定する
     */
    public NotificationData setIcon(Context context, @DrawableRes int resId) {
        setIcon(ImageUtil.decode(context, resId));
        return this;
    }

    public Bitmap getIcon() {
        return icon;
    }

    /**
     * 背景色を指定する。ただし、アルファは削除される。
     */
    public NotificationData setBackgroundColor(int color) {
        this.backgroundColor = (0xFF000000 | color);
        return this;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * 値をシリアライズする
     */
    public byte[] serialize() {
        NotificationProtocol.RawNotification raw = new NotificationProtocol.RawNotification();
        raw.uniqueId = uniqueId;
        if (icon != null) {
            raw.iconFile = iconCompressLevel.compress(icon);
        }
        raw.message = message;
        raw.date = date.getTime();
        raw.length = duration;
        raw.backgroundXRGB = backgroundColor;
        try {
            return SerializeUtil.serializePublicFieldObject(raw);
        } catch (SerializeException e) {
            LogUtil.log(e);
            throw new IllegalStateException();
        }
    }
}
