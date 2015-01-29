package com.eaglesakura.andriders.command;

import android.content.Intent;

import com.eaglesakura.andriders.protocol.AcesProtocol;

/**
 * トリガーコマンド用Key
 */
public class AcesTriggerUtil {
    /**
     * コマンドの起動キー
     */
    public static final String EXTRA_COMMAND_KEY = "COMMANDEXTRA_COMMAND_KEY";

    /**
     * 現在のユーザーステータス
     */
    public static final String EXTRA_MASTER_PAYLOAD = "COMMANDEXTRA_MASTER_PAYLOAD";

    /**
     * Intentからマスター情報を取り出す
     *
     * @param intent
     * @return
     */
    public static AcesProtocol.MasterPayload getMasterPayload(Intent intent) {
        try {
            return AcesProtocol.MasterPayload.parseFrom(intent.getByteArrayExtra(EXTRA_MASTER_PAYLOAD));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * コマンドの起動キーを取得する
     *
     * @param activityIntent
     * @return
     */
    public static CommandKey getKey(Intent activityIntent) {
        return activityIntent.getParcelableExtra(EXTRA_COMMAND_KEY);
    }
}
