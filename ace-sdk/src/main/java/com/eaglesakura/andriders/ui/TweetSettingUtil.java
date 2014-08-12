package com.eaglesakura.andriders.ui;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.eaglesakura.andriders.AceLog;
import com.eaglesakura.andriders.protocol.CommandProtocol.TweetRequestPayload;

public class TweetSettingUtil {

    /**
     * ツイート設定用メッセージ
     */
    public static final String EXTRA_TWEETREQUEST_PAYLOAD = "EXTRA_TWEETREQUEST_PAYLOAD";

    /**
     * ツイート設定用Activityを開く
     * @param context
     * @return
     */
    public static Intent createDefaultTweetSettingActivityIntent(Context context) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.eaglesakura.andriders", "com.eaglesakura.andriders.ui.sns.TweetMessageInputActivity_"));
        return intent;
    }

    /**
     * ツイート設定Activityからツイート設定メッセージを受け取る
     * @param resultIntent
     * @return
     */
    public static TweetRequestPayload getResultTweetRequest(Intent resultIntent) {
        byte[] extra = resultIntent.getByteArrayExtra(EXTRA_TWEETREQUEST_PAYLOAD);
        if (extra == null) {
            return null;
        }

        try {
            return TweetRequestPayload.parseFrom(extra);
        } catch (Exception e) {
            AceLog.d(e);
            return null;
        }
    }
}