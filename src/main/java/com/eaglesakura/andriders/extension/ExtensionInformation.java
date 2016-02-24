package com.eaglesakura.andriders.extension;

import com.eaglesakura.andriders.internal.protocol.ExtensionProtocol;
import com.eaglesakura.serialize.PublicFieldSerializer;
import com.eaglesakura.serialize.error.SerializeException;
import com.eaglesakura.util.LogUtil;
import com.eaglesakura.util.SerializeUtil;
import com.eaglesakura.util.StringUtil;
import com.eaglesakura.util.Util;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * 拡張情報
 */
public class ExtensionInformation {
    ExtensionProtocol.RawExtensionInfo raw;

    private ExtensionInformation(ExtensionProtocol.RawExtensionInfo raw) {
        if (raw == null) {
            raw = new ExtensionProtocol.RawExtensionInfo();
        }
        this.raw = raw;
        makeDefault();
    }

    public ExtensionInformation(Context context, String id) {
        this(null);
        if (id.indexOf('@') >= 0) {
            throw new IllegalArgumentException();
        }

        raw.id = (context.getPackageName() + "@" + id);
        makeDefault();
    }

    private void makeDefault() {
        if (StringUtil.isEmpty(raw.category)) {
            setCategory(ExtensionCategory.CATEGORY_OTHERS);
        }
    }

    public String getId() {
        return raw.id;
    }

    public void setSummary(String set) {
        raw.summary = set;
    }

    public String getSummary() {
        return raw.summary;
    }

    public boolean hasSetting() {
        return raw.hasSetting;
    }

    /**
     * 拡張機能が設定画面を持っていたらtrue
     */
    public void setHasSetting(boolean set) {
        raw.hasSetting = true;
    }

    /**
     * 拡張機能の使用準備ができていたらtrue
     */
    public void setActivated(boolean value) {
        raw.activated = value;
    }

    /**
     * 拡張機能の使用準備ができていたらtrue
     */
    public boolean isActivated() {
        return raw.activated;
    }

    public ExtensionCategory getCategory() {
        return ExtensionCategory.fromName(raw.category);
    }

    public void setCategory(ExtensionCategory category) {
        raw.category = category.getName();
    }

    /**
     * データをシリアライズする
     */
    public byte[] serialize() {
        try {
            return new PublicFieldSerializer().serialize(raw);
        } catch (Exception e) {
            LogUtil.log(e);
            throw new IllegalStateException();
        }
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
        try {
            List<byte[]> serializedBuffers = SerializeUtil.toByteArrayList(buffer);
            if (!Util.isEmpty(serializedBuffers)) {
                List<ExtensionInformation> result = new ArrayList<>();
                for (byte[] serialized : serializedBuffers) {
                    result.add(new ExtensionInformation(SerializeUtil.deserializePublicFieldObject(ExtensionProtocol.RawExtensionInfo.class, serialized)));
                }
                return result;
            }
        } catch (SerializeException e) {
            LogUtil.log(e);
        }
        throw new IllegalArgumentException("deserialize failed");
    }
}
