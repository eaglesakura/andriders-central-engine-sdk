package com.eaglesakura.andriders;

import com.eaglesakura.andriders.protocol.AcesProtocol;
import com.eaglesakura.android.device.external.StorageInfo;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 環境を指定する
 */
public class AcesEnvironment {

    /**
     * ACEs Bluetooth P2P Connect Server Protocol
     */
    @Deprecated
    public static final UUID BLUETOOTH_SERVER_PROTOCOL = UUID.fromString("0000ACE0-0000-1000-8000-00805f9b34fb");


    private static final String BASIC_PACKAGE_NAME = "com.eaglesakura.andriders";

    public static final String SCHEMA_TRIGGER_COMMAND = "acestrigger";

    public static final String SCHEMA_TRIGGER_ORDER = "acesteamorder";

    /**
     * ACE Extension Packのファイル拡張子
     */
    public static final String ACE_EXTENTION_PACK_FILE_EXT = ".aep";

    private static String APPLICATION_PACKAGE_NAME = BASIC_PACKAGE_NAME;

    static {
        {
            AcesProtocol.VersionInfo.Builder builder = AcesProtocol.VersionInfo.newBuilder();
            builder.setProtocolVersion(0);
        }
    }


    /**
     * 実行しているACE package nameを変更する。
     * ただし、指定のpackage名から始まらない場合はエラーとする。
     *
     * @param context app context
     */
    static void initialize(Context context) {
        if (!context.getPackageName().startsWith(BASIC_PACKAGE_NAME)) {
            throw new IllegalStateException();
        }

        APPLICATION_PACKAGE_NAME = getSelfPackageName(context);
    }

    public static String getSelfPackageName(Context context) {
        String packageName = context.getPackageName();
        if (!packageName.startsWith(BASIC_PACKAGE_NAME)) {
            // 違うアプリの場合はそのまま返す
            return packageName;
        }

        if (packageName.endsWith(".debug")) {
            // debug build
            return BASIC_PACKAGE_NAME + ".debug";
        } else {
            // release build
            return BASIC_PACKAGE_NAME;
        }
    }

    public static String getApplicationPackageName() {
        return APPLICATION_PACKAGE_NAME;
    }

    private static File getAceDirectory(Context context, File root) {
        // base
        File directory = new File(root, "andriders");
        final String appPackageName = context.getPackageName();
        if (!appPackageName.startsWith(BASIC_PACKAGE_NAME)) {
            return new File(directory, "app/" + appPackageName);
        } else {
            return new File(directory, "central");
        }
    }

    /**
     * ACEデータ保存用ディレクトリを取得する
     */
    public static File getAceDirectory(Context context) {
        return getAceDirectory(context, Environment.getExternalStorageDirectory());
    }

    /**
     * メディア保存用のディレクトリを取得する
     */
    public static File getMediaDirectory(Context context) {
        File mediaStorage = null;

        final long CHECK_FREE_SIZE = 1024 * 1024 * 128; // 128 MiB以上の空き容量を確保しておく

        List<StorageInfo> storageInfos = StorageInfo.listExternalStorages();
        for (StorageInfo info : storageInfos) {
            info.loadStorageInfo();
            if (mediaStorage == null && info.getFreeSize() > CHECK_FREE_SIZE) {
                mediaStorage = info.getPath();
            }
        }

        if (mediaStorage == null) {
            mediaStorage = getAceDirectory(context);
        } else {
            mediaStorage = getAceDirectory(context, mediaStorage);
        }
        return new File(mediaStorage, "media");
    }

    /**
     * 指定した日に保存するディレクトリを取得する
     *
     * @param date 保存する日付
     */
    public static File getDateMediaDirectory(Context context, Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return new File(getMediaDirectory(context), formatter.format(date));
    }
}
