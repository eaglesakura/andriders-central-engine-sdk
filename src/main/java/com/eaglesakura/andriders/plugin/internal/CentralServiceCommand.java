package com.eaglesakura.andriders.plugin.internal;

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
     * セッションを開始させる
     */
    public static final String ACTION_SESSION_START = "com.eaglesakura.andriders.ACTION_SESSION_START";

    /**
     * セッション停止をリクエストする
     */
    public static final String ACTION_SESSION_STOP = "com.eaglesakura.andriders.ACTION_SESSION_STOP";

    /**
     * セッション制御用
     */
    public static final String ACTION_SESSION_CONTROL = "com.eaglesakura.andriders.ACTION_SESSION_CONTROL";

    public static final String EXTRA_BOOT_DEBUG_MODE = "EXTRA_BOOT_DEBUG_MODE";

    /**
     * 接続をリクエストしているアプリID
     */
    public static final String EXTRA_CLIENT_APPLICATION_ID = "EXTRA_CONNECTION_PACKAGE_NAME";

    /**
     * 接続対象のコンポーネント
     */
    public static final ComponentName COMPONENT_SESSION_SERVICE = new ComponentName("com.eaglesakura.andriders.v3", "com.eaglesakura.andriders.service.CentralSessionService");
}
