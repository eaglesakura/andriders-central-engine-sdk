package com.eaglesakura.ace.systemlayer;

import org.androidannotations.annotations.EActivity;

import android.app.Activity;
import android.os.Bundle;

import com.eaglesakura.ace.systemlayer.service.UiService;

@EActivity(R.layout.activity_main)
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // UIを起動して終了
        UiService.start(this);
        finish();
    }
}
