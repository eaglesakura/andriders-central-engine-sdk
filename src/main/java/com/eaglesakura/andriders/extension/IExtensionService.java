package com.eaglesakura.andriders.extension;

import com.eaglesakura.andriders.extension.data.RemoteDataManager;

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
    String ACTION_ACE_EXTENSION_BIND = "com.eaglesakura.andriders.ACTION_ACE_EXTENSION_BIND_V2";

    String ACES_PACKAGE_NAME = "com.eaglesakura.andriders.service.central.CentralService_";

    /**
     * Client
     */
    String CLIENT_ID_ACE_SERVICE = "com.eaglesakura.andriders.service.central.CentralService_";

    /**
     * 拡張機能情報を取得する
     */
    ExtensionInformation getExtensionInformation();

    /**
     * ディスプレイの表示情報を取得する
     */
    List<DisplayInformation> getDisplayInformation();

    /**
     * ACEに接続され、サイコンの使用準備ができた
     */
    void onAceServiceConnected(RemoteDataManager dataManager);

    /**
     * ACE
     */
    void onAceServiceDisconnected(RemoteDataManager dataManager);

    /**
     * 拡張サービスが有効化された
     */
    void onEnable(RemoteDataManager dataManager);

    /**
     * 拡張サービスが無効化された
     */
    void onDisable(RemoteDataManager dataManager);

    /**
     * 設定画面を開く
     */
    void startSetting(RemoteDataManager dataManager);
}
