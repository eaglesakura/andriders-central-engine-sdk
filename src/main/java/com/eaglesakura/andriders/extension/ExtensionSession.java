package com.eaglesakura.andriders.extension;

import com.eaglesakura.andriders.extension.internal.ExtensionServerImpl;
import com.eaglesakura.io.Disposable;
import com.eaglesakura.util.IOUtil;
import com.eaglesakura.util.StringUtil;

import android.app.Service;
import android.content.Intent;

import java.util.HashMap;
import java.util.Map;

/**
 * 拡張機能のセッション管理を行う
 */
public class ExtensionSession implements Disposable {
    final Service mService;

    final IExtensionService mExtensionService;

    final String mSessionId;

    final ExtensionServerImpl mServerImpl;

    ExtensionSession(Service service, Intent intent) {
        mService = service;
        mExtensionService = (IExtensionService) service;

        mSessionId = intent.getStringExtra(ExtensionServerImpl.EXTRA_SESSION_ID);
        if (StringUtil.isEmpty(mSessionId)) {
            throw new IllegalArgumentException("SessionId is empty");
        }

        // サーバーを構築する
        mServerImpl = new ExtensionServerImpl(this, mService, mExtensionService);
    }

    /**
     * セッション識別用IDを取得する
     */
    public String getSessionId() {
        return mSessionId;
    }

    /**
     * ACEsに接続されていればtrueを返却する
     * <p/>
     * 設定画面から接続されている、もしくは接続されていない場合はfalseを返却する
     */
    public boolean isAcesSession() {
        return mSessionId.equals(ExtensionServerImpl.SESSION_ID_CENTRALSERVICE);
    }

    /**
     * 明示的解放を行う。
     * 解放そのものは ExtensionSession.onUnbind()で行うため、明示的に行わないこと。
     */
    @Override
    public void dispose() {
        IOUtil.close(mServerImpl);
    }

    /**
     * 生成されたセッション一覧
     */
    static Map<String, ExtensionSession> gSessions = new HashMap<>();

    /**
     * Service.onBindで呼び出す。
     * ACE管理用の新たなセッションを生成する。
     * バインド可能なIntentではない場合、nullを返却する。
     */
    public static ExtensionSession onBind(Service service, Intent intent) {
        if (!(service instanceof IExtensionService)) {
            throw new IllegalArgumentException();
        }

        if (!ExtensionServerImpl.ACTION_ACE_EXTENSION_BIND.equals(intent.getAction())) {
            return null;
        }

        final ExtensionSession result = new ExtensionSession(service, intent);
        synchronized (gSessions) {
            if (gSessions.containsKey(result.getSessionId())) {
                throw new IllegalStateException("session conflict :: " + result.getSessionId());
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
    public static ExtensionSession onUnbind(Service service, Intent intent) {
        final String sessionId = intent.getStringExtra(ExtensionServerImpl.EXTRA_SESSION_ID);
        if (StringUtil.isEmpty(sessionId)) {
            return null;
        }

        final ExtensionSession result;
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
