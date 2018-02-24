package com.eaglesakura.andriders.plugin.internal;

import com.eaglesakura.andriders.plugin.AcePluginService;
import com.eaglesakura.andriders.plugin.DisplayKey;
import com.eaglesakura.andriders.plugin.PluginInformation;
import com.eaglesakura.andriders.plugin.connection.PluginConnection;
import com.eaglesakura.andriders.sdk.BuildConfig;
import com.eaglesakura.android.service.CommandMap;
import com.eaglesakura.android.service.CommandServer;
import com.eaglesakura.android.service.aidl.ICommandClientCallback;
import com.eaglesakura.android.service.data.Payload;
import com.eaglesakura.android.thread.ui.UIHandler;

import android.app.Service;
import android.os.RemoteException;

import java.io.Closeable;
import java.util.Arrays;
import java.util.List;

/**
 * ACE拡張機能の内部通信を行う
 */
public class PluginServerImpl extends CommandServer implements Closeable {
    final AcePluginService mExtensionService;

    final CommandMap mCommandMap = new CommandMap();

    final PluginConnection mConnection;

    String mClientId;

    public static final String ACTION_ACE_EXTENSION_BIND = "org.andriders.ace.ACTION_ACE_EXTENSION_BIND";

    /**
     * Serviceに接続する度に付与されるコネクションID
     * 必ず付与される。付与されないIntentは例外として扱われる。
     */
    public static final String EXTRA_CONNECTION_ID = "ace.EXTRA_CONNECTION_ID";

    /**
     * ACE本体サービスに接続するComponentName
     */
    public static final String EXTRA_ACE_COMPONENT = "ace.EXTRA_ACE_COMPONENT";

    /**
     * ACEに搭載されたSDKのバージョン
     */
    public static final String EXTRA_ACE_IMPL_SDK_VERSION = "ace.EXTRA_ACE_IMPL_SDK_VERSION";

    /**
     * デバッグ機能がONである場合true
     */
    public static final String EXTRA_DEBUGGABLE = "ace.EXTRA_DEBUGGABLE";

    /**
     * ACEsのコールバックが登録されていたらtrue
     */
    private boolean mRegisteredAces = false;

    public PluginServerImpl(PluginConnection session, Service service, AcePluginService extensionService) {
        super(service);
        mConnection = session;
        mExtensionService = extensionService;

        buildSystemCommands();
        buildDataCommands();
        buildDisplayCommands();
    }

    public Payload postToClient(String cmd, Payload payload) throws RemoteException {
        return postToClient(mClientId, cmd, payload);
    }

    /**
     * データをACEに送信し、結果をStringで受け取る
     */
    public String postToClientAsString(String cmd, String arg) throws RemoteException {
        Payload payload = postToClient(mClientId, cmd, Payload.fromString(arg));
        return Payload.deserializeStringOrNull(payload);
    }

    @Override
    protected Payload onReceivedDataFromClient(String cmd, String clientId, Payload payload) throws RemoteException {
        return mCommandMap.execute(this, cmd, payload);
    }

    @Override
    protected void onRegisterClient(String id, ICommandClientCallback callback) {
        mClientId = id;
    }

    @Override
    protected void onUnregisterClient(String id) {
        if (mConnection.isAcesSession()) {
            onUnregisterCallback();
        }
    }

    public void validAcesSession() {
        if (!mConnection.isAcesSession() || !mRegisteredAces) {
            throw new IllegalStateException("Not Connected!!");
        }
    }

    private void onUnregisterCallback() {
        UIHandler.postUIorRun(() -> {
            if (mRegisteredAces) {
                mRegisteredAces = false;
                mExtensionService.onAceServiceDisconnected(mConnection);
            }
        });
    }

    @Override
    public void close() {
        onUnregisterCallback();
    }

    /**
     * システム用コマンドを登録
     */
    private void buildSystemCommands() {
        /**
         * SDKバージョンを取得する
         */
        mCommandMap.addAction(CentralDataCommand.CMD_getSDKVersion, (sender, cmd, payload) -> {
            return Payload.fromString(BuildConfig.ACE_SDK_VERSION);
        });

        /**
         * 拡張機能情報を取得する
         */
        mCommandMap.addAction(CentralDataCommand.CMD_getInformations, (sender, cmd, payload) -> {
            PluginInformation information = mExtensionService.getExtensionInformation(mConnection);
            return new Payload(PluginInformation.serialize(Arrays.asList(information)));
        });

        /**
         * 表示情報を取得する
         */
        mCommandMap.addAction(CentralDataCommand.CMD_getDisplayInformations, (sender, cmd, payload) -> {
            List<DisplayKey> displayInformation = mExtensionService.getDisplayInformation(mConnection);
            return new Payload(DisplayKey.serialize(displayInformation));
        });

        /**
         * 拡張機能が有効化された
         */
        mCommandMap.addAction(CentralDataCommand.CMD_onExtensionEnable, (sender, cmd, payload) -> {
            UIHandler.postUI(() -> {
                mExtensionService.onEnable(mConnection);
            });
            return null;
        });

        /**
         * 拡張機能が無効化された
         */
        mCommandMap.addAction(CentralDataCommand.CMD_onExtensionDisable, (Object sender, String cmd, Payload payload) -> {
            UIHandler.postUI(() -> {
                mExtensionService.onDisable(mConnection);
            });
            return null;
        });

        /**
         * 設定ボタンが押された
         */
        mCommandMap.addAction(CentralDataCommand.CMD_onSettingStart, (Object sender, String cmd, Payload payload) -> {
            UIHandler.postUI(() -> {
                mExtensionService.startSetting(mConnection);
            });
            return null;
        });

        /**
         * 強制再起動を行う
         */
        mCommandMap.addAction(CentralDataCommand.CMD_requestRebootPlugin, (Object sender, String cmd, Payload payload) -> {
            UIHandler.postUI(() -> {
                android.os.Process.killProcess(android.os.Process.myPid());
            });
            return null;
        });

        /**
         * Centralの起動を完了した
         */
        mCommandMap.addAction(CentralDataCommand.CMD_onCentralBootCompleted, (Object sender, String cmd, Payload payload) -> {
            UIHandler.postUI(() -> {
                mRegisteredAces = true;
                mExtensionService.onAceServiceConnected(mConnection);
            });
            return null;
        });
    }

    /**
     * データ用コマンドを登録
     */
    private void buildDataCommands() {
    }

    /**
     * 表示用コマンドを登録
     */
    private void buildDisplayCommands() {

    }
}
