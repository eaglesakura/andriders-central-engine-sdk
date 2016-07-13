package com.eaglesakura.andriders.serialize;

import com.eaglesakura.serialize.Serialize;
import com.eaglesakura.util.RandomUtil;
import com.eaglesakura.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * シリアライズ対応のIntent情報
 *
 * コマンド等のデータを通信する際に用いる
 */
public class RawIntent {

    @Serialize(id = 1)
    public IntentType intentType;

    @Serialize(id = 2)
    public String componentName;

    @Serialize(id = 3)
    public String action;

    @Serialize(id = 4)
    public int flags;

    @Serialize(id = 5)
    public String data;

    @Serialize(id = 6)
    public List<Extra> extras = new ArrayList<>();

    @Serialize(id = 7)
    public List<String> categories = new ArrayList<>();

    public RawIntent() {
    }

    @Deprecated
    public RawIntent(Class<Random> dummy) {
        intentType = RandomUtil.randEnum(IntentType.class);
        componentName = RandomUtil.randString();
        action = RandomUtil.randString();
        flags = RandomUtil.randInt8();
        data = RandomUtil.randString();

        if (RandomUtil.randBool()) {
            extras = Arrays.asList(new Extra(dummy), new Extra(dummy), new Extra(dummy));
        }

        if (RandomUtil.randBool()) {
            categories = Arrays.asList(RandomUtil.randString(), RandomUtil.randString(), RandomUtil.randString(), RandomUtil.randString());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RawIntent rawIntent = (RawIntent) o;

        if (flags != rawIntent.flags) return false;
        if (intentType != rawIntent.intentType) return false;
        if (componentName != null ? !componentName.equals(rawIntent.componentName) : rawIntent.componentName != null)
            return false;
        if (action != null ? !action.equals(rawIntent.action) : rawIntent.action != null)
            return false;
        if (data != null ? !data.equals(rawIntent.data) : rawIntent.data != null) return false;
        if (extras != null ? !extras.equals(rawIntent.extras) : rawIntent.extras != null)
            return false;
        return categories != null ? categories.equals(rawIntent.categories) : rawIntent.categories == null;

    }

    @Override
    public int hashCode() {
        int result = intentType != null ? intentType.hashCode() : 0;
        result = 31 * result + (componentName != null ? componentName.hashCode() : 0);
        result = 31 * result + (action != null ? action.hashCode() : 0);
        result = 31 * result + flags;
        result = 31 * result + (data != null ? data.hashCode() : 0);
        result = 31 * result + (extras != null ? extras.hashCode() : 0);
        result = 31 * result + (categories != null ? categories.hashCode() : 0);
        return result;
    }

    public static class Extra {
        @Serialize(id = 1)
        public String key;

        @Serialize(id = 2)
        public String value;

        @Serialize(id = 3)
        public ValueType type;

        public Extra() {
        }

        public Extra(String key, boolean value) {
            this.key = key;
            this.value = String.valueOf(value);
            this.type = ValueType.Boolean;
        }

        public Extra(String key, int value) {
            this.key = key;
            this.value = String.valueOf(value);
            this.type = ValueType.Integer;
        }

        public Extra(String key, long value) {
            this.key = key;
            this.value = String.valueOf(value);
            this.type = ValueType.Long;
        }

        public Extra(String key, float value) {
            this.key = key;
            this.value = String.valueOf(value);
            this.type = ValueType.Float;
        }

        public Extra(String key, double value) {
            this.key = key;
            this.value = String.valueOf(value);
            this.type = ValueType.Double;
        }

        public Extra(String key, byte[] value) {
            this.key = key;
            this.value = StringUtil.toString(value);
            this.type = ValueType.ByteArray;
        }

        public Extra(String key, String value) {
            this.key = key;
            this.value = value;
            this.type = ValueType.String;
        }

        @Deprecated
        public Extra(Class<Random> dummy) {
            key = RandomUtil.randString();
            value = RandomUtil.randString();
            type = RandomUtil.randEnum(ValueType.class);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Extra extra = (Extra) o;

            if (key != null ? !key.equals(extra.key) : extra.key != null) return false;
            if (value != null ? !value.equals(extra.value) : extra.value != null) return false;
            return type == extra.type;

        }

        @Override
        public int hashCode() {
            int result = key != null ? key.hashCode() : 0;
            result = 31 * result + (value != null ? value.hashCode() : 0);
            result = 31 * result + (type != null ? type.hashCode() : 0);
            return result;
        }
    }

    public enum IntentType {
        Activity,
        Broadcast,
        Service,
    }

    public enum ValueType {
        String,
        Boolean,
        Integer,
        Long,
        Float,
        Double,
        ByteArray,
    }
}
