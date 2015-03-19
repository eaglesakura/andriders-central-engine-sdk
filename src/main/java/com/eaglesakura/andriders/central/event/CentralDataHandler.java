package com.eaglesakura.andriders.central.event;

import com.eaglesakura.andriders.central.AcesProtocolReceiver;
import com.eaglesakura.andriders.protocol.AcesProtocol.MasterPayload;
import com.eaglesakura.andriders.protocol.GeoProtocol;

/**
 * ACEsの情報ハンドリングを行う
 */
public abstract class CentralDataHandler {
    /**
     * マスターデータを受け取った
     *
     * @param buffer 受け取ったデータ
     * @param master すべてのデータを含んだペイロード
     */
    public void onMasterPayloadReceived(AcesProtocolReceiver receiver, byte[] buffer, MasterPayload master) {
    }

    /**
     * マスターデータの解析に失敗した
     *
     * @param receiver
     */
    public void onMasterPayloadParseFiled(AcesProtocolReceiver receiver, byte[] buffer) {
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

    /**
     * 周辺情報を受け取った
     *
     * @param receiver
     * @param master
     * @param geography
     */
    public void onGeographyReceived(AcesProtocolReceiver receiver, MasterPayload master, GeoProtocol.GeographyPayload geography) {
    }

    /**
     * 周辺情報が更新された
     *
     * @param receiver
     * @param master
     * @param oldGeography
     * @param newGeography
     */
    public void onGeographyUpdated(AcesProtocolReceiver receiver, MasterPayload master, GeoProtocol.GeographyPayload oldGeography, GeoProtocol.GeographyPayload newGeography) {
    }

}
