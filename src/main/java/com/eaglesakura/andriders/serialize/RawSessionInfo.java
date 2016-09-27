package com.eaglesakura.andriders.serialize;

import com.eaglesakura.serialize.Serialize;
import com.eaglesakura.util.RandomUtil;

import java.util.Random;

/**
 * セッション情報
 */
public class RawSessionInfo {

    /**
     * セッション情報
     *
     * 開始時刻として扱うことができる
     */
    @Serialize(id = 1)
    public long sessionId;

    public RawSessionInfo(long sessionId) {
        this.sessionId = sessionId;
    }

    public RawSessionInfo() {
    }

    public RawSessionInfo(Class<Random> dummy) {
        sessionId = RandomUtil.randUInt64();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RawSessionInfo that = (RawSessionInfo) o;

        return sessionId == that.sessionId;

    }

    @Override
    public int hashCode() {
        return (int) (sessionId ^ (sessionId >>> 32));
    }
}
