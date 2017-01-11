package com.eaglesakura.andriders.central;

import com.eaglesakura.andriders.command.CommandKey;
import com.eaglesakura.andriders.serialize.NotificationProtocol;
import com.eaglesakura.andriders.serialize.RawCentralData;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Centralに送信された通知情報をハンドリングする
 */
public abstract class CentralNotificationHandler {
    /**
     * 新たに通知が発行された
     */
    public void onReceivedNotification(@NonNull NotificationProtocol.RawNotification notification, @Nullable RawCentralData centralData) {
    }

    /**
     * コマンドの起動が行われた
     *
     * @param key 起動されたコマンドキー
     */
    public void onReceivedCommandBoot(@NonNull CommandKey key, @Nullable RawCentralData centralData) {
    }
}
