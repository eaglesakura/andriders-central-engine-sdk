package com.eaglesakura.andriders.central.event;

import com.eaglesakura.andriders.central.TeamMemberReceiver;
import com.eaglesakura.andriders.protocol.TeamProtocol;

/**
 * チームメンバーの情報を受け取った際のハンドリング
 */
public abstract class TeamMemberDataHandler {
    /**
     * マスターデータを受け取った
     *
     * @param receiver レシーバ
     * @param master   すべてのデータを含んだペイロード
     */
    public void onMasterPayloadReceived(TeamMemberReceiver receiver, TeamProtocol.TeamMember master) {
    }

    /**
     * データの接続状態が変化したら呼び出される
     *
     * @param receiver
     * @param master
     */
    public void onConnectedStateChanged(TeamMemberReceiver receiver, TeamProtocol.TeamMember master) {
    }
}
