package com.eaglesakura.andriders.plugin;

import com.eaglesakura.andriders.AceSdkUtil;
import com.eaglesakura.andriders.serialize.PluginProtocol;
import com.eaglesakura.util.CollectionUtil;
import com.eaglesakura.util.SerializeUtil;
import com.eaglesakura.util.StringUtil;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * サイコンの表示情報を管理する
 *
 * 管理IDにはアプリPackage名が含まれるため、プラグイン単位でUniqueであることを保証すれば、グローバルでもUniqueとなる。
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

    public void setTitle(@NonNull String set) {
        raw.title = set;
    }

    public String getSummary() {
        return raw.summary;
    }

    public void setSummary(@Nullable String summary) {
        raw.summary = summary;
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
            return AceSdkUtil.serializeToByteArray(raw);
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
                    PluginProtocol.RawCycleDisplayInfo displayInfo = AceSdkUtil.deserializeFromByteArray(PluginProtocol.RawCycleDisplayInfo.class, serialized);
                    result.add(new DisplayKey(displayInfo));
                }
                return result;
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
