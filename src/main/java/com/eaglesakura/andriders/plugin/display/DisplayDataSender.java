package com.eaglesakura.andriders.plugin.display;

import com.eaglesakura.andriders.notification.NotificationData;
import com.eaglesakura.andriders.plugin.connection.PluginConnection;
import com.eaglesakura.andriders.plugin.internal.DisplayCommand;
import com.eaglesakura.andriders.plugin.internal.PluginServerImpl;
import com.eaglesakura.android.service.data.Payload;

import java.util.Arrays;
import java.util.List;

/**
 * Displayの表示データを送信する。
 *
 * 送信可能（正確には、表示可能）なのは、自分が提供を宣言したKeyのみである。
 */
public final class DisplayDataSender {
    final PluginConnection mSession;

    final PluginServerImpl mServerImpl;

    public DisplayDataSender(PluginConnection session, PluginServerImpl serverImpl) {
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
    public void setValue(List<DisplayData> dataList) {
        mServerImpl.validAcesSession();

        try {
            Payload payload = new Payload(DisplayData.serialize(dataList));
            mServerImpl.postToClient(DisplayCommand.CMD_setDisplayValue, payload);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 通知を表示する
     */
    public void queueNotification(NotificationData notify) {
        mServerImpl.validAcesSession();

        try {
            Payload payload = new Payload(notify.serialize());
            mServerImpl.postToClient(DisplayCommand.CMD_queueNotification, payload);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
