package com.eaglesakura.andriders.data.gpx;

import com.eaglesakura.andriders.serialize.RawGeoPoint;

import java.util.Date;

/**
 * 1地点での情報を示す
 */
public class GpxPointElement {
    /**
     * GPS座標
     */
    RawGeoPoint mLocation;

    /**
     * 保存された時刻
     */
    Date mTime;

    public RawGeoPoint getLocation() {
        return mLocation;
    }

    public Date getTime() {
        return mTime;
    }
}
