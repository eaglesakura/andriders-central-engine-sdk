package com.eaglesakura.andriders.data.gpx;

import com.eaglesakura.andriders.serialize.RawGeoPoint;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Date;

/**
 * 1地点での情報を示す
 */
public class GpxPoint {
    /**
     * GPS座標
     */
    @Nullable
    RawGeoPoint mLocation;

    /**
     * 保存された時刻
     */
    @NonNull
    Date mTime;

    public GpxPoint(@NonNull Date time) {
        mTime = time;
    }

    public RawGeoPoint getLocation() {
        return mLocation;
    }

    public Date getTime() {
        return mTime;
    }
}
