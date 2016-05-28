package com.eaglesakura.andriders.plugin.display;

import com.eaglesakura.andriders.plugin.CentralEngineConnection;
import com.eaglesakura.andriders.plugin.internal.DisplayCommand;
import com.eaglesakura.andriders.plugin.internal.ExtensionServerImpl;
import com.eaglesakura.android.service.data.Payload;
import com.eaglesakura.util.LogUtil;

import java.util.Arrays;
import java.util.List;

/**
 * Displayの表示データを送信する。
 *
 * 送信可能（正確には、表示可能）なのは、自分が提供を宣言したKeyのみである。
 */
public final class DisplayDataSender {
    final CentralEngineConnection mSession;

    final ExtensionServerImpl mServerImpl;

    public DisplayDataSender(CentralEngineConnection session, ExtensionServerImpl serverImpl) {
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
