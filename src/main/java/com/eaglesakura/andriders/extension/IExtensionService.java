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
     *
     * @return
     */
    ExtensionInformation getExtensionInformation();

    /**
     * ACEに接続され、サイコンの使用準備ができた
     *
     * @param dataManager
     */
    void onAceServiceConnected(RemoteDataManager dataManager);

    /**
     * ACE
     *
     * @param dataManager
     */
    void onAceServiceDisconnected(RemoteDataManager dataManager);
}
