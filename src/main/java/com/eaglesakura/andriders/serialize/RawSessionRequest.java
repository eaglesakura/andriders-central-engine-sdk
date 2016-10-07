package com.eaglesakura.andriders.serialize;

import com.eaglesakura.serialize.Serialize;

/**
 * セッションの開始情報
 *
 * 将来のための予約
 */
public class RawSessionRequest {

    @Serialize(id = 1234)
    public long __dummy_data;

    public RawSessionRequest() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RawSessionRequest request = (RawSessionRequest) o;

        return __dummy_data == request.__dummy_data;

    }

    @Override
    public int hashCode() {
        return (int) (__dummy_data ^ (__dummy_data >>> 32));
    }
}
