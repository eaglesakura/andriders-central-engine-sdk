package com.eaglesakura.andriders.central;

import com.eaglesakura.andriders.UnitTestCase;
import com.eaglesakura.andriders.serialize.RawCentralData;
import com.eaglesakura.andriders.serialize.RawSensorData;

import org.junit.Test;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class CentralDataReceiverTest extends UnitTestCase {

    @Test
    public void ハンドラが登録されていない場合は何もしない() {
        CentralDataReceiver receiver = new CentralDataReceiver(getContext());

        RawCentralData data = new RawCentralData(Random.class);
        int flags = receiver.mHeartrateReceiver.onReceivedValue(data, data.sensor.heartrate, RawSensorData.getHash(data.sensor.heartrate));
        assertEquals(flags, SensorDataReceiver.RECEIVE_HANDLE_ABORT);
    }

    @Test
    public void 正しくハンドリングが呼び出されることを検証する() {
        CentralDataReceiver receiver = new CentralDataReceiver(getContext());
        receiver.addHandler(new SensorDataReceiver.HeartrateHandler() {
            boolean connected;
            int receivedCount = 0;

            @Override
            public void onConnectedSensor(@NonNull RawCentralData master, @NonNull RawSensorData.RawHeartrate sensor) {
                assertFalse(connected);
                connected = true;
            }

            @Override
            public void onDisconnectedSensor(@NonNull RawCentralData master) {
                assertTrue(connected);
                connected = false;
            }

            @Override
            public void onReceived(@NonNull RawCentralData master, @NonNull RawSensorData.RawHeartrate sensor) {
                assertTrue(connected);
                ++receivedCount;
            }

            @Override
            public void onUpdated(@NonNull RawCentralData master, @Nullable RawSensorData.RawHeartrate oldValue, @NonNull RawSensorData.RawHeartrate newValue) {
                assertTrue(connected);
                assertNotEquals(receivedCount, 0);
            }
        });

        {
            RawCentralData data = new RawCentralData(Random.class);
            data.sensor.heartrate.bpm = 100;
            int handle = receiver.mHeartrateReceiver.onReceivedValue(data, data.sensor.heartrate, RawSensorData.getHash(data.sensor.heartrate));
            assertEquals(handle, SensorDataReceiver.RECEIVE_HANDLE_CONNECTED | SensorDataReceiver.RECEIVE_HANDLE_UPDATED | SensorDataReceiver.RECEIVE_HANDLE_RECEIVED);
        }
        {
            RawCentralData data = new RawCentralData(Random.class);
            data.sensor.heartrate.bpm = 200;
            int handle = receiver.mHeartrateReceiver.onReceivedValue(data, data.sensor.heartrate, RawSensorData.getHash(data.sensor.heartrate));
            assertEquals(handle, SensorDataReceiver.RECEIVE_HANDLE_UPDATED | SensorDataReceiver.RECEIVE_HANDLE_RECEIVED);
        }
        {
            RawCentralData data = new RawCentralData(Random.class);
            data.sensor.heartrate = null;
            int handle = receiver.mHeartrateReceiver.onReceivedValue(data, data.sensor.heartrate, RawSensorData.getHash(data.sensor.heartrate));
            assertEquals(handle, SensorDataReceiver.RECEIVE_HANDLE_DISCONNECTED);
        }
    }
}
