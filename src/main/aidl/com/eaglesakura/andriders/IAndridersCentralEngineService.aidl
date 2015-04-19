package com.eaglesakura.andriders;

interface IAndridersCentralEngineService {
    /**
     * データを送信する
     */
    byte[] sendRemoteMessage(in byte[] remoteMessageBuffer);
}