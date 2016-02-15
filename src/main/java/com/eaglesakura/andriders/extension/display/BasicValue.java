package com.eaglesakura.andriders.extension.display;

import com.eaglesakura.andriders.protocol.internal.InternalData;
import com.eaglesakura.util.StringUtil;

import android.graphics.Color;

/**
 * 心拍やケイデンス等の表示に用いられる、シンプルなディスプレイ内容
 *
 * 表示内容
 * * メイン値（画面中央）
 * * タイトル（画面下部）
 * * カラーバー（画面左もしくは右）
 * * バーインフォメーション（カラーバーの隣）
 */
public class BasicValue {
    public static final String TYPE = "BASIC_INFORMATION";
    InternalData.IdlCycleDisplayValue.BasicValue.Builder raw;

    public BasicValue() {
        this.raw = InternalData.IdlCycleDisplayValue.BasicValue.newBuilder();
        makeDefault();
    }

    BasicValue(InternalData.IdlCycleDisplayValue.BasicValue.Builder raw) {
        this.raw = raw;
        makeDefault();
    }

    private void makeDefault() {
        if (!raw.hasBarColorA()) {
            raw.setBarColorA(255);
            raw.setBarColorR(128);
            raw.setBarColorG(128);
            raw.setBarColorB(128);
        }
    }

    public int getBarColorARGB() {
        return Color.argb(raw.getBarColorA(), raw.getBarColorR(), raw.getBarColorG(), raw.getBarColorB());
    }

    public void setBarColorARGB(int barColorARGB) {
        raw.setBarColorA(Color.alpha(barColorARGB));
        raw.setBarColorR(Color.red(barColorARGB));
        raw.setBarColorG(Color.green(barColorARGB));
        raw.setBarColorB(Color.blue(barColorARGB));
    }

    /**
     * 表示可能である場合true
     */
    public boolean valid() {
        return !StringUtil.isEmpty(raw.getMain());
    }

    /**
     * メインの表示内容を取得する
     */
    public String getValue() {
        return raw.getMain();
    }

    public void setValue(String mainValue) {
        raw.setMain(mainValue);
    }

    /**
     * ゾーンテキスト（危険域、等）を取得する
     */
    public String getZoneText() {
        return raw.getBarInfo();
    }

    /**
     * ゾーンテキスト（心拍ゾーン等）を設定する
     */
    public void setZoneText(String infoText) {
        raw.setBarInfo(infoText);
    }

    /**
     * タイトル（心拍、ケイデンス等）を取得する
     */
    public String getTitle() {
        return raw.getTitle();
    }

    /**
     * タイトルを設定する
     */
    public void setTitle(String titleText) {
        raw.setTitle(titleText);
    }

}
