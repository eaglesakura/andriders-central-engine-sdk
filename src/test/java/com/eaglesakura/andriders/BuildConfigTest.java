package com.eaglesakura.andriders;

import com.eaglesakura.andriders.sdk.BuildConfig;
import com.eaglesakura.andriders.sdk.R;
import com.eaglesakura.thread.Holder;

import org.junit.Test;

public class BuildConfigTest extends UnitTestCase {
    @Test
    public void BuildConfig出力が行えている() throws Exception {
        assertNotEmpty(getContext().getString(R.string.Ace_Word_HeartrateZone_NonOxygenatedMotion));
        assertNotEmpty(BuildConfig.ACE_SDK_VERSION);
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