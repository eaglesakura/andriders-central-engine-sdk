package com.eaglesakura.andriders.central;

import com.eaglesakura.andriders.central.event.TeamDataHandler;
import com.eaglesakura.andriders.serialize.TeamProtocol;
import com.eaglesakura.util.LogUtil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Team Andriders Central Engine Service(TACEs)のデータを受信・ハンドリングする。
 */
@Deprecated
public class TeamProtocolReceiver {
    /**
     * 送受信用Action
     */
    static final String INTENT_ACTION = "com.eaglesakura.andriders.ACTION_CENTRAL_TEAM";

    /**
     *
     */
    static final String INTENT_CATEGORY = CentralDataReceiver.INTENT_CATEGORY;

    /**
     * Intent経由で送られる場合のデータマスター
     */
    static final String INTENT_EXTRA_MASTER = CentralDataReceiver.INTENT_EXTRA_MASTER;

    /**
     * 他のアプリへデータを投げる
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

    private TeamProtocol.TeamPayload lastReceivedTeamPayload;

    public TeamProtocolReceiver(Context context) {
        this.context = context;
    }

    /**
     * TACEsへ接続する。
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
     * TACEsから切断する。
     */
    public void disconnect() {
        if (connected) {
            context.unregisterReceiver(receiver);
            connected = false;
        }
    }

    public TeamProtocol.TeamPayload getLastReceivedTeamPayload() {
        return lastReceivedTeamPayload;
    }

    /**
     * メンバー一覧を取得する。
     * <br>
     * 招待後、まだroomに入っていないメンバーは取得できない。
     *
     * @return IDによってソートされたメンバー一覧
     */
    public List<TeamMemberReceiver> listMembers() {
        synchronized (lock) {
            List<TeamMemberReceiver> list = new ArrayList<>(memberReceivers.values());
            sortById(list);
            return list;
        }
    }

    /**
     * メンバーIDを指定してReceiverを取得する
     *
     * @param memberId 対象メンバーID
     * @return メンバー用Receiver
     */
    public TeamMemberReceiver getMemberReceiver(String memberId) {
        synchronized (lock) {
            return memberReceivers.get(memberId);
        }
    }

    /**
     * チーム自体のデータのハンドラを追加する。
     *
     * @param handler 対象ハンドラ
     */
    public void addTeamDataHandler(TeamDataHandler handler) {
        teamDataHandlers.add(handler);
    }

    /**
     * チームデータのハンドラを削除する
     *
     * @param handler 対象ハンドラ
     */
    public void removeTeamDataHandler(TeamDataHandler handler) {
        teamDataHandlers.remove(handler);
    }

    /**
     * マスター情報を受け取った場合の処理を行う。
     * <br>
     * 自動的に処理が行われるため、基本的にこのメソッドを明示的に呼び出す必要はない。
     *
     * @param master 受信した{@link com.eaglesakura.andriders.serialize.TeamProtocol.TeamPayload}の情報
     */
    public void onReceivedMasterPayload(byte[] master) throws Exception {
        synchronized (lock) {
            lastReceivedTeamPayload = TeamProtocol.TeamPayload.parseFrom(master);
            // 新しいメンバーを受信したことを通知する
            for (TeamDataHandler handler : teamDataHandlers) {
                handler.onMasterPayloadReceived(this, master, lastReceivedTeamPayload);
            }

            // メンバー情報を取り出す
            List<TeamProtocol.TeamMember> members = lastReceivedTeamPayload.getMembersList();
            for (TeamProtocol.TeamMember member : members) {
                TeamMemberReceiver memberReceiver = getMemberReceiver(member.getUserId());
                // メンバーデータを追加
                if (memberReceiver == null) {
                    // 初めての相手が来た
                    memberReceiver = new TeamMemberReceiver(context);
                    // MasterPayloadを受信と各々処理をさせる
                    memberReceiver.onReceivedMasterPayload(member);

                    // 追加する
                    memberReceivers.put(member.getUserId(), memberReceiver);

                    // 新しいメンバーを受信したことを通知する
                    for (TeamDataHandler handler : teamDataHandlers) {
                        handler.onJoinTeamMember(this, lastReceivedTeamPayload, member.getUserId(), memberReceiver);
                    }
                } else {
                    // MasterPayloadを受信と各々処理をさせる
                    memberReceiver.onReceivedMasterPayload(member);
                }
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
                LogUtil.d(e);
            }
        }
    };

    /**
     * メンバーをIDに従ってソートする
     *
     * @param receivers 全レシーバ
     * @return ソートされたreceivers
     */
    public static List<TeamMemberReceiver> sortById(List<TeamMemberReceiver> receivers) {
        Collections.sort(receivers, new Comparator<TeamMemberReceiver>() {
            @Override
            public int compare(TeamMemberReceiver lhs, TeamMemberReceiver rhs) {
                String lhsData = lhs.getLastReceivedMemberData().getUserId();
                String rhsData = rhs.getLastReceivedMemberData().getUserId();
                return lhsData.compareTo(rhsData);
            }
        });
        return receivers;
    }
}
