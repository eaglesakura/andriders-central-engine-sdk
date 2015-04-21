package com.eaglesakura.andriders.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.eaglesakura.andriders.IAcesExtensionService;
import com.eaglesakura.andriders.IAndridersCentralEngineService;
import com.eaglesakura.andriders.IAndridersCentralEngineTeamService;
import com.eaglesakura.andriders.central.AcesProtocolReceiver;
import com.eaglesakura.andriders.central.TeamMemberReceiver;
import com.eaglesakura.andriders.central.TeamProtocolReceiver;
import com.eaglesakura.andriders.protocol.CommandProtocol;
import com.eaglesakura.andriders.protocol.ExtensionMessage;

/**
 * ACEsに対して常にバインドを行わせるServiceを定義する。
 * <p/>
 * ACEs/Team-ACEsのどちらか一方、もしくは両方がバインドされる。
 */
public abstract class AcesExtensionService extends Service {

    /**
     * ACEs/Team-ACEsから接続リクエストを送る
     */
    public static final String ACTION_ACE_EXTENSION_BIND = "com.eaglesakura.andriders.ACTION_ACE_EXTENSION_BIND";

    /**
     * ローカルユーザー用Service
     */
    private IAndridersCentralEngineService aceService;

    /**
     * チームユーザー用Service
     */
    private IAndridersCentralEngineTeamService teamService;

    private AcesProtocolReceiver protocolReceiver;

    private TeamProtocolReceiver teamProtocolReceiver;

    @Override
    public IBinder onBind(Intent intent) {
        String action = intent.getAction();
        if (!ACTION_ACE_EXTENSION_BIND.equals(action)) {
            throw new IllegalStateException("Action error :: " + action);
        }

        return new IAcesExtensionService.Stub() {
            @Override
            public void onConnectedAces(IAndridersCentralEngineService aces) throws RemoteException {
                aceService = aces;
            }

            @Override
            public void onDisconnectedAces() throws RemoteException {
                aceService = null;
            }

            @Override
            public void onConnectedTeamAces(IAndridersCentralEngineTeamService teamAces) throws RemoteException {
                teamService = teamAces;
            }

            @Override
            public void onDisconnectedTeamAces() throws RemoteException {
                teamService = null;
            }
        };
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        protocolReceiver = new AcesProtocolReceiver(this);
        protocolReceiver.connect();

        teamProtocolReceiver = new TeamProtocolReceiver(this);
        teamProtocolReceiver.connect();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        protocolReceiver.disconnect();
        protocolReceiver = null;

        teamProtocolReceiver.disconnect();
        teamProtocolReceiver = null;
    }

    public boolean isConnectedAces() {
        return aceService != null;
    }

    public boolean isConnectedTeamAces() {
        return teamService != null;
    }

    /**
     * 遠隔端末にIntentを発行する
     */
    public static final String MESSAGE_TYPE_TEAM_sendRemoteIntent = "MESSAGE_TYPE_TEAM_sendRemoteIntent";

    /**
     * 離れているユーザーに対してIntentを発行する
     *
     * @param member       メンバーID
     * @param remoteIntent 送信するIntent
     *
     * @return 成功したらtrue
     */
    public boolean sendRemoteIntent(TeamMemberReceiver member, CommandProtocol.IntentPayload remoteIntent) {
        try {
            if (teamService == null || member == null) {
                return false;
            }

            ExtensionMessage.RemoteMessagePayload.Builder message = ExtensionMessage.RemoteMessagePayload.newBuilder();
            ExtensionMessage.TeamRemoteIntentMessage.Builder remoteIntentMessage = ExtensionMessage.TeamRemoteIntentMessage.newBuilder();

            remoteIntentMessage.setTargetUserId(member.getLastReceivedMemberData().getUserId());
            remoteIntentMessage.setIntent(remoteIntent);

            message.setType(MESSAGE_TYPE_TEAM_sendRemoteIntent);
            message.setData(remoteIntentMessage.build().toByteString());

            teamService.sendRemoteMessage(message.build().toByteArray());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * データ受信用のReceiverを取得する
     *
     * @return
     */
    public AcesProtocolReceiver getProtocolReceiver() {
        return protocolReceiver;
    }

    public TeamProtocolReceiver getTeamProtocolReceiver() {
        return teamProtocolReceiver;
    }
}
