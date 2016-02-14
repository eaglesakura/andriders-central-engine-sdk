package com.eaglesakura.andriders.extension;

import com.eaglesakura.andriders.CiJUnitTester;
import com.eaglesakura.andriders.extension.display.BasicValue;

import junit.framework.Assert;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class DisplayInformationTest extends CiJUnitTester {

    @Test
    public void serializeTest() {
        DisplayInformation info = new DisplayInformation(getContext(), "junit");

        Assert.assertTrue(info.getId().startsWith(getContext().getPackageName() + "@"));
        Assert.assertTrue(info.getId().endsWith("@junit"));

        byte[] serialized = DisplayInformation.serialize(Arrays.asList(info));
        Assert.assertNotNull(serialized);
        Assert.assertFalse(serialized.length == 0);

        List<DisplayInformation> deserialize = DisplayInformation.deserialize(serialized);
        Assert.assertNotNull(deserialize);
        Assert.assertEquals(deserialize.size(), 1);

        Assert.assertEquals(deserialize.get(0).getId(), info.getId());
    }

}
