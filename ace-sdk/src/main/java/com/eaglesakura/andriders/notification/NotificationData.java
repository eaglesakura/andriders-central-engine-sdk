package com.eaglesakura.andriders.notification;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;

import com.eaglesakura.andriders.protocol.CommandProtocol;
import com.eaglesakura.android.util.ImageUtil;
import com.eaglesakura.util.StringUtil;
import com.google.protobuf.ByteString;

import java.util.Date;

/**
 * 通知用データ
 */
public class NotificationData {

    /**
     * 通知アイコンの大きさ
     */
    public static final int NOTIFICATION_ICON_SIZE = 64;

    /**
     * アイコン
     * <p/>
     * 適当な大きさよりも大きい場合は縮小される
     */
    Bitmap icon;

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

    /**
     * アイコンを指定する
     * <p/>
     * アイコンは自動的にサムネイルサイズに縮小される
     *
     * @param icon
     */
    public void setIcon(Bitmap icon) {
        this.icon = ImageUtil.toSquareImage(icon, NOTIFICATION_ICON_SIZE);
    }

    /**
     * アイコンを指定する
     *
     * @param context
     * @param drawableid
     */
    public void setIcon(Context context, int drawableid) {
        setIcon(ImageUtil.decode(context, drawableid));
    }

    public Bitmap getIcon() {
        return icon;
    }

    /**
     * 背景色を指定する。ただし、アルファは削除される。
     *
     * @param color
     */
    public void setBackgroundColor(int color) {
        this.backgroundColor = (0xFF000000 | color);
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * payloadを構築する
     *
     * @return
     */
    public CommandProtocol.NotificationRequestPayload buildPayload() {
        CommandProtocol.NotificationRequestPayload.Builder builder = CommandProtocol.NotificationRequestPayload.newBuilder();
        builder.setUniqueId(uniqueId);
        if (icon != null) {
            builder.setIconFile(ByteString.copyFrom(ImageUtil.encodePng(icon)));
        }
        builder.setMessage(message);
        builder.setDate(StringUtil.toString(date));
        builder.setLength(notificationLength);
        builder.setBackgroundXRGB(backgroundColor);

        return builder.build();
    }
}
