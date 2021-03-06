package com.eaglesakura.andriders.plugin.display;

import com.eaglesakura.andriders.serialize.PluginProtocol;
import com.eaglesakura.util.StringUtil;

import android.graphics.Color;
import android.support.annotation.ColorInt;

/**
 * 心拍やケイデンス等の表示に用いられる、シンプルなディスプレイ内容
 *
 * 表示内容
 * * メイン値（画面中央）
 * * タイトル（画面下部）
 * * カラーバー（画面左もしくは右）
 * * バーインフォメーション（カラーバーの隣）
 */
public final class BasicValue {
    public static final String TYPE = "BASIC_INFORMATION";
    PluginProtocol.RawCycleDisplayValue.BasicValue raw;

    public BasicValue() {
        this.raw = new PluginProtocol.RawCycleDisplayValue.BasicValue();
        makeDefault();
    }

    BasicValue(PluginProtocol.RawCycleDisplayValue.BasicValue raw) {
        this.raw = raw;
        makeDefault();
    }

    private void makeDefault() {
    }

    public int getBarColorARGB() {
        return Color.argb(raw.barColorA & 0xFF, raw.barColorR & 0xFF, raw.barColorG & 0xFF, raw.barColorB & 0xFF);
    }

    public boolean hasZoneBar() {
        return raw.barColorA > 0;
    }

    public void setBarColorARGB(@ColorInt int barColorARGB) {
        raw.barColorA = (short) (Color.alpha(barColorARGB));
        raw.barColorR = (short) (Color.red(barColorARGB));
        raw.barColorG = (short) (Color.green(barColorARGB));
        raw.barColorB = (short) (Color.blue(barColorARGB));
    }

    /**
     * 表示可能である場合true
     */
    public boolean valid() {
        return !StringUtil.isEmpty(raw.main);
    }

    /**
     * メインの表示内容を取得する
     */
    public String getValue() {
        return raw.main;
    }

    public void setValue(String mainValue) {
        raw.main = mainValue;
    }

    /**
     * ゾーンテキスト（危険域、等）を取得する
     */
    public String getZoneText() {
        return raw.zoneText;
    }

    /**
     * ゾーンテキスト（心拍ゾーン等）を設定する
     */
    public void setZoneText(String infoText) {
        raw.zoneText = infoText;
    }

    /**
     * タイトル（心拍、ケイデンス等）を取得する
     */
    public String getTitle() {
        return raw.title;
    }

    /**
     * タイトルを設定する
     */
    public void setTitle(String titleText) {
        raw.title = titleText;
    }

}
