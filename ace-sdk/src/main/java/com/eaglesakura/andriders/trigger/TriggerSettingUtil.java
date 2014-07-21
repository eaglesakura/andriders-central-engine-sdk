package com.eaglesakura.andriders.trigger;

import android.content.Intent;

import com.eaglesakura.andriders.protocol.CommandProtocol.TriggerType;

/**
 * 近接コマンド設定用Util
 */
public class TriggerSettingUtil {

    /**
     * 指定時間
     */
    public static final String EXTRA_COMMAND_TIME_SEC = "TRIGGERCOMMAND_EXTRA_COMMAND_TIME_SEC";

    /**
     * トリガーの種類
     * {@link TriggerType}が指定される。
     */
    public static final String EXTRA_TRIGGER_TYPE = "TRIGGERCOMMAND_EXTRA_TRIGGER_TYPE";

    /**
     * 近接コマンド入力時間を取得する
     * @param activityIntent
     * @return
     */
    public static int getCommandTimeSec(Intent activityIntent) {
        return activityIntent.getIntExtra(EXTRA_COMMAND_TIME_SEC, -1);
    }

    /**
     * トリガーの種類を取得する
     * @param activityIntent
     * @return
     */
    public static TriggerType getTriggerType(Intent activityIntent) {
        return TriggerType.valueOf(activityIntent.getStringExtra(EXTRA_TRIGGER_TYPE));
    }
}
