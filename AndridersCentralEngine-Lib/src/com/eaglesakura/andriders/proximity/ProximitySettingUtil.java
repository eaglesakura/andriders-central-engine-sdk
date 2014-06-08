package com.eaglesakura.andriders.proximity;

import android.content.Intent;

/**
 * 近接コマンド設定用Util
 */
public class ProximitySettingUtil {

    /**
     * 指定時間
     */
    public static final String EXTRA_COMMAND_TIME_SEC = "PROXIMITY_EXTRA_COMMAND_TIME_SEC";

    /**
     * 近接コマンド入力時間を取得する
     * @param activityIntent
     * @return
     */
    public static int getCommandTimeSec(Intent activityIntent) {
        return activityIntent.getIntExtra(EXTRA_COMMAND_TIME_SEC, -1);
    }
}
