package com.eaglesakura.andriders;

import com.eaglesakura.andriders.IAndridersCentralEngineService;
import com.eaglesakura.andriders.IAndridersCentralEngineTeamService;

/**
 * Andriders Central Engine起動時に自動的にバインドされるServiceを定義する
 */
interface IAcesExtensionService {
    void    onConnectedAces(IAndridersCentralEngineService aces);

    void    onDisconnectedAces();

    void    onConnectedTeamAces(IAndridersCentralEngineTeamService teamAces);

    void    onDisconnectedTeamAces();
}