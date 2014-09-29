package com.eaglesakura.andriders.media;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;

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
    final Context context;

    Date imageDate = new Date();

    /**
     * 書き込みを行った場合はファイルパスを保持しておく
     */
    File imageFilePath;

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
