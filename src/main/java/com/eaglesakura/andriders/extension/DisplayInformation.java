package com.eaglesakura.andriders.extension;

import com.eaglesakura.andriders.idl.display.IdlCycleDisplayInfo;
import com.eaglesakura.android.db.BaseProperties;
import com.eaglesakura.util.StringUtil;
import com.eaglesakura.util.Util;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * サイコンの表示情報を管理する
 */
public class DisplayInformation {
    IdlCycleDisplayInfo raw;

    private DisplayInformation(IdlCycleDisplayInfo raw) {
        if (raw == null) {
            raw = new IdlCycleDisplayInfo(null);
        }
        this.raw = raw;
    }

    public DisplayInformation(Context context, String id) {
        this(null);
        if (StringUtil.isEmpty(id) || id.indexOf('@') >= 0) {
            throw new IllegalArgumentException();
        }
        raw.setId(context.getPackageName() + "@" + id);
    }

    public String getId() {
        return raw.getId();
    }

    public String getText() {
        return raw.getText();
    }

    public void setText(String set) {
        raw.setText(set);
    }

    public String getTitle() {
        return raw.getTitle();
    }

    public void setTitle(String set) {
        raw.setTitle(set);
    }

    /**
     * データをシリアライズする
     */
    public byte[] serialize() {
        return raw.toByteArray();
    }

    public static byte[] serialize(List<DisplayInformation> list) {
        if (Util.isEmpty(list)) {
            return null;
        }

        List<IdlCycleDisplayInfo> rawList = new ArrayList<>();
        for (DisplayInformation item : list) {
            rawList.add(item.raw);
        }
        return BaseProperties.serialize(rawList);
    }

    /**
     * バッファからデシリアライズする
     */
    public static List<DisplayInformation> deserialize(byte[] buffer) {
        if (buffer == null) {
            return new ArrayList<>();
        }

        List<IdlCycleDisplayInfo> rawList = BaseProperties.deserializeToArray(null, IdlCycleDisplayInfo.class, buffer);
        if (rawList != null) {
            List<DisplayInformation> result = new ArrayList<>();
            for (IdlCycleDisplayInfo raw : rawList) {
                result.add(new DisplayInformation(raw));
            }
            return result;
        } else {
            return new ArrayList<>();
        }
    }
}
