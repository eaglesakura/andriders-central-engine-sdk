package com.eaglesakura.andriders.internal.model;

/**
 * 横方向にタイトルと値を並べる表示
 * <p/>
 * カロリー表記等に使用する。
 */
public class BasicLineJson {
    public static final String TYPE_1_LINE = "LINE_INFORMATION_1";
    public static final String TYPE_2_LINE = "LINE_INFORMATION_2";
    public static final String TYPE_3_LINE = "LINE_INFORMATION_3";

    /**
     * タイトル
     */
    public String title;

    /**
     * 値
     */
    public String value;
}
