package com.eaglesakura.andriders.extension.display;

import com.eaglesakura.andriders.extension.DisplayInformation;
import com.eaglesakura.andriders.internal.protocol.IdlExtension;
import com.eaglesakura.serialize.error.SerializeException;
import com.eaglesakura.util.LogUtil;
import com.eaglesakura.util.SerializeUtil;
import com.eaglesakura.util.Util;

import android.content.Context;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * ディスプレイ表示内容を指定する
 */
public class DisplayData {
    private IdlExtension.CycleDisplayValue raw;

    private BasicValue mBasicValue;

    private LineValue mLineValue;

    /**
     * @param bind 表示対象のディスプレイ情報
     */
    public DisplayData(DisplayInformation bind) {
        this.raw = new IdlExtension.CycleDisplayValue();
        raw.id = bind.getId();
        makeDefault();
    }

    /**
     * ディスプレイ
     */
    public DisplayData(Context context, String id) {
        this.raw = new IdlExtension.CycleDisplayValue();
        raw.id = (new DisplayInformation(context, id).getId());
        makeDefault();
    }


    protected DisplayData(IdlExtension.CycleDisplayValue raw) {
        this.raw = raw;

        if (raw.basicValue != null) {
            mBasicValue = new BasicValue(raw.basicValue);
        } else if (!Util.isEmpty(raw.keyValues) && raw.keyValues.size() <= LineValue.MAX_LINES) {
            mLineValue = new LineValue(raw.keyValues);
        }

        makeDefault();
    }

    private void makeDefault() {
    }

    /**
     * 表示が有効であればtrue
     */
    public boolean valid() {
        return mBasicValue != null || mLineValue != null;
    }

    public String getId() {
        return raw.id;
    }

    /**
     * 表示タイプを取得する
     *
     * @see BasicValue#TYPE
     * @see LineValue#TYPE
     */
    public String getType() {
        if (mBasicValue != null) {
            return BasicValue.TYPE;
        } else if (mLineValue != null) {
            return LineValue.TYPE;
        } else {
            return null;
        }
    }

    /**
     * 値が有効である期限を指定する
     *
     * この期間を過ぎると、値がN/A扱いとなる。
     * 負の値の場合、永続化する。
     */
    public void setTimeoutMs(int set) {
        raw.timeoutMs = set;
    }

    /**
     * タイムアウト時間が設定されている場合はtrue
     */
    public boolean hasTimeout() {
        return raw.timeoutMs > 0;
    }

    public int getTimeoutMs() {
        return raw.timeoutMs;
    }

    private void resetValue() {
        mBasicValue = null;
        mLineValue = null;
        raw.basicValue = null;
        raw.keyValues = null;
    }

    /**
     * 表示内容を指定する
     */
    public void setValue(BasicValue newValue) {
        resetValue();
        mBasicValue = newValue;
    }

    /**
     * 表示内容を指定する
     */
    public void setValue(LineValue newValue) {
        resetValue();
        mLineValue = newValue;
    }

    /**
     * 表示値を取得する
     */
    public BasicValue getBasicValue() {
        return mBasicValue;
    }

    /**
     * 表示値を取得する
     */
    public LineValue getLineValue() {
        return mLineValue;
    }

    private byte[] serialize() {
        if (mBasicValue != null) {
            raw.basicValue = mBasicValue.raw;
        } else if (mLineValue != null) {
            raw.keyValues = mLineValue.values;
        }

        try {
            return SerializeUtil.serializePublicFieldObject(raw);
        } catch (SerializeException e) {
            LogUtil.log(e);
            throw new IllegalStateException();
        }
    }

    public static byte[] serialize(List<DisplayData> list) {
        if (Util.isEmpty(list)) {
            return null;
        }

        List<byte[]> serializeList = new ArrayList<>();
        for (DisplayData item : list) {
            serializeList.add(item.serialize());
        }
        return SerializeUtil.toByteArray(serializeList);
    }

    /**
     * バッファからデシリアライズする
     */
    public static <T extends DisplayData> List<T> deserialize(byte[] buffer, Class<T> clazz) {
        if (buffer == null) {
            return new ArrayList<>();
        }

        try {
            Constructor<T> constructor = clazz.getConstructor(IdlExtension.CycleDisplayValue.class);

            List<byte[]> serializeList = SerializeUtil.toByteArrayList(buffer);
            if (!Util.isEmpty(serializeList)) {
                List<T> result = new ArrayList<>();
                for (byte[] serialized : serializeList) {
                    IdlExtension.CycleDisplayValue v = SerializeUtil.deserializePublicFieldObject(IdlExtension.CycleDisplayValue.class, serialized);
                    result.add(constructor.newInstance(v));
                }
                return result;
            } else {
                return new ArrayList<>();
            }
        } catch (Exception e) {
            throw new IllegalStateException();
        }
    }
}
