package com.eaglesakura.andriders.central;

import com.google.protobuf.ByteString;

import com.eaglesakura.andriders.central.event.ActivityEventHandler;
import com.eaglesakura.andriders.central.event.CentralDataHandler;
import com.eaglesakura.andriders.central.event.CommandEventHandler;
import com.eaglesakura.andriders.central.event.SensorEventHandler;
import com.eaglesakura.andriders.command.AcesTriggerUtil;
import com.eaglesakura.andriders.command.CommandKey;
import com.eaglesakura.andriders.notification.NotificationData;
import com.eaglesakura.andriders.notification.SoundData;
import com.eaglesakura.andriders.serialize.AcesProtocol;
import com.eaglesakura.andriders.serialize.AcesProtocol.MasterPayload;
import com.eaglesakura.andriders.serialize.ActivityProtocol;
import com.eaglesakura.andriders.serialize.CommandProtocol;
import com.eaglesakura.andriders.serialize.CommandProtocol.CommandPayload;
import com.eaglesakura.andriders.serialize.CommandProtocol.CommandType;
import com.eaglesakura.andriders.serialize.CommandProtocol.TriggerPayload;
import com.eaglesakura.andriders.serialize.GeoProtocol;
import com.eaglesakura.andriders.serialize.SensorProtocol.RawCadence;
import com.eaglesakura.andriders.serialize.SensorProtocol.RawHeartrate;
import com.eaglesakura.andriders.serialize.SensorProtocol.RawSpeed;
import com.eaglesakura.andriders.serialize.SensorProtocol.SensorPayload;
import com.eaglesakura.andriders.serialize.SensorProtocol.SensorType;
import com.eaglesakura.geo.Geohash;
import com.eaglesakura.geo.GeohashGroup;
import com.eaglesakura.util.EncodeUtil;
import com.eaglesakura.util.LogUtil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CentralDataReceiver {
    /**
     * Intent経由で送られる場合のデータマスター
     */
    static final String INTENT_EXTRA_MASTER = "INTENT_EXTRA_MASTER";

    /**
     * ACEsの特殊イベント（Service起動、シャットダウン等）を受信する
     */
    static final String INTENT_EXTRA_EVENT = "INTENT_EXTRA_EVENT";

    /**
     * 送受信用Action
     */
    static final String INTENT_ACTION = "com.eaglesakura.andriders.ACTION_CENTRAL_DATA_v2";

    /**
     * 送受信用カテゴリ
     */
    static final String INTENT_CATEGORY = "com.eaglesakura.andriders.CATEGORY_ACE_DATA";

    /**
     * 最後に受け取ったセントラル情報
     */
    private AcesProtocol.CentralSpec mLastRedeivedCentralSpec;

    /**
     * 他のアプリへデータを投げる
     */
    static Intent newBroadcastIntent() {
        Intent intent = new Intent(INTENT_ACTION);
        intent.addCategory(INTENT_CATEGORY);
        return intent;
    }

    private final Context mContext;

    /**
     * broadcastに接続済みであればtrue
     */
    private boolean mConnected = false;

    /**
     * 最後に受信したハートレート
     */
    private RawHeartrate mLastReceivedHeartrate;

    /**
     * 最後に受信したケイデンス
     */
    private RawCadence mLastReceivedCadence;

    /**
     * 最後に受信したスピード
     */
    private RawSpeed mLastReceivedSpeed;

    /**
     * ジオハッシュ管理
     */
    private GeohashGroup mGeoGroup = new GeohashGroup();

    /**
     * 最後に受け取った位置情報
     */
    private GeoProtocol.GeoPayload mLastReceivedGeo;

    /**
     * ジオハッシュの更新回数
     */
    private int mGeohashUpdatedCount;

    /**
     * 古いジオハッシュで最後に受け取った位置情報
     */
    private GeoProtocol.GeoPayload mBeforeHashGeo;

    /**
     * 周辺情報
     */
    private GeoProtocol.GeographyPayload mLastReceivedGeography;

    /**
     * ACEsの現在のステータス
     */
    private AcesProtocol.CentralStatus mLastReceivedCentralStatus;

    private ActivityProtocol.SessionStatus mLastReceivedSessionStatus;

    private ActivityProtocol.UserRecord mLastReceivedUserRecord;

    private ActivityProtocol.FitnessStatus mLastReceivedFitness;

    /**
     * 最後にセンサー情報を受け取ったマスター情報
     */
    private MasterPayload mLastReceivedCentralMaster;

    /**
     * ロックオブジェクト
     */
    private final Object lock = new Object();

    /**
     * データ取得クラスを構築する
     */
    public CentralDataReceiver(Context context) {
        this.mContext = context.getApplicationContext();
    }

    /**
     * ACEがデバッグ状態である場合true
     *
     * まだMasterPayloadを受け取っていない場合はfalseを返却する
     */
    public boolean isCentralDebugable() {
        if (mLastReceivedCentralStatus != null) {
            return mLastReceivedCentralStatus.getDebug();
        } else {
            return false;
        }
    }

    /**
     * ジオハッシュの文字数を指定する。
     * <br>
     * 文字数は長いほど狭い範囲でしか扱えない。
     */
    public void setGeohashLength(int len) {
        mGeoGroup.setGeohashLength(len);
    }

    /**
     * 最後に受信したACEsステータスを取得する。
     */
    public AcesProtocol.CentralStatus getLastReceivedCentralStatus() {
        return mLastReceivedCentralStatus;
    }

    /**
     * 最後に受信したセッション情報を取得する。
     */
    public ActivityProtocol.SessionStatus getLastReceivedSessionStatus() {
        return mLastReceivedSessionStatus;
    }

    /**
     * 最後に受信したユーザーの自己記録を取得する。
     */
    public ActivityProtocol.UserRecord getLastReceivedUserRecord() {
        return mLastReceivedUserRecord;
    }


    /**
     * 最後に受信したMasterPayloadを取得する。
     */
    public MasterPayload getLastReceivedCentralMaster() {
        return mLastReceivedCentralMaster;
    }

    /**
     * 最後に受信したフィットネス情報を取得する。
     */
    public ActivityProtocol.FitnessStatus getLastReceivedFitness() {
        return mLastReceivedFitness;
    }

    /**
     * 最後に受け取ったMasterPayloadの打刻時刻を取得する。
     */
    public Date getLastReceivedCentralMasterTime() {
        if (mLastReceivedCentralMaster == null) {
            return null;
        } else {
            return new Date(mLastReceivedCentralMaster.getCreatedDateInt());
        }
    }

    /**
     * 自走状態であることをチェックする。
     * <br>
     * 自分の脚で前進している状態であるため、速度がStopゾーンよりも大きく、ケイデンスが1rpm以上である必要がある。
     * <br>
     * ケイデンスセンサーが接続されていない場合、ケイデンスチェックはskipされる。
     * <br>
     * スピードが正常に取得できない場合、強制的にfalseを返却する。
     *
     * @return 自走であればtrue
     */
    public boolean isActiveMoving() {
        RawCadence cadence = getLastReceivedCadence();
        RawSpeed speed = getLastReceivedSpeed();

        if (speed == null || speed.getSpeedZone() == RawSpeed.SpeedZone.Stop) {
            // スピードがないか、停止状態であればnot active
            return false;
        }

        if (cadence != null && cadence.getRpm() <= 0) {
            // 脚が停止していたらnot active
            return false;
        }

        // ひとまず動いているからactive
        return true;
    }

    /**
     * 最後に受信したケイデンスを取得する。
     */
    public RawCadence getLastReceivedCadence() {
        return mLastReceivedCadence;
    }

    /**
     * 最後に受信した心拍を取得する。
     */
    public RawHeartrate getLastReceivedHeartrate() {
        return mLastReceivedHeartrate;
    }

    /**
     * 最後に受信したスピードを取得する。
     */
    public RawSpeed getLastReceivedSpeed() {
        return mLastReceivedSpeed;
    }

    /**
     * 最後に受信したGPS座標を取得する。
     */
    public GeoProtocol.GeoPayload getLastReceivedGeo() {
        return mLastReceivedGeo;
    }

    /**
     * 最後に受信した周辺情報を取得する。
     */
    public GeoProtocol.GeographyPayload getLastReceivedGeography() {
        return mLastReceivedGeography;
    }

    /**
     * 現在自分がいる座標のジオハッシュを取得する。
     */
    public String getCurrentGeohash() {
        synchronized (lock) {
            if (mLastReceivedGeo != null) {
                return mGeoGroup.getCenterGeohash();
            } else {
                return null;
            }
        }
    }

    /**
     * ジオハッシュ更新回数を取得する。
     */
    public int getGeohashUpdatedCount() {
        return mGeohashUpdatedCount;
    }

    /**
     * 直前に所屬していたジオハッシュを取得する。
     * <br>
     * 起動直後で動いていない場合や、GPSが正常に取得できない等、nullが返却される場合がある。
     */
    public String getBeforeGeohash() {
        synchronized (lock) {
            if (mBeforeHashGeo == null) {
                return null;
            }

            GeoProtocol.GeoPoint location = mBeforeHashGeo.getLocation();
            return Geohash.encode(location.getLatitude(), location.getLongitude()).substring(0, mGeoGroup.getGeohashLength());
        }
    }

    /**
     * ACEsへ接続する。
     */
    public void connect() {
        if (!mConnected) {
            IntentFilter filter = new IntentFilter(INTENT_ACTION);
            filter.addCategory(INTENT_CATEGORY);
            mContext.registerReceiver(mReceiver, filter);
            mConnected = true;
        }
    }

    /**
     * ACEsから切断する。
     */
    public void disconnect() {
        if (mConnected) {
            mContext.unregisterReceiver(mReceiver);
            mConnected = false;
        }
    }

    /**
     * ACEsへ接続済みの場合trueを返却する。
     */
    public boolean isConnected() {
        return mConnected;
    }

    /**
     * ACEsの情報ハンドリングを行う
     */
    private final Set<CentralDataHandler> centralHandlers = new HashSet<>();

    /**
     * センサーイベントのハンドリング
     */
    private final Set<SensorEventHandler> sensorHandlers = new HashSet<>();

    /**
     * コマンドイベントのハンドリング
     */
    private final Set<CommandEventHandler> commandHandlers = new HashSet<>();

    /**
     * ユーザー活動のハンドリングを行う
     */
    private final Set<ActivityEventHandler> activityHandlers = new HashSet<>();

    /**
     * 心拍を受け取った
     */
    private void onHeartrateReceived(MasterPayload master, ByteString payload) throws Exception {
        RawHeartrate heartrate = RawHeartrate.parseFrom(payload);
        this.mLastReceivedHeartrate = heartrate;

        // ハンドラに通知
        for (SensorEventHandler handler : sensorHandlers) {
            handler.onHeartrateReceived(this, master, heartrate);
        }
    }

    /**
     * ケイデンスを受け取った
     */
    private void onCadenceReceived(MasterPayload master, ByteString payload) throws Exception {
        RawCadence cadence = RawCadence.parseFrom(payload);
        this.mLastReceivedCadence = cadence;

        // ハンドラに通知
        for (SensorEventHandler handler : sensorHandlers) {
            handler.onCadenceReceived(this, master, cadence);
        }
    }

    /**
     * 速度を受け取った
     */
    private void onSpeedReceived(MasterPayload master, ByteString payload) throws Exception {
        RawSpeed speed = RawSpeed.parseFrom(payload);
        this.mLastReceivedSpeed = speed;

        // ハンドラに通知
        for (SensorEventHandler handler : sensorHandlers) {
            handler.onSpeedReceived(this, master, speed);
        }
    }

    /**
     * 不明なセンサーを受け取った
     */
    private void onUnknownSensorReceived(MasterPayload master, SensorPayload payload) throws Exception {
        // ハンドラに通知
        for (SensorEventHandler handler : sensorHandlers) {
            handler.onUnknownSensorReceived(this, master, payload);
        }
    }

    /**
     * センサー系イベントのハンドリングを行う
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
                LogUtil.d(e);
            }
        }
    }


    /**
     * 近接コマンドを受け取った
     */
    private void onCommandReceived(MasterPayload master, TriggerPayload trigger) {
        CommandKey key = CommandKey.fromString(trigger.getKey());
        Intent intent = new Intent();
        if (trigger.hasAppIntent()) {
            intent = AcesTriggerUtil.makeIntent(trigger.getAppIntent());
        }

        for (CommandEventHandler handler : commandHandlers) {
            handler.onTriggerReceived(this, master, key, trigger, intent);
        }
    }

    /**
     * ACEsへの通知を受け取った
     */
    private void onAcesNotificationCommand(MasterPayload master, CommandProtocol.NotificationRequestPayload requestPayload) {
        NotificationData notificationData = new NotificationData(requestPayload);
        for (CommandEventHandler handler : commandHandlers) {
            handler.onNotificationReceived(this, master, notificationData, requestPayload);
        }
    }

    /**
     * ACEsへのサウンドリクエストを受け取った
     */
    private void onAcesSoundNotificationCommand(MasterPayload master, CommandProtocol.SoundNotificationPayload soundNotificationPayload) {
        SoundData soundData = new SoundData(soundNotificationPayload);
        for (CommandEventHandler handler : commandHandlers) {
            handler.onSoundNotificationReceived(this, master, soundData, soundNotificationPayload);
        }
    }

    /**
     * 不明なコマンドを受け取った
     */
    private void onUnknownCommandRecieved(MasterPayload master, CommandPayload command) {
        for (CommandEventHandler handler : commandHandlers) {
            handler.onUnknownCommandReceived(this, master, command);
        }
    }

    /**
     * コマンド処理のハンドリング
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
            } else if (CommandType.SoundNotification.name().equals(commandType)) {
                // サウンド通知
                CommandProtocol.SoundNotificationPayload soundNotificationPayload = CommandProtocol.SoundNotificationPayload.parseFrom(cmd.getExtraPayload());
                onAcesSoundNotificationCommand(master, soundNotificationPayload);
            } else {
                // 不明なコマンド
                onUnknownCommandRecieved(master, cmd);
            }
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

        // フィットネスデータを取得した
        if (master.hasFitness()) {
            mLastReceivedFitness = master.getFitness();
            for (ActivityEventHandler handler : activityHandlers) {
                handler.onFitnessDataReceived(this, master, mLastReceivedFitness);
            }
        }
    }

    /**
     * 位置情報を受け取った
     */
    protected void handleGeoStatus(MasterPayload master) throws Exception {
        final GeoProtocol.GeoPayload oldGeoStatus;
        final GeoProtocol.GeoPayload newGeoStatus;
        final GeoProtocol.GeoPoint newLocation;
        final String oldGeohash;
        final boolean updatedGeohash;
        final boolean updatedGeography;
        final GeoProtocol.GeographyPayload oldGeography = mLastReceivedGeography;

        // ジオハッシュ処理はロック処理を行う
        synchronized (lock) {
            oldGeoStatus = mLastReceivedGeo;
            newGeoStatus = master.getGeoStatus();

            // 座標を上書き
            mLastReceivedGeo = newGeoStatus;

            // ジオハッシュを更新
            newLocation = newGeoStatus.getLocation();
            oldGeohash = mGeoGroup.getCenterGeohash();
            updatedGeohash = mGeoGroup.updateLocation(newLocation.getLatitude(), newLocation.getLongitude());

            if (updatedGeohash) {
                // ジオハッシュ更新処理
                LogUtil.log("geohash moved old(%s) -> new(%s)", oldGeohash, mGeoGroup.getCenterGeohash());
                // ジオハッシュが更新されたら、前回の座標を保存する
                mBeforeHashGeo = oldGeoStatus;

                ++mGeohashUpdatedCount;
            }

            // 周辺情報を持っている
            if (master.hasGeography()) {
                GeoProtocol.GeographyPayload newGeography = master.getGeography();
                if (mLastReceivedGeography == null) {
                    // 新しい地理情報
                    updatedGeography = true;
                    mLastReceivedGeography = newGeography;
                } else {
                    String lastDate = mLastReceivedGeography.getDate();
                    if (lastDate.equals(newGeography.getDate())) {
                        // 同一時刻である場合は更新がない
                        updatedGeography = false;
                    } else {
                        // 新しい地理情報
                        updatedGeography = true;
                        mLastReceivedGeography = newGeography;
                    }
                }
            } else {
                updatedGeography = false;
            }
        }

        // オブジェクトにハンドリング
        // まずは受け取ったことを通知
        for (CentralDataHandler handler : centralHandlers) {
            handler.onGeoStatusReceived(this, master, newGeoStatus);
        }

        if (updatedGeohash) {
            // ジオハッシュ通知
            for (CentralDataHandler handler : centralHandlers) {
                handler.onGeohashUpdated(this, master, oldGeoStatus, newGeoStatus);
            }
        }

        // 周辺情報の通知
        if (master.hasGeography()) {
            for (CentralDataHandler handler : centralHandlers) {
                handler.onGeographyReceived(this, master, master.getGeography());
            }
        }
        if (updatedGeography) {
            // ジオハッシュ通知
            for (CentralDataHandler handler : centralHandlers) {
                handler.onGeographyUpdated(this, master, oldGeography, mLastReceivedGeography);
            }
        }
    }

    public synchronized void onReceivedIntent(Intent intent) {
        if (!INTENT_ACTION.equals(intent.getAction())) {
            // Actionが一致しない
            return;
        }
        //            BleLog.d("received :: " + intent.getExtras());
        try {
            byte[] master = intent.getByteArrayExtra(INTENT_EXTRA_MASTER);
            onReceivedPayload(master);
        } catch (Exception e) {
            LogUtil.d(e);
        }

    }

    /**
     * 最上位ペイロードを受け取った
     *
     * @param masterbuffer 受信したバッファ
     */
    public synchronized void onReceivedPayload(byte[] masterbuffer) throws Exception {
        // バッファをデコードする
        masterbuffer = decompressMasterPayload(masterbuffer);
        AcesProtocol.MasterPayload master;

        try {
            master = AcesProtocol.MasterPayload.parseFrom(masterbuffer);
        } catch (Exception e) {
            // failed parse
            for (CentralDataHandler handler : centralHandlers) {
                handler.onMasterPayloadParseFiled(this, masterbuffer);
            }
            return;
        }


        mLastReceivedCentralMaster = master;
        mLastRedeivedCentralSpec = master.getCentralSpec();
        mLastReceivedCentralStatus = master.getCentralStatus();

        // セッション情報
        if (master.hasSessionStatus()) {
            this.mLastReceivedSessionStatus = master.getSessionStatus();
        }

        // ユーザー記録
        if (master.hasUserRecord()) {
            mLastReceivedUserRecord = master.getUserRecord();
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

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            onReceivedIntent(intent);
        }
    };

    /**
     * センサーイベントを登録する
     *
     * @param handler 対象ハンドラ
     */
    public void addSensorEventHandler(SensorEventHandler handler) {
        sensorHandlers.add(handler);
    }

    /**
     * センサーイベントを削除する
     *
     * @param handler 対象ハンドラ
     */
    public void removeSensorEventHandler(SensorEventHandler handler) {
        sensorHandlers.remove(handler);
    }

    /**
     * コマンドイベントを登録する
     *
     * @param handler 対象ハンドラ
     */
    public void addCommandEventHandler(CommandEventHandler handler) {
        commandHandlers.add(handler);
    }

    /**
     * コマンドイベントを削除する
     *
     * @param handler 対象ハンドラ
     */
    public void removeCommandEventHandler(CommandEventHandler handler) {
        commandHandlers.remove(handler);
    }

    /**
     * ACEsのハンドリングを行う
     *
     * @param handler 対象ハンドラ
     */
    public void addCentralDataHandler(CentralDataHandler handler) {
        centralHandlers.add(handler);
    }

    /**
     * ACEsのハンドリングを削除する
     *
     * @param handler 対象ハンドラ
     */
    public void removeCentralDataHandler(CentralDataHandler handler) {
        centralHandlers.remove(handler);
    }

    /**
     * 活動イベントのハンドリングを行う
     *
     * @param handler 対象ハンドラ
     */
    public void addActivityEventHandler(ActivityEventHandler handler) {
        activityHandlers.add(handler);
    }

    /**
     * 活動イベントのハンドリングを削除する
     *
     * @param handler 対象ハンドラ
     */
    public void removeActivityEventHandler(ActivityEventHandler handler) {
        activityHandlers.remove(handler);
    }

    /**
     * MasterPayloadを圧縮する
     * <br>
     * 基本的にはgzipを用いる。また、gzip圧縮後の容量が元の容量を上回る場合、bufferをそのまま返却する。
     *
     * @param buffer 圧縮するバッファ
     * @return 容量を小さくしたバッファ
     */
    public static byte[] compressMasterPayload(byte[] buffer) {
        return EncodeUtil.compressOrRaw(buffer);
    }

    /**
     * MasterPayloadを元の状態に戻す。
     * <br>
     * {@link #compressMasterPayload(byte[])}で圧縮されたバッファを返却する。
     * <br>
     * bufferがgzipでない場合、そのままバッファを返却する
     *
     * @param buffer 解凍するバッファ
     * @return 解凍されたバッファ
     */
    public static byte[] decompressMasterPayload(byte[] buffer) {
        return EncodeUtil.decompressOrRaw(buffer);
    }
}
