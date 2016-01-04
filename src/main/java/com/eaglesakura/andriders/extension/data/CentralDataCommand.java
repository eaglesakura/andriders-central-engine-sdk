package com.eaglesakura.andriders.extension.data;

public class CentralDataCommand {

    /**
     * 心拍情報を更新する
     */
    public static final String CMD_POST_HEARTRATE = "CMD_POST_HEARTRATE";

    /**
     * スピード＆ケイデンスセンサー情報を更新する
     */
    public static final String CMD_POST_SPEED_AND_CADENCE = "CMD_POST_SPEED_AND_CADENCE";

    /**
     * BLEガジェットの接続先Macアドレスを取得する
     */
    public static final String CMD_POST_QUERY_BLE_GADGET_ADDRESS = "CMD_POST_QUERY_BLE_GADGET_ADDRESS";

    /**
     * 拡張情報を取得する
     */
    public static final String CMD_PULL_EXTENSION_INFORMATION = "CMD_PULL_EXTENSION_INFORMATION";
}
