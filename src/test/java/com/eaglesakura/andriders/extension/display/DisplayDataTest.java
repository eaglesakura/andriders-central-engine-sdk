package com.eaglesakura.andriders.extension.display;

import com.eaglesakura.andriders.CiJUnitTester;
import com.eaglesakura.andriders.extension.DisplayInformation;
import com.eaglesakura.andriders.protocol.internal.InternalData;

import junit.framework.Assert;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class DisplayDataTest extends CiJUnitTester {

    public static class TestDisplayDataImpl extends DisplayData {
        public TestDisplayDataImpl(InternalData.IdlCycleDisplayValue.Builder raw) {
            super(raw);
        }
    }

    @Test
    public void initTest() {
        DisplayData data = new DisplayData(getContext(), "junit");
        DisplayInformation info = new DisplayInformation(getContext(), "junit");
        Assert.assertEquals(data.getId(), info.getId());
        Assert.assertFalse(data.hasTimeout());
    }

    @Test
    public void basicValueTest() {
        DisplayData data = new DisplayData(getContext(), "junit");
        BasicValue value = new BasicValue();
        value.setValue("1.23");
        value.setTitle("unit test");

        data.setValue(value);

        Assert.assertNotNull(data.getBasicValue());
        Assert.assertNull(data.getLineValue());

        byte[] serialize = DisplayData.serialize(Arrays.asList(data));
        Assert.assertNotNull(serialize);
        Assert.assertTrue(serialize.length > 0);

        List<TestDisplayDataImpl> datas = DisplayData.deserialize(serialize, TestDisplayDataImpl.class);
        Assert.assertNotNull(data);
        Assert.assertEquals(datas.size(), 1);

        DisplayData deserializedData = datas.get(0);
        Assert.assertEquals(deserializedData.getId(), data.getId());
        Assert.assertNotNull(deserializedData.getBasicValue());
        Assert.assertNull(deserializedData.getLineValue());

        BasicValue deserializedValue = deserializedData.getBasicValue();
        Assert.assertEquals(deserializedValue.getValue(), value.getValue());
        Assert.assertEquals(deserializedValue.getTitle(), value.getTitle());
    }
}
