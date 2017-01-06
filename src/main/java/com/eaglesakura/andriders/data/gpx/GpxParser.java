package com.eaglesakura.andriders.data.gpx;

import com.eaglesakura.andriders.serialize.RawGeoPoint;
import com.eaglesakura.android.xml.XmlElement;
import com.eaglesakura.util.DateUtil;
import com.eaglesakura.util.StringUtil;

import org.xmlpull.v1.XmlPullParserException;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * GPXファイルを解析する
 */
public class GpxParser {

    static final DateFormat sGpxDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    static final DateFormat sGpxDateFormat2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    static {
        sGpxDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        sGpxDateFormat2.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    public enum DateOption {
        /**
         * 時差を加算する
         */
        AddTimeZone {
            @Override
            int offset() {
                return DateUtil.getDateOffset();
            }
        },

        /**
         * 時差を減じる
         */
        SubTimeZone {
            @Override
            int offset() {
                return -DateUtil.getDateOffset();
            }
        },

        /**
         * 何も行わない
         */
        None {
            @Override
            int offset() {
                return 0;
            }
        };

        abstract int offset();
    }

    @NonNull
    DateOption mDateOption = DateOption.None;

    @NonNull
    DateFormat mDateFormat = sGpxDateFormat2;

    /**
     * 最低限持たなければならないポイント数
     * これ以下のポイントしか持たないセグメントは取り除く
     */
    int mMinPoints = 50;

    public void setDateFormat(@NonNull DateFormat dateFormat) {
        mDateFormat = dateFormat;
    }

    public void setDateOption(@NonNull DateOption dateOption) {
        mDateOption = dateOption;
    }

    /**
     * 最低限のポイント数を指定する
     *
     * @param minPoints これ以下のポイント数の場合、セグメントを取り除く
     */
    public void setMinPoints(int minPoints) {
        mMinPoints = minPoints;
    }

    private Date parse(String value) throws Exception {
        try {
            return mDateFormat.parse(value);
        } catch (Exception e) {
            mDateFormat = sGpxDateFormat;
            return sGpxDateFormat.parse(value);
        }
    }

    @Nullable
    private GpxPoint newPoint(XmlElement trkpt) {
        String time = trkpt.childToString("time");
        Date ptDate;
        // 時刻が取れなかったらポイントとして不良である
        if (!StringUtil.isEmpty(time)) {
            try {
                long date = parse(time).getTime();
                date += mDateOption.offset();
                ptDate = new Date(date);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
        GpxPoint pt = new GpxPoint(ptDate);

        double lat = StringUtil.toDouble(trkpt.getAttribute("lat"), 0);
        double lng = StringUtil.toDouble(trkpt.getAttribute("lon"), 0);
        double elevation = trkpt.childToDouble("ele", 0);

        // GPS座標解析
        if (lat != 0 && lng != 0) {
            pt.mLocation = new RawGeoPoint(lat, lng, elevation);
        }

        // TODO 心拍、ケイデンス
        return pt;
    }

    /**
     * trksegタグを解析し、トラックを挿入する
     */
    private void addTrack(Gpx gpx, XmlElement trkseg) {
        GpxSegment segment = new GpxSegment();
        trkseg.listChilds("trkpt", trkpt -> {
            GpxPoint pt = newPoint(trkpt);
            if (pt != null) {
                segment.mPoints.add(pt);
            }
        });

        if (segment.mPoints.size() <= mMinPoints) {
            // 位置が規定数に達しなかった
            return;
        }

        gpx.mTrackSegments.add(segment);
    }

    /**
     * GPXファイルを解析する
     */
    public Gpx parse(InputStream stream) throws XmlPullParserException, IOException {
        Gpx gpx = new Gpx();
        XmlElement element = XmlElement.parse(stream);
        element.listChilds("trk", trk -> {
            XmlElement trkseg = trk.getChild("trkseg");
            if (trkseg == null) {
                return;
            }

            addTrack(gpx, trkseg);
        });

        return gpx;
    }
}
