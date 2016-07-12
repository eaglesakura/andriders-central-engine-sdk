package com.eaglesakura.andriders;

import com.eaglesakura.andriders.serialize.RawIntent;
import com.eaglesakura.util.IOUtil;

import org.junit.runners.model.InitializationError;

import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.manifest.AndroidManifest;

import java.io.File;

/**
 * Manifestを規定ディレクトリにコピーするようにしたワークアラウンド
 */
public class RobolectricGradleLibraryTestRunner extends RobolectricGradleTestRunner {
    public RobolectricGradleLibraryTestRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }

    @Override
    protected AndroidManifest getAppManifest(Config config) {
        AndroidManifest manifest = super.getAppManifest(config);
        try {
            File src = new File(config.manifest());
            File dst = new File(manifest.getAndroidManifestFile().getPath());
            IOUtil.copy(src, dst);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        return manifest;
    }
}
