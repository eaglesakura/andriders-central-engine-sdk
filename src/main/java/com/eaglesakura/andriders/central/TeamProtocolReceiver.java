package com.eaglesakura.andriders.central;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.eaglesakura.andriders.AceLog;
import com.eaglesakura.andriders.central.event.TeamDataHandler;
import com.eaglesakura.andriders.protocol.TeamProtocol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * チーム情報を受け取るためのレシーバ
 */
public class TeamProtocolReceiver {
    /**
     * 送受信用Action
     */
    static final String INTENT_ACTION = "com.eaglesakura.andriders.ACTION_CENTRAL_TEAM";

    /**
     *
     */
    static final String INTENT_CATEGORY = AcesProtocolReceiver.INTENT_CATEGORY;

    /**
     * Intent経由で送られる場合のデータマスター
     */
    static final String INTENT_EXTRA_MASTER = AcesProtocolReceiver.INTENT_EXTRA_MASTER;

    /**
     * 他のアプリへデータを投げる
     *
     * @return
     */
    static Intent newBroadcastIntent() {
        Intent intent = new Intent(INTENT_ACTION);
        intent.addCategory(INTENT_CATEGORY);
        return intent;
    }

    private final Context context;

    private boolean connected;

    private Object lock = new Object();

    private Map<String, TeamMemberReceiver> memberReceivers = new HashMap<>();

    private Set<TeamDataHandler> teamDataHandlers = new HashSet<>();

    public TeamProtocolReceiver(Context context) {
        this.context = context;
    }

    /**
     * サービスへ接続する
     */
    public void connect() {
        if (!connected) {
            IntentFilter filter = new IntentFilter(INTENT_ACTION);
            filter.addCategory(INTENT_CATEGORY);
            context.registerReceiver(receiver, filter);
            connected = true;
        }
    }

    /**
     * サービスから切断する
     */
    public void disconnect() {
        if (connected) {
            context.unregisterReceiver(receiver);
            connected = false;
        }
    }

    /**
     * メンバー一覧を取得する
     *
     * @return
     */
    public List<TeamMemberReceiver> listMembers() {
        synchronized (lock) {
            return new ArrayList<>(memberReceivers.values());
        }
    }

    public TeamMemberReceiver getMemberReceiver(String memberId) {
        synchronized (lock) {
            return memberReceivers.get(memberId);
        }
    }

    public void addTeamDataHandler(TeamDataHandler handler) {
        teamDataHandlers.add(handler);
    }

    public void removeTeamDataHandler(TeamDataHandler handler) {
        teamDataHandlers.remove(handler);
    }

    /**
     * マスター情報を受け取った場合の処理
     *
     * @param master
     */
    public void onReceivedMasterPayload(byte[] master) throws Exception {
        synchronized (lock) {
            TeamProtocol.TeamPayload teamPayload = TeamProtocol.TeamPayload.parseFrom(master);

            // メンバー情報を取り出す
            List<TeamProtocol.TeamMember> members = teamPayload.getMembersList();
            for (TeamProtocol.TeamMember member : members) {
                TeamMemberReceiver memberReceiver = getMemberReceiver(member.getUserId());
                // メンバーデータを追加
                if (memberReceiver == null) {
                    // 初めての相手が来た
                    memberReceiver = new TeamMemberReceiver(context);

                    // 追加する
                    memberReceivers.put(member.getUserId(), memberReceiver);

                    // 新しいメンバーを受信したことを通知する
                    for (TeamDataHandler handler : teamDataHandlers) {
                        handler.onJoinTeamMember(this, teamPayload, member.getUserId(), memberReceiver);
                    }
                }

                // MasterPayloadを受信と各々処理をさせる
                memberReceiver.onReceivedMasterPayload(member);
            }
        }
    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //            BleLog.d("received :: " + intent.getExtras());
            try {
                byte[] masterbuffer = intent.getByteArrayExtra(INTENT_EXTRA_MASTER);
                onReceivedMasterPayload(masterbuffer);
            } catch (Exception e) {
                AceLog.d(e);
            }
        }
    };
}
