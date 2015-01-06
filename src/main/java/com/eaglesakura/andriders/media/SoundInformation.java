package com.eaglesakura.andriders.media;

import java.util.ArrayList;
import java.util.List;

/**
 * サウンド情報
 */
public class SoundInformation {
    /**
     * 一意に識別するためのID
     */
    public String id;

    /**
     * サウンドセット名
     */
    public String name;

    /**
     * サウンド情報一覧
     */
    public List<Mapping> sounds = new ArrayList<Mapping>();

    /**
     * キーと表示名のマッピング
     */
    public static class Mapping {
        /**
         * サウンド用のキー
         */
        public String soundKey;

        /**
         * 表示名
         */
        public String name;
    }
}
