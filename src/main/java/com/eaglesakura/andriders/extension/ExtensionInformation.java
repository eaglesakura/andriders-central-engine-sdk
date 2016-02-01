package com.eaglesakura.andriders.extension;

import com.eaglesakura.andriders.idl.remote.IdlExtensionInfo;
import com.eaglesakura.android.db.BaseProperties;

import android.content.Context;

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

    public ExtensionInformation(Context context, String id) {
        this(null);
        if (id.indexOf('@') >= 0) {
            throw new IllegalArgumentException();
        }

        raw.setId(context.getPackageName() + "@" + id);
    }

    public String getId() {
        return raw.getId();
    }

    public void setText(String set) {
        raw.setText(set);
    }

    public String getText() {
        return raw.getText();
    }

    public boolean hasSetting() {
        return raw.getHasSetting();
    }

    public void setHasSetting(boolean set) {
        raw.setHasSetting(set);
    }

    public ExtensionCategory getCategory() {
        return ExtensionCategory.fromName(raw.getCategory());
    }

    public void setCategory(ExtensionCategory category) {
        raw.setCategory(category.getName());
    }

    /**
     * データをシリアライズする
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
