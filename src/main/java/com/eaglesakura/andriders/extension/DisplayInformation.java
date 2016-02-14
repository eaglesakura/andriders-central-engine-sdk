package com.eaglesakura.andriders.extension;

import com.eaglesakura.andriders.protocol.internal.InternalData;
import com.eaglesakura.android.service.data.Payload;
import com.eaglesakura.util.SerializeUtil;
import com.eaglesakura.util.StringUtil;
import com.eaglesakura.util.Util;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * サイコンの表示情報を管理する
 */
public class DisplayInformation {
    InternalData.IdlCycleDisplayInfo.Builder raw;

    private DisplayInformation(InternalData.IdlCycleDisplayInfo.Builder raw) {
        if (raw == null) {
            raw = InternalData.IdlCycleDisplayInfo.newBuilder();
        }
        this.raw = raw;
        makeDefault();
    }

    public DisplayInformation(Context context, String id) {
        this(null);
        if (StringUtil.isEmpty(id) || id.indexOf('@') >= 0) {
            throw new IllegalArgumentException();
        }
        raw.setId(context.getPackageName() + "@" + id);
        makeDefault();
    }

    private void makeDefault() {
        raw.setTitle("Unknown");
    }

    public String getId() {
        return raw.getId();
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
    private byte[] serialize() {
        return raw.build().toByteArray();
    }

    public static byte[] serialize(List<DisplayInformation> list) {
        if (Util.isEmpty(list)) {
            return null;
        }

        List<byte[]> serializeList = new ArrayList<>();
        for (DisplayInformation item : list) {
            serializeList.add(item.serialize());
        }
        return SerializeUtil.toByteArray(serializeList);
    }

    /**
     * バッファからデシリアライズする
     */
    public static List<DisplayInformation> deserialize(byte[] buffer) {
        if (buffer == null) {
            return new ArrayList<>();
        }

        List<byte[]> serializeList = SerializeUtil.toByteArrayList(buffer);
        if (!Util.isEmpty(serializeList)) {
            List<DisplayInformation> result = new ArrayList<>();
            for (byte[] serialized : serializeList) {
                result.add(new DisplayInformation(Payload.deserializeMessageOrNull(InternalData.IdlCycleDisplayInfo.class, serialized).toBuilder()));
            }
            return result;
        } else {
            return new ArrayList<>();
        }
    }
}
