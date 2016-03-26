package com.eaglesakura.andriders.data.gpx;

import com.eaglesakura.andriders.serialize.RawGeoPoint;
import com.eaglesakura.android.xml.XmlElement;
import com.eaglesakura.util.DateUtil;
import com.eaglesakura.util.StringUtil;

import org.xmlpull.v1.XmlPullParserException;

import android.support.annotation.NonNull;

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

    static {
        sGpxDateFormat.setTimeZone(TimeZone.getDefault());
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
    DateFormat mDateFormat = sGpxDateFormat;

    public void setDateFormat(@NonNull DateFormat dateFormat) {
        mDateFormat = dateFormat;
    }

    public void setDateOption(@NonNull DateOption dateOption) {
        mDateOption = dateOption;
    }

    private GpxPointElement newPoint(XmlElement trkpt) {
        GpxPointElement pt = new GpxPointElement();

        double lat = StringUtil.toDouble(trkpt.getAttribute("lat"), 0);
        double lng = StringUtil.toDouble(trkpt.getAttribute("lon"), 0);
        double elevation = trkpt.childToDouble("ele", 0);
        String time = trkpt.childToString("time");

        // GPS座標解析
        if (lat != 0 && lng != 0) {
            pt.mLocation = new RawGeoPoint(lat, lng, elevation);
        }

        // 時刻
        if (!StringUtil.isEmpty(time)) {
            try {
                long date = mDateFormat.parse(time).getTime();
                date += mDateOption.offset();
                pt.mTime = new Date(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
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
            GpxPointElement pt = newPoint(trkpt);
            segment.mPoints.add(pt);
        });

        if (segment.mPoints.isEmpty()) {
            // 位置が一つも見つからなかった
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

        if (gpx.mTrackSegments.isEmpty()) {
            throw new IllegalStateException("track not found");
        }
        return gpx;
    }
}
