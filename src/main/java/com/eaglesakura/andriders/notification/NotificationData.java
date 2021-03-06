package com.eaglesakura.andriders.notification;

import com.eaglesakura.andriders.AceEnvironment;
import com.eaglesakura.andriders.AceSdkUtil;
import com.eaglesakura.andriders.plugin.internal.CentralServiceCommand;
import com.eaglesakura.andriders.serialize.NotificationProtocol;
import com.eaglesakura.android.graphics.Graphics;
import com.eaglesakura.android.thread.ui.UIHandler;
import com.eaglesakura.android.util.ImageUtil;
import com.eaglesakura.util.CollectionUtil;
import com.eaglesakura.util.StringUtil;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import java.util.Date;

/**
 * 通知用データ
 */
public class NotificationData {

    /**
     * Andriders Central Engine Serviceを起動した
     */
    public static final String ID_CENTRAL_SERVICE_BOOT = "org.andriders.ace.ID_CENTRAL_SERVICE_BOOT";

    /**
     * ハートレートモニターに接続した
     */
    public static final String ID_GADGET_BLE_HRMONITOR_BATTERY = "org.andriders.ace.ID_GADGET_BLE_HRMONITOR_BATTERY";

    /**
     * ハートレートモニターに接続した
     */
    public static final String ID_GADGET_BLE_HRMONITOR_SEARCH = "org.andriders.ace.ID_GADGET_BLE_HRMONITOR_SEARCH";

    /**
     * ハートレートモニターに接続した
     */
    public static final String ID_GADGET_BLE_HRMONITOR_CONNECT = "org.andriders.ace.ID_GADGET_BLE_HRMONITOR_CONNECT";

    /**
     * ハートレートモニターから切断された
     */
    public static final String ID_GADGET_BLE_HRMONITOR_DISCONNECT = "org.andriders.ace.ID_GADGET_BLE_HRMONITOR_DISCONNECT";

    /**
     * ケイデンスセンサーに接続した
     */
    public static final String ID_GADGET_BLE_SPEED_CADENCE_BATTERY = "org.andriders.ace.ID_GADGET_BLE_SPEED_CADENCE_BATTERY";

    /**
     * ケイデンスセンサーに接続した
     */
    public static final String ID_GADGET_BLE_SPEED_CADENCE_SEARCH = "org.andriders.ace.ID_GADGET_BLE_SPEED_CADENCE_SEARCH";

    /**
     * ケイデンスセンサーに接続した
     */
    public static final String ID_GADGET_BLE_SPEED_CADENCE_CONNECT = "org.andriders.ace.ID_GADGET_BLE_SPEED_CADENCE_CONNECT";

    /**
     * ケイデンスセンサーから切断された
     */
    public static final String ID_GADGET_BLE_SPEED_CADENCE_DISCONNECT = "org.andriders.ace.ID_GADGET_BLE_SPEED_CADENCE_DISCONNECT";

    public enum Duration {
        /**
         * 短い時間だけ通知を表示する
         */
        Short,

        /**
         * 通常の時間だけ通知を表示する
         */
        Normal,

        /**
         * 長めに通知を表示する
         */
        Long,
    }

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
    String uniqueId = StringUtil.format("notify(%d)", System.currentTimeMillis());

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
    Duration duration = Duration.Normal;

    public NotificationData(Context context, byte[] buffer) {
        NotificationProtocol.RawNotification raw = AceSdkUtil.deserializeFromByteArray(NotificationProtocol.RawNotification.class, buffer);

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

    public NotificationData setDuration(Duration notificationLength) {
        this.duration = notificationLength;
        return this;
    }

    public Duration getDuration() {
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

    /**
     * Drawableからアイコンを設定する
     */
    public NotificationData setIcon(Context context, @NonNull Drawable drawable) {
        setIcon(ImageUtil.toBitmap(drawable, NOTIFICATION_ICON_SIZE));
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
     * Broadcastを通じて通知をリクエストする
     *
     * これにより、Plugin Serviceを使用しなくても通知を表示できる。
     * 例えば、SNSへの投稿を完了した、などのメッセージで使用する。
     */
    public void showRequest(@NonNull Context context) {
        UIHandler.postUIorRun(() -> {
            Intent intent = new Intent(CentralServiceCommand.ACTION_NOTIFICATION_REQUEST);
            intent.putExtra(CentralServiceCommand.EXTRA_NOTIFICATION_DATA, serialize());
            intent.setPackage(AceEnvironment.ANDRIDERS_CENTRAL_ENGINE_PACKAGE_NAME);
            context.sendBroadcast(intent);
        });
    }

    public static class Builder {
        NotificationData mRaw = new NotificationData();

        Context mContext;

        public Builder(@NonNull Context context) {
            mContext = context;
        }


        public Builder(@NonNull Context context, @NonNull String uniqueId) {
            mContext = context;
            mRaw.uniqueId = uniqueId;
        }

        public Builder date(Date date) {
            mRaw.setDate(date);
            return this;
        }

        public Builder message(String message) {
            mRaw.setMessage(message);
            return this;
        }

        public Builder message(@StringRes int resId) {
            return message(mContext.getString(resId));
        }

        public Builder uniqueId(String uniqueId) {
            mRaw.setUniqueId(uniqueId);
            return this;
        }

        public Builder icon(Bitmap icon, IconCompressLevel level) {
            mRaw.setIcon(icon, level);
            return this;
        }

        public Builder icon(@NonNull Drawable icon, IconCompressLevel level) {
            if (icon instanceof BitmapDrawable) {
                return icon(((BitmapDrawable) icon).getBitmap(), level);
            } else {
                mRaw.setIcon(ImageUtil.toBitmap(icon, Math.max(icon.getMinimumWidth(), icon.getMinimumHeight())), level);
                return this;
            }
        }

        public Builder backgroundColor(@ColorInt int color) {
            mRaw.setBackgroundColor(color);
            return this;
        }

        public Builder icon(@DrawableRes int resId) {
            mRaw.setIcon(mContext, resId);
            return this;
        }

        public Builder icon(Bitmap icon) {
            mRaw.setIcon(icon);
            return this;
        }

        public Builder icon(@NonNull Drawable icon) {
            if (icon instanceof BitmapDrawable) {
                return icon(((BitmapDrawable) icon).getBitmap());
            } else {
                mRaw.setIcon(ImageUtil.toBitmap(icon, NOTIFICATION_ICON_SIZE));
                return this;
            }
        }

        public Builder duration(Duration duration) {
            mRaw.setDuration(duration);
            return this;
        }

        public NotificationData getNotification() {
            if (mRaw.icon == null) {
                icon(android.R.drawable.ic_dialog_alert);
            }
            return mRaw;
        }
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
        return AceSdkUtil.serializeToByteArray(raw);
    }
}
