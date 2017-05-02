package com.eaglesakura.andriders.data.gpx;

import com.eaglesakura.andriders.UnitTestCase;
import com.eaglesakura.log.Logger;
import com.eaglesakura.util.DateUtil;

import org.junit.Test;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class GpxParserTest extends UnitTestCase {

    @Test
    public void GPX書式の時刻が正しくパースできる_形式A() throws Exception {
        String time = "2015-05-23T20:51:28Z";
        Date date = GpxParser.sGpxDateFormat.parse(time);

        // AACR2015の朝である
        Logger.out(Logger.LEVEL_DEBUG, TAG, "Date[%s]", date.toString());
        assertEquals(DateUtil.getYear(date, TimeZone.getDefault()), 2015);
        assertEquals(DateUtil.getMonth(date, TimeZone.getDefault()), 5);
        assertEquals(DateUtil.getDay(date, TimeZone.getDefault()), 24);
        assertEquals(DateUtil.getHour(date, TimeZone.getDefault()), 5);
    }

    @Test
    public void GPX書式の時刻が正しくパースできる_形式B() throws Exception {
        String time = "2016-05-21T20:51:11.000Z";
        Date date = GpxParser.sGpxDateFormat2.parse(time);

        // AACR2016の朝である
        Logger.out(Logger.LEVEL_DEBUG, TAG, "Date[%s]", date.toString());
        assertEquals(DateUtil.getYear(date, TimeZone.getDefault()), 2016);
        assertEquals(DateUtil.getMonth(date, TimeZone.getDefault()), 5);
        assertEquals(DateUtil.getDay(date, TimeZone.getDefault()), 22);
        assertEquals(DateUtil.getHour(date, TimeZone.getDefault()), 5);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Test
    public void AACRのGPXが正しくパースできる() throws Exception {
        GpxParser parser = new GpxParser();
        Gpx gpx;
        try (InputStream is = new FileInputStream(getTestAsset("gpx/sample-aacr2015.gpx"))) {
            gpx = parser.parse(is);
        }

        assertNotNull(gpx);
        assertNotNull(gpx.getTrackSegments());
        assertNotEquals(gpx.getTrackSegments().size(), 0);

        for (GpxSegment segment : gpx.getTrackSegments()) {
            Logger.out(Logger.LEVEL_DEBUG, TAG, "Track index[%d] points[%d]", gpx.getTrackSegments().indexOf(segment), segment.getPoints().size());
            assertNotNull(segment.getPoints());
            assertNotEquals(segment.getPoints().size(), 0);
        }


        // 開始時刻が正しくAACRの朝である
        {
            GpxPoint pt = gpx.getFirstSegment().getFirstPoint();
            assertNotNull(pt.getLocation());
            assertNotNull(pt.getTime());

            Date date = pt.getTime();

            Logger.out(Logger.LEVEL_DEBUG, TAG, "Start Date[%s]", date.toString());
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeZone(TimeZone.getDefault());
            calendar.setTime(date);
            assertEquals(DateUtil.getYear(calendar), 2015);
            assertEquals(DateUtil.getMonth(calendar), 5);
            assertEquals(DateUtil.getDay(calendar), 24);
            assertEquals(DateUtil.getHour(calendar), 5);
        }
        // 終了時刻が正しくAACRの夕方である
        {
            GpxPoint pt = gpx.getLastSegment().getLastPoint();
            assertNotNull(pt.getLocation());
            assertNotNull(pt.getTime());

            Date date = pt.getTime();

            Logger.out(Logger.LEVEL_DEBUG, TAG, "End Date[%s]", date.toString());
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeZone(TimeZone.getDefault());
            calendar.setTime(date);
            assertEquals(DateUtil.getYear(calendar), 2015);
            assertEquals(DateUtil.getMonth(calendar), 5);
            assertEquals(DateUtil.getDay(calendar), 24);
            assertEquals(DateUtil.getHour(calendar), 16);
        }
    }
}
