package com.eaglesakura.andriders.plugin.internal;

import com.eaglesakura.android.service.CommandClient;

import android.content.Context;

/**
 * ACEの動作Service本体との接続を行う。
 *
 * プラグインがACE(Client) -> Plugin(Server)に対し、
 * これはACE(Server) <- 対応アプリ(Client)を実現する。
 */
public class CentralClientImpl extends CommandClient {
    public CentralClientImpl(Context context) {
        super(context);
    }

}
