package com.eaglesakura.andriders.extension.display;

import com.eaglesakura.andriders.protocol.internal.InternalData;
import com.eaglesakura.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 行表示される値
 *
 * カロリー表示等に使用する。
 */
public class LineValue {
    public static final String TYPE = "LINE_INFORMATION";
    public static final int MAX_LINES = 3;

    List<InternalData.IdlCycleDisplayValue.KeyValue.Builder> values = new ArrayList<>();

    public LineValue(int lines) {
        if (lines > MAX_LINES) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < lines; ++i) {
            values.add(InternalData.IdlCycleDisplayValue.KeyValue.newBuilder());
        }
    }

    LineValue(List<InternalData.IdlCycleDisplayValue.KeyValue.Builder> values) {
        this.values = values;
    }

    /**
     * 指定したラインが有効であればtrue
     */
    public boolean valid(int line) {
        InternalData.IdlCycleDisplayValue.KeyValue.Builder v = values.get(line);
        return !StringUtil.isEmpty(v.getValue()) || !StringUtil.isEmpty(v.getTitle());
    }

    /**
     * いずれか1行でも有効であればtrue
     */
    public boolean valid() {
        for (int i = 0; i < values.size(); ++i) {
            if (valid(i)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 表示する行数を取得する
     */
    public int getLineNum() {
        return values.size();
    }

    public String getTitle(int index) {
        InternalData.IdlCycleDisplayValue.KeyValue.Builder builder = values.get(index);
        if (builder.hasTitle()) {
            return builder.getTitle();
        } else {
            return null;
        }
    }

    public String getValue(int index) {
        InternalData.IdlCycleDisplayValue.KeyValue.Builder builder = values.get(index);
        if (builder.hasValue()) {
            return builder.getValue();
        } else {
            return null;
        }
    }

    /**
     * 表示する値を指定する
     */
    public void setLine(int index, String title, String value) {
        InternalData.IdlCycleDisplayValue.KeyValue.Builder v = values.get(index);

        v.setTitle(title != null ? title : "");
        v.setValue(value != null ? value : "");
    }
}
