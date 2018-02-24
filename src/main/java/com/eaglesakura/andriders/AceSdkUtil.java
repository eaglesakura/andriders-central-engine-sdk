package com.eaglesakura.andriders;

import com.google.gson.Gson;

import com.eaglesakura.util.EncodeUtil;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class AceSdkUtil {

    /**
     * オブジェクトをシリアライズする
     * デシリアライズは必ず {@link AceSdkUtil#deserializeFromByteArray(Class, byte[])} で行う
     */
    public static byte[] serializeToByteArray(Object obj) {
        byte[] bytes = new Gson().toJson(obj).getBytes();
        return EncodeUtil.compressOrRaw(bytes);
    }

    /**
     * シリアライズされたByte配列からオブジェクトを取り出す
     *
     * @param clazz  デコード対象のClass
     * @param buffer バッファ
     */
    public static <T> T deserializeFromByteArray(Class<? extends T> clazz, byte[] buffer) {
        try {
            byte[] bytes = EncodeUtil.decompressOrRaw(buffer);
            return new Gson().fromJson(new InputStreamReader(new ByteArrayInputStream(bytes), "UTF-8"), clazz);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
