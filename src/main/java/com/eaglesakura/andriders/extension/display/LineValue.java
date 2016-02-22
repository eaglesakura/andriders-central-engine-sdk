package com.eaglesakura.andriders.extension.display;

import com.eaglesakura.andriders.internal.protocol.IdlExtension;
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

    List<IdlExtension.CycleDisplayValue.KeyValue> values = new ArrayList<>();

    public LineValue(int lines) {
        if (lines > MAX_LINES) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < lines; ++i) {
            values.add(new IdlExtension.CycleDisplayValue.KeyValue());
        }
    }

    LineValue(List<IdlExtension.CycleDisplayValue.KeyValue> values) {
        this.values = values;
    }

    /**
     * 指定したラインが有効であればtrue
     */
    public boolean valid(int line) {
        IdlExtension.CycleDisplayValue.KeyValue value = values.get(line);
        return !StringUtil.isEmpty(value.value) || !StringUtil.isEmpty(value.title);
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
        IdlExtension.CycleDisplayValue.KeyValue value = values.get(index);
        if (!StringUtil.isEmpty(value.title)) {
            return value.title;
        } else {
            return null;
        }
    }

    public String getValue(int index) {
        IdlExtension.CycleDisplayValue.KeyValue value = values.get(index);
        if (!StringUtil.isEmpty(value.value)) {
            return value.value;
        } else {
            return null;
        }
    }

    /**
     * 表示する値を指定する
     */
    public void setLine(int index, String title, String value) {
        IdlExtension.CycleDisplayValue.KeyValue v = values.get(index);

        v.title = (title != null ? title : "");
        v.value = (value != null ? value : "");
    }
}
