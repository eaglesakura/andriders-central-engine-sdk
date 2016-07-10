package com.eaglesakura.andriders.plugin;

import com.eaglesakura.andriders.central.CentralDataReceiver;
import com.eaglesakura.andriders.plugin.data.CentralEngineData;
import com.eaglesakura.andriders.plugin.display.DisplayDataSender;
import com.eaglesakura.andriders.plugin.internal.ExtensionServerImpl;
import com.eaglesakura.util.IOUtil;
import com.eaglesakura.util.LogUtil;
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
 * ACEと拡張サービスの接続管理を行う
 */
public class CentralEngineConnection {
    @NonNull
    final Service mService;

    @NonNull
    final AcePluginService mExtensionService;

    @NonNull
    final String mSessionId;

    @NonNull
    final ExtensionServerImpl mServerImpl;

    @NonNull
    final CentralEngineData mCentralDataExtension;

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

    CentralEngineConnection(Service service, Intent intent) {
        mService = service;
        mExtensionService = (AcePluginService) service;
        mSessionId = intent.getStringExtra(ExtensionServerImpl.EXTRA_SESSION_ID);
        mAcesComponent = intent.getParcelableExtra(ExtensionServerImpl.EXTRA_ACE_COMPONENT);
        mDebuggable = intent.getBooleanExtra(ExtensionServerImpl.EXTRA_DEBUGGABLE, false);
        mAcesSdkVersion = intent.getStringExtra(ExtensionServerImpl.EXTRA_ACE_IMPL_SDK_VERSION);


        if (StringUtil.isEmpty(mSessionId)) {
            throw new IllegalArgumentException("SessionId is empty");
        }
        if (StringUtil.isEmpty(mAcesSdkVersion)) {
            throw new IllegalArgumentException("SdkVersion is empty");
        }

        // サーバーを構築する
        mServerImpl = new ExtensionServerImpl(this, mService, mExtensionService);


        // アクセス用インターフェースを生成する
        mCentralDataExtension = new CentralEngineData(this, mServerImpl);
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
    public String getSessionId() {
        return mSessionId;
    }

    /**
     * データ拡張インターフェースを取得する
     */
    public CentralEngineData getCentralDataExtension() {
        return mCentralDataExtension;
    }

    /**
     * 表示拡張インターフェースを取得する
     */
    public DisplayDataSender getDisplayExtension() {
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
    static Map<String, CentralEngineConnection> gSessions = new HashMap<>();

    /**
     * Service.onBindで呼び出す。
     * ACE管理用の新たなコネクションを生成する。
     * バインド可能なIntentではない場合、nullを返却する。
     */
    public static CentralEngineConnection onBind(Service service, Intent intent) {
        if (!(service instanceof AcePluginService)) {
            throw new IllegalArgumentException();
        }

        final String action = intent.getAction();
        if (StringUtil.isEmpty(action)) {
            return null;
        }

        if (!action.startsWith(ExtensionServerImpl.ACTION_ACE_EXTENSION_BIND)) {
            return null;
        }

        final CentralEngineConnection result = new CentralEngineConnection(service, intent);
        synchronized (gSessions) {
            LogUtil.log("add session :: " + result.getSessionId() + " class :: " + service.getClass());
            if (gSessions.containsKey(result.getSessionId())) {
                throw new IllegalStateException("session conflict :: " + result.getSessionId() + " :: sessions -> " + gSessions.size());
            } else {
                gSessions.put(result.getSessionId(), result);
            }
        }
        return result;
    }

    /**
     * Service.onUnbindで呼び出す。
     * ACE管理用のセッションを終了させる。
     * 終了したセッションを返すが、管理対象でない場合は戻り値は捨てて問題ない。
     */
    public static CentralEngineConnection onUnbind(Service service, Intent intent) {
        final String sessionId = intent.getStringExtra(ExtensionServerImpl.EXTRA_SESSION_ID);
        if (StringUtil.isEmpty(sessionId)) {
            return null;
        }

        final CentralEngineConnection result;
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
