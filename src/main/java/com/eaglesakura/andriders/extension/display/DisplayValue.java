package com.eaglesakura.andriders.extension.display;

import com.eaglesakura.andriders.extension.DisplayInformation;
import com.eaglesakura.andriders.idl.display.IdlCycleDisplayValue;
import com.eaglesakura.android.db.BaseProperties;
import com.eaglesakura.util.StringUtil;
import com.eaglesakura.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * ディスプレイ表示内容を指定する
 */
public class DisplayValue {
    private IdlCycleDisplayValue raw;

    private BasicValue mBasicValue;

    private LineValue mLineValue;

    /**
     * @param bind 表示対象のディスプレイ情報
     */
    public DisplayValue(DisplayInformation bind) {
        this.raw = new IdlCycleDisplayValue(null);
        raw.setId(bind.getId());
    }

    private DisplayValue(IdlCycleDisplayValue raw) {
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

    public static byte[] serialize(List<DisplayValue> list) {
        if (Util.isEmpty(list)) {
            return null;
        }

        List<IdlCycleDisplayValue> rawList = new ArrayList<>();
        for (DisplayValue item : list) {
            rawList.add(item.raw);
        }
        return BaseProperties.serialize(rawList);
    }

    /**
     * バッファからデシリアライズする
     */
    public static List<DisplayValue> deserialize(byte[] buffer) {
        if (buffer == null) {
            return new ArrayList<>();
        }

        List<IdlCycleDisplayValue> rawList = BaseProperties.deserializeToArray(null, IdlCycleDisplayValue.class, buffer);
        if (rawList != null) {
            List<DisplayValue> result = new ArrayList<>();
            for (IdlCycleDisplayValue raw : rawList) {
                result.add(new DisplayValue(raw));
            }
            return result;
        } else {
            return new ArrayList<>();
        }
    }
}
