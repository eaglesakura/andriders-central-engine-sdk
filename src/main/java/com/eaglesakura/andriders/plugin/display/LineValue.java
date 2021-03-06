package com.eaglesakura.andriders.plugin.display;

import com.eaglesakura.andriders.serialize.PluginProtocol;
import com.eaglesakura.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 行表示される値
 *
 * カロリー表示等に使用する。
 */
public final class LineValue {
    public static final String TYPE = "LINE_INFORMATION";
    public static final int MAX_LINES = 3;

    List<PluginProtocol.RawCycleDisplayValue.KeyValue> values = new ArrayList<>();

    public LineValue(int lines) {
        if (lines > MAX_LINES) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < lines; ++i) {
            values.add(new PluginProtocol.RawCycleDisplayValue.KeyValue());
        }
    }

    LineValue(List<PluginProtocol.RawCycleDisplayValue.KeyValue> values) {
        this.values = values;
    }

    /**
     * 指定したラインが有効であればtrue
     */
    public boolean valid(int line) {
        PluginProtocol.RawCycleDisplayValue.KeyValue value = values.get(line);
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
        PluginProtocol.RawCycleDisplayValue.KeyValue value = values.get(index);
        if (!StringUtil.isEmpty(value.title)) {
            return value.title;
        } else {
            return null;
        }
    }

    public String getValue(int index) {
        PluginProtocol.RawCycleDisplayValue.KeyValue value = values.get(index);
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
        PluginProtocol.RawCycleDisplayValue.KeyValue v = values.get(index);

        v.title = (title != null ? title : "");
        v.value = (value != null ? value : "");
    }
}
