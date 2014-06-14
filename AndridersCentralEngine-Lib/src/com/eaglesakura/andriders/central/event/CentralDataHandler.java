package com.eaglesakura.andriders.central.event;

import com.eaglesakura.andriders.central.AcesProtocolReceiver;
import com.eaglesakura.andriders.protocol.AcesProtocol.MasterPayload;

/**
 * ACEsの情報ハンドリングを行う
 */
public class CentralDataHandler {
    /**
     * マスターデータを受け取った
     * @param buffer 受け取ったデータ
     * @param master すべてのデータを含んだペイロード
     */
    public void onMasterPayloadReceived(AcesProtocolReceiver receiver, byte[] buffer, MasterPayload master) {
        
    }
}
