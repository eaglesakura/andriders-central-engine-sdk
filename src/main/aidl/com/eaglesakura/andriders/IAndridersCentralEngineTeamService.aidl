package com.eaglesakura.andriders;

interface IAndridersCentralEngineTeamService {
    /**
     * データを送信する
     */
    byte[] sendRemoteMessage(in byte[] remoteMessageBuffer);
}