package com.eaglesakura.andriders.plugin.connection;

import com.eaglesakura.andriders.error.SessionControlException;
import com.eaglesakura.andriders.plugin.internal.CentralServiceCommand;
import com.eaglesakura.andriders.serialize.RawSessionInfo;
import com.eaglesakura.andriders.serialize.RawSessionRequest;
import com.eaglesakura.android.service.data.Payload;

import android.content.Context;
import android.support.annotation.Nullable;

/**
 * ACEのService本体とのデータバインドを行う
 */
public class CentralSessionController {
    final SessionClientImpl mClientImpl;

    final Context mContext;

    CentralSessionController(Context context, SessionClientImpl clientImpl) {
        mContext = context;
        mClientImpl = clientImpl;
    }

    /**
     * 走行セッションが開始されていればtrue
     */
    public boolean isSessionStarted() {
        return getSessionInfo() != null;
    }

    /**
     * 現在のセッション情報を取得する
     */
    @Nullable
    public RawSessionInfo getSessionInfo() {
        try {
            Payload payload = mClientImpl.requestPostToServer(CentralServiceCommand.CMD_getSessionInfo, null);
            return payload.deserializePublicField(RawSessionInfo.class);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * セッションの開始をリクエストする
     */
    public void requestSessionStart() throws SessionControlException {
        try {
            RawSessionRequest request = new RawSessionRequest();
            mClientImpl.requestPostToServer(CentralServiceCommand.CMD_requestSessionStart, Payload.fromPublicField(request));
        } catch (Exception e) {
            throw new SessionControlException(e);
        }
    }

    /**
     * セッションの停止をリクエストする
     */
    public void requestSessionStop() throws SessionControlException {
        try {
            mClientImpl.requestPostToServer(CentralServiceCommand.CMD_requestSessionStop, null);
        } catch (Exception e) {
            throw new SessionControlException(e);
        }
    }
}
