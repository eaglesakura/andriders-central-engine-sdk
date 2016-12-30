package com.eaglesakura.andriders.plugin;

import com.eaglesakura.andriders.UnitTestCase;
import com.eaglesakura.andriders.sdk.BuildConfig;
import com.eaglesakura.util.CollectionUtil;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class PluginInformationTest extends UnitTestCase {

    @Test
    public void infoInitialize() {
        PluginInformation info = new PluginInformation(getContext(), "junit");
        assertTrue(info.getId().startsWith(getContext().getPackageName() + "@"));
        assertTrue(info.getId().endsWith("@junit"));
        assertEquals(info.getSdkVersion(), BuildConfig.ACE_SDK_VERSION);
        assertEquals(info.getSdkProtocolVersion(), BuildConfig.ACE_PROTOCOL_VERSION);

        assertEquals(info.getCategory(), Category.CATEGORY_OTHERS);
        assertFalse(info.hasSetting());
        assertTrue(info.isActivated());
    }

    @Test
    public void serializeTest() {
        PluginInformation info = new PluginInformation(getContext(), "junit");
        info.setCategory(Category.CATEGORY_OTHERS);
        byte[] serialized = info.serialize();
        assertNotNull(serialized);
    }

    @Test
    public void deserializeTest() {
        PluginInformation info = new PluginInformation(getContext(), "junit");
        info.setCategory(Category.CATEGORY_OTHERS);

        byte[] serialize = PluginInformation.serialize(Arrays.asList(info));
        assertFalse(CollectionUtil.isEmpty(serialize));

        List<PluginInformation> deserialize = PluginInformation.deserialize(serialize);
        assertEquals(deserialize.size(), 1);
        assertEquals(info.getId(), deserialize.get(0).getId());
    }
}
