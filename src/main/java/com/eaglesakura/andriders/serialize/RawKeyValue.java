package com.eaglesakura.andriders.serialize;

import com.eaglesakura.util.RandomUtil;

import android.support.annotation.NonNull;

import java.util.Random;

/**
 * シンプルなKey-Value
 */
public class RawKeyValue {

    @NonNull
    public String key;


    @NonNull
    public String value;

    public RawKeyValue() {
    }

    public RawKeyValue(@NonNull String key, @NonNull String value) {
        this.key = key;
        this.value = value;
    }

    @Deprecated
    public RawKeyValue(Class<Random> dummy) {
        key = RandomUtil.randShortString();
        value = RandomUtil.randShortString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RawKeyValue that = (RawKeyValue) o;

        if (key != null ? !key.equals(that.key) : that.key != null) return false;
        return value != null ? value.equals(that.value) : that.value == null;

    }

    @Override
    public int hashCode() {
        int result = key != null ? key.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
