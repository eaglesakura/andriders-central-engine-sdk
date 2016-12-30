package com.eaglesakura.andriders.sensor;

import com.eaglesakura.andriders.sdk.R;

import android.content.Context;
import android.support.annotation.NonNull;

public enum SpeedZone {
    Stop {
        @NonNull
        @Override
        public String getDisplayText(@NonNull Context context) {
            return context.getString(R.string.Ace_Word_SpeedZone_Stop);
        }
    },
    Slow {
        @NonNull
        @Override
        public String getDisplayText(@NonNull Context context) {
            return context.getString(R.string.Ace_Word_SpeedZone_Slow);
        }
    },
    Cruise {
        @NonNull
        @Override
        public String getDisplayText(@NonNull Context context) {
            return context.getString(R.string.Ace_Word_SpeedZone_Cruise);
        }
    },
    Sprint {
        @NonNull
        @Override
        public String getDisplayText(@NonNull Context context) {
            return context.getString(R.string.Ace_Word_SpeedZone_Sprint);
        }
    };


    /**
     * 表示用のテキストを取得する
     */
    @NonNull
    public abstract String getDisplayText(@NonNull Context context);
}
