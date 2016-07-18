package com.eaglesakura.andriders.central;

import com.eaglesakura.andriders.command.CommandKey;
import com.eaglesakura.andriders.command.SerializableIntent;
import com.eaglesakura.andriders.serialize.NotificationProtocol;
import com.eaglesakura.andriders.serialize.RawIntent;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

/**
 * Centralに送信された通知情報をハンドリングする
 */
public abstract class CentralNotificationHandler {
    /**
     * 新たに通知が発行された
     */
    public void onReceivedNotification(@NonNull NotificationProtocol.RawNotification notification) {
    }

    /**
     * コマンドの起動が行われた
     *
     * @param key               起動されたコマンドキー
     * @param aceInternalExtras 起動されたコマンドのACEオプション（ACEのアップデートにより変動の可能性があるため、信頼性はない）
     */
    public void onReceivedCommandBoot(@NonNull CommandKey key, @Nullable List<RawIntent.Extra> aceInternalExtras) {
    }
}
