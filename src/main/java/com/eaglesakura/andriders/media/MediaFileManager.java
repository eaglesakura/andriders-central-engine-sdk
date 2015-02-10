package com.eaglesakura.andriders.media;

import android.content.Context;

import com.eaglesakura.andriders.AcesEnvironment;
import com.eaglesakura.andriders.central.AcesProtocolReceiver;
import com.eaglesakura.andriders.protocol.AcesProtocol;
import com.eaglesakura.andriders.protocol.GeoProtocol;
import com.eaglesakura.andriders.protocol.MediaMetaProtocol;
import com.eaglesakura.andriders.protocol.SensorProtocol;
import com.eaglesakura.util.StringUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 画像書き込みを行う
 */
public class MediaFileManager {

    /**
     * ACEのメタファイルの内容
     */
    public static final String METAFILE_EXT = ".mediameta";

    final Context context;

    Date imageDate = new Date();

    /**
     * 書き込みを行った場合はファイルパスを保持しておく
     */
    File mediaFilePath;

    /**
     * 現在の位置
     */
    GeoProtocol.GeoPayload loc;

    public MediaFileManager(Context context) {
        this.context = context.getApplicationContext();
    }

    public MediaFileManager(Context context, File file) {
        if (!file.isFile()) {
            throw new IllegalArgumentException("!= File");
        }
        this.context = context.getApplicationContext();
        this.mediaFilePath = file;
    }

    public Date getImageDate() {
        return imageDate;
    }

    static final SimpleDateFormat formatter = new SimpleDateFormat("HH-mm-ss");

    public String getVideoFileName() {
        return String.format("%s.mp4", formatter.format(imageDate));
    }

    /**
     * 画像ファイル名を取得する
     *
     * @return
     */
    public String getImageFileName() {
        return String.format("%s.jpg", formatter.format(imageDate));
    }

    /**
     * 画像ディレクトリを取得する
     *
     * @return
     */
    public File getImageDirectory() {
        return AcesEnvironment.getDateMediaDirectory(context, imageDate);
    }

    public File getMediaMetaFileName() {
        return new File(String.format("%s%s", mediaFilePath.getAbsolutePath(), METAFILE_EXT));
    }

    /**
     * メタ情報を書き出す
     *
     * @param receiver
     */
    public void writeMediaMeta(AcesProtocolReceiver receiver) throws IOException {

        final GeoProtocol.GeoPayload geo = receiver.getLastReceivedGeo();
        final SensorProtocol.RawHeartrate heartrate = receiver.getLastReceivedHeartrate();
        final SensorProtocol.RawSpeed speed = receiver.getLastReceivedSpeed();
        final SensorProtocol.RawCadence cadence = receiver.getLastReceivedCadence();
        final AcesProtocol.CentralStatus centralStatus = receiver.getLastReceivedCentralStatus();

        if (centralStatus == null) {
            // セントラルを取得できて無ければ何もしない
            return;
        }

        MediaMetaProtocol.MediaMetaPayload.Builder metaBuilder = MediaMetaProtocol.MediaMetaPayload.newBuilder();
        metaBuilder.setDate(StringUtil.toString(new Date()));

        if (geo != null) {
            metaBuilder.setGeo(receiver.getLastReceivedGeo());
        }

        if (heartrate != null && centralStatus.getConnectedHeartrate()) {
            metaBuilder.setHeartrate(heartrate);
        }

        if (cadence != null && centralStatus.getConnectedCadence()) {
            metaBuilder.setCadence(cadence);
        }

        if (speed != null && centralStatus.getConnectedSpeed()) {
            metaBuilder.setSpeed(speed);
        }

        // ファイルを書き出す
        FileOutputStream os = new FileOutputStream(getMediaMetaFileName());
        os.write(metaBuilder.build().toByteArray());
        os.flush();
        os.close();
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

        mediaFilePath = new File(getImageDirectory(), getImageFileName());
        mediaFilePath.getParentFile().mkdirs();

        FileOutputStream os = new FileOutputStream(mediaFilePath);
        os.write(jpegImage);
        os.flush();
        os.close();

        return mediaFilePath;
    }

    /**
     * 一時ファイルから本番領域にファイルを移す
     *
     * @param tempFile
     * @return
     */
    public File swapVideo(File tempFile) {
        if (tempFile == null || tempFile.length() == 0) {
            return null;
        }

        mediaFilePath = new File(getImageDirectory(), getVideoFileName());
        mediaFilePath.getParentFile().mkdirs();

        tempFile.renameTo(mediaFilePath);
        return mediaFilePath;
    }

    /**
     * 画像ファイル本体を取得する
     *
     * @return
     */
    public File getMediaFilePath() {
        return mediaFilePath;
    }
}
