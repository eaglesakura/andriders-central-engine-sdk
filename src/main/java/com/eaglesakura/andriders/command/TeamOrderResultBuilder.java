package com.eaglesakura.andriders.command;

import android.app.Activity;
import android.content.Intent;

import com.eaglesakura.andriders.protocol.CommandProtocol;

/**
 * チームメンバーに対するオーダーを返却する
 * <p/>
 * オーダーは相手端末にRemoteIntentとして送信され、Intent処理される。
 */
public class TeamOrderResultBuilder {
    /**
     * 起動対象Intent
     */
    public static final String RESULT_EXTRA_INTENTDATA = "COMMAND_RESULT_EXTRA_INTENTDATA";


    private CommandProtocol.IntentPayload.Builder remoteIntent;

    private final Activity activity;

    public TeamOrderResultBuilder(Activity activity) {
        this.activity = activity;
    }

    /**
     * 相手端末で起動するIntentを指定する
     *
     * @param remoteIntent
     */
    public void setRemoteIntent(CommandProtocol.IntentPayload remoteIntent) {
        this.remoteIntent = remoteIntent.toBuilder();
    }

    public void setRemoteIntent(CommandProtocol.IntentPayload.Builder remoteIntent) {
        this.remoteIntent = remoteIntent;
    }

    /**
     * ビルドを完了する
     */
    public void finish() {
        if (remoteIntent == null) {
            throw new IllegalStateException();
        }

        Intent data = new Intent();
        data.putExtra(RESULT_EXTRA_INTENTDATA, remoteIntent.build().toByteArray());
        activity.setResult(Activity.RESULT_OK, data);
        activity.finish();
    }
}
