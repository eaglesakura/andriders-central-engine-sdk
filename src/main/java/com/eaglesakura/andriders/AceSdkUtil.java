package com.eaglesakura.andriders;

import com.eaglesakura.json.JSON;
import com.eaglesakura.util.EncodeUtil;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class AceSdkUtil {

    /**
     * オブジェクトをシリアライズする
     * デシリアライズは必ず {@link AceSdkUtil#deserializeFromByteArray(Class, byte[])} で行う
     */
    public static byte[] serializeToByteArray(Object obj) {
        try {
            byte[] bytes = JSON.encode(obj).getBytes();
            return EncodeUtil.compressOrRaw(bytes);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * シリアライズされたByte配列からオブジェクトを取り出す
     *
     * @param clazz  デコード対象のClass
     * @param buffer バッファ
     */
    public static <T> T deserializeFromByteArray(Class<? extends T> clazz, byte[] buffer) {
        try {
            return JSON.decode(new ByteArrayInputStream(EncodeUtil.decompressOrRaw(buffer)), clazz);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
