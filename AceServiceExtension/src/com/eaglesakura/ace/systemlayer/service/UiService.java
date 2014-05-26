package com.eaglesakura.ace.systemlayer.service;

import org.androidannotations.annotations.EService;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.eaglesakura.ace.systemlayer.service.ui.StatusbarUiManager;
import com.eaglesakura.ace.systemlayer.service.ui.SystemViewManager;
import com.eaglesakura.android.annotations.AnnotationUtil;

@EService
public class UiService extends Service {

    StatusbarUiManager statusbarUiManager;

    SystemViewManager systemView;

    @Override
    public void onCreate() {
        super.onCreate();

        statusbarUiManager = new StatusbarUiManager(this);
        statusbarUiManager.connect();

        systemView = new SystemViewManager(this);
        systemView.connect();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        systemView.disconnect();
        systemView = null;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static void start(Context context) {
        context = context.getApplicationContext();
        Intent intent = new Intent(context, AnnotationUtil.annotation(UiService.class));
        context.startService(intent);
    }

    public static void stop(Context context) {
        context = context.getApplicationContext();
        Intent intent = new Intent(context, AnnotationUtil.annotation(UiService.class));
        context.stopService(intent);
    }
}
