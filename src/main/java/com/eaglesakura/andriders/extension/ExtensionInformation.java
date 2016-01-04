package com.eaglesakura.andriders.extension;

import android.app.Activity;

import com.eaglesakura.andriders.idl.remote.IdlExtensionInfo;
import com.eaglesakura.android.db.BaseProperties;
import com.eaglesakura.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * 拡張情報
 */
public class ExtensionInformation {
    IdlExtensionInfo raw;

    private ExtensionInformation(IdlExtensionInfo raw) {
        if (raw == null) {
            raw = new IdlExtensionInfo(null);
        }
        this.raw = raw;
    }

    public ExtensionInformation() {
        this(null);
    }

    public void setName(String set) {
        raw.setName(set);
    }

    public String getName() {
        return raw.getName();
    }

    public void setText(String set) {
        raw.setText(set);
    }

    public String getText() {
        return raw.getText();
    }

    public void setSettingActivityName(Class<? extends Activity> clazz) {
        raw.setSettingActivityName(clazz.getName());
    }

    public String getSettingActivityName() {
        return raw.getSettingActivityName();
    }


    public ExtensionCategory getCategory() {
        return ExtensionCategory.fromName(raw.getCategory());
    }

    public void setCategory(ExtensionCategory category) {
        raw.setCategory(category.getName());
    }

    /**
     * データをシリアライズする
     *
     * @return
     */
    public byte[] serialize() {
        return raw.toByteArray();
    }

    public static byte[] serialize(List<ExtensionInformation> list) {
        List<IdlExtensionInfo> rawList = new ArrayList<>();
        for (ExtensionInformation item : list) {
            rawList.add(item.raw);
        }
        return BaseProperties.serialize(rawList);
    }

    /**
     * バッファからデシリアライズする
     *
     * @param buffer
     * @return
     */
    public static List<ExtensionInformation> deserialize(byte[] buffer) {
        List<IdlExtensionInfo> rawList = BaseProperties.deserializeToArray(null, IdlExtensionInfo.class, buffer);
        if (rawList != null) {
            List<ExtensionInformation> result = new ArrayList<>();
            for (IdlExtensionInfo raw : rawList) {
                result.add(new ExtensionInformation(raw));
            }
            return result;
        } else {
            throw new IllegalArgumentException("deserialize failed");
        }
    }
}
