package com.eaglesakura.andriders.command;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * コマンドの発動タイミングを一意に指定するキーを取得する
 * <p/>
 * ユーザーの特定動作ごとに一意のCommandKeyが生成され、それに見合った動作を行う。
 */
public class CommandKey implements Parcelable {

    /**
     * 近接コマンドのヘッダ文字列
     */
    private static final String COMMAND_HEADER_PROXIMITY = "proximity#";

    /**
     * Activityコマンドのヘッダ文字列
     */
    private static final String COMMAND_HEADER_ACTIVITY = "activity#";

    /**
     * Activity/最高速度更新時
     */
    private static final String COMMAND_ID_ACTIVITY_MAXSPEED = "maxspeed#0";

    /**
     * Activity/停止時
     */
    private static final String COMMAND_ID_ACTIVITY_STOP = "stop#0";

    final String key;

    private CommandKey(String key) {
        this.key = key;
    }

    private CommandKey(Parcel in) {
        this.key = in.readString();
    }

    public String getKey() {
        return key;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(key);
    }

    public static final Parcelable.Creator<CommandKey> CREATOR
            = new Parcelable.Creator<CommandKey>() {
        public CommandKey createFromParcel(Parcel in) {
            return new CommandKey(in);
        }

        public CommandKey[] newArray(int size) {
            return new CommandKey[size];
        }
    };


    @Override
    public String toString() {
        return key;
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;

        CommandKey that = (CommandKey) o;

        if (key != null ? !key.equals(that.key) : that.key != null) return false;

        return true;
    }

    /**
     * 近接コマンドからキーを生成する
     *
     * @param commandSec
     * @return
     */
    public static CommandKey fromProximity(int commandSec) {
        return new CommandKey(String.format("%s%d", COMMAND_HEADER_PROXIMITY, commandSec));
    }

    /**
     * 文字列キーから生成する。基本的には復元用
     * @param key
     * @return
     */
    public static CommandKey fromString(String key) {
        return new CommandKey(key);
    }
}
