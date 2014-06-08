package com.eaglesakura.andriders.central;

import java.util.ArrayList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.eaglesakura.andriders.AceLog;
import com.eaglesakura.andriders.protocol.AcesProtocol;
import com.eaglesakura.andriders.protocol.AcesProtocol.MasterPayload;
import com.eaglesakura.andriders.protocol.CommandProtocol.CommandPayload;
import com.eaglesakura.andriders.protocol.SensorProtocol.RawCadence;
import com.eaglesakura.andriders.protocol.SensorProtocol.RawHeartrate;
import com.eaglesakura.andriders.protocol.SensorProtocol.SensorPayload;
import com.eaglesakura.andriders.protocol.SensorProtocol.SensorType;
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

    /**
     * 自分自身のpackage名
     */
    private final String selfPackageName;

    private boolean checkTargetPackage = true;

    /**
     * broadcastに接続済みであればtrue
     */
    private boolean connected = false;

    /**
     * データ取得クラスを構築する
     * @param context
     */
    public AcesProtocolReceiver(Context context) {
        this.context = context.getApplicationContext();
        this.selfPackageName = context.getPackageName();
    }

    /**
     * 送信対象のpackageチェックを行う
     * @param checkTargetPackage
     */
    public void setCheckTargetPackage(boolean checkTargetPackage) {
        this.checkTargetPackage = checkTargetPackage;
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
     * コマンド
     */
    private final List<CommandListener> commandListeners = new ArrayList<AcesProtocolReceiver.CommandListener>();

    /**
     * 心拍を受け取った
     * @param payload
     * @throws Exception
     */
    private void onHeartrateReceived(MasterPayload master, ByteString payload) throws Exception {
        RawHeartrate heartrate = RawHeartrate.parseFrom(payload);
        for (HeartrateListener listener : heartrateListeners) {
            listener.onHeartrateReceived(this, master, heartrate);
        }
    }

    /**
     * ケイデンスを受け取った
     * @param payload
     * @throws Exception
     */
    private void onCadenceReceived(MasterPayload master, ByteString payload) throws Exception {
        RawCadence cadence = RawCadence.parseFrom(payload);
        for (CadenceListener listener : cadenceListeners) {
            listener.onCadenceReceived(this, master, cadence);
        }
    }

    /**
     * 最上位ペイロードを受け取った
     * @param master
     */
    public void onReceivedMasterPayload(byte[] masterbuffer) throws Exception {
        AcesProtocol.MasterPayload master = AcesProtocol.MasterPayload.parseFrom(masterbuffer);
        final String targetPackage = master.hasTargetPackage() ? master.getTargetPackage() : null;

        // senderが自分であれば反応しない
        // ただし、自分自身が対象である場合は何もしない
        if (selfPackageName.equals(master.getSenderPackage()) && !master.getSenderPackage().equals(targetPackage) && checkTargetPackage) {
            //            Log.i("ACES", "error sender :: " + master.getSenderPackage());
            //            Log.i("ACES", "error target :: " + targetPackage);
            return;
        }

        // target check
        {
            if (targetPackage != null) {
                // 自分自身が対象でないなら
                if (!targetPackage.equals(selfPackageName) && checkTargetPackage) {
                    // payloadの送信対象じゃないから、何もしない
                    //                    Log.i("ACES", "target error sender :: " + master.getSenderPackage());
                    //                    Log.i("ACES", "target error target :: " + targetPackage);
                    return;
                }
            }
        }

        // 正常なマスターデータを受け取った
        for (CentralDataListener listener : centralListeners) {
            listener.onMasterPayloadReceived(this, masterbuffer, master);
        }

        // 各種データを設定する
        if (master.hasCentralStatus()) {
            // セントラルステータスを持っていなければcommand onlyとかんがえる

            List<SensorPayload> payloads = master.getSensorPayloadsList();
            for (SensorPayload payload : payloads) {
                try {
                    final SensorType serviceType = payload.getType();
                    //  サービスの種類によってハンドリングを変更する
                    switch (serviceType) {
                        case HeartrateMonitor:
                            // ハートレート
                            onHeartrateReceived(master, payload.getBuffer());
                            break;

                        case CadenceSensor:
                            // ケイデンス
                            onCadenceReceived(master, payload.getBuffer());
                            break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                    AceLog.d(e);
                }
            }
        }

        // コマンドを解析する
        {
            List<CommandPayload> payloadsList = master.getCommandPayloadsList();
            for (CommandPayload cmd : payloadsList) {
                for (CommandListener listener : commandListeners) {
                    listener.onCommandReceived(AcesProtocolReceiver.this, master, cmd);
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
     * コマンドリスナを追加する
     * @param listener
     */
    public void addCommandListener(CommandListener listener) {
        commandListeners.remove(listener);
        commandListeners.add(listener);
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
        void onCadenceReceived(AcesProtocolReceiver receiver, MasterPayload master, RawCadence cadence);
    }

    /**
     * 心拍データを受け取った
     */
    public interface HeartrateListener {
        void onHeartrateReceived(AcesProtocolReceiver receiver, MasterPayload master, RawHeartrate heartrate);
    }

    /**
     * コマンドを受け取った
     */
    public interface CommandListener {
        void onCommandReceived(AcesProtocolReceiver receiver, MasterPayload payload, CommandPayload command);
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
        void onMasterPayloadReceived(AcesProtocolReceiver receiver, byte[] buffer, MasterPayload master);
    }
}
