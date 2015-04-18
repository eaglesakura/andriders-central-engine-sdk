package com.eaglesakura.andriders.central;

import android.content.Context;

import com.eaglesakura.andriders.central.event.TeamMemberDataHandler;
import com.eaglesakura.andriders.protocol.TeamProtocol;

import java.util.HashSet;
import java.util.Set;

/**
 * チームメンバー情報を管理する。
 * <br>
 * このレシーバはSDK利用者がnewすることはなく、SDK内部で生成される。
 */
public class TeamMemberReceiver extends AcesProtocolReceiver {
    /**
     * チームメンバーの情報
     */
    private TeamProtocol.TeamMember lastReceivedMemberData;

    private TeamProtocol.TeamMember.Status connectedStatus;

    private Set<TeamMemberDataHandler> memberMasterHandlers = new HashSet<>();

    TeamMemberReceiver(Context context) {
        super(context);
        setCheckSelfPackage(false);
    }

    /**
     * メンバーデータのハンドラを追加する
     *
     * @param handler 対象ハンドラ
     */
    public void addMemberDataHandler(TeamMemberDataHandler handler) {
        this.memberMasterHandlers.add(handler);
    }

    /**
     * メンバーデータのハンドラを削除する
     *
     * @param handler 対象ハンドラ
     */
    public void removeMemberDataHandler(TeamMemberDataHandler handler) {
        this.memberMasterHandlers.remove(handler);
    }

    /**
     * 最後に受け取ったメンバーデータを取得する
     */
    public TeamProtocol.TeamMember getLastReceivedMemberData() {
        return lastReceivedMemberData;
    }

    /**
     * このユーザーがACEsを起動していたらtrueを返却する。
     */
    public boolean isAcesBooted() {
        return lastReceivedMemberData != null && lastReceivedMemberData.hasMasterPayload();
    }

    /**
     * メンバーのMasterPayloadを受け取った際のハンドリングを行う
     *
     * @param master 　MasterPayload
     *
     * @throws Exception
     */
    public synchronized void onReceivedMasterPayload(TeamProtocol.TeamMember master) throws Exception {
        this.lastReceivedMemberData = master;

        // マスターを受け取ったことを通知する
        for (TeamMemberDataHandler handler : memberMasterHandlers) {
            handler.onMasterPayloadReceived(this, master);
        }

        if (connectedStatus != master.getStatus()) {
            // ステータスが変化したことを通知する
            for (TeamMemberDataHandler handler : memberMasterHandlers) {
                handler.onConnectedStateChanged(this, master);
            }
            connectedStatus = master.getStatus();
        }

        /**
         * このメンバーのセントラルデータも更新する
         */
        if (lastReceivedMemberData.hasMasterPayload()) {
            super.onReceivedMasterPayload(lastReceivedMemberData.getMasterPayload().toByteArray());
        }
    }
}
