package com.eaglesakura.andriders.plugin.internal;

import com.eaglesakura.andriders.sdk.BuildConfig;

import android.content.ComponentName;

/**
 * ACE本体の動作サービスとの連携を行う
 */
public class CentralServiceCommand {

    /**
     * 現在動作しているセッションIDを取得する。
     *
     * セッションが開始されていない場合、nullを返却する
     */
    public static final String CMD_getSessionInfo = "CSC_getSessionInfo";

    /**
     * セッション開始をリクエストする
     */
    public static final String CMD_requestSessionStart = "CSC_requestSessionStart";

    /**
     * セッションの停止をリクエストする
     */
    public static final String CMD_requestSessionStop = "CSC_requestSessionStop";

    /**
     * セッションが開始された
     */
    public static final String CMD_onSessionStarted = "CSC_onSessionStarted";

    /**
     * セッションが停止された
     */
    public static final String CMD_onSessionStopped = "CSC_onSessionStopped";

    /**
     * セッションを開始させる
     */
    public static final String ACTION_SESSION_START = "org.andriders.ace.internal.ACTION_SESSION_START";

    /**
     * セッション停止をリクエストする
     */
    public static final String ACTION_SESSION_STOP = "org.andriders.ace.internal.ACTION_SESSION_STOP";

    /**
     * セッション制御用
     */
    public static final String ACTION_SESSION_CONTROL = "org.andriders.ace.internal.ACTION_SESSION_CONTROL";

    /**
     * 接続をリクエストしているアプリID
     */
    public static final String EXTRA_CLIENT_APPLICATION_ID = "EXTRA_CONNECTION_PACKAGE_NAME";

    /**
     * 接続対象のコンポーネント
     */
    public static final ComponentName COMPONENT_SESSION_SERVICE = new ComponentName(BuildConfig.ACE_APPLICATION_ID, "com.eaglesakura.andriders.service.CentralSessionService");


    /**
     * セッションの通知をリクエストする
     */
    public static final String ACTION_NOTIFICATION_REQUEST = "org.andriders.ace.internal.ACTION_NOTIFICATION_REQUEST";

    /**
     * 通知データを付与する
     */
    public static final String EXTRA_NOTIFICATION_DATA = "EXTRA_NOTIFICATION_DATA";
}
