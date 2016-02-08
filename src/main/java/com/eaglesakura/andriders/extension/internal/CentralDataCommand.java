package com.eaglesakura.andriders.extension.internal;

public class CentralDataCommand {

    /**
     * 心拍情報を更新する
     */
    public static final String CMD_setHeartrate = "CMD_setHeartrate";

    /**
     * スピード＆ケイデンスセンサー情報を更新する
     */
    public static final String CMD_setSpeedAndCadence = "CMD_setSpeedAndCadence";

    /**
     * BLEガジェットの接続先Macアドレスを取得する
     */
    public static final String CMD_setBleGadgetAddress = "CMD_setBleGadgetAddress";

    /**
     * 拡張情報を取得する
     */
    public static final String CMD_getInformations = "CMD_getInformations";

    /**
     * サイコン表示情報を取得する
     */
    public static final String CMD_getDisplayInformations = "CMD_getDisplayInformations";

    /**
     * ロケーションを更新する
     */
    public static final String CMD_setLocation = "CMD_setLocation";

    /**
     * 拡張機能が有効化された
     */
    public static final String CMD_onExtensionEnable = "CMD_onExtensionEnable";

    /**
     * 拡張機能が無効化された
     */
    public static final String CMD_onExtensionDisable = "CMD_onExtensionDisable";

    /**
     * 設定ボタンが押された
     */
    public static final String CMD_onSettingStart = "CMD_onSettingStart";

    /**
     * 自身のServiceを速やかに殺し、再起動を促す
     */
    public static final String CMD_requestRebootExtention = "CMD_requestRebootExtention";

    /**
     * SDKバージョンを取得する
     */
    public static final String CMD_getSDKVersion = "CMD_getSDKVersion";
}
