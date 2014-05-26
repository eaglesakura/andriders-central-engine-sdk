package com.eaglesakura.ace.systemlayer.service.ui;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.androidquery.AQuery;
import com.eaglesakura.ace.systemlayer.R;
import com.eaglesakura.ace.systemlayer.service.UiService;
import com.eaglesakura.andriders.central.ServiceDataReceiver;
import com.eaglesakura.andriders.protocol.Payload.CentralStatus;
import com.eaglesakura.andriders.protocol.Payload.MasterPayload;
import com.eaglesakura.andriders.protocol.SensorProtocol.RawCadence;
import com.eaglesakura.andriders.protocol.SensorProtocol.RawHeartrate;
import com.eaglesakura.andriders.protocol.SensorProtocol.RawHeartrate.HeartrateZone;

/**
 * システムUI表示用View
 */
public class SystemViewManager {
    View systemView;

    final UiService service;

    final WindowManager windowManager;

    ServiceDataReceiver receiver;

    public SystemViewManager(UiService service) {
        this.service = service;
        this.windowManager = (WindowManager) service.getSystemService(Context.WINDOW_SERVICE);
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
            receiver = new ServiceDataReceiver(service);
            receiver.addCentralDataListener(centralDataListenerImpl);
            receiver.addCadenceListener(cadenceListenerImpl);
            receiver.addHeartrateListener(heartrateListenerImpl);
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

    ServiceDataReceiver.CentralDataListener centralDataListenerImpl = new ServiceDataReceiver.CentralDataListener() {
        @Override
        public void onMasterPayloadReceived(ServiceDataReceiver receiver, byte[] buffer, MasterPayload master) {
            CentralStatus status = master.getCentralStatus();

            //            BleLog.d("received master");
            {
                // heartrate
                AQuery q = new AQuery(systemView.findViewById(R.id.SystemLayer_Heartrate_Root));
                q.id(R.id.SystemLayer_Card_Text);

                if (!status.getConnectedHeartrate()) {
                    // ハートレートモニターに接続されて無ければN/A
                    q.text(R.string.Common_NA);
                }
            }
            {
                // cadence
                AQuery q = new AQuery(systemView.findViewById(R.id.SystemLayer_Cadence_Root));
                q.id(R.id.SystemLayer_Card_Text);

                if (!status.getConnectedCadence()) {
                    // ハートレートモニターに接続されて無ければN/A
                    q.text(R.string.Common_NA);
                }
            }
        }
    };

    /**
     * ハートレート受信
     */
    ServiceDataReceiver.HeartrateListener heartrateListenerImpl = new ServiceDataReceiver.HeartrateListener() {
        @Override
        public void onHeartrateReceived(ServiceDataReceiver receiver, RawHeartrate heartrate) {
            // heartrate
            AQuery q = new AQuery(systemView.findViewById(R.id.SystemLayer_Heartrate_Root));
            q.id(R.id.SystemLayer_Card_Text);

            q.text(String.format("%d", heartrate.getBpm()));

            int colorId = R.color.SystemLayer_Zonebar_None;
            String zoneText = "";
            if (heartrate.hasHeartrateZone()) {
                HeartrateZone zone = heartrate.getHeartrateZone();
                switch (zone) {
                    case Repose:
                        colorId = R.color.SystemLayer_Zonebar_Lv0;
                        zoneText = "安静";
                        break;
                    case Easy:
                        colorId = R.color.SystemLayer_Zonebar_Lv1;
                        zoneText = "軽度";
                        break;
                    case FatCombustion:
                        colorId = R.color.SystemLayer_Zonebar_Lv2;
                        zoneText = "脂肪燃焼";
                        break;
                    case PossessionOxygenMotion:
                        colorId = R.color.SystemLayer_Zonebar_Lv3;
                        zoneText = "有酸素";
                        break;
                    case NonOxygenatedMotion:
                        colorId = R.color.SystemLayer_Zonebar_Lv4;
                        zoneText = "無酸素";
                        break;
                    case Overwork:
                        colorId = R.color.SystemLayer_Zonebar_Lv5;
                        zoneText = "危険域";
                        break;
                }
            }
            // 背景色指定
            q.id(R.id.SystemLayer_Zonebar).backgroundColor(service.getResources().getColor(colorId));
            q.id(R.id.SystemLayer_Zonebar_Info).text(zoneText);
        }
    };

    /**
     * ケイデンス受信
     */
    ServiceDataReceiver.CadenceListener cadenceListenerImpl = new ServiceDataReceiver.CadenceListener() {
        @Override
        public void onCadenceReceived(ServiceDataReceiver receiver, RawCadence cadence) {
            // cadence
            AQuery q = new AQuery(systemView.findViewById(R.id.SystemLayer_Cadence_Root));
            q.id(R.id.SystemLayer_Card_Text);
            q.text(String.format("%d", cadence.getRpm()));

            // ゾーンレベル
            int colorId = R.color.SystemLayer_Zonebar_None;
            if (cadence.getRpm() > 0) {
                switch (cadence.getCadenceZone()) {
                    case Easy:
                        colorId = R.color.SystemLayer_Zonebar_Lv0;
                        break;
                    case Beginner:
                        colorId = R.color.SystemLayer_Zonebar_Lv2;
                        break;
                    case Ideal:
                        colorId = R.color.SystemLayer_Zonebar_Lv4;
                        break;
                }
            }

            q.id(R.id.SystemLayer_Zonebar).backgroundColor(service.getResources().getColor(colorId));
        }
    };
}
