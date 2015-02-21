package com.eaglesakura.andriders.central.event;

import com.eaglesakura.andriders.central.TeamMemberReceiver;
import com.eaglesakura.andriders.protocol.TeamProtocol;

/**
 * チームメンバーの情報を受け取った際のハンドリング
 */
public class TeamMemberDataHandler {
    /**
     * マスターデータを受け取った
     *
     * @param receiver レシーバ
     * @param master   すべてのデータを含んだペイロード
     */
    public void onMasterPayloadReceived(TeamMemberReceiver receiver, TeamProtocol.TeamMember master) {
    }
}
