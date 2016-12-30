package com.eaglesakura.andriders;

import com.eaglesakura.andriders.sdk.BuildConfig;
import com.eaglesakura.andriders.sdk.R;
import com.eaglesakura.thread.Holder;

import org.junit.Test;

public class BuildConfigTest extends UnitTestCase {
    @Test
    public void helloContextTest() throws Exception {
        assertNotNull(getContext().getString(R.string.com_eaglesakura_andriders_category_TRIGGER_GEO));
        assertNotNull(BuildConfig.ACE_SDK_VERSION);
    }

    @Test
    public void ラムダ式を呼び出す() throws Exception {
        Holder<Boolean> holder = new Holder<>();
        holder.set(Boolean.FALSE);

        Runnable call = () -> {
            holder.set(Boolean.TRUE);
        };

        call.run();

        assertEquals(holder.get(), Boolean.TRUE);
    }
}