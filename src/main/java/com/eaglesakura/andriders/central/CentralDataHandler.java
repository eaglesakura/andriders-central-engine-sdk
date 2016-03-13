package com.eaglesakura.andriders.central;

import com.eaglesakura.andriders.internal.protocol.RawCentralData;

public abstract class CentralDataHandler {

    /**
     * サイコン情報が更新された場合に呼び出される
     */
    public void onReceived(RawCentralData newData) {
    }
}
