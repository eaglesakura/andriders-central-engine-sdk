package com.eaglesakura.andriders.plugin.connection;

import com.eaglesakura.andriders.central.CentralDataReceiver;
import com.eaglesakura.andriders.plugin.AcePluginService;
import com.eaglesakura.andriders.plugin.data.CentralEngineSessionData;
import com.eaglesakura.andriders.plugin.display.DisplayDataSender;
import com.eaglesakura.andriders.plugin.internal.PluginServerImpl;
import com.eaglesakura.log.Logger;
import com.eaglesakura.util.IOUtil;
import com.eaglesakura.util.StringUtil;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * ACEとプラグインの連携を行うためのコネクション
 * これはPluginServiceとの連携を前提としているため、Central Serviceに対してアクティブに接続するためには別な手段を用いる。
 */
public class PluginConnection {
    @NonNull
    final Service mService;

    @NonNull
    final AcePluginService mExtensionService;

    @NonNull
    final String mConnectionId;

    @NonNull
    final PluginServerImpl mServerImpl;

    @NonNull
    final CentralEngineSessionData mCentralDataExtension;

    @NonNull
    final DisplayDataSender mDisplayExtension;

    @Nullable
    final ComponentName mAcesComponent;

    /**
     * デバッグモードの場合true
     */
    final boolean mDebuggable;

    /**
     * SDKバージョン
     */
    final String mAcesSdkVersion;

    @Nullable
    final CentralDataReceiver mCentralDataReceiver;

    PluginConnection(Service service, Intent intent) {
        mService = service;
        mExtensionService = (AcePluginService) service;
        mConnectionId = intent.getStringExtra(PluginServerImpl.EXTRA_CONNECTION_ID);
        mAcesComponent = intent.getParcelableExtra(PluginServerImpl.EXTRA_ACE_COMPONENT);
        mDebuggable = intent.getBooleanExtra(PluginServerImpl.EXTRA_DEBUGGABLE, false);
        mAcesSdkVersion = intent.getStringExtra(PluginServerImpl.EXTRA_ACE_IMPL_SDK_VERSION);

        if (StringUtil.isEmpty(mConnectionId)) {
            throw new IllegalArgumentException("SessionId is empty");
        }
        if (StringUtil.isEmpty(mAcesSdkVersion)) {
            throw new IllegalArgumentException("SdkVersion is empty");
        }

        // サーバーを構築する
        mServerImpl = new PluginServerImpl(this, mService, mExtensionService);


        // アクセス用インターフェースを生成する
        mCentralDataExtension = new CentralEngineSessionData(this, mServerImpl);
        mDisplayExtension = new DisplayDataSender(this, mServerImpl);

        if (isAcesSession()) {
            mCentralDataReceiver = new CentralDataReceiver(mService.getApplicationContext());
            mCentralDataReceiver.connect();
        } else {
            mCentralDataReceiver = null;
        }
    }

    public Context getContext() {
        return mService;
    }

    /**
     * IOインターフェースを取得する
     */
    public IBinder getBinder() {
        return mServerImpl.getBinder();
    }

    /**
     * セッション識別用IDを取得する
     */
    public String getConnectionId() {
        return mConnectionId;
    }

    /**
     * データ拡張インターフェースを取得する
     */
    public CentralEngineSessionData getCentralData() {
        return mCentralDataExtension;
    }

    /**
     * 表示拡張インターフェースを取得する
     */
    public DisplayDataSender getDisplay() {
        return mDisplayExtension;
    }

    /**
     * CentralData更新通知クラスを取得する
     *
     * このクラスは自動的にconnect/disconnectされる。
     *
     * Sessionごとに生成される。
     *
     * Aceセッションでのみ取得可能。
     */
    @Nullable
    public CentralDataReceiver getCentralDataReceiver() {
        mServerImpl.validAcesSession();
        return mCentralDataReceiver;
    }

    /**
     * ACEsに接続されていればtrueを返却する
     * <p/>
     * 設定画面から接続されている、もしくは接続されていない場合はfalseを返却する
     */
    public boolean isAcesSession() {
        return mAcesComponent != null;
    }

    /**
     * ACEsが組み込んでいるSDKバージョンを取得する
     */
    public String getAcesSdkVersion() {
        return mAcesSdkVersion;
    }

    /**
     * デバッグが有効化されている場合true
     */
    public boolean isDebuggable() {
        return mDebuggable;
    }

    /**
     * 明示的解放を行う。
     * 解放そのものは onUnbind()で行うため、明示的に行わないこと。
     */
    void dispose() {
        IOUtil.close(mServerImpl);
        if (mCentralDataReceiver != null) {
            mCentralDataReceiver.disconnect();
        }
    }

    /**
     * 生成されたセッション一覧
     */
    static Map<String, PluginConnection> gSessions = new HashMap<>();

    /**
     * Service.onBindで呼び出す。
     * ACE管理用の新たなコネクションを生成する。
     * バインド可能なIntentではない場合、nullを返却する。
     */
    public static PluginConnection onBind(Service service, Intent intent) {
        if (!(service instanceof AcePluginService)) {
            throw new IllegalArgumentException();
        }

        final String action = intent.getAction();
        if (StringUtil.isEmpty(action)) {
            return null;
        }

        if (!action.startsWith(PluginServerImpl.ACTION_ACE_EXTENSION_BIND)) {
            return null;
        }

        final PluginConnection result = new PluginConnection(service, intent);
        synchronized (gSessions) {
            Logger.out(Logger.LEVEL_DEBUG, "Plugin", "add session :: " + result.getConnectionId() + " class :: " + service.getClass());
            if (gSessions.containsKey(result.getConnectionId())) {
                throw new IllegalStateException("session conflict :: " + result.getConnectionId() + " :: sessions -> " + gSessions.size());
            } else {
                gSessions.put(result.getConnectionId(), result);
            }
        }
        return result;
    }

    /**
     * Service.onUnbindで呼び出す。
     * ACE管理用のセッションを終了させる。
     * 終了したセッションを返すが、管理対象でない場合は戻り値は捨てて問題ない。
     */
    public static PluginConnection onUnbind(Service service, Intent intent) {
        final String sessionId = intent.getStringExtra(PluginServerImpl.EXTRA_CONNECTION_ID);
        if (StringUtil.isEmpty(sessionId)) {
            return null;
        }

        final PluginConnection result;
        synchronized (gSessions) {
            result = gSessions.remove(sessionId);
        }

        if (result != null) {
            // 廃棄メソッドを呼び出す
            result.dispose();
        }

        return result;
    }
}
