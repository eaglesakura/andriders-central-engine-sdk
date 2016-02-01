package com.eaglesakura.andriders.extension.display;

import com.eaglesakura.andriders.idl.display.IdlLineDisplayValue;
import com.eaglesakura.android.db.BaseProperties;
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

    private List<IdlLineDisplayValue> values = new ArrayList<>();

    public LineValue(int lines) {
        if (lines > MAX_LINES) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < lines; ++i) {
            values.add(new IdlLineDisplayValue(null));
        }
    }

    private LineValue(List<IdlLineDisplayValue> values) {
        this.values = values;
    }

    /**
     * 指定したラインが有効であればtrue
     */
    public boolean valid(int line) {
        IdlLineDisplayValue v = values.get(line);
        return !StringUtil.isEmpty(v.getValue()) || !StringUtil.isEmpty(v.getTitle());
    }

    /**
     * いずれか1行でも有効であればtrue
     */
    public boolean valid() {
        for (int i = 0; i < values.size(); ++i) {
            if (valid()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 表示する値を指定する
     */
    public void setLine(int index, String title, String value) {
        IdlLineDisplayValue v = values.get(index);

        v.setTitle(title != null ? title : "");
        v.setValue(value != null ? value : "");
    }

    /**
     * 文字列エンコードする
     */
    public String encode() {
        return StringUtil.toString(BaseProperties.serialize(values));
    }

    public static LineValue decode(String encoded) {
        try {
            List<IdlLineDisplayValue> values = BaseProperties.deserializeToArray(null, IdlLineDisplayValue.class, StringUtil.toByteArray(encoded));
            // 最大行数を超えていたら、末尾を切り落とす
            while (values.size() > MAX_LINES) {
                values.remove(values.size() - 1);
            }

            return new LineValue(values);
        } catch (Exception e) {
            return null;
        }
    }
}
