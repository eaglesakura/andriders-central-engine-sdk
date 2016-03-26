package com.eaglesakura.andriders.ui;

import com.eaglesakura.andriders.AcesEnvironment;
import com.eaglesakura.andriders.serialize.CommandProtocol.TweetRequestPayload;
import com.eaglesakura.util.LogUtil;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

@Deprecated
public class TweetSettingUtil {

    /**
     * ツイート設定用メッセージ
     */
    public static final String EXTRA_TWEETREQUEST_PAYLOAD = "EXTRA_TWEETREQUEST_PAYLOAD";

    /**
     * ツイート設定用Activityを開く
     */
    public static Intent createDefaultTweetSettingActivityIntent(Context context) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(AcesEnvironment.getApplicationPackageName(), "com.eaglesakura.andriders.ui.sns.TweetMessageInputActivity_"));
        return intent;
    }

    /**
     * ツイート設定Activityからツイート設定メッセージを受け取る
     */
    public static TweetRequestPayload getResultTweetRequest(Intent resultIntent) {
        byte[] extra = resultIntent.getByteArrayExtra(EXTRA_TWEETREQUEST_PAYLOAD);
        if (extra == null) {
            return null;
        }

        try {
            return TweetRequestPayload.parseFrom(extra);
        } catch (Exception e) {
            LogUtil.d(e);
            return null;
        }
    }
}
