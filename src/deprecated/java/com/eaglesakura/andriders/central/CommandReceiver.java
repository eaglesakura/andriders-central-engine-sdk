package com.eaglesakura.andriders.central;

import android.content.Context;
import android.content.Intent;

/**
 * TODO コマンドの受信をこちらに移動する
 */
public class CommandReceiver {
    /**
     * コマンドデータのマスタ
     */
    static final String INTENT_EXTRA_MASTER = "INTENT_EXTRA_CMD_MASTER";

    /**
     * 送受信用カテゴリ
     *
     * フィルタリングのため、${self.package_name}.ace.CATEGORY_ACE_COMMAND が実際には指定される
     */
    final String INTENT_CATEGORY;

    Context mContext;

    /**
     *
     * @param context
     */
    public CommandReceiver(Context context) {
        mContext = context.getApplicationContext();
        INTENT_CATEGORY = String.format("%s.ace.CATEGORY_ACE_COMMAND", mContext.getPackageName());
    }

    public void connect() {

    }

    public void disconnect() {

    }

    /**
     * Intentを受信した
     */
    public synchronized void onReceivedIntent(Intent intent) {

    }


    /**
     * ペイロードを受信した
     */
    public synchronized void onReceivedPayload(byte[] buffer) {

    }
}
