package com.eaglesakura.andriders.plugin.connection;

import com.eaglesakura.andriders.plugin.data.CentralSessionController;
import com.eaglesakura.andriders.plugin.internal.SessionClientImpl;
import com.eaglesakura.lambda.CancelCallback;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.annotation.WorkerThread;

/**
 * CentralServiceへの接続と制御を行う。
 */
public class SessionControlConnection {

    SessionClientImpl mClient;

    CentralSessionController mCentralSessionController;

    public SessionControlConnection(Context context) {
        mClient = new SessionClientImpl(context);
        mCentralSessionController = new CentralSessionController(context, mClient);
    }

    /**
     * Serviceバインド前に呼び出してはいけない
     */
    public CentralSessionController getCentralSessionController() {
        if (!mClient.isConnected()) {
            throw new IllegalStateException("Not Connected");
        }
        return mCentralSessionController;
    }

    @UiThread
    public void connectAsync() {
        mClient.connectAsync();
    }

    @WorkerThread
    public boolean connect(@Nullable CancelCallback cancelCallback) {
        return mClient.connect(cancelCallback);
    }

    @UiThread
    public void disconnectAsync() {
        mClient.disconnect();
    }

    @WorkerThread
    public boolean disconnect(CancelCallback cancelCallback) {
        return mClient.disconnect(cancelCallback);
    }
}
