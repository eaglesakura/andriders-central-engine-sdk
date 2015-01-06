package com.eaglesakura.andriders.central.event;

import com.eaglesakura.andriders.central.AcesProtocolReceiver;
import com.eaglesakura.andriders.protocol.AcesProtocol.MasterPayload;
import com.eaglesakura.andriders.protocol.GeoProtocol;

/**
 * ACEsの情報ハンドリングを行う
 */
public class CentralDataHandler {
    /**
     * マスターデータを受け取った
     *
     * @param buffer 受け取ったデータ
     * @param master すべてのデータを含んだペイロード
     */
    public void onMasterPayloadReceived(AcesProtocolReceiver receiver, byte[] buffer, MasterPayload master) {

    }

    /**
     * 位置情報を受け取った
     *
     * @param receiver
     * @param master
     * @param geo
     */
    public void onGeoStatusReceived(AcesProtocolReceiver receiver, MasterPayload master, GeoProtocol.GeoPayload geo) {

    }

    /**
     * ジオハッシュの更新が行われた
     *
     * @param receiver
     * @param master
     * @param oldStatus
     * @param newStatus
     */
    public void onGeohashUpdated(AcesProtocolReceiver receiver, MasterPayload master, GeoProtocol.GeoPayload oldStatus, GeoProtocol.GeoPayload newStatus) {
    }
}
