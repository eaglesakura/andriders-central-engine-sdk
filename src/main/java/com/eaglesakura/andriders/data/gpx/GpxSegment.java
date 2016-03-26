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
    List<GpxPointElement> mPoints = new LinkedList<>();

    /**
     * 位置情報を取得する
     */
    @NonNull
    public List<GpxPointElement> getPoints() {
        return mPoints;
    }

    public GpxPointElement getFirstPoint() {
        return mPoints.get(0);
    }

    public GpxPointElement getLastPoint() {
        return mPoints.get(mPoints.size() - 1);
    }
}
