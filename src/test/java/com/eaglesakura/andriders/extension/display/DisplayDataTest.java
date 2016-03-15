package com.eaglesakura.andriders.extension.display;

import com.eaglesakura.andriders.UnitTestCase;
import com.eaglesakura.andriders.extension.DisplayInformation;
import com.eaglesakura.andriders.internal.protocol.ExtensionProtocol;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

public class DisplayDataTest extends UnitTestCase {

    public static class TestDisplayDataImpl extends DisplayData {
        public TestDisplayDataImpl(ExtensionProtocol.RawCycleDisplayValue raw) {
            super(raw);
        }
    }

    @Test
    public void initTest() throws Exception {
        DisplayData data = new DisplayData(getContext(), "junit");
        DisplayInformation info = new DisplayInformation(getContext(), "junit");
        Assert.assertEquals(data.getId(), info.getId());
        Assert.assertFalse(data.hasTimeout());
    }

    @Test
    public void lineValueTest() throws Exception {
        DisplayData data = new DisplayData(getContext(), "junit");
        LineValue value = new LineValue(2);
        value.setLine(0, "test.title.0", "test.value.0");
        value.setLine(1, "test.title.1", "test.value.1");
        data.setValue(value);

        byte[] serialize = DisplayData.serialize(Arrays.asList(data));
        Assert.assertNotNull(serialize);
        Assert.assertTrue(serialize.length > 0);

        List<TestDisplayDataImpl> datas = DisplayData.deserialize(serialize, TestDisplayDataImpl.class);
        Assert.assertNotNull(data);
        Assert.assertEquals(datas.size(), 1);

        DisplayData deserializedData = datas.get(0);
        Assert.assertEquals(deserializedData.getId(), data.getId());
        Assert.assertNull(deserializedData.getBasicValue());
        Assert.assertNotNull(deserializedData.getLineValue());

        LineValue deserializedLineValue = deserializedData.getLineValue();
        Assert.assertEquals(value.getLineNum(), deserializedLineValue.getLineNum());
        for (int i = 0; i < 2; ++i) {
            Assert.assertEquals(value.getTitle(i), deserializedLineValue.getTitle(i));
            Assert.assertEquals(value.getValue(i), deserializedLineValue.getValue(i));
        }
    }

    @Test
    public void basicValueTest() throws Exception {
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
