package com.eaglesakura.andriders.data.gpx;

import java.util.ArrayList;
import java.util.List;

/**
 * 解析されたGPXファイル
 */
public class Gpx {
    /**
     * GPXファイルに含まれていたセグメント
     */
    List<GpxSegment> mTrackSegments = new ArrayList<>();


    /**
     * トラック一覧を取得する
     */
    public List<GpxSegment> getTrackSegments() {
        return mTrackSegments;
    }

    public GpxSegment getFirstSegment() {
        return mTrackSegments.get(0);
    }

    public GpxSegment getLastSegment() {
        return mTrackSegments.get(mTrackSegments.size() - 1);
    }
}
