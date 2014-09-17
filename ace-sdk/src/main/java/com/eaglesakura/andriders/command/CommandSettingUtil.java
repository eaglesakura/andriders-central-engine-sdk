package com.eaglesakura.andriders.command;

import android.content.Intent;

/**
 * 近接コマンド設定用Util
 */
public class CommandSettingUtil {
    /**
     * コマンドの起動キー
     */
    public static final String EXTRA_COMMAND_KEY = "COMMANDEXTRA_COMMAND_KEY";

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
