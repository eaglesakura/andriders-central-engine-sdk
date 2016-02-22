package com.eaglesakura.andriders.notification;

import com.eaglesakura.andriders.protocol.CommandProtocol;
import com.eaglesakura.util.StringUtil;

import android.net.Uri;

import java.io.File;

/**
 * 通知を行うためのサウンドデータ
 */
@Deprecated
public class SoundData {
    String soundKey;

    Uri uri;

    boolean queue = false;

    public SoundData(CommandProtocol.SoundNotificationPayload sound) {
        if (sound.hasSourceUri()) {
            this.uri = Uri.parse(sound.getSourceUri());
        }
        if (sound.hasSoundKey()) {
            this.soundKey = sound.getSoundKey();
        }
        this.queue = sound.getQueue();
    }

    public SoundData() {
    }

    /**
     * キューに追加する場合はtrue
     */
    public boolean isQueue() {
        return queue;
    }

    /**
     * URIを取得する
     */
    public Uri getUri() {
        return uri;
    }

    public String getSoundKey() {
        return soundKey;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public void setUri(File file) {
        this.uri = Uri.fromFile(file);
    }

    public void setSoundKey(String defaultSoundKey) {
        this.soundKey = defaultSoundKey;
    }

    /**
     * キューに貯める場合はtrue
     */
    public void setQueue(boolean queue) {
        this.queue = queue;
    }

    /**
     * ビルドを行う
     */
    public CommandProtocol.SoundNotificationPayload buildPayload() {
        CommandProtocol.SoundNotificationPayload.Builder builder = CommandProtocol.SoundNotificationPayload.newBuilder();
        builder.setQueue(queue);
        if (uri != null) {
            builder.setSourceUri(uri.toString());
        }
        if (!StringUtil.isEmpty(soundKey)) {
            builder.setSoundKey(soundKey);
        }
        return builder.build();
    }
}
