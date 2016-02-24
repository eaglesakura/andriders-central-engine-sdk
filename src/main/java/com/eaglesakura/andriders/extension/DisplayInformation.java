package com.eaglesakura.andriders.extension;

import com.eaglesakura.andriders.internal.protocol.ExtensionProtocol;
import com.eaglesakura.serialize.error.SerializeException;
import com.eaglesakura.util.LogUtil;
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
    ExtensionProtocol.RawCycleDisplayInfo raw;

    private DisplayInformation(ExtensionProtocol.RawCycleDisplayInfo raw) {
        if (raw == null) {
            raw = new ExtensionProtocol.RawCycleDisplayInfo();
        }
        this.raw = raw;
        makeDefault();
    }

    public DisplayInformation(Context context, String id) {
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

        try {
            List<byte[]> serializeList = SerializeUtil.toByteArrayList(buffer);
            if (!Util.isEmpty(serializeList)) {
                List<DisplayInformation> result = new ArrayList<>();
                for (byte[] serialized : serializeList) {
                    result.add(new DisplayInformation(SerializeUtil.deserializePublicFieldObject(ExtensionProtocol.RawCycleDisplayInfo.class, serialized)));
                }
                return result;
            }
        } catch (SerializeException e) {
            LogUtil.log(e);
        }
        return new ArrayList<>();
    }
}
