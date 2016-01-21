package com.eaglesakura.andriders.internal.model;

public class BasicDisplayJson {
    /**
     * バーの色を指定する。
     * 0の場合、バーを非表示にする。
     */
    public int barColorARGB = 0;

    /**
     * 中央に表示する値のテキスト
     * <p/>
     * 大きく表示される。
     */
    public String mainValueText = "";

    /**
     * インフォメーション用の短いテキスト
     * <p/>
     * 「低速」「巡航」等の短いテキストを扱う。
     */
    public String infoText = "";

    /**
     * タイトル表記（中央下部）に表示するテキスト
     */
    public String titleText = "";
}
