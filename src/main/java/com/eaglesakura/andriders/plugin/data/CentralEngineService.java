package com.eaglesakura.andriders.plugin.data;

import com.eaglesakura.andriders.plugin.internal.CentralClientImpl;
import com.eaglesakura.andriders.plugin.internal.CentralServiceCommand;
import com.eaglesakura.andriders.serialize.RawSessionInfo;
import com.eaglesakura.android.service.data.Payload;

import android.content.Context;
import android.support.annotation.Nullable;

/**
 * ACEのService本体とのデータバインドを行う
 */
public class CentralEngineService {
    final CentralClientImpl mClientImpl;

    final Context mContext;

    public CentralEngineService(Context context, CentralClientImpl clientImpl) {
        mContext = context;
        mClientImpl = clientImpl;
    }

    /**
     * 現在のセッション情報を取得する
     */
    @Nullable
    public RawSessionInfo getSessionInfo() {
        try {
            Payload payload = mClientImpl.requestPostToServer(CentralServiceCommand.CMD_getSessionInfo, null);
            return payload.deserializePublicField(RawSessionInfo.class);
        } catch (Exception e) {
            return null;
        }
    }
}
