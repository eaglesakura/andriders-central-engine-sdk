package com.eaglesakura.andriders.extension.display;

import com.eaglesakura.andriders.extension.DisplayInformation;
import com.eaglesakura.andriders.idl.display.IdlCycleDisplayValue;
import com.eaglesakura.android.db.BaseProperties;
import com.eaglesakura.util.StringUtil;
import com.eaglesakura.util.Util;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * ディスプレイ表示内容を指定する
 */
public class DisplayData {
    private IdlCycleDisplayValue raw;

    private BasicValue mBasicValue;

    private LineValue mLineValue;

    /**
     * @param bind 表示対象のディスプレイ情報
     */
    public DisplayData(DisplayInformation bind) {
        this.raw = new IdlCycleDisplayValue(null);
        raw.setId(bind.getId());
    }

    protected DisplayData(IdlCycleDisplayValue raw) {
        this.raw = raw;

        if (!StringUtil.isEmpty(raw.getType())) {
            if (raw.getType().equals(BasicValue.TYPE)) {
                mBasicValue = BasicValue.decode(raw.getValues());
            } else if (raw.getType().equals(LineValue.TYPE)) {
                mLineValue = LineValue.decode(raw.getValues());
            }
        }
    }

    /**
     * 表示が有効であればtrue
     */
    public boolean valid() {
        return !StringUtil.isEmpty(raw.getType());
    }

    public String getId() {
        return raw.getId();
    }

    public String getType() {
        return raw.getType();
    }

    public void setTimeoutMs(long set) {
        raw.setTimeoutMs(set);
    }

    public long getTimeoutMs() {
        return raw.getTimeoutMs();
    }

    private void resetValue() {
        raw.setValues("");
        raw.setType("");

        mBasicValue = null;
        mLineValue = null;
    }

    /**
     * 表示内容を指定する
     */
    public void setValue(BasicValue newValue) {
        resetValue();
        raw.setValues(newValue.encode());
        raw.setType(BasicValue.TYPE);
        mBasicValue = newValue;
    }

    /**
     * 表示内容を指定する
     */
    public void setValue(LineValue newValue) {
        resetValue();
        raw.setValues(newValue.encode());
        raw.setType(LineValue.TYPE);
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

    public static byte[] serialize(List<DisplayData> list) {
        if (Util.isEmpty(list)) {
            return null;
        }

        List<IdlCycleDisplayValue> rawList = new ArrayList<>();
        for (DisplayData item : list) {
            rawList.add(item.raw);
        }
        return BaseProperties.serialize(rawList);
    }

    /**
     * バッファからデシリアライズする
     */
    public static <T extends DisplayData> List<T> deserialize(byte[] buffer, Class<T> clazz) {
        if (buffer == null) {
            return new ArrayList<>();
        }

        try {
            Constructor<T> constructor = clazz.getConstructor(IdlCycleDisplayValue.class);

            List<IdlCycleDisplayValue> rawList = BaseProperties.deserializeToArray(null, IdlCycleDisplayValue.class, buffer);
            if (rawList != null) {
                List<T> result = new ArrayList<>();
                for (IdlCycleDisplayValue raw : rawList) {
                    result.add(constructor.newInstance(raw));
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
