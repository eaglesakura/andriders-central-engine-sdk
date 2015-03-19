package com.eaglesakura.andriders.central.event;

import com.eaglesakura.andriders.central.TeamMemberReceiver;
import com.eaglesakura.andriders.central.TeamProtocolReceiver;
import com.eaglesakura.andriders.protocol.TeamProtocol;

/**
 * ACEsの情報ハンドリングを行う
 */
public abstract class TeamDataHandler {
    /**
     * マスターデータを受け取った
     *
     * @param buffer 受け取ったデータ
     * @param master すべてのデータを含んだペイロード
     */
    public void onMasterPayloadReceived(TeamProtocolReceiver receiver, byte[] buffer, TeamProtocol.TeamPayload master) {

    }

    /**
     * チームメンバーの参加を検知した
     *
     * @param receiver
     * @param master
     * @param memberId
     * @param memberMasterReceiver
     */
    public void onJoinTeamMember(TeamProtocolReceiver receiver, TeamProtocol.TeamPayload master, String memberId, TeamMemberReceiver memberMasterReceiver) {
    }
}
