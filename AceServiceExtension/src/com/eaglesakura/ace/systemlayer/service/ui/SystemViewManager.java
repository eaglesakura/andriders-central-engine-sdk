package com.eaglesakura.ace.systemlayer.service.ui;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.eaglesakura.ace.systemlayer.R;
import com.eaglesakura.ace.systemlayer.service.UiService;
import com.eaglesakura.andriders.central.AcesProtocolReceiver;
import com.eaglesakura.andriders.central.event.SensorEventHandler;
import com.eaglesakura.andriders.protocol.AcesProtocol.MasterPayload;
import com.eaglesakura.andriders.protocol.SensorProtocol.RawCadence;
import com.eaglesakura.andriders.protocol.SensorProtocol.RawHeartrate;
import com.eaglesakura.andriders.protocol.SensorProtocol.RawSpeed;
import com.eaglesakura.andriders.ui.BasicUiBuilder;

/**
 * システムUI表示用View
 */
public class SystemViewManager {
    View systemView;

    final UiService service;

    final WindowManager windowManager;

    AcesProtocolReceiver receiver;

    BasicUiBuilder uiBuilder;

    public SystemViewManager(UiService service) {
        this.service = service;
        this.windowManager = (WindowManager) service.getSystemService(Context.WINDOW_SERVICE);

        uiBuilder = new BasicUiBuilder(service);
    }

    /**
     * ウィンドウに接続する
     */
    public void connect() {
        if (systemView != null) {
            return;
        }

        LayoutInflater inflater = LayoutInflater.from(service);
        systemView = inflater.inflate(R.layout.view_systemlayer, null);

        {

            WindowManager.LayoutParams params = new WindowManager.LayoutParams(
            // レイアウトの幅 / 高さ設定
                    WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT,
                    // レイアウトの挿入位置設定
                    // TYPE_SYSTEM_OVERLAYはほぼ最上位に位置して、ロック画面よりも上に表示される。
                    // ただし、タッチを拾うことはできない。
                    WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                    // ウィンドウ属性
                    // TextureViewを利用するには、FLAG_HARDWARE_ACCELERATED が必至となる。
                    // 現状は仮画面のため使用しないが、後々のアップデートを見越して入れておく
                    WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                    // 透過属性を持たなければならないため、TRANSLUCENTを利用する
                    PixelFormat.TRANSLUCENT);

            // Viewを画面上に重ね合わせする
            windowManager.addView(systemView, params);
        }

        // セントラルに接続する
        {
            receiver = new AcesProtocolReceiver(service);
            receiver.addSensorEventHandler(sensorHandlerImpl);
            receiver.connect();
        }
    }

    /**
     * ウィンドウから切断する
     */
    public void disconnect() {
        if (systemView == null) {
            return;
        }

        // セントラルから切断
        {
            receiver.disconnect();
            receiver = null;
        }

        // Viewを切断
        {
            windowManager.removeView(systemView);
            systemView = null;
        }
    }

    SensorEventHandler sensorHandlerImpl = new SensorEventHandler() {
        /**
         * ケイデンス受信
         */
        @Override
        public void onCadenceReceived(AcesProtocolReceiver receiver, MasterPayload master, RawCadence cadence) {
            uiBuilder.build(systemView.findViewById(R.id.SystemLayer_Cadence_Root), master, cadence);
        }

        /**
         * 心拍受信
         */
        @Override
        public void onHeartrateReceived(AcesProtocolReceiver receiver, MasterPayload master, RawHeartrate heartrate) {
            uiBuilder.build(systemView.findViewById(R.id.SystemLayer_Heartrate_Root), master, heartrate);
        }

        /**
         * 速度受信
         */
        @Override
        public void onSpeedReceived(AcesProtocolReceiver receiver, MasterPayload master, RawSpeed speed) {
            View current = systemView.findViewById(R.id.SystemLayer_Speed_Root);
            View max = systemView.findViewById(R.id.SystemLayer_MaxSpeed_Root);
            uiBuilder.build(current, max, master, speed);
        }
    };
}
