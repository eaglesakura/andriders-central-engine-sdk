package com.eaglesakura.andriders.plugin;

import com.eaglesakura.andriders.serialize.PluginProtocol;
import com.eaglesakura.serialize.PublicFieldSerializer;
import com.eaglesakura.serialize.error.SerializeException;
import com.eaglesakura.util.CollectionUtil;
import com.eaglesakura.util.LogUtil;
import com.eaglesakura.util.SerializeUtil;
import com.eaglesakura.util.StringUtil;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * 拡張情報
 */
public class PluginInformation {
    PluginProtocol.RawExtensionInfo raw;

    private PluginInformation(PluginProtocol.RawExtensionInfo raw) {
        if (raw == null) {
            raw = new PluginProtocol.RawExtensionInfo();
        }
        this.raw = raw;
        makeDefault();
    }

    public PluginInformation(Context context, String id) {
        this(null);
        if (id.indexOf('@') >= 0) {
            throw new IllegalArgumentException();
        }

        raw.id = (context.getPackageName() + "@" + id);
        makeDefault();
    }

    private void makeDefault() {
        if (StringUtil.isEmpty(raw.category)) {
            setCategory(Category.CATEGORY_OTHERS);
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
        raw.hasSetting = set;
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

    public Category getCategory() {
        return Category.fromName(raw.category);
    }

    public void setCategory(Category category) {
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

    public static byte[] serialize(List<PluginInformation> list) {
        List<byte[]> rawList = new ArrayList<>();
        for (PluginInformation item : list) {
            rawList.add(item.serialize());
        }
        return SerializeUtil.toByteArray(rawList);
    }

    /**
     * バッファからデシリアライズする
     */
    public static List<PluginInformation> deserialize(byte[] buffer) {
        try {
            List<byte[]> serializedBuffers = SerializeUtil.toByteArrayList(buffer);
            if (!CollectionUtil.isEmpty(serializedBuffers)) {
                List<PluginInformation> result = new ArrayList<>();
                for (byte[] serialized : serializedBuffers) {
                    result.add(new PluginInformation(SerializeUtil.deserializePublicFieldObject(PluginProtocol.RawExtensionInfo.class, serialized)));
                }
                return result;
            }
        } catch (SerializeException e) {
            LogUtil.log(e);
        }
        throw new IllegalArgumentException("deserialize failed");
    }
}
