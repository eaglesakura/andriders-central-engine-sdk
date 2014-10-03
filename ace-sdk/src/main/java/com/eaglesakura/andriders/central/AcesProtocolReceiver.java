package com.eaglesakura.andriders.central;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.eaglesakura.andriders.AceLog;
import com.eaglesakura.andriders.central.event.ActivityEventHandler;
import com.eaglesakura.andriders.central.event.CentralDataHandler;
import com.eaglesakura.andriders.central.event.CommandEventHandler;
import com.eaglesakura.andriders.central.event.SensorEventHandler;
import com.eaglesakura.andriders.command.CommandKey;
import com.eaglesakura.andriders.notification.NotificationData;
import com.eaglesakura.andriders.protocol.AcesProtocol;
import com.eaglesakura.andriders.protocol.AcesProtocol.MasterPayload;
import com.eaglesakura.andriders.protocol.ActivityProtocol.ActivityPayload;
import com.eaglesakura.andriders.protocol.ActivityProtocol.MaxSpeedActivity;
import com.eaglesakura.andriders.protocol.CommandProtocol;
import com.eaglesakura.andriders.protocol.CommandProtocol.CommandPayload;
import com.eaglesakura.andriders.protocol.CommandProtocol.CommandType;
import com.eaglesakura.andriders.protocol.CommandProtocol.TriggerPayload;
import com.eaglesakura.andriders.protocol.GeoProtocol;
import com.eaglesakura.andriders.protocol.SensorProtocol.RawCadence;
import com.eaglesakura.andriders.protocol.SensorProtocol.RawHeartrate;
import com.eaglesakura.andriders.protocol.SensorProtocol.RawSpeed;
import com.eaglesakura.andriders.protocol.SensorProtocol.SensorPayload;
import com.eaglesakura.andriders.protocol.SensorProtocol.SensorType;
import com.eaglesakura.android.geo.GeohashGroup;
import com.eaglesakura.util.LogUtil;
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
     *
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

    /**
     * 自分自身のパッケージが送ったメッセージをハンドリングする
     */
    private boolean checkSelfPackage = true;

    /**
     * 対象パッケージを無視してハンドリングする
     */
    private boolean checkTargetPackage = true;

    /**
     * broadcastに接続済みであればtrue
     */
    private boolean connected = false;

    /**
     * 最後に受信したハートレート
     */
    private RawHeartrate lastReceivedHartrate;

    /**
     * 最後に受信したケイデンス
     */
    private RawCadence lastReceivedCadence;

    /**
     * 最後に受信したスピード
     */
    private RawSpeed lastReceivedSpeed;

    /**
     * ジオハッシュ管理
     */
    private GeohashGroup geoGroup = new GeohashGroup();

    /**
     * 最後に受け取った位置情報
     */
    private GeoProtocol.GeoPayload lastReceivedGeo;

    /**
     * 古いジオハッシュで最後に受け取った位置情報
     */
    private GeoProtocol.GeoPayload beforeHashGeo;

    /**
     * データ取得クラスを構築する
     *
     * @param context
     */
    public AcesProtocolReceiver(Context context) {
        this.context = context.getApplicationContext();
        this.selfPackageName = context.getPackageName();
    }

    /**
     * ジオハッシュの文字数を指定する。
     * <p/>
     * 文字数は長いほど狭い範囲でしか扱えない。
     *
     * @param len
     */
    public void setGeohashLength(int len) {
        geoGroup.setGeohashLength(len);
    }

    /**
     * 送信元のpackageチェックを行う
     * 自分自身が送ったブロードキャストで自分自身でハンドリングしたい場合はfalseを指定する
     *
     * @param checkSelfPackage
     */
    public void setCheckSelfPackage(boolean checkSelfPackage) {
        this.checkSelfPackage = checkSelfPackage;
    }

    /**
     * 送信対象のpackageチェックを行う
     * 自分以外のpackageに送られたブロードキャストに反応したい場合はfalseを指定する。
     *
     * @param checkTargetPackage
     */
    public void setCheckTargetPackage(boolean checkTargetPackage) {
        this.checkTargetPackage = checkTargetPackage;
    }

    /**
     * 最後に受信したケイデンスを取得する
     *
     * @return
     */
    public RawCadence getLastReceivedCadence() {
        return lastReceivedCadence;
    }

    /**
     * 最後に受信した心拍を取得する
     *
     * @return
     */
    public RawHeartrate getLastReceivedHartrate() {
        return lastReceivedHartrate;
    }

    /**
     * 最後に受信したスピードを取得する
     *
     * @return
     */
    public RawSpeed getLastReceivedSpeed() {
        return lastReceivedSpeed;
    }

    /**
     * 最後に受信したGPS座標を取得する
     * @return
     */
    public GeoProtocol.GeoPayload getLastReceivedGeo() {
        return lastReceivedGeo;
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
     *
     * @return
     */
    public boolean isConnected() {
        return connected;
    }

    /**
     * ACEsの情報ハンドリングを行う
     */
    private final Set<CentralDataHandler> centralHandlers = new HashSet<CentralDataHandler>();

    /**
     * センサーイベントのハンドリング
     */
    private final Set<SensorEventHandler> sensorHandlers = new HashSet<SensorEventHandler>();

    /**
     * コマンドイベントのハンドリング
     */
    private final Set<CommandEventHandler> commandHandlers = new HashSet<CommandEventHandler>();

    /**
     * ユーザー活動のハンドリングを行う
     */
    private final Set<ActivityEventHandler> activityHandlers = new HashSet<ActivityEventHandler>();

    /**
     * 心拍を受け取った
     *
     * @param payload
     * @throws Exception
     */
    private void onHeartrateReceived(MasterPayload master, ByteString payload) throws Exception {
        RawHeartrate heartrate = RawHeartrate.parseFrom(payload);
        this.lastReceivedHartrate = heartrate;

        // ハンドラに通知
        for (SensorEventHandler handler : sensorHandlers) {
            handler.onHeartrateReceived(this, master, heartrate);
        }
    }

    /**
     * ケイデンスを受け取った
     *
     * @param payload
     * @throws Exception
     */
    private void onCadenceReceived(MasterPayload master, ByteString payload) throws Exception {
        RawCadence cadence = RawCadence.parseFrom(payload);
        this.lastReceivedCadence = cadence;

        // ハンドラに通知
        for (SensorEventHandler handler : sensorHandlers) {
            handler.onCadenceReceived(this, master, cadence);
        }
    }

    /**
     * 速度を受け取った
     *
     * @param master
     * @param payload
     * @throws Exception
     */
    private void onSpeedReceived(MasterPayload master, ByteString payload) throws Exception {
        RawSpeed speed = RawSpeed.parseFrom(payload);
        this.lastReceivedSpeed = speed;

        // ハンドラに通知
        for (SensorEventHandler handler : sensorHandlers) {
            handler.onSpeedReceived(this, master, speed);
        }
    }

    /**
     * 不明なセンサーを受け取った
     *
     * @param master
     * @param payload
     * @throws Exception
     */
    private void onUnknownSensorReceived(MasterPayload master, SensorPayload payload) throws Exception {
        // ハンドラに通知
        for (SensorEventHandler handler : sensorHandlers) {
            handler.onUnknownSensorReceived(this, master, payload);
        }
    }

    /**
     * センサー系イベントのハンドリングを行う
     *
     * @param master
     * @throws Exception
     */
    protected void handleSensorEvents(MasterPayload master) throws Exception {
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
                    case SpeedSensor:
                        // スピード
                        onSpeedReceived(master, payload.getBuffer());
                        break;
                    default:
                        // 不明
                        onUnknownSensorReceived(master, payload);
                        break;
                }
            } catch (Exception e) {
                AceLog.d(e);
            }
        }
    }

    /**
     * 近接コマンドを受け取った
     *
     * @param master
     * @param trigger
     */
    private void onCommandReceived(MasterPayload master, TriggerPayload trigger) {
        CommandKey key = CommandKey.fromString(trigger.getKey());
        for (CommandEventHandler handler : commandHandlers) {
            handler.onTriggerReceived(this, master, key, trigger);
        }
    }

    /**
     * ACEsへの通知を受け取った
     *
     * @param master
     * @param requestPayload
     */
    private void onAcesNotificationCommand(MasterPayload master, CommandProtocol.NotificationRequestPayload requestPayload) {
        NotificationData notificationData = new NotificationData(requestPayload);
        for (CommandEventHandler handler : commandHandlers) {
            handler.onNotificationReceived(this, master, notificationData, requestPayload);
        }
    }

    /**
     * 不明なコマンドを受け取った
     *
     * @param master
     * @param command
     */
    private void onUnknownCommandRecieved(MasterPayload master, CommandPayload command) {
        for (CommandEventHandler handler : commandHandlers) {
            handler.onUnknownCommandReceived(this, master, command);
        }
    }

    /**
     * コマンド処理のハンドリング
     *
     * @param master
     */
    protected void handleCommandEvents(MasterPayload master) throws Exception {
        if (commandHandlers.isEmpty()) {
            // ハンドリングする相手がいない
            return;
        }

        List<CommandPayload> payloadsList = master.getCommandPayloadsList();
        for (CommandPayload cmd : payloadsList) {

            String commandType = cmd.getCommandType();
            if (CommandType.ExtensionTrigger.name().equals(commandType)) {
                // 拡張機能トリガー
                TriggerPayload trigger = TriggerPayload.parseFrom(cmd.getExtraPayload());
                onCommandReceived(master, trigger);
            } else if (CommandType.AcesNotification.name().equals(commandType)) {
                // 通知コマンド
                CommandProtocol.NotificationRequestPayload notification = CommandProtocol.NotificationRequestPayload.parseFrom(cmd.getExtraPayload());
                onAcesNotificationCommand(master, notification);
            } else {
                // 不明なコマンド
                onUnknownCommandRecieved(master, cmd);
            }
        }
    }

    /**
     * 最大速度更新情報を受信
     *
     * @param master 受信したマスターデータ
     * @param buffer 受信したデータ本体
     * @throws Exception
     */
    private void onMaxSpeedUpdateReceived(MasterPayload master, ByteString buffer) throws Exception {
        MaxSpeedActivity maxSpeed = MaxSpeedActivity.parseFrom(buffer);
        for (ActivityEventHandler handler : activityHandlers) {
            handler.onMaxSpeedActivityReceived(this, master, maxSpeed);
        }
    }

    /**
     * 不明な活動記録を受信した
     *
     * @param master   受信したマスターデータ
     * @param activity 活動データ
     * @throws Exception
     */
    private void onUnknownActivityEventReceived(MasterPayload master, ActivityPayload activity) throws Exception {
        for (ActivityEventHandler handler : activityHandlers) {
            handler.onUnknownActivityEventReceived(this, master, activity);
        }
    }

    /**
     * 活動イベントを受け取った
     *
     * @param master 受信したマスターデータ
     */
    protected void handleActivityEvents(MasterPayload master) throws Exception {
        if (activityHandlers.isEmpty()) {
            return;
        }

        List<ActivityPayload> activityPayloadsList = master.getActivityPayloadsList();
        for (ActivityPayload activity : activityPayloadsList) {
            switch (activity.getType()) {
                case MaxSpeedUpdate:
                    onMaxSpeedUpdateReceived(master, activity.getBuffer());
                    break;
                default:
                    onUnknownActivityEventReceived(master, activity);
                    break;
            }
        }
    }

    /**
     * 位置情報を受け取った
     *
     * @param master
     * @throws Exception
     */
    protected void handleGeoStatus(MasterPayload master) throws Exception {
        GeoProtocol.GeoPayload oldGeoStatus = lastReceivedGeo;
        GeoProtocol.GeoPayload newGeoStatus = master.getGeoStatus();

        // 座標を上書き
        lastReceivedGeo = newGeoStatus;

        // まずは受け取ったことを通知
        for (CentralDataHandler handler : centralHandlers) {
            handler.onGeoStatusReceived(this, master, newGeoStatus);
        }

        // ジオハッシュを更新
        GeoProtocol.GeoPoint location = newGeoStatus.getLocation();
        String oldGeohash = geoGroup.getCenterGeohash();
        if (geoGroup.updateLocation(location.getLatitude(), location.getLongitude())) {
            LogUtil.log("geohash moved old(%s) -> new(%s)", oldGeohash, geoGroup.getCenterGeohash());

            // ジオハッシュが更新されたら、前回の座標を保存する
            beforeHashGeo = oldGeoStatus;

            // 通知する
            for (CentralDataHandler handler : centralHandlers) {
                handler.onGeohashUpdated(this, master, oldGeoStatus, newGeoStatus);
            }
        }
    }

    /**
     * 最上位ペイロードを受け取った
     *
     * @param masterbuffer 受信したバッファ
     */
    public synchronized void onReceivedMasterPayload(byte[] masterbuffer) throws Exception {
        AcesProtocol.MasterPayload master = AcesProtocol.MasterPayload.parseFrom(masterbuffer);
        final String targetPackage = master.hasTargetPackage() ? master.getTargetPackage() : null;

        // senderが自分であれば反応しない
        if (checkSelfPackage) {
            if (selfPackageName.equals(master.getSenderPackage())) {
                //            Log.i("ACES", "error sender :: " + master.getSenderPackage());
                //            Log.i("ACES", "error target :: " + targetPackage);
                return;
            }
        }

        // target check
        if (checkTargetPackage) {
            if (targetPackage != null) {
                // 自分自身が対象でないなら
                if (!targetPackage.equals(selfPackageName)) {
                    // payloadの送信対象じゃないから、何もしない
                    //                    Log.i("ACES", "target error sender :: " + master.getSenderPackage());
                    //                    Log.i("ACES", "target error target :: " + targetPackage);
                    return;
                }
            }
        }

        // 位置情報を受け取った
        if (master.hasGeoStatus()) {
            handleGeoStatus(master);
        }

        // 正常なマスターデータを受け取った
        for (CentralDataHandler handler : centralHandlers) {
            handler.onMasterPayloadReceived(this, masterbuffer, master);
        }

        // 各種データを設定する
        if (master.hasCentralStatus()) {
            // セントラルステータスを持っていなければcommand onlyとかんがえる
            handleSensorEvents(master);
        }

        // 活動を解析する
        handleActivityEvents(master);

        // コマンドを解析する
        handleCommandEvents(master);
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
     * センサーイベントを登録する
     *
     * @param handler
     */
    public void addSensorEventHandler(SensorEventHandler handler) {
        sensorHandlers.add(handler);
    }

    /**
     * センサーイベントを削除する
     *
     * @param handler
     */
    public void removeSensorEventHandler(SensorEventHandler handler) {
        sensorHandlers.remove(handler);
    }

    /**
     * コマンドイベントを登録する
     *
     * @param handler
     */
    public void addCommandEventHandler(CommandEventHandler handler) {
        commandHandlers.add(handler);
    }

    /**
     * コマンドイベントを削除する
     *
     * @param handler
     */
    public void removeCommandEventHandler(CommandEventHandler handler) {
        commandHandlers.remove(handler);
    }

    /**
     * ACEsのハンドリングを行う
     *
     * @param handler
     */
    public void addCentralDataHandler(CentralDataHandler handler) {
        centralHandlers.add(handler);
    }

    /**
     * ACEsのハンドリングを削除する
     *
     * @param handler
     */
    public void removeCentralDataHandler(CentralDataHandler handler) {
        centralHandlers.remove(handler);
    }

    /**
     * 活動イベントのハンドリングを行う
     *
     * @param handler
     */
    public void addActivityEventHandler(ActivityEventHandler handler) {
        activityHandlers.add(handler);
    }

    /**
     * 活動イベントのハンドリングを削除する
     *
     * @param handler
     */
    public void removeActivityEventHandler(ActivityEventHandler handler) {
        activityHandlers.remove(handler);
    }

    /**
     * メインのステータス等のチェックを行う
     */
    public interface CentralDataListener {
        /**
         * マスターデータを受け取った
         *
         * @param buffer 受け取ったデータ
         * @param master すべてのデータを含んだペイロード
         */
        void onMasterPayloadReceived(AcesProtocolReceiver receiver, byte[] buffer, MasterPayload master);
    }
}
