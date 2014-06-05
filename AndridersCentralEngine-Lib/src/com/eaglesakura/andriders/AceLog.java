package com.eaglesakura.andriders;

import android.util.Log;

public class AceLog {
    public static void i(String msg) {
        Log.d("ACE", msg);
    }

    public static void d(String msg) {
        Log.d("ACE", msg);
    }

    public static void d(Exception e) {
        e.printStackTrace();
    }
}
