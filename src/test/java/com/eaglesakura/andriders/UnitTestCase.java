package com.eaglesakura.andriders;

import com.eaglesakura.andriders.sdk.BuildConfig;
import com.eaglesakura.android.AndroidSupportTestCase;

import org.robolectric.annotation.Config;

@Config(constants = BuildConfig.class, packageName = BuildConfig.APPLICATION_ID, sdk = 23, manifest = "src/main/AndroidManifest.xml")
public abstract class UnitTestCase extends AndroidSupportTestCase {
}
