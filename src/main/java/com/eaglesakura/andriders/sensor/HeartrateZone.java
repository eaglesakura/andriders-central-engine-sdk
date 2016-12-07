package com.eaglesakura.andriders.sensor;

import com.eaglesakura.andriders.sdk.R;

import android.content.Context;
import android.support.annotation.NonNull;

public enum HeartrateZone {

    /**
     * 安静
     */
    Repose {
        @NonNull
        @Override
        public String getDisplayText(@NonNull Context context) {
            return context.getString(R.string.Ace_Word_HeartrateZone_Repose);
        }
    },

    /**
     * 軽度
     */
    Easy {
        @NonNull
        @Override
        public String getDisplayText(@NonNull Context context) {
            return context.getString(R.string.Ace_Word_HeartrateZone_Easy);
        }
    },

    /**
     * 脂肪燃焼
     */
    FatCombustion {
        @NonNull
        @Override
        public String getDisplayText(@NonNull Context context) {
            return context.getString(R.string.Ace_Word_HeartrateZone_FatCombustion);
        }
    },

    /**
     * 有酸素
     */
    PossessionOxygenMotion {
        @NonNull
        @Override
        public String getDisplayText(@NonNull Context context) {
            return context.getString(R.string.Ace_Word_HeartrateZone_PossessionOxygenMotion);
        }
    },

    /**
     * 無酸素
     */
    NonOxygenatedMotion {
        @NonNull
        @Override
        public String getDisplayText(@NonNull Context context) {
            return context.getString(R.string.Ace_Word_HeartrateZone_NonOxygenatedMotion);
        }
    },

    /**
     * 危険域
     */
    Overwork {
        @NonNull
        @Override
        public String getDisplayText(@NonNull Context context) {
            return context.getString(R.string.Ace_Word_HeartrateZone_Overwork);
        }
    };

    /**
     * 表示用のテキストを取得する
     */
    @NonNull
    public abstract String getDisplayText(@NonNull Context context);
}
