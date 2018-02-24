package com.eaglesakura.andriders.plugin;

import com.eaglesakura.andriders.AceSdkUtil;
import com.eaglesakura.andriders.sdk.BuildConfig;
import com.eaglesakura.andriders.serialize.PluginProtocol;
import com.eaglesakura.util.CollectionUtil;
import com.eaglesakura.util.SerializeUtil;
import com.eaglesakura.util.StringUtil;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * 拡張情報
 */
public class PluginInformation {
    PluginProtocol.RawPluginInfo mRaw;

    private PluginInformation(PluginProtocol.RawPluginInfo raw) {
        if (raw == null) {
            raw = new PluginProtocol.RawPluginInfo();
        }
        this.mRaw = raw;
        makeDefault();
    }

    public PluginInformation(Context context, String id) {
        this(null);
        if (id.indexOf('@') >= 0) {
            throw new IllegalArgumentException();
        }

        mRaw.id = (context.getPackageName() + "@" + id);
        makeDefault();
    }

    private void makeDefault() {
        if (StringUtil.isEmpty(mRaw.category)) {
            setCategory(Category.CATEGORY_OTHERS);
        }

        // SDKのバージョンを明示する
        if (StringUtil.isEmpty(mRaw.sdkVersion)) {
            mRaw.sdkVersion = BuildConfig.ACE_SDK_VERSION;
            mRaw.sdkProtocolVersion = BuildConfig.ACE_PROTOCOL_VERSION;
        }
    }

    public String getId() {
        return mRaw.id;
    }

    public void setSummary(String set) {
        mRaw.summary = set;
    }

    public String getSummary() {
        return mRaw.summary;
    }

    public boolean hasSetting() {
        return mRaw.hasSetting;
    }

    /**
     * 拡張機能が設定画面を持っていたらtrue
     */
    public void setHasSetting(boolean set) {
        mRaw.hasSetting = set;
    }

    /**
     * 拡張機能の使用準備ができていたらtrue
     */
    public void setActivated(boolean value) {
        mRaw.activated = value;
    }

    /**
     * 拡張機能の使用準備ができていたらtrue
     */
    public boolean isActivated() {
        return mRaw.activated;
    }

    public Category getCategory() {
        return Category.fromName(mRaw.category);
    }

    public void setCategory(Category category) {
        mRaw.category = category.getName();
    }

    /**
     * ビルドされているSDKバージョンを取得する
     */
    @NonNull
    public String getSdkVersion() {
        return mRaw.sdkVersion;
    }

    /**
     * 互換バージョン番号を取得する
     */
    @NonNull
    public int getSdkProtocolVersion() {
        return mRaw.sdkProtocolVersion;
    }

    /**
     * データをシリアライズする
     */
    public byte[] serialize() {
        return AceSdkUtil.serializeToByteArray(mRaw);
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
                    PluginProtocol.RawPluginInfo pluginInfo = AceSdkUtil.deserializeFromByteArray(PluginProtocol.RawPluginInfo.class, serialized);
                    result.add(new PluginInformation(pluginInfo));
                }
                return result;
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("deserialize failed");
    }
}
