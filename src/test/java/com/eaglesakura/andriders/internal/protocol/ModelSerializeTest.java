package com.eaglesakura.andriders.internal.protocol;

import com.eaglesakura.andriders.CiJUnitTester;
import com.eaglesakura.andriders.SdkTestUtil;
import com.eaglesakura.serialize.PublicFieldDeserializer;
import com.eaglesakura.serialize.PublicFieldSerializer;
import com.eaglesakura.util.LogUtil;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class ModelSerializeTest extends CiJUnitTester {

    @Test
    public void IdlExtension_Location_シリアライズ() throws Exception {
        IdlExtension.Location origin = new IdlExtension.Location();
        origin.latitude = SdkTestUtil.randFloat();
        origin.longitude = SdkTestUtil.randFloat();
        origin.altitude = SdkTestUtil.randFloat();

        byte[] buffer = new PublicFieldSerializer().serialize(origin);
        assertNotNull(buffer);
        assertNotEquals(buffer.length, 0);
        LogUtil.log("Serialized :: %s(%d bytes)", origin.getClass().getSimpleName(), buffer.length);

        Object deserialized = new PublicFieldDeserializer().deserialize(origin.getClass(), buffer);
        assertNotNull(deserialized);
        assertEquals(origin, deserialized);
    }

    @Test
    public void IdlExtension_Heartrate_シリアライズ() throws Exception {
        IdlExtension.Heartrate origin = new IdlExtension.Heartrate();
        origin.bpm = SdkTestUtil.randInteger();

        byte[] buffer = new PublicFieldSerializer().serialize(origin);
        assertNotNull(buffer);
        assertNotEquals(buffer.length, 0);
        LogUtil.log("Serialized :: %s(%d bytes)", origin.getClass().getSimpleName(), buffer.length);

        Object deserialized = new PublicFieldDeserializer().deserialize(origin.getClass(), buffer);
        assertNotNull(deserialized);
        assertEquals(origin, deserialized);
    }

    @Test
    public void IdlExtension_SpeedAndCadence_シリアライズ() throws Exception {
        IdlExtension.SpeedAndCadence origin = new IdlExtension.SpeedAndCadence();
        origin.wheelRpm = SdkTestUtil.randFloat();
        origin.wheelRevolution = SdkTestUtil.randInteger();

        byte[] buffer = new PublicFieldSerializer().serialize(origin);
        assertNotNull(buffer);
        assertNotEquals(buffer.length, 0);
        LogUtil.log("Serialized :: %s(%d bytes)", origin.getClass().getSimpleName(), buffer.length);

        Object deserialized = new PublicFieldDeserializer().deserialize(origin.getClass(), buffer);
        assertNotNull(deserialized);
        assertEquals(origin, deserialized);
    }
}
