package com.eaglesakura.andriders.plugin;

import com.eaglesakura.andriders.plugin.connection.PluginConnection;

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
public interface AcePluginService {
    /**
     * 拡張機能情報を取得する
     */
    PluginInformation getExtensionInformation(PluginConnection connection);

    /**
     * ディスプレイの表示情報を取得する
     */
    List<DisplayKey> getDisplayInformation(PluginConnection connection);

    /**
     * ACEに接続され、サイコンの使用準備ができた
     */
    void onAceServiceConnected(PluginConnection connection);

    /**
     * ACEから切断された
     */
    void onAceServiceDisconnected(PluginConnection connection);

    /**
     * 拡張サービスが有効化された
     */
    void onEnable(PluginConnection connection);

    /**
     * 拡張サービスが無効化された
     */
    void onDisable(PluginConnection connection);

    /**
     * 設定画面を開く
     */
    void startSetting(PluginConnection connection);
}
