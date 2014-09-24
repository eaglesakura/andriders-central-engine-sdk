package com.eaglesakura.andriders.media;

import android.media.ExifInterface;

import java.io.IOException;

/**
 * ACEs独自EXIFの管理を行う
 */
public class AcesExifInterface {

    /**
     * 撮影時の心拍 bpm
     */
    public static final String EXIF_ACES_HEARTRATE = "AcesHeartrate";

    /**
     * 撮影時のケイデンス rpm
     */
    public static final String EXIF_ACES_CADENCE = "AcesCadence";

    /**
     * 撮影時の速度 km/h
     */
    public static final String EXIF_ACES_SPEED_KMH = "AcesPeedKmh";

    public final ExifInterface exif;

    public AcesExifInterface(String fileName) throws IOException {
        this.exif = new ExifInterface(fileName);
    }
}
