package com.eaglesakura.andriders.plugin.internal;

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
     * 現在のCentral Dataを取得する
     * ACEが起動していない場合、nullを返却する。
     */
    public static final String CMD_getCurrentCentralData = "CSC_getCurrentCentralData";
}
