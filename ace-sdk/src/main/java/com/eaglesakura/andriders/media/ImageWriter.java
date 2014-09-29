package com.eaglesakura.andriders.media;

import android.content.Context;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;

import com.eaglesakura.andriders.central.AcesProtocolReceiver;
import com.eaglesakura.andriders.protocol.GeoProtocol;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

/**
 * 画像書き込みを行う
 */
public class ImageWriter {

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
    public static final String EXIF_ACES_SPEED_KMH = "AcesSpeedKmh";

    final Context context;

    Date imageDate = new Date();

    /**
     * 書き込みを行った場合はファイルパスを保持しておく
     */
    File imageFilePath;

    /**
     * 現在の位置
     */
    GeoProtocol.GeoPayload loc;

    public ImageWriter(Context context) {
        this.context = context.getApplicationContext();
    }

    public Date getImageDate() {
        return imageDate;
    }

    /**
     * 画像ファイル名を取得する
     *
     * @return
     */
    public String getImageFileName() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd-HH-mm-ss");
        return String.format("%s.jpg", formatter.format(imageDate));
    }

    /**
     * 画像ディレクトリを取得する
     *
     * @return
     */
    public File getImageDirectory() {
        return new File(Environment.getExternalStorageDirectory(), "DCIM/ACE/Pictures");
    }

    /**
     * サムネイルディレクトリを取得する
     *
     * @return
     */
    public File getImageThumbnailDirectory() {
        return new File(Environment.getExternalStorageDirectory(), "DCIM/ACE/Thumbnails");
    }

    /**
     * EXIF情報を上書きする
     *
     * @param receiver
     */
    public void writeExif(AcesProtocolReceiver receiver) throws IOException {
        ExifInterface exif = new ExifInterface(getImageFilePath().getAbsolutePath());

        // ケイデンス
        if (receiver.getLastReceivedHartrate() != null) {
            int cadenceRpm = receiver.getLastReceivedCadence().getRpm();
            exif.setAttribute(EXIF_ACES_CADENCE, String.valueOf(cadenceRpm));
        }

        // 心拍
        if (receiver.getLastReceivedHartrate() != null) {
            int heartrateBpm = receiver.getLastReceivedHartrate().getBpm();
            exif.setAttribute(EXIF_ACES_HEARTRATE, String.valueOf(heartrateBpm));
        }

        // 速度
        if (receiver.getLastReceivedSpeed() != null) {
            float speedKmh = receiver.getLastReceivedSpeed().getSpeedKmPerHour();
            exif.setAttribute(EXIF_ACES_SPEED_KMH, String.format("%.2f", speedKmh));
        }

        // 保存を行う
        exif.saveAttributes();
    }

    /**
     * 画像を保存する
     *
     * @param jpegImage 　Jpeg画像配列
     * @return 書き込んだ画像のURI
     * @throws IOException
     */
    public File writeImage(byte[] jpegImage) throws IOException {
        if (jpegImage == null || jpegImage.length == 0) {
            return null;
        }

        imageFilePath = new File(getImageDirectory(), getImageFileName());
        imageFilePath.getParentFile().mkdirs();

        FileOutputStream os = new FileOutputStream(imageFilePath);
        os.write(jpegImage);
        os.flush();
        os.close();

        return imageFilePath;
    }

    /**
     * 画像ファイル本体を取得する
     *
     * @return
     */
    public File getImageFilePath() {
        return imageFilePath;
    }
}
