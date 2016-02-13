package com.eaglesakura.andriders.extension;

import com.eaglesakura.andriders.protocol.internal.InternalData;
import com.eaglesakura.android.service.data.Payload;
import com.eaglesakura.util.SerializeUtil;
import com.eaglesakura.util.Util;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * 拡張情報
 */
public class ExtensionInformation {
    InternalData.IdlExtensionInfo.Builder raw;

    private ExtensionInformation(InternalData.IdlExtensionInfo.Builder raw) {
        if (raw == null) {
            raw = InternalData.IdlExtensionInfo.newBuilder();
        }
        this.raw = raw;
        makeDefault();
    }

    public ExtensionInformation(Context context, String id) {
        this(null);
        if (id.indexOf('@') >= 0) {
            throw new IllegalArgumentException();
        }

        raw.setId(context.getPackageName() + "@" + id);
        makeDefault();
    }

    private void makeDefault() {
        if (!raw.hasCategory()) {
            setCategory(ExtensionCategory.CATEGORY_OTHERS);
        }
        if (!raw.hasHasSetting()) {
            setHasSetting(false);
        }
        if (!raw.hasActivated()) {
            setActivated(true);
        }
    }

    public String getId() {
        return raw.getId();
    }

    public void setSummary(String set) {
        raw.setSummary(set);
    }

    public String getSummary() {
        return raw.getSummary();
    }

    public boolean hasSetting() {
        return raw.getHasSetting();
    }

    /**
     * 拡張機能が設定画面を持っていたらtrue
     */
    public void setHasSetting(boolean set) {
        raw.setHasSetting(set);
    }

    /**
     * 拡張機能の使用準備ができていたらtrue
     */
    public void setActivated(boolean value) {
        raw.setActivated(value);
    }

    /**
     * 拡張機能の使用準備ができていたらtrue
     */
    public boolean isActivated() {
        return raw.getActivated();
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
        return raw.build().toByteArray();
    }

    public static byte[] serialize(List<ExtensionInformation> list) {
        List<byte[]> rawList = new ArrayList<>();
        for (ExtensionInformation item : list) {
            rawList.add(item.serialize());
        }
        return SerializeUtil.toByteArray(rawList);
    }

    /**
     * バッファからデシリアライズする
     */
    public static List<ExtensionInformation> deserialize(byte[] buffer) {
        List<byte[]> serializedBuffers = SerializeUtil.toByteArrayList(buffer);
        if (!Util.isEmpty(serializedBuffers)) {
            List<ExtensionInformation> result = new ArrayList<>();
            for (byte[] serialized : serializedBuffers) {
                result.add(new ExtensionInformation(Payload.deserializeMessageOrNull(InternalData.IdlExtensionInfo.class, serialized).toBuilder()));
            }
            return result;
        } else {
            throw new IllegalArgumentException("deserialize failed");
        }
    }
}
