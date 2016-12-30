package com.eaglesakura.andriders.central.event;

import com.eaglesakura.andriders.central.CentralDataReceiver;
import com.eaglesakura.andriders.serialize.AcesProtocol.MasterPayload;
import com.eaglesakura.andriders.serialize.GeoProtocol;

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
    public void onMasterPayloadReceived(CentralDataReceiver receiver, byte[] buffer, MasterPayload master) {
    }

    /**
     * マスターデータの解析に失敗した
     *
     * @param receiver 受信したレシーバ
     */
    public void onMasterPayloadParseFiled(CentralDataReceiver receiver, byte[] buffer) {
    }

    /**
     * 位置情報を受け取った
     *
     * @param receiver 受信したレシーバ
     * @param master   マスターデータ
     * @param geo      位置情報
     */
    public void onGeoStatusReceived(CentralDataReceiver receiver, MasterPayload master, GeoProtocol.GeoPayload geo) {
    }

    /**
     * ジオハッシュの更新が行われた
     *
     * @param receiver  受信したレシーバ
     * @param master    マスターデータ
     * @param oldStatus 古い位置情報
     * @param newStatus 新しい位置情報
     */
    public void onGeohashUpdated(CentralDataReceiver receiver, MasterPayload master, GeoProtocol.GeoPayload oldStatus, GeoProtocol.GeoPayload newStatus) {
    }

    /**
     * 周辺情報を受け取った
     *
     * @param receiver  受信したレシーバ
     * @param master    マスターデータ
     * @param geography 周辺データ
     */
    public void onGeographyReceived(CentralDataReceiver receiver, MasterPayload master, GeoProtocol.GeographyPayload geography) {
    }

    /**
     * 周辺情報が更新された
     *
     * @param receiver     受信したレシーバ
     * @param master       マスターデータ
     * @param oldGeography 古い周辺データ
     * @param newGeography 新しい周辺データ
     */
    public void onGeographyUpdated(CentralDataReceiver receiver, MasterPayload master, GeoProtocol.GeographyPayload oldGeography, GeoProtocol.GeographyPayload newGeography) {
    }

}
