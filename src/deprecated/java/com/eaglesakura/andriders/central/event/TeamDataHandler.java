package com.eaglesakura.andriders.central.event;

import com.eaglesakura.andriders.central.TeamMemberReceiver;
import com.eaglesakura.andriders.central.TeamProtocolReceiver;
import com.eaglesakura.andriders.serialize.TeamProtocol;

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
     * @param receiver             受信したレシーバ
     * @param master               マスターデータ
     * @param memberId             参加したメンバーのID
     * @param memberMasterReceiver 受信したチームレシーバ
     */
    public void onJoinTeamMember(TeamProtocolReceiver receiver, TeamProtocol.TeamPayload master, String memberId, TeamMemberReceiver memberMasterReceiver) {
    }
}
