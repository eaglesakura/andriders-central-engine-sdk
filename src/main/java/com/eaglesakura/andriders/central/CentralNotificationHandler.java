package com.eaglesakura.andriders.central;

import com.eaglesakura.andriders.serialize.NotificationProtocol;

/**
 * Centralに送信された通知情報をハンドリングする
 */
public abstract class CentralNotificationHandler {
    /**
     * 新たに通知が発行された
     */
    public void onReceivedNotification(NotificationProtocol.RawNotification notification) {
    }
}
