package com.eaglesakura.andriders.ui;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;

import com.androidquery.AQuery;
import com.eaglesakura.andriders.R;
import com.eaglesakura.andriders.protocol.AcesProtocol.MasterPayload;
import com.eaglesakura.andriders.protocol.SensorProtocol.RawCadence;
import com.eaglesakura.andriders.protocol.SensorProtocol.RawCadence.CadenceZone;
import com.eaglesakura.andriders.protocol.SensorProtocol.RawHeartrate;
import com.eaglesakura.andriders.protocol.SensorProtocol.RawHeartrate.HeartrateZone;
import com.eaglesakura.andriders.protocol.SensorProtocol.RawSpeed;
import com.eaglesakura.andriders.protocol.SensorProtocol.RawSpeed.SpeedZone;

/**
 * ACE用のシンプルな（標準的な）UIを構築するためのビルダーを提供する。
 * 指定したIDでUIを構築することで、共通化した内容を当てはめることが出来る。
 */
public class BasicUiBuilder {
    @SuppressWarnings("unused")
    private final Context context;

    /**
     * 各種ゾーンのイメージカラー
     */
    private static int[] zoneColorTable;

    /**
     * ゾーンエラー時
     */
    private static int zoneErrorColor;

    /**
     * 心拍ゾーンのタイトル
     */
    private static String[] cadenceZoneTable;

    /**
     * 心拍ゾーンのタイトル
     */
    private static String[] heartrateZoneTable;

    /**
     * 速度ゾーンのタイトル
     */
    private static String[] speedZoneTable;

    private static boolean initialized = false;

    public BasicUiBuilder(Context context) {
        this.context = context.getApplicationContext();

        if (initialized) {
            // 初期化済みなら何もしない
            return;
        }

        Resources res = context.getResources();
        // カラーテーブルを読み込む
        {
            zoneColorTable = new int[] {
                    res.getColor(R.color.AceUI_Zonebar_Lv0), //
                    res.getColor(R.color.AceUI_Zonebar_Lv1), //
                    res.getColor(R.color.AceUI_Zonebar_Lv2), //
                    res.getColor(R.color.AceUI_Zonebar_Lv3), //
                    res.getColor(R.color.AceUI_Zonebar_Lv4), //
                    res.getColor(R.color.AceUI_Zonebar_Lv5), //
                    res.getColor(R.color.AceUI_Zonebar_Lv6), //
            };

            zoneErrorColor = res.getColor(R.color.AceUI_Zonebar_None);
        }
        // ケイデンス
        {
            cadenceZoneTable = new String[] {
                    res.getString(R.string.AceUI_Zone_Cadence_Easy), // 軽度
                    res.getString(R.string.AceUI_Zone_Cadence_Beginner), // ビギナー
                    res.getString(R.string.AceUI_Zone_Cadence_Ideal), // 巡航
            };
        }
        // 心拍
        {
            heartrateZoneTable = new String[] {
                    res.getString(R.string.AceUI_Zone_Heartrate_Repose), // 安静
                    res.getString(R.string.AceUI_Zone_Heartrate_Easy), // 軽度
                    res.getString(R.string.AceUI_Zone_Heartrate_FatCombustion), // 脂肪燃焼
                    res.getString(R.string.AceUI_Zone_Heartrate_PossessionOxygenMotion), // 有酸素
                    res.getString(R.string.AceUI_Zone_Heartrate_NonOxygenatedMotion), // 無酸素
                    res.getString(R.string.AceUI_Zone_Heartrate_Overwork), // 危険域
            };
        }
        // 速度
        {
            speedZoneTable = new String[] {
                    res.getString(R.string.AceUI_Zone_Speed_Stop), // 停止
                    res.getString(R.string.AceUI_Zone_Speed_Slow), // 低速
                    res.getString(R.string.AceUI_Zone_Speed_Cruise), // 巡航
                    res.getString(R.string.AceUI_Zone_Speed_Sprint), // スプリント
            };
        }
        initialized = true;
    }

    /**
     * ケイデンスゾーンから色を取得する
     * @param zone
     * @return
     */
    public int getZoneColor(CadenceZone zone) {
        switch (zone) {
            case Easy:
                return zoneColorTable[0];
            case Beginner:
                return zoneColorTable[2];
            case Ideal:
                return zoneColorTable[4];
        }

        return zoneErrorColor;
    }

    /**
     * スピードゾーンから色を取得する
     * @param zone
     * @return
     */
    public int getZoneClor(SpeedZone zone) {
        switch (zone) {
            case Stop:
                return zoneColorTable[0];
            case Slow:
                return zoneColorTable[1];
            case Cruise:
                return zoneColorTable[4];
            case Sprint:
                return zoneColorTable[6];
        }

        return zoneErrorColor;
    }

    /**
     * ケイデンスゾーンのタイトルを取得する
     * @param zone
     * @return
     */
    public String getZoneInfoText(CadenceZone zone) {
        return cadenceZoneTable[zone.getNumber()];
    }

    /**
     * スピードゾーンのタイトルを取得する
     * @param zone
     * @return
     */
    public String getZoneInfoText(SpeedZone zone) {
        return speedZoneTable[zone.getNumber()];
    }

    /**
     * 心拍ゾーンから色を取得する
     * @param zone
     * @return
     */
    public int getZoneColor(HeartrateZone zone) {
        return zoneColorTable[zone.getNumber()];
    }

    /**
     * 心拍ゾーンのタイトルを取得する
     * @param zone
     * @return
     */
    public String getZoneInfoText(HeartrateZone zone) {
        return heartrateZoneTable[zone.getNumber()];
    }

    /**
     * 心拍表示をビルドする
     * @param root
     * @param heartarate
     * @return
     */
    public View build(View root, MasterPayload master, RawHeartrate heartrate) {
        AQuery q = new AQuery(root);
        if (master.getCentralStatus().getConnectedHeartrate()) {
            q.id(R.id.AceUI_BasicUI_Value).text(String.valueOf(heartrate.getBpm()));
        } else {
            q.id(R.id.AceUI_BasicUI_Value).text(R.string.AceUI_Information_NotConnected);
        }
        q.id(R.id.AceUI_BasicUI_ZoneColor).backgroundColor(getZoneColor(heartrate.getHeartrateZone()));
        q.id(R.id.AceUI_BasicUI_ZoneTitle).text(getZoneInfoText(heartrate.getHeartrateZone()));
        return root;
    }

    /**
     * ケイデンス表示をビルドする
     * @param root
     * @param cadence
     * @return
     */
    public View build(View root, MasterPayload master, RawCadence cadence) {
        AQuery q = new AQuery(root);
        if (master.getCentralStatus().getConnectedCadence()) {
            q.id(R.id.AceUI_BasicUI_Value).text(String.valueOf(cadence.getRpm()));
        } else {
            q.id(R.id.AceUI_BasicUI_Value).text(R.string.AceUI_Information_NotConnected);
        }
        q.id(R.id.AceUI_BasicUI_ZoneColor).backgroundColor(getZoneColor(cadence.getCadenceZone()));
        q.id(R.id.AceUI_BasicUI_ZoneTitle).text(getZoneInfoText(cadence.getCadenceZone()));
        return root;
    }

    /**
     * 速度表示をビルドする
     * @param currentSpeedView
     * @param maxSpeedView
     * @param master
     * @param speed
     * @return
     */
    public void build(View currentSpeedView, View maxSpeedView, MasterPayload master, RawSpeed speed) {
        // 現在速度を指定
        if (currentSpeedView != null) {
            AQuery q = new AQuery(currentSpeedView);

            if (master.getCentralStatus().getConnectedSpeed()) {
                q.id(R.id.AceUI_BasicUI_Value).text(String.format("%.01f", speed.getSpeedKmPerHour()));
            } else {
                q.id(R.id.AceUI_BasicUI_Value).text(R.string.AceUI_Information_NotConnected);
            }
            q.id(R.id.AceUI_BasicUI_ZoneColor).backgroundColor(getZoneClor(speed.getSpeedZone()));
            q.id(R.id.AceUI_BasicUI_ZoneTitle).text(getZoneInfoText(speed.getSpeedZone()));
        }
        // 最高速度を指定
        if (maxSpeedView != null) {
            AQuery q = new AQuery(maxSpeedView);
            q.id(R.id.AceUI_BasicUI_Value).text(String.format("%.01f", speed.getMaxKmPerHour()));
        }
    }
}
