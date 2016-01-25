package com.eaglesakura.andriders.extension.display;

import com.eaglesakura.andriders.idl.display.IdlBasicDisplayValue;
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
    IdlBasicDisplayValue raw;

    public BasicValue() {
        this.raw = new IdlBasicDisplayValue(null);
    }

    private BasicValue(IdlBasicDisplayValue raw) {
        this.raw = raw;
    }

    public int getBarColorARGB() {
        return Color.argb(raw.getBarA(), raw.getBarR(), raw.getBarG(), raw.getBarB());
    }

    public void setBarColorARGB(int barColorARGB) {
        raw.setBarA(Color.alpha(barColorARGB));
        raw.setBarR(Color.red(barColorARGB));
        raw.setBarG(Color.green(barColorARGB));
        raw.setBarB(Color.blue(barColorARGB));
    }

    /**
     * 表示可能である場合true
     */
    public boolean valid() {
        return !StringUtil.isEmpty(raw.getMainValue());
    }

    public String getValue() {
        return raw.getMainValue();
    }

    public void setValue(String mainValue) {
        raw.setMainValue(mainValue);
    }

    public String getInformation() {
        return raw.getInfo();
    }

    public void setInformation(String infoText) {
        raw.setInfo(infoText);
    }

    public String getTitle() {
        return raw.getTitle();
    }

    public void setTitle(String titleText) {
        raw.setTitle(titleText);
    }

    /**
     * base64にエンコードする
     */
    public String encode() {
        return StringUtil.toString(raw.toByteArray());
    }

    public static BasicValue decode(String encoded) {
        try {
            IdlBasicDisplayValue newValue = new IdlBasicDisplayValue(null);
            newValue.fromByteArray(StringUtil.toByteArray(encoded));
            return new BasicValue(newValue);
        } catch (Exception e) {
            return null;
        }
    }
}
