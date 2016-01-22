package com.eaglesakura.andriders.command;

import com.eaglesakura.andriders.protocol.CommandProtocol;

import android.app.Activity;
import android.content.Intent;

/**
 * チームメンバーに対するオーダーを返却する
 * <br>
 * オーダーは相手端末にRemoteIntentとして送信され、Intent処理される。
 */
public class TeamOrderResultBuilder {
    /**
     * 起動対象Intent
     */
    public static final String RESULT_EXTRA_INTENTDATA = "COMMAND_RESULT_EXTRA_INTENTDATA";

    /**
     * チームメンバーのID
     */
    public static final String RESULT_EXTRA_MEMBER_ID = "COMMAND_RESULT_EXTRA_MEMBER_ID";


    private CommandProtocol.IntentPayload.Builder remoteIntent;

    private final Activity activity;

    public TeamOrderResultBuilder(Activity activity) {
        this.activity = activity;
    }

    /**
     * 相手端末で起動するIntentを指定する
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

        String memberId = activity.getIntent().getStringExtra(AcesTriggerUtil.EXTRA_MEMBER_ID);
        data.putExtra(RESULT_EXTRA_MEMBER_ID, memberId);

        activity.setResult(Activity.RESULT_OK, data);
        activity.finish();
    }
}
