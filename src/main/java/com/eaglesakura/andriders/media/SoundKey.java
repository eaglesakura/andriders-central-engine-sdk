package com.eaglesakura.andriders.media;

import com.eaglesakura.android.util.ContextUtil;
import com.eaglesakura.util.StringUtil;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * サウンド
 */
public class SoundKey {

    String key;

    SoundKey(String key) {
        this.key = String.format("aces-%s", key);
    }

    public String getKey() {
        return key;
    }

    /**
     * 表示名を取得する
     */
    public String getText(Context context) {
        String result = ContextUtil.getStringFromIdName(context, String.format("Sound.Key.%s", getKey()));
        if (StringUtil.isEmpty(result)) {
            return getKey();
        } else {
            return result;
        }
    }

    /**
     * ACESを起動した
     */
    public static final SoundKey SYSTEM_BOOT_ACE_SERVICE;

    /**
     * 写真を撮影した
     */
    public static final SoundKey NOTIFICATION_CAPTURE_PHOTO;

    /**
     * 写真をツイートした
     */
    public static final SoundKey NOTIFICATION_TWITTER_TWEETED_PHOTO;

    /**
     * 低ケイデンスをキープしている
     */
    public static final SoundKey ACTIVITY_CADENCE_KEEP_SLOW;

    /**
     * 高ケイデンスをキープしている
     */
    public static final SoundKey ACTIVITY_CADENCE_KEEP_HIGH;

    /**
     * 最高速度を更新した
     */
    public static final SoundKey ACTIVITY_MAXSPEED;

    /**
     * 近接コマンドを起動した
     */
    public static final SoundKey COMMAND_PROXIMITY_BOOTED;

    /**
     * リモートセントラルに接続された
     */
    public static final SoundKey SYSTEM_REMOTECENTRAL_CONNECTED;

    /**
     * リモートセントラルから切断された
     */
    public static final SoundKey SYSTEM_REMOTECENTRAL_DISCONNECTED;

    /**
     * ハートレートモニターに接続した
     */
    public static final SoundKey SYSTEM_SENSOR_HRM_CONNECTED;

    /**
     * ハートレートモニターから切断された
     */
    public static final SoundKey SYSTEM_SENSOR_HRM_DISCONNECTED;

    /**
     * スピード＆ケイデンスセンサーに接続した
     */
    public static final SoundKey SYSTEM_SENSOR_SC_CONNECTED;

    /**
     * スピード＆ケイデンスセンサーから切断された
     */
    public static final SoundKey SYSTEM_SENSOR_SC_DISCONNECTED;

    /**
     * 巡航速度を保っている
     */
    public static final SoundKey ACTIVITY_CRUISE;

    /**
     * 当日合計 10km移動した
     */
    public static final SoundKey ACTIVITY_MOVE_10KM;

    /**
     * 当日合計 20km移動した
     */
    public static final SoundKey ACTIVITY_MOVE_20KM;

    /**
     * 当日合計 30km移動した
     */
    public static final SoundKey ACTIVITY_MOVE_30KM;

    /**
     * 当日合計 40km移動した
     */
    public static final SoundKey ACTIVITY_MOVE_40KM;

    /**
     * 当日合計 50km移動した
     */
    public static final SoundKey ACTIVITY_MOVE_50KM;

    /**
     * 何らかの通知を受け取った
     */
    public static final SoundKey NOTIFICATION_EXTRA_RECEIVED;

    /**
     * メンションを受け取った
     */
    public static final SoundKey NOTIFICATION_TWITTER_MENTION_RECEIVED;

    /**
     * ツイートを完了した
     */
    public static final SoundKey NOTIFICATION_TWITTER_TWEETED;

    /**
     * ビデオの録画を開始した
     */
    public static final SoundKey NOTIFICATION_CAMERA_RECORDVIDEO_START;

    /**
     * ビデオの録画を開始した
     */
    public static final SoundKey NOTIFICATION_CAMERA_RECORDVIDEO_EXTENSION;

    /**
     * ビデオの録画を停止した
     */
    public static final SoundKey NOTIFICATION_CAMERA_RECORDVIDEO_STOP;

    /**
     * 先頭交代
     */
    public static final SoundKey NOTIFICATION_TEAM_CHANGE_FIRST;

    private static final List<SoundKey> gSoundKeys;

    static {
        gSoundKeys = new ArrayList<>();

        gSoundKeys.add(SYSTEM_BOOT_ACE_SERVICE = new SoundKey("system-boot-ace-service"));
        gSoundKeys.add(NOTIFICATION_CAPTURE_PHOTO = new SoundKey("notification-capture-photo"));
        gSoundKeys.add(NOTIFICATION_TWITTER_TWEETED_PHOTO = new SoundKey("notification-tiwtter-tweeted-photo"));
        gSoundKeys.add(ACTIVITY_CADENCE_KEEP_SLOW = new SoundKey("activity-cadence-keep-slow"));
        gSoundKeys.add(ACTIVITY_CADENCE_KEEP_HIGH = new SoundKey("activity-cadence-keep-high"));
        gSoundKeys.add(ACTIVITY_MAXSPEED = new SoundKey("activity-maxspeed"));
        gSoundKeys.add(COMMAND_PROXIMITY_BOOTED = new SoundKey("command-proximity"));
        gSoundKeys.add(SYSTEM_REMOTECENTRAL_CONNECTED = new SoundKey("system-remotecentral-connected"));
        gSoundKeys.add(SYSTEM_REMOTECENTRAL_DISCONNECTED = new SoundKey("system-remotecentral-disconnected"));
        gSoundKeys.add(SYSTEM_SENSOR_HRM_CONNECTED = new SoundKey("system-sensor-hrm-connected"));
        gSoundKeys.add(SYSTEM_SENSOR_HRM_DISCONNECTED = new SoundKey("system-sensor-hrm-disconnected"));
        gSoundKeys.add(SYSTEM_SENSOR_SC_CONNECTED = new SoundKey("system-sensor-sc-connected"));
        gSoundKeys.add(SYSTEM_SENSOR_SC_DISCONNECTED = new SoundKey("system-sensor-sc-disconnected"));
        gSoundKeys.add(ACTIVITY_CRUISE = new SoundKey("activity-cruise"));
        gSoundKeys.add(ACTIVITY_MOVE_10KM = new SoundKey("activity-move-10km"));
        gSoundKeys.add(ACTIVITY_MOVE_20KM = new SoundKey("activity-move-20km"));
        gSoundKeys.add(ACTIVITY_MOVE_30KM = new SoundKey("activity-move-30km"));
        gSoundKeys.add(ACTIVITY_MOVE_40KM = new SoundKey("activity-move-40km"));
        gSoundKeys.add(ACTIVITY_MOVE_50KM = new SoundKey("activity-move-50km"));
        gSoundKeys.add(NOTIFICATION_EXTRA_RECEIVED = new SoundKey("notification-extra-received"));
        gSoundKeys.add(NOTIFICATION_TWITTER_MENTION_RECEIVED = new SoundKey("notification-twitter-mention-received"));
        gSoundKeys.add(NOTIFICATION_TWITTER_TWEETED = new SoundKey("notification-twitter-tweeted"));

        gSoundKeys.add(NOTIFICATION_CAMERA_RECORDVIDEO_START = new SoundKey("notification-camera-recordvideo-start"));
        gSoundKeys.add(NOTIFICATION_CAMERA_RECORDVIDEO_EXTENSION = new SoundKey("notification-camera-recordvideo-extension"));
        gSoundKeys.add(NOTIFICATION_CAMERA_RECORDVIDEO_STOP = new SoundKey("notification-camera-recordvideo-stop"));
        gSoundKeys.add(NOTIFICATION_TEAM_CHANGE_FIRST = new SoundKey("notification-team-change-first"));
    }


    /**
     * 一覧を取得する
     */
    public static List<SoundKey> list() {
        return new ArrayList<>(gSoundKeys);
    }
}
