package com.eaglesakura.andriders.extension.internal;

import com.eaglesakura.andriders.extension.DisplayInformation;
import com.eaglesakura.andriders.extension.ExtensionInformation;
import com.eaglesakura.andriders.extension.ExtensionSession;
import com.eaglesakura.andriders.extension.IExtensionService;
import com.eaglesakura.andriders.sdk.BuildConfig;
import com.eaglesakura.android.db.BaseProperties;
import com.eaglesakura.android.service.CommandMap;
import com.eaglesakura.android.service.CommandServer;
import com.eaglesakura.android.service.aidl.ICommandClientCallback;
import com.eaglesakura.android.service.data.Payload;
import com.eaglesakura.android.thread.ui.UIHandler;
import com.eaglesakura.io.Disposable;

import android.app.Service;
import android.os.RemoteException;

import java.util.Arrays;
import java.util.List;

/**
 * ACE拡張機能の内部通信を行う
 */
public class ExtensionServerImpl extends CommandServer implements Disposable {
    final IExtensionService mExtensionService;

    final CommandMap mCommandMap = new CommandMap();

    final ExtensionSession mSession;

    String mClientId;

    public static final String ACTION_ACE_EXTENSION_BIND = "com.eaglesakura.andriders.ACTION_ACE_EXTENSION_BIND_V3";

    /**
     * セントラルを示すセッション
     */
    public static final String SESSION_ID_CENTRALSERVICE = "session.service.central";

    /**
     * セッション管理ID
     * 必ず付与される。付与されないIntentは例外として扱われる。
     */
    public static final String EXTRA_SESSION_ID = "ace.EXTRA_SESSION_ID";

    /**
     * ACE本体サービスに接続するComponentName
     */
    public static final String EXTRA_ACE_COMPONENT = "ace.EXTRA_ACE_COMPONENT";

    /**
     * ACEsのコールバックが登録されていたらtrue
     */
    private boolean mRegisteredAces = false;

    public ExtensionServerImpl(ExtensionSession session, Service service, IExtensionService extensionService) {
        super(service);
        mSession = session;
        mExtensionService = extensionService;

        buildSystemCommands();
        buildDataCommands();
        buildDisplayCommands();
    }


    public Payload postToClient(String cmd, BaseProperties args) throws RemoteException {
        byte[] postData = args != null ? args.toByteArray() : null;
        return postToClient(mClientId, cmd, postData);
    }

    public Payload postToClient(String cmd, Payload payload) {
        return postToClient(mClientId, payload);
    }

    public <T extends BaseProperties> T postToClient(Class<T> clazz, String cmd, BaseProperties args) throws RemoteException {
        return Payload.deserializePropOrNull(postToClient(cmd, args), clazz);
    }

    /**
     * データをACEに送信し、結果をStringで受け取る
     */
    public String postToClientAsString(String cmd, String arg) throws RemoteException {
        Payload payload = postToClient(mClientId, cmd, new Payload(arg));
        return Payload.deserializeStringOrNull(payload);
    }

    /**
     * データをACEに送信し、結果をStringで受け取る
     */
    public String postToClientAsString(String cmd, BaseProperties args) throws RemoteException {
        Payload payload = postToClient(mClientId, args);
        if (payload != null) {
            return null;
        } else {
            return new String(payload.getBuffer());
        }
    }


    @Override
    protected Payload onReceivedDataFromClient(String cmd, String clientId, Payload payload) throws RemoteException {
        return mCommandMap.execute(this, cmd, payload);
    }

    @Override
    protected void onRegisterClient(String id, ICommandClientCallback callback) {
        mClientId = id;
        if (mSession.isAcesSession()) {
            UIHandler.postUI(new Runnable() {
                @Override
                public void run() {
                    mRegisteredAces = true;
                    mExtensionService.onAceServiceConnected(mSession);
                }
            });
        }
    }

    @Override
    protected void onUnregisterClient(String id) {
        if (mSession.isAcesSession()) {
            onUnregisterCallback();
        }
    }

    public void validAcesSession() {
        if (!mSession.isAcesSession() || !mRegisteredAces) {
            throw new IllegalStateException("Not Connected!!");
        }
    }

    private void onUnregisterCallback() {
        UIHandler.postUIorRun(new Runnable() {
            @Override
            public void run() {
                if (mRegisteredAces) {
                    mRegisteredAces = false;
                    mExtensionService.onAceServiceDisconnected(mSession);
                }
            }
        });
    }

    @Override
    public void dispose() {
        onUnregisterCallback();
    }

    /**
     * システム用コマンドを登録
     */
    private void buildSystemCommands() {
        /**
         * SDKバージョンを取得する
         */
        mCommandMap.addAction(CentralDataCommand.CMD_getSDKVersion, new CommandMap.Action() {
            @Override
            public Payload execute(Object sender, String cmd, Payload payload) throws Exception {
                return new Payload(BuildConfig.ACE_SDK_VERSION.getBytes());
            }
        });

        /**
         * 拡張機能情報を取得する
         */
        mCommandMap.addAction(CentralDataCommand.CMD_getInformations, new CommandMap.Action() {
            @Override
            public Payload execute(Object sender, String cmd, Payload payload) throws Exception {
                ExtensionInformation information = mExtensionService.getExtensionInformation();
                return new Payload(ExtensionInformation.serialize(Arrays.asList(information)));
            }
        });

        /**
         * 表示情報を取得する
         */
        mCommandMap.addAction(CentralDataCommand.CMD_getDisplayInformations, new CommandMap.Action() {
            @Override
            public Payload execute(Object sender, String cmd, Payload payload) throws Exception {
                List<DisplayInformation> displayInformation = mExtensionService.getDisplayInformation();
                return new Payload(DisplayInformation.serialize(displayInformation));
            }
        });

        /**
         * 拡張機能が有効化された
         */
        mCommandMap.addAction(CentralDataCommand.CMD_onExtensionEnable, new CommandMap.Action() {
            @Override
            public Payload execute(Object sender, String cmd, Payload payload) throws Exception {
                UIHandler.postUI(new Runnable() {
                    @Override
                    public void run() {
                        mExtensionService.onEnable(mSession);
                    }
                });
                return null;
            }
        });

        /**
         * 拡張機能が無効化された
         */
        mCommandMap.addAction(CentralDataCommand.CMD_onExtensionDisable, new CommandMap.Action() {

            @Override
            public Payload execute(Object sender, String cmd, Payload payload) throws Exception {
                UIHandler.postUI(new Runnable() {
                    @Override
                    public void run() {
                        mExtensionService.onDisable(mSession);
                    }
                });
                return null;
            }
        });

        /**
         * 設定ボタンが押された
         */
        mCommandMap.addAction(CentralDataCommand.CMD_onSettingStart, new CommandMap.Action() {
            @Override
            public Payload execute(Object sender, String cmd, Payload payload) throws Exception {
                UIHandler.postUI(new Runnable() {
                    @Override
                    public void run() {
                        mExtensionService.startSetting(mSession);
                    }
                });
                return null;
            }
        });

        /**
         * 強制再起動を行う
         */
        mCommandMap.addAction(CentralDataCommand.CMD_requestRebootExtention, new CommandMap.Action() {
            @Override
            public Payload execute(Object sender, String cmd, Payload payload) throws Exception {
                UIHandler.postDelayedUI(new Runnable() {
                    @Override
                    public void run() {
                        android.os.Process.killProcess(android.os.Process.myPid());
                    }
                }, 10);
                return null;
            }
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
