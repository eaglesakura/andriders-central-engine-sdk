package com.eaglesakura.andriders.central;

import android.content.Context;

import com.eaglesakura.andriders.central.event.TeamMemberDataHandler;
import com.eaglesakura.andriders.protocol.TeamProtocol;

import java.util.HashSet;
import java.util.Set;

/**
 * チームメンバー情報を管理する
 */
public class TeamMemberReceiver extends AcesProtocolReceiver {
    /**
     * チームメンバーの情報
     */
    private TeamProtocol.TeamMember lastReceivedMemberData;

    private Set<TeamMemberDataHandler> memberMasterHandlers = new HashSet<>();

    public TeamMemberReceiver(Context context) {
        super(context);
    }

    public void addMemberDataHandler(TeamMemberDataHandler handler) {
        this.memberMasterHandlers.add(handler);
    }

    public void removeMemberDataHandler(TeamMemberDataHandler handler) {
        this.memberMasterHandlers.remove(handler);
    }

    public TeamProtocol.TeamMember getLastReceivedMemberData() {
        return lastReceivedMemberData;
    }

    /**
     * このユーザーがACEsを起動していたらtrue
     *
     * @return
     */
    public boolean isAcesBooted() {
        return lastReceivedMemberData != null && lastReceivedMemberData.hasMasterPayload();
    }

    public synchronized void onReceivedMasterPayload(TeamProtocol.TeamMember master) throws Exception {
        this.lastReceivedMemberData = master;

        // マスターを受け取ったことを通知する
        for (TeamMemberDataHandler handler : memberMasterHandlers) {
            handler.onMasterPayloadReceived(this, master);
        }

        /**
         * このメンバーのセントラルデータも更新する
         */
        if (lastReceivedMemberData.hasMasterPayload()) {
            super.onReceivedMasterPayload(lastReceivedMemberData.getMasterPayload().toByteArray());
        }
    }
}
