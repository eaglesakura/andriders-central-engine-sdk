package com.eaglesakura.andriders.ui;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;

import com.androidquery.AQuery;
import com.eaglesakura.andriders.R;
import com.eaglesakura.andriders.protocol.SensorProtocol.RawCadence;
import com.eaglesakura.andriders.protocol.SensorProtocol.RawCadence.CadenceZone;
import com.eaglesakura.andriders.protocol.SensorProtocol.RawHeartrate;
import com.eaglesakura.andriders.protocol.SensorProtocol.RawHeartrate.HeartrateZone;

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
     * ケイデンスUIタイトル
     */
    private static String cadenceUiTitle;

    /**
     * 心拍ゾーンのタイトル
     */
    private static String[] cadenceZoneTable;
    /**
     * 心拍UIタイトル
     */
    private static String heartrateUiTitle;

    /**
     * 心拍ゾーンのタイトル
     */
    private static String[] heartrateZoneTable;

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
            cadenceUiTitle = res.getString(R.string.AceUI_Information_Cadence);
            cadenceZoneTable = new String[] {
                    res.getString(R.string.AceUI_Zone_Cadence_Easy), // 軽度
                    res.getString(R.string.AceUI_Zone_Cadence_Beginner), // ビギナー
                    res.getString(R.string.AceUI_Zone_Cadence_Ideal), // 巡航
            };
        }
        // 心拍
        {
            heartrateUiTitle = res.getString(R.string.AceUI_Information_Heartrate);
            heartrateZoneTable = new String[] {
                    res.getString(R.string.AceUI_Zone_Heartrate_Repose), // 安静
                    res.getString(R.string.AceUI_Zone_Heartrate_Easy), // 軽度
                    res.getString(R.string.AceUI_Zone_Heartrate_FatCombustion), // 脂肪燃焼
                    res.getString(R.string.AceUI_Zone_Heartrate_PossessionOxygenMotion), // 有酸素
                    res.getString(R.string.AceUI_Zone_Heartrate_NonOxygenatedMotion), // 無酸素
                    res.getString(R.string.AceUI_Zone_Heartrate_Overwork), // 危険域
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
     * ケイデンスゾーンのタイトルを取得する
     * @param zone
     * @return
     */
    public String getZoneInfoText(CadenceZone zone) {
        return cadenceZoneTable[zone.getNumber()];
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
    public View build(View root, RawHeartrate heartrate) {
        AQuery q = new AQuery(root);
        q.id(R.id.AceUI_BasicUI_Title).text(heartrateUiTitle);
        q.id(R.id.AceUI_BasicUI_Value).text(String.valueOf(heartrate.getBpm()));
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
    public View build(View root, RawCadence cadence) {
        AQuery q = new AQuery(root);
        q.id(R.id.AceUI_BasicUI_Title).text(cadenceUiTitle);
        q.id(R.id.AceUI_BasicUI_Value).text(String.valueOf(cadence.getRpm()));
        q.id(R.id.AceUI_BasicUI_ZoneColor).backgroundColor(getZoneColor(cadence.getCadenceZone()));
        q.id(R.id.AceUI_BasicUI_ZoneTitle).text(getZoneInfoText(cadence.getCadenceZone()));
        return root;
    }
}
