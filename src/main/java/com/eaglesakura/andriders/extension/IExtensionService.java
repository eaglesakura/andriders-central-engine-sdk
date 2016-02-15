package com.eaglesakura.andriders.extension;

import java.util.List;

/**
 * 拡張機能を実装したService
 * <p/>
 * ACEsが拡張機能をBindすることにより動作する。
 * <p/>
 * ライブラリ的には拡張機能がServerとなり、ACEsがクライアントとして動作することになる。
 * <p/>
 * ユーザーの操作によって意図的に拡張Serviceがシャットダウンされる場合がある。例えば正常にBLEガジェットが接続できない場合など。
 */
public interface IExtensionService {
    String ACES_PACKAGE_NAME = "com.eaglesakura.andriders.service.central.CentralService";

    /**
     * 拡張機能情報を取得する
     */
    ExtensionInformation getExtensionInformation(ExtensionSession session);

    /**
     * ディスプレイの表示情報を取得する
     */
    List<DisplayInformation> getDisplayInformation(ExtensionSession session);

    /**
     * ACEに接続され、サイコンの使用準備ができた
     */
    void onAceServiceConnected(ExtensionSession session);

    /**
     * ACE
     */
    void onAceServiceDisconnected(ExtensionSession session);

    /**
     * 拡張サービスが有効化された
     */
    void onEnable(ExtensionSession session);

    /**
     * 拡張サービスが無効化された
     */
    void onDisable(ExtensionSession session);

    /**
     * 設定画面を開く
     */
    void startSetting(ExtensionSession session);
}
