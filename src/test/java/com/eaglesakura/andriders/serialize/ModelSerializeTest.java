package com.eaglesakura.andriders.serialize;

import com.eaglesakura.andriders.UnitTestCase;
import com.eaglesakura.io.data.DataVerifier;
import com.eaglesakura.json.JSON;
import com.eaglesakura.refrection.NullableConstructor;
import com.eaglesakura.util.LogUtil;
import com.eaglesakura.util.SerializeUtil;
import com.eaglesakura.util.StringUtil;

import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

public class ModelSerializeTest extends UnitTestCase {

    final int TRY_SERIALIZE_COUNT = 1024;


    /**
     * 全てのSerializeモデルはシリアライズとデシリアライズが可能であり、かつJSONとしてもシリアライズ出来ることを保証する
     */
    <T> void assertSerialize(Class<T> clazz) throws Exception {
        LogUtil.log("Serialize :: " + clazz.getName());
        for (int i = 0; i < TRY_SERIALIZE_COUNT; ++i) {
            DataVerifier verifier = new DataVerifier();
            T obj = NullableConstructor.get(clazz, Random.class.getClass()).newInstance(Random.class);
            assertNotNull(obj);

            byte[] buffer = SerializeUtil.serializePublicFieldObject(obj, true);
            assertNotNull(buffer);
            assertNotEquals(buffer.length, 0);

            // ベリファイコードを与える
            byte[] packed = verifier.pack(buffer);
            assertNotNull(packed);
            assertTrue(packed.length > buffer.length);
            // ベリファイコードの後ろ4桁が0x00でないことを検証する
            assertNotEquals(packed[(packed.length - 4)], 0x00);
            assertNotEquals(packed[(packed.length - 3)], 0x00);
            assertNotEquals(packed[(packed.length - 2)], 0x00);
            assertNotEquals(packed[(packed.length - 1)], 0x00);
//            LogUtil.log("   num[%04d] hash(%s) verify(%s)", i, EncodeUtil.genMD5(buffer), StringUtil.toHexString(new byte[]{packed[(packed.length - 4)], packed[(packed.length - 3)], packed[(packed.length - 2)], packed[(packed.length - 1)]}));

            // ベリファイコードを剥がす
            byte[] unpacked = verifier.unpack(packed);
            assertNotNull(unpacked);
            assertEquals(unpacked.length, buffer.length);
            assertTrue(Arrays.equals(buffer, unpacked));

            Object deserialized = SerializeUtil.deserializePublicFieldObject(obj.getClass(), buffer);
            assertNotNull(deserialized);
            assertEquals(obj, deserialized);

            // JSONを経由しても同じオブジェクトが得られる
            String jsonEncoded = JSON.encode(obj);
            Object jsonDecoded = JSON.decode(jsonEncoded, clazz);
            assertEquals(obj, jsonDecoded);

            // サイズを比較する
            if (i == 0) {
                LogUtil.log("  Serialize[%d bytes] BASE64[%d bytes] JSON[%d bytes]",
                        buffer.length,
                        StringUtil.toString(buffer).getBytes().length,
                        jsonEncoded.getBytes().length
                );
                LogUtil.log("  JSON-STRING[%s]", jsonEncoded);
            }
        }
        LogUtil.log("  Finished");
    }

    @Test
    public void 各IDLをシリアライズandデシリアライズする() throws Exception {
        // 通知
        assertSerialize(NotificationProtocol.RawNotification.class);

        // 拡張機能
        assertSerialize(PluginProtocol.SrcLocation.class);
        assertSerialize(PluginProtocol.SrcHeartrate.class);
        assertSerialize(PluginProtocol.SrcSpeedAndCadence.class);
        assertSerialize(PluginProtocol.RawExtensionInfo.class);
        assertSerialize(PluginProtocol.RawCycleDisplayInfo.class);
        assertSerialize(PluginProtocol.RawCycleDisplayValue.class);

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
        assertSerialize(RawRecord.class);
        assertSerialize(RawSessionInfo.class);

        // Intent
        assertSerialize(RawIntent.class);

        // Util
        assertSerialize(RawKeyValue.class);
    }

    @Test
    public void 差分チェック用のハッシュが重複しないことを確認する() {
        {
            int initHash = RawSensorData.getHash(new RawSensorData.RawHeartrate(Random.class));
            boolean succes = false;
            for (int i = 0; i < 128; ++i) {
                int hash = RawSensorData.getHash(new RawSensorData.RawHeartrate(Random.class));
                if (initHash != hash) {
                    succes = true;
                }
            }

            // 全部同じ数が出たら検証にならない　
            assertTrue(succes);
        }
        {
            int initHash = RawSensorData.getHash(new RawSensorData.RawSpeed(Random.class));
            boolean succes = false;
            for (int i = 0; i < 128; ++i) {
                int hash = RawSensorData.getHash(new RawSensorData.RawSpeed(Random.class));
                if (initHash != hash) {
                    succes = true;
                }
            }

            // 全部同じ数が出たら検証にならない　
            assertTrue(succes);
        }
        {
            int initHash = RawSensorData.getHash(new RawSensorData.RawCadence(Random.class));
            boolean succes = false;
            for (int i = 0; i < 128; ++i) {
                int hash = RawSensorData.getHash(new RawSensorData.RawCadence(Random.class));
                if (initHash != hash) {
                    succes = true;
                }
            }

            // 全部同じ数が出たら検証にならない　
            assertTrue(succes);
        }
        {
            int initHash = RawSensorData.getHash(new RawLocation(Random.class));
            boolean succes = false;
            for (int i = 0; i < 128; ++i) {
                int hash = RawSensorData.getHash(new RawLocation(Random.class));
                if (initHash != hash) {
                    succes = true;
                }
            }

            // 全部同じ数が出たら検証にならない　
            assertTrue(succes);
        }
    }
}
