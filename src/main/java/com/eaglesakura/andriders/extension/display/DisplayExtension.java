package com.eaglesakura.andriders.extension.display;

import com.eaglesakura.andriders.extension.ExtensionSession;
import com.eaglesakura.andriders.extension.internal.DisplayCommand;
import com.eaglesakura.andriders.extension.internal.ExtensionServerImpl;
import com.eaglesakura.android.service.data.Payload;
import com.eaglesakura.util.LogUtil;

import java.util.Arrays;
import java.util.List;

/**
 * サイコンの表示データを拡張する
 */
public class DisplayExtension {
    final ExtensionSession mSession;

    final ExtensionServerImpl mServerImpl;

    public DisplayExtension(ExtensionSession session, ExtensionServerImpl serverImpl) {
        mServerImpl = serverImpl;
        mSession = session;
    }

    /**
     * 表示データを更新する
     */
    public void setValue(DisplayData data) {
        setValue(Arrays.asList(data));
    }

    /**
     * 表示データを複数更新する
     */
    public void setValue(List<DisplayData> datas) {
        mServerImpl.validAcesSession();

        try {
            Payload payload = new Payload(DisplayData.serialize(datas));
            mServerImpl.postToClient(DisplayCommand.CMD_setDisplayValue, payload);
        } catch (Exception e) {
            LogUtil.log(e);
        }
    }
}
