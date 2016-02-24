package com.eaglesakura.andriders.internal.protocol;

import com.eaglesakura.andriders.CiJUnitTester;
import com.eaglesakura.util.LogUtil;
import com.eaglesakura.util.SerializeUtil;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class ModelSerializeTest extends CiJUnitTester {


    <T> void assertSerialize(T obj) throws Exception {
        byte[] buffer = SerializeUtil.serializePublicFieldObject(obj, true);
        assertNotNull(buffer);
        assertNotEquals(buffer.length, 0);
        LogUtil.log("Serialized :: %s(%d bytes)", obj.getClass().getSimpleName(), buffer.length);

        Object deserialized = SerializeUtil.deserializePublicFieldObject(obj.getClass(), buffer);
        assertNotNull(deserialized);
        assertEquals(obj, deserialized);
    }

    @Test
    public void 各IDLをシリアライズandデシリアライズする() throws Exception {
        // 通知
        assertSerialize(new NotificationProtocol.RawNotification(Random.class));

        // 拡張機能
        assertSerialize(new ExtensionProtocol.SrcLocation(Random.class));
        assertSerialize(new ExtensionProtocol.SrcHeartrate(Random.class));
        assertSerialize(new ExtensionProtocol.SrcSpeedAndCadence(Random.class));
        assertSerialize(new ExtensionProtocol.RawExtensionInfo(Random.class));
        assertSerialize(new ExtensionProtocol.RawCycleDisplayInfo(Random.class));
        assertSerialize(new ExtensionProtocol.RawCycleDisplayValue(Random.class));

        // 位置情報
        assertSerialize(new GeoProtocol.GeoPoint(Random.class));
        assertSerialize(new GeoProtocol.GeoPayload(Random.class));

        // センサー
        assertSerialize(new SensorProtocol.RawCadence(Random.class));
        assertSerialize(new SensorProtocol.RawSpeed(Random.class));
        assertSerialize(new SensorProtocol.RawHeartrate(Random.class));

    }
}
