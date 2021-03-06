package com.eaglesakura.andriders.plugin.internal;

public class CentralDataCommand {

    /**
     * 心拍情報を更新する
     */
    public static final String CMD_setHeartrate = "CTL_setHeartrate";

    /**
     * スピード＆ケイデンスセンサー情報を更新する
     */
    public static final String CMD_setSpeedAndCadence = "CTL_setSpeedAndCadence";

    /**
     * BLEガジェットの接続先Macアドレスを取得する
     */
    public static final String CMD_setBleGadgetAddress = "CTL_setBleGadgetAddress";

    /**
     * ホイール外周サイズを取得する
     */
    public static final String CMD_getWheelOuterLength = "CTL_getWheelOuterLength";

    /**
     * 拡張情報を取得する
     */
    public static final String CMD_getInformations = "CTL_getInformations";

    /**
     * サイコン表示情報を取得する
     */
    public static final String CMD_getDisplayInformations = "CTL_getDisplayInformations";

    /**
     * ロケーションを更新する
     */
    public static final String CMD_setLocation = "CTL_setLocation";

    /**
     * 拡張機能が有効化された
     */
    public static final String CMD_onExtensionEnable = "CTL_onExtensionEnable";

    /**
     * 拡張機能が無効化された
     */
    public static final String CMD_onExtensionDisable = "CTL_onExtensionDisable";

    /**
     * 設定ボタンが押された
     */
    public static final String CMD_onSettingStart = "CTL_onSettingStart";

    /**
     * 自身のServiceを速やかに殺し、再起動を促す
     */
    public static final String CMD_requestRebootPlugin = "CTL_requestRebootPlugin";

    /**
     * SDKバージョンを取得する
     */
    public static final String CMD_getSDKVersion = "CTL_getSDKVersion";

    /**
     * ACEsの起動が完了した
     */
    public static final String CMD_onCentralBootCompleted = "CTL_onCentralBootCompleted";
}
