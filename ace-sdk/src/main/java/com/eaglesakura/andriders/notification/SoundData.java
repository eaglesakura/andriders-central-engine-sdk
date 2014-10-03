package com.eaglesakura.andriders.notification;

import android.net.Uri;

import com.eaglesakura.andriders.protocol.CommandProtocol;

import java.io.File;

/**
 * 通知を行うためのサウンドデータ
 */
public class SoundData {
    String defaultSoundKey;

    Uri uri;

    boolean queue = false;

    public SoundData(CommandProtocol.SoundNotificationPayload sound) {
        if (sound.hasSourceUri()) {
            this.uri = Uri.parse(sound.getSourceUri());
        }
        if (sound.hasDefaultSoundKey()) {
            this.defaultSoundKey = sound.getDefaultSoundKey();
        }
        this.queue = sound.getQueue();
    }

    public SoundData() {
    }

    /**
     * キューに追加する場合はtrue
     *
     * @return
     */
    public boolean isQueue() {
        return queue;
    }

    /**
     * URIを取得する
     *
     * @return
     */
    public Uri getUri() {
        return uri;
    }

    public String getDefaultSoundKey() {
        return defaultSoundKey;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public void setUri(File file) {
        this.uri = Uri.fromFile(file);
    }

    public void setDefaultSoundKey(String defaultSoundKey) {
        this.defaultSoundKey = defaultSoundKey;
    }

    /**
     * キューに貯める場合はtrue
     *
     * @param queue
     */
    public void setQueue(boolean queue) {
        this.queue = queue;
    }

    /**
     * ビルドを行う
     *
     * @return
     */
    public CommandProtocol.SoundNotificationPayload buildPayload() {
        CommandProtocol.SoundNotificationPayload.Builder builder = CommandProtocol.SoundNotificationPayload.newBuilder();
        builder.setQueue(queue);
        builder.setSourceUri(uri.toString());
        builder.setDefaultSoundKey(defaultSoundKey);
        return builder.build();
    }
}
