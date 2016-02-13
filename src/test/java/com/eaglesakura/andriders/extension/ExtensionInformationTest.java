package com.eaglesakura.andriders.extension;

import com.eaglesakura.andriders.CiJUnitTester;
import com.eaglesakura.util.Util;

import junit.framework.Assert;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class ExtensionInformationTest extends CiJUnitTester {

    @Test
    public void serializeTest() {
        ExtensionInformation info = new ExtensionInformation(getContext(), "junit");
        info.setCategory(ExtensionCategory.CATEGORY_OTHERS);
        byte[] serialized = info.serialize();
        Assert.assertNotNull(serialized);
    }

    @Test
    public void deserializeTest() {
        ExtensionInformation info = new ExtensionInformation(getContext(), "junit");
        info.setCategory(ExtensionCategory.CATEGORY_OTHERS);

        byte[] serialize = ExtensionInformation.serialize(Arrays.asList(info));
        Assert.assertFalse(Util.isEmpty(serialize));

        List<ExtensionInformation> deserialize = ExtensionInformation.deserialize(serialize);
        Assert.assertEquals(deserialize.size(), 1);
        Assert.assertEquals(info.getId(), deserialize.get(0).getId());
    }
}
