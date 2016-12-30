package com.eaglesakura.andriders.sensor;

import com.eaglesakura.andriders.sdk.R;

import android.content.Context;
import android.support.annotation.NonNull;

public enum CadenceZone {
    /**
     * 停止中
     */
    Stop {
        @NonNull
        @Override
        public String getDisplayText(@NonNull Context context) {
            return context.getString(R.string.Ace_Word_CadenceZone_Stop);
        }
    },

    /**
     * 低ケイデンス
     */
    Slow {
        @NonNull
        @Override
        public String getDisplayText(@NonNull Context context) {
            return context.getString(R.string.Ace_Word_CadenceZone_Slow);
        }
    },

    /**
     * 理想値
     */
    Ideal {
        @NonNull
        @Override
        public String getDisplayText(@NonNull Context context) {
            return context.getString(R.string.Ace_Word_CadenceZone_Ideal);
        }
    },

    /**
     * 高ケイデンス
     */
    High {
        @NonNull
        @Override
        public String getDisplayText(@NonNull Context context) {
            return context.getString(R.string.Ace_Word_CadenceZone_High);
        }
    };

    /**
     * 表示用のテキストを取得する
     */
    @NonNull
    public abstract String getDisplayText(@NonNull Context context);
}
