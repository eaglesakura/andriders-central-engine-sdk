package com.eaglesakura.andriders.internal.protocol;

import com.eaglesakura.andriders.UnitTestCase;
import com.eaglesakura.io.data.DataPackage;
import com.eaglesakura.refrection.NullableConstructor;
import com.eaglesakura.util.LogUtil;
import com.eaglesakura.util.SerializeUtil;

import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ModelSerializeTest extends UnitTestCase {

    final int TRY_SERIALIZE_COUNT = 1024;


    <T> void assertSerialize(Class<T> clazz) throws Exception {
        LogUtil.log("Serialize :: " + clazz.getName());
        for (int i = 0; i < TRY_SERIALIZE_COUNT; ++i) {
            T obj = NullableConstructor.get(clazz, Random.class.getClass()).newInstance(Random.class);
            assertNotNull(obj);

            byte[] buffer = SerializeUtil.serializePublicFieldObject(obj, true);
            assertNotNull(buffer);
            assertNotEquals(buffer.length, 0);
//            LogUtil.log("Serialized :: %s(%d bytes)", obj.getClass().getSimpleName(), buffer.length);

            Object deserialized = SerializeUtil.deserializePublicFieldObject(obj.getClass(), buffer);
            assertNotNull(deserialized);
            assertEquals(obj, deserialized);

            DataPackage packed = DataPackage.pack(DataPackage.class, buffer);
            byte[] unpacked = DataPackage.unpack(DataPackage.class, packed.getPackedBuffer());
            assertTrue(Arrays.equals(buffer, unpacked));
        }
        LogUtil.log("  Finished");
    }

    @Test
    public void 各IDLをシリアライズandデシリアライズする() throws Exception {
        // 通知
        assertSerialize(NotificationProtocol.RawNotification.class);

        // 拡張機能
        assertSerialize(ExtensionProtocol.SrcLocation.class);
        assertSerialize(ExtensionProtocol.SrcHeartrate.class);
        assertSerialize(ExtensionProtocol.SrcSpeedAndCadence.class);
        assertSerialize(ExtensionProtocol.RawExtensionInfo.class);
        assertSerialize(ExtensionProtocol.RawCycleDisplayInfo.class);
        assertSerialize(ExtensionProtocol.RawCycleDisplayValue.class);

        // 位置情報
        assertSerialize(RawGeoPoint.class);
        assertSerialize(RawLocation.class);

        // センサー
        assertSerialize(RawSensorData.RawCadence.class);
        assertSerialize(RawSensorData.RawSpeed.class);
        assertSerialize(RawSensorData.RawHeartrate.class);

        // Central
        assertSerialize(RawSpecs.RawAppSpec.class);
        assertSerialize(RawCentralData.RawCentralStatus.class);
        assertSerialize(RawCentralData.class);
        assertSerialize(RawSessionData.class);
    }
}
