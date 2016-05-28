package com.eaglesakura.andriders.plugin;

import com.eaglesakura.andriders.UnitTestCase;
import com.eaglesakura.util.CollectionUtil;

import junit.framework.Assert;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class ExtensionInformationTest extends UnitTestCase {

    @Test
    public void infoInitialize() {
        PluginInformation info = new PluginInformation(getContext(), "junit");
        Assert.assertTrue(info.getId().startsWith(getContext().getPackageName() + "@"));
        Assert.assertTrue(info.getId().endsWith("@junit"));

        Assert.assertEquals(info.getCategory(), Category.CATEGORY_OTHERS);
        Assert.assertFalse(info.hasSetting());
        Assert.assertTrue(info.isActivated());
    }

    @Test
    public void serializeTest() {
        PluginInformation info = new PluginInformation(getContext(), "junit");
        info.setCategory(Category.CATEGORY_OTHERS);
        byte[] serialized = info.serialize();
        Assert.assertNotNull(serialized);
    }

    @Test
    public void deserializeTest() {
        PluginInformation info = new PluginInformation(getContext(), "junit");
        info.setCategory(Category.CATEGORY_OTHERS);

        byte[] serialize = PluginInformation.serialize(Arrays.asList(info));
        Assert.assertFalse(CollectionUtil.isEmpty(serialize));

        List<PluginInformation> deserialize = PluginInformation.deserialize(serialize);
        Assert.assertEquals(deserialize.size(), 1);
        Assert.assertEquals(info.getId(), deserialize.get(0).getId());
    }
}
