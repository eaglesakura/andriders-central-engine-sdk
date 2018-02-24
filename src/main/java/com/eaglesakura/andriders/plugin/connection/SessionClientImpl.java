package com.eaglesakura.andriders.plugin.connection;

import com.eaglesakura.andriders.AceSdkUtil;
import com.eaglesakura.andriders.plugin.internal.CentralServiceCommand;
import com.eaglesakura.andriders.serialize.RawSessionInfo;
import com.eaglesakura.android.service.CommandClient;
import com.eaglesakura.android.service.CommandMap;
import com.eaglesakura.android.service.data.Payload;
import com.eaglesakura.android.thread.ui.UIHandler;
import com.eaglesakura.android.util.AndroidThreadUtil;
import com.eaglesakura.lambda.CancelCallback;

import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.annotation.WorkerThread;

/**
 * セッションサーバーへの接続を行うクライアント
 */
class SessionClientImpl extends CommandClient {
    CommandMap mCommandMap = new CommandMap();

    SessionClientImpl(Context context) {
        super(context);
        buildCommands();
    }

    private Intent newConnectionIntent() {
        Intent intent = new Intent(CentralServiceCommand.ACTION_SESSION_CONTROL);
        intent.setComponent(CentralServiceCommand.COMPONENT_SESSION_SERVICE);
        intent.putExtra(CentralServiceCommand.EXTRA_CLIENT_APPLICATION_ID, mContext.getPackageName());
        return intent;
    }

    /**
     * 非同期で接続を行う。
     * 接続チェックは isConnected() を見る。
     */
    public void connectAsync() {
        connectToSever(newConnectionIntent());
    }

    /**
     * バックグラウンドスレッドから呼び出し、接続待ちを行う。
     */
    @WorkerThread
    public boolean connect(@Nullable CancelCallback cancelCallback) {
        AndroidThreadUtil.assertBackgroundThread();
        return connectToSever(newConnectionIntent(), cancelCallback);
    }

    @Override
    public Payload requestPostToServer(String cmd, Payload payload) throws RemoteException {
        return super.requestPostToServer(cmd, payload);
    }

    @Override
    protected Payload onReceivedData(String cmd, Payload payload) throws RemoteException {
        return mCommandMap.execute(this, cmd, payload);
    }

    void buildCommands() {
        mCommandMap.addAction(CentralServiceCommand.CMD_onSessionStarted, (sender, cmd, payload) -> {
            RawSessionInfo sessionInfo = AceSdkUtil.deserializeFromByteArray(RawSessionInfo.class, payload.getBuffer());
            UIHandler.postUI(() -> onSessionStarted(sessionInfo));
            return null;
        });
        mCommandMap.addAction(CentralServiceCommand.CMD_onSessionStopped, (sender, cmd, payload) -> {
            RawSessionInfo sessionInfo = AceSdkUtil.deserializeFromByteArray(RawSessionInfo.class, payload.getBuffer());
            UIHandler.postUI(() -> onSessionStopped(sessionInfo));
            return null;
        });
    }

    @UiThread
    protected void onSessionStarted(RawSessionInfo info) {

    }

    @UiThread
    protected void onSessionStopped(RawSessionInfo info) {

    }
}
