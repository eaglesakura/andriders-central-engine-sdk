package com.eaglesakura.andriders.internal.protocol;

import com.eaglesakura.serialize.Serialize;

/**
 * Centralのデータ本体を構築するデータ
 */
public class RawCentralData {
    @Serialize(id = 1)
    public ApplicationProtocol.RawCentralSpec centralSpec;

    @Serialize(id = 2)
    public ApplicationProtocol.RawCentralStatus centralStatus;


}
