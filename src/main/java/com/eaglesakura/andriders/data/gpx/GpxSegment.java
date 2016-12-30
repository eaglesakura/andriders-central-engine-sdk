package com.eaglesakura.andriders.data.gpx;

import android.support.annotation.NonNull;

import java.util.LinkedList;
import java.util.List;

/**
 * GPSに書き込まれた1セッションを示す
 */
public class GpxSegment {
    /**
     * 管理下にある位置情報
     */
    List<GpxPoint> mPoints = new LinkedList<>();

    /**
     * 位置情報を取得する
     */
    @NonNull
    public List<GpxPoint> getPoints() {
        return mPoints;
    }

    public GpxPoint getFirstPoint() {
        return mPoints.get(0);
    }

    public GpxPoint getLastPoint() {
        return mPoints.get(mPoints.size() - 1);
    }
}
