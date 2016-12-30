package com.eaglesakura.andriders.plugin;

import com.eaglesakura.andriders.UnitTestCase;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

public class DisplayInformationTest extends UnitTestCase {

    @Test
    public void serializeTest() {
        DisplayKey info = new DisplayKey(getContext(), "junit");
        info.setTitle("JUnit4");

        Assert.assertTrue(info.getId().startsWith(getContext().getPackageName() + "@"));
        Assert.assertTrue(info.getId().endsWith("@junit"));
        Assert.assertEquals("JUnit4", info.getTitle());

        byte[] serialized = DisplayKey.serialize(Arrays.asList(info));
        Assert.assertNotNull(serialized);
        Assert.assertFalse(serialized.length == 0);

        List<DisplayKey> deserialize = DisplayKey.deserialize(serialized);
        Assert.assertNotNull(deserialize);
        Assert.assertEquals(deserialize.size(), 1);

        Assert.assertEquals(deserialize.get(0).getId(), info.getId());
        Assert.assertEquals(deserialize.get(0).getTitle(), info.getTitle());
    }

}
