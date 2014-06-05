package com.eaglesakura.andriders.central;

import java.util.ArrayList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.eaglesakura.andriders.ble.AceLog;
import com.eaglesakura.andriders.protocol.AceConstants.SensorType;
import com.eaglesakura.andriders.protocol.AcesProtocol;
import com.eaglesakura.andriders.protocol.AcesProtocol.SensorPayload;
import com.eaglesakura.andriders.protocol.SensorProtocol.RawCadence;
import com.eaglesakura.andriders.protocol.SensorProtocol.RawHeartrate;
import com.google.protobuf.ByteString;

public class AcesProtocolReceiver {
    /**
     * Intent経由で送られる場合のデータマスター
     */
    static final String INTENT_EXTRA_MASTER = "INTENT_EXTRA_MASTER";

    /**
     * 送受信用Action
     */
    static final String INTENT_ACTION = "com.eaglesakura.andriders.ACTION_CENTRAL_DATA";

    /**
     * 送受信用カテゴリ
     */
    static final String INTENT_CATEGORY = "com.eaglesakura.andriders.CATEGORY_CENTRAL_DATA";

    /**
     * 他のアプリへデータを投げる
     * @param payload
     * @return
     */
    static Intent newBroadcastIntent() {
        Intent intent = new Intent(INTENT_ACTION);
        intent.addCategory(INTENT_CATEGORY);
        return intent;
    }

    private final Context context;

    private boolean connected = false;

    /**
     * データ取得クラスを構築する
     * @param context
     */
    public AcesProtocolReceiver(Context context) {
        this.context = context.getApplicationContext();
    }

    /**
     * サービスへ接続する
     */
    public void connect() {
        if (!connected) {
            IntentFilter filter = new IntentFilter(INTENT_ACTION);
            filter.addCategory(INTENT_CATEGORY);
            context.registerReceiver(receiver, filter);
            connected = true;
        }
    }

    /**
     * サービスから切断する
     */
    public void disconnect() {
        if (connected) {
            context.unregisterReceiver(receiver);
            connected = false;
        }
    }

    /**
     * 接続済みの場合true
     * @return
     */
    public boolean isConnected() {
        return connected;
    }

    /**
     * セントラルから情報を受けとった
     */
    private final List<CentralDataListener> centralListeners = new ArrayList<AcesProtocolReceiver.CentralDataListener>();

    /**
     * 心拍
     */
    private final List<HeartrateListener> heartrateListeners = new ArrayList<AcesProtocolReceiver.HeartrateListener>();

    /**
     * ケイデンス受信
     */
    private final List<CadenceListener> cadenceListeners = new ArrayList<AcesProtocolReceiver.CadenceListener>();

    /**
     * 心拍を受け取った
     * @param payload
     * @throws Exception
     */
    private void onHeartrateReceived(ByteString payload) throws Exception {
        RawHeartrate heartrate = RawHeartrate.parseFrom(payload);
        for (HeartrateListener listener : heartrateListeners) {
            listener.onHeartrateReceived(this, heartrate);
        }
    }

    /**
     * ケイデンスを受け取った
     * @param payload
     * @throws Exception
     */
    private void onCadenceReceived(ByteString payload) throws Exception {
        RawCadence cadence = RawCadence.parseFrom(payload);
        for (CadenceListener listener : cadenceListeners) {
            listener.onCadenceReceived(this, cadence);
        }
    }

    /**
     * 最上位ペイロードを受け取った
     * @param master
     */
    void onReceivedMasterPayload(byte[] masterbuffer) throws Exception {
        AcesProtocol.MasterPayload master = AcesProtocol.MasterPayload.parseFrom(masterbuffer);

        // 正常なマスターデータを受け取った
        for (CentralDataListener listener : centralListeners) {
            listener.onMasterPayloadReceived(this, masterbuffer, master);
        }

        // 各種データを設定する
        {
            List<SensorPayload> payloads = master.getSensorPayloadsList();
            for (SensorPayload payload : payloads) {
                try {
                    final SensorType serviceType = payload.getType();
                    //  サービスの種類によってハンドリングを変更する
                    switch (serviceType) {
                        case HeartrateMonitor:
                            // ハートレート
                            onHeartrateReceived(payload.getBuffer());
                            break;

                        case CadenceSensor:
                            // ケイデンス
                            onCadenceReceived(payload.getBuffer());
                            break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                    AceLog.d(e);
                }
            }
        }
    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //            BleLog.d("received :: " + intent.getExtras());
            try {
                byte[] masterbuffer = intent.getByteArrayExtra(INTENT_EXTRA_MASTER);
                onReceivedMasterPayload(masterbuffer);
            } catch (Exception e) {
                AceLog.d(e);
            }
        }
    };

    /**
     * セントラル用のリスナを追加する
     * @param listener
     */
    public void addCentralDataListener(CentralDataListener listener) {
        centralListeners.remove(listener);
        centralListeners.add(listener);
    }

    /**
     * 心拍リスナーを追加する
     * @param listener
     */
    public void addHeartrateListener(HeartrateListener listener) {
        heartrateListeners.remove(listener);
        heartrateListeners.add(listener);
    }

    /**
     * ケイデンスリスナを追加する
     * @param listener
     */
    public void addCadenceListener(CadenceListener listener) {
        cadenceListeners.remove(listener);
        cadenceListeners.add(listener);
    }

    /**
     * セントラルデータ用リスナを削除する
     * @param listener
     */
    public void removeCentralDataListener(CentralDataListener listener) {
        centralListeners.remove(listener);
    }

    /**
     * 心拍リスナーを削除する
     * @param listener
     */
    public void removeHeartrateListener(HeartrateListener listener) {
        heartrateListeners.remove(listener);
    }

    /**
     * ケイデンスリスナを削除する
     * @param listener
     */
    public void removeCadenceListener(CadenceListener listener) {
        cadenceListeners.remove(listener);
    }

    /**
     * ケイデンスデータを受け取った
     */
    public interface CadenceListener {
        void onCadenceReceived(AcesProtocolReceiver receiver, RawCadence cadence);
    }

    /**
     * 心拍データを受け取った
     */
    public interface HeartrateListener {
        void onHeartrateReceived(AcesProtocolReceiver receiver, RawHeartrate heartrate);
    }

    /**
     * メインのステータス等のチェックを行う
     */
    public interface CentralDataListener {
        /**
         * マスターデータを受け取った
         * @param buffer 受け取ったデータ
         * @param master すべてのデータを含んだペイロード
         */
        void onMasterPayloadReceived(AcesProtocolReceiver receiver, byte[] buffer, AcesProtocol.MasterPayload master);
    }
}
