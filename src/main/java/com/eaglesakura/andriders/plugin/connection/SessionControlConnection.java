package com.eaglesakura.andriders.plugin.connection;

import com.eaglesakura.andriders.serialize.RawSessionInfo;
import com.eaglesakura.collection.AnonymousBroadcaster;
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

    AnonymousBroadcaster mConnectedListeners = new AnonymousBroadcaster();

    AnonymousBroadcaster mStateChangeListeners = new AnonymousBroadcaster();

    public SessionControlConnection(Context context) {
        mClient = new SessionClientImpl(context) {
            @Override
            protected void onConnected() {
                super.onConnected();
                for (OnConnectedListener listener : mConnectedListeners.list(OnConnectedListener.class)) {
                    listener.onConnected(SessionControlConnection.this);
                }
            }

            @Override
            protected void onSessionStarted(RawSessionInfo info) {
                super.onSessionStarted(info);
                for (OnSessionStateChangeListener listener : mStateChangeListeners.list(OnSessionStateChangeListener.class)) {
                    listener.onSessionStarted(SessionControlConnection.this, info);
                }
            }

            @Override
            protected void onSessionStopped(RawSessionInfo info) {
                super.onSessionStopped(info);
                for (OnSessionStateChangeListener listener : mStateChangeListeners.list(OnSessionStateChangeListener.class)) {
                    listener.onSessionStopped(SessionControlConnection.this, info);
                }
            }
        };
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

    public boolean isConnected() {
        return mClient.isConnected();
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

    public void registerConnectedListener(OnConnectedListener obj) {
        mConnectedListeners.register(obj);
    }

    public void unregisterConnectedListener(OnConnectedListener obj) {
        mConnectedListeners.unregister(obj);
    }

    public void registerSessionStateChangeListener(OnSessionStateChangeListener obj) {
        mStateChangeListeners.register(obj);
    }

    public void unregisterSessionStateChangeListener(OnSessionStateChangeListener obj) {
        mStateChangeListeners.unregister(obj);
    }

    public interface OnConnectedListener {
        void onConnected(SessionControlConnection connection);
    }

    public interface OnSessionStateChangeListener {
        void onSessionStarted(SessionControlConnection connection, RawSessionInfo info);

        void onSessionStopped(SessionControlConnection connection, RawSessionInfo info);
    }
}
