package com.eaglesakura.andriders.command;

import com.eaglesakura.util.StringUtil;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * コマンドの発動タイミングを一意に指定するキーを取得する
 * <br>
 * ユーザーの特定動作ごとに一意のCommandKeyが生成され、それに見合った動作を行う。
 */
public class CommandKey implements Parcelable {

    /**
     * 近接コマンドのヘッダ文字列
     */
    private static final String COMMAND_HEADER_PROXIMITY = "proximity#";

    /**
     * スピードコマンド用ヘッダ文字
     */
    private static final String COMMAND_HEADER_SPEED = "speed#";

    /**
     * 距離コマンド用ヘッダ文字
     */
    private static final String COMMAND_HEADER_DISTANCE = "distance#";

    /**
     * タイマーコマンド
     */
    private static final String COMMAND_HEADER_TIMER = "timer#";

    final String mKey;

    private CommandKey(String key) {
        this.mKey = key;
    }

    private CommandKey(Parcel in) {
        this.mKey = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mKey);
    }

    public static final Creator<CommandKey> CREATOR
            = new Creator<CommandKey>() {
        public CommandKey createFromParcel(Parcel in) {
            return new CommandKey(in);
        }

        public CommandKey[] newArray(int size) {
            return new CommandKey[size];
        }
    };


    /**
     * Keyを文字列に変換する。
     * <br>
     * 変換された文字列は、{@link #fromString(String)}で再度CommandKeyに変換できる。
     *
     * @return 変化されたキー
     */
    @Override
    public String toString() {
        return mKey;
    }

    @Override
    public int hashCode() {
        return mKey.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;

        CommandKey that = (CommandKey) o;

        if (mKey != null ? !mKey.equals(that.mKey) : that.mKey != null) return false;

        return true;
    }

    /**
     * IntentからKeyを取り出す
     *
     * @param intent ACEから渡されたIntent
     */
    @Nullable
    public static CommandKey fromIntent(Intent intent) {
        return intent.getParcelableExtra(CommandSetting.EXTRA_COMMAND_KEY);
    }

    /**
     * 近接コマンドからキーを生成する
     *
     * @param commandSec 秒数
     * @return キー
     */
    @NonNull
    public static CommandKey fromProximity(int commandSec) {
        return new CommandKey(StringUtil.format("%s%d", COMMAND_HEADER_PROXIMITY, commandSec));
    }

    /**
     * 文字列キーから生成する。基本的には復元用
     *
     * @param key キー文字列
     * @return キー
     */
    @NonNull
    public static CommandKey fromString(String key) {
        return new CommandKey(key);
    }

    /**
     * タイマーイベントのキーを生成する。
     * <br>
     * タイマーは複数指定できるため、追加された時刻をキーとする
     *
     * @param settingCurrentTime 設定するスロット番号
     * @return キー
     */
    @NonNull
    public static CommandKey fromTimer(long settingCurrentTime) {
        return new CommandKey(StringUtil.format("%s%d", COMMAND_HEADER_TIMER, settingCurrentTime));
    }

    /**
     * スピードコマンドのキーを生成する
     *
     * @param settingCurrentTime 現在の時刻
     * @return キー
     */
    @NonNull
    public static CommandKey fromSpeed(long settingCurrentTime) {
        return new CommandKey(StringUtil.format("%s%d", COMMAND_HEADER_SPEED, settingCurrentTime));
    }

    /**
     * 距離コマンドのキーを生成する
     *
     * @param settingCurrentTime 現在の時刻
     * @return キー
     */
    @NonNull
    public static CommandKey fromDistance(long settingCurrentTime) {
        return new CommandKey(StringUtil.format("%s%d", COMMAND_HEADER_DISTANCE, settingCurrentTime));
    }

}
