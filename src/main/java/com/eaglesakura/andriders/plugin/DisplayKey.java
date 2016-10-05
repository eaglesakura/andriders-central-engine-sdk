package com.eaglesakura.andriders.plugin;

import com.eaglesakura.andriders.serialize.PluginProtocol;
import com.eaglesakura.serialize.error.SerializeException;
import com.eaglesakura.util.CollectionUtil;
import com.eaglesakura.util.LogUtil;
import com.eaglesakura.util.SerializeUtil;
import com.eaglesakura.util.StringUtil;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * サイコンの表示情報を管理する
 */
public class DisplayKey {
    PluginProtocol.RawCycleDisplayInfo raw;

    private DisplayKey(PluginProtocol.RawCycleDisplayInfo raw) {
        if (raw == null) {
            raw = new PluginProtocol.RawCycleDisplayInfo();
        }
        this.raw = raw;
        makeDefault();
    }

    public DisplayKey(Context context, String id) {
        this(null);
        if (StringUtil.isEmpty(id) || id.indexOf('@') >= 0) {
            throw new IllegalArgumentException();
        }
        raw.id = (context.getPackageName() + "@" + id);
        makeDefault();
    }

    private void makeDefault() {
        if (StringUtil.isEmpty(raw.title)) {
            setTitle("Unknown");
        }
    }


    public String getId() {
        return raw.id;
    }

    public String getTitle() {
        return raw.title;
    }

    public void setTitle(String set) {
        raw.title = set;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DisplayKey that = (DisplayKey) o;

        return getId().equals(that.getId());

    }

    @Override
    public int hashCode() {
        return raw != null ? raw.hashCode() : 0;
    }

    /**
     * データをシリアライズする
     */
    private byte[] serialize() {
        try {
            return SerializeUtil.serializePublicFieldObject(raw);
        } catch (Exception e) {
            throw new IllegalStateException();
        }
    }

    public static byte[] serialize(List<DisplayKey> list) {
        if (CollectionUtil.isEmpty(list)) {
            return null;
        }

        List<byte[]> serializeList = new ArrayList<>();
        for (DisplayKey item : list) {
            serializeList.add(item.serialize());
        }
        return SerializeUtil.toByteArray(serializeList);
    }

    /**
     * バッファからデシリアライズする
     */
    public static List<DisplayKey> deserialize(byte[] buffer) {
        if (buffer == null) {
            return new ArrayList<>();
        }

        try {
            List<byte[]> serializeList = SerializeUtil.toByteArrayList(buffer);
            if (!CollectionUtil.isEmpty(serializeList)) {
                List<DisplayKey> result = new ArrayList<>();
                for (byte[] serialized : serializeList) {
                    result.add(new DisplayKey(SerializeUtil.deserializePublicFieldObject(PluginProtocol.RawCycleDisplayInfo.class, serialized)));
                }
                return result;
            }
        } catch (SerializeException e) {
            LogUtil.log(e);
        }
        return new ArrayList<>();
    }
}
