package com.eaglesakura.andriders.extension;

import com.eaglesakura.andriders.UnitTestCase;

import junit.framework.Assert;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class DisplayInformationTest extends UnitTestCase {

    @Test
    public void serializeTest() {
        DisplayInformation info = new DisplayInformation(getContext(), "junit");
        info.setTitle("JUnit4");

        Assert.assertTrue(info.getId().startsWith(getContext().getPackageName() + "@"));
        Assert.assertTrue(info.getId().endsWith("@junit"));
        Assert.assertEquals("JUnit4", info.getTitle());

        byte[] serialized = DisplayInformation.serialize(Arrays.asList(info));
        Assert.assertNotNull(serialized);
        Assert.assertFalse(serialized.length == 0);

        List<DisplayInformation> deserialize = DisplayInformation.deserialize(serialized);
        Assert.assertNotNull(deserialize);
        Assert.assertEquals(deserialize.size(), 1);

        Assert.assertEquals(deserialize.get(0).getId(), info.getId());
        Assert.assertEquals(deserialize.get(0).getTitle(), info.getTitle());
    }

}
