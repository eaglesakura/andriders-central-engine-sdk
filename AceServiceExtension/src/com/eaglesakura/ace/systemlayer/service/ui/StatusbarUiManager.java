package com.eaglesakura.ace.systemlayer.service.ui;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.RemoteViews;

import com.eaglesakura.ace.systemlayer.R;
import com.eaglesakura.ace.systemlayer.service.UiService;
import com.eaglesakura.util.LogUtil;

public class StatusbarUiManager {

    final UiService service;
    final NotificationManager notificationManager;

    /**
     * ステータスバーから受け取ったコマンド
     */
    private static final String EXTRA_COMMAND = "EXTRA_COMMAND";

    /**
     * AceServiceを停止させる
     */
    private static final String COMMAND_STOP_SERVICE = "COMMAND_STOP_SERVICE";

    /**
     * サービス操作用コントロール
     */
    private static String ACTION_SERVICE_CONTROLL = "com.eaglesakura.ace.systemlayer.private.ACTION_SERVICE_CONTROLL";

    /**
     * Notification BarのIDを設定する。
     */
    private static final int NOTIFICATION_ID = 0x3103 + 0;

    /**
     * リモート
     */
    RemoteViews remoteController;

    /**
     * 通知本体
     */
    Notification notification;

    private final BroadcastReceiver viewReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String cmd = intent.getStringExtra(EXTRA_COMMAND);
            if (COMMAND_STOP_SERVICE.equals(cmd)) {
                // サービス停止指示の場合
                UiService.stop(service);
            }
        }
    };

    public StatusbarUiManager(UiService service) {
        this.service = service;
        this.notificationManager = (NotificationManager) service.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    /**
     * UIの初期化を行う
     */
    private void initializeRemoteViews() {
        remoteController = new RemoteViews(service.getPackageName(), R.layout.view_statusbar);

        {
            // 停止ボタン
            remoteController.setOnClickPendingIntent(R.id.Statusbar_Disconnect, createCommand(COMMAND_STOP_SERVICE));
        }

    }

    /**
     * ステータスバーへの通知を表示する
     */
    public void connect() {
        initializeRemoteViews();

        // コマンド用のレシーバーを設定
        service.registerReceiver(viewReceiver, new IntentFilter(ACTION_SERVICE_CONTROLL));

        // notificationを表示する
        {
            Notification.Builder builder = new Notification.Builder(service);
            builder.setAutoCancel(true);
            builder.setTicker(service.getString(R.string.app_name));

            builder.setSmallIcon(R.drawable.ic_launcher);
            builder.setWhen(System.currentTimeMillis());
            builder.setContent(remoteController);
            notification = builder.build();
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
        }

        service.startForeground(NOTIFICATION_ID, notification);
    }

    /**
     * Viewの再描画を行う
     */
    void invalidateViews() {
        try {
            notificationManager.notify(NOTIFICATION_ID, notification);
        } catch (Exception e) {
            LogUtil.log(e);
        }
    }

    /**
     * ステータスバーへの通知を停止する
     */
    public void disconnect() {
        service.unregisterReceiver(viewReceiver);
    }

    /**
     * コマンドを生成する
     * @param command
     * @return
     */
    private PendingIntent createCommand(String command) {
        return PendingIntent.getBroadcast(service, command.hashCode(), new Intent(ACTION_SERVICE_CONTROLL).putExtra(EXTRA_COMMAND, command), 0);
    }
}
