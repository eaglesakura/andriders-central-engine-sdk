package com.eaglesakura.andriders.media;

import com.eaglesakura.andriders.AcesEnvironment;
import com.eaglesakura.andriders.central.CentralDataReceiver;
import com.eaglesakura.andriders.serialize.AcesProtocol;
import com.eaglesakura.andriders.serialize.GeoProtocol;
import com.eaglesakura.andriders.serialize.MediaMetaProtocol;
import com.eaglesakura.andriders.serialize.SensorProtocol;
import com.eaglesakura.util.StringUtil;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 画像書き込みを行う
 */
@Deprecated
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
     * 1時間毎のディレクトリを生成する場合はtrue
     */
    boolean mediaDirectory1h = false;

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

    public boolean isMediaDirectory1h() {
        return mediaDirectory1h;
    }

    public void setMediaDirectory1h(boolean mediaDirectory1h) {
        this.mediaDirectory1h = mediaDirectory1h;
    }

    static final SimpleDateFormat formatter = new SimpleDateFormat("HH-mm-ss");
    static final SimpleDateFormat hourFormatter = new SimpleDateFormat("HH");

    public String getVideoFileName() {
        return String.format("%s.mp4", formatter.format(imageDate));
    }

    /**
     * 画像ファイル名を取得する
     */
    public String getImageFileName() {
        return String.format("%s.jpg", formatter.format(imageDate));
    }

    /**
     * 画像ディレクトリを取得する
     */
    public File getImageDirectory() {
        File result = AcesEnvironment.getDateMediaDirectory(context, imageDate);
        // 一時間ごとにディレクトリを区切る場合は再度区切る
        if (mediaDirectory1h) {
            result = new File(result, hourFormatter.format(imageDate));
        }
        return result;
    }

    public File getMediaMetaFileName() {
        return new File(String.format("%s%s", mediaFilePath.getAbsolutePath(), METAFILE_EXT));
    }

    /**
     * メタ情報を書き出す
     */
    public void writeMediaMeta(CentralDataReceiver receiver) throws IOException {

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

    public void generateNomedia() {
        File nomedia = new File(AcesEnvironment.getMediaDirectory(context), ".nomedia");
        // nomediaが存在する場合は何もしない
        if (nomedia.exists()) {
            return;
        }

        FileOutputStream os = null;
        try {
            os = new FileOutputStream(nomedia);
            os.write(0);
        } catch (Exception e) {
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (Exception e) {
                }
            }
        }
    }

    /**
     * 画像を保存する
     *
     * @param jpegImage 　Jpeg画像配列
     * @return 書き込んだ画像のURI
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
     */
    public File getMediaFilePath() {
        return mediaFilePath;
    }
}
