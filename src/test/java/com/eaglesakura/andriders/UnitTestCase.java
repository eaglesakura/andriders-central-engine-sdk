package com.eaglesakura.andriders;

import com.eaglesakura.andriders.sdk.BuildConfig;
import com.eaglesakura.android.AndroidSupportTestCase;

import org.junit.runner.RunWith;

import org.robolectric.annotation.Config;

@Config(constants = BuildConfig.class, packageName = BuildConfig.APPLICATION_ID, sdk = 23)
public abstract class UnitTestCase extends AndroidSupportTestCase {
    protected String TAG = getClass().getSimpleName();
}
