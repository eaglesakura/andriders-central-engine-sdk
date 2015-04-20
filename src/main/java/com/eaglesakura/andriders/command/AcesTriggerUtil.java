package com.eaglesakura.andriders.command;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.eaglesakura.andriders.protocol.AcesProtocol;
import com.eaglesakura.andriders.protocol.CommandProtocol;
import com.eaglesakura.util.StringUtil;

import java.util.List;

/**
 * 各種トリガー用の共通処理を記述したUtil
 */
public class AcesTriggerUtil {
    /**
     * コマンドの起動キー
     */
    public static final String EXTRA_COMMAND_KEY = "COMMANDEXTRA_COMMAND_KEY";

    /**
     * コマンドの起動キー
     */
    public static final String EXTRA_COMMAND_KEY_STRING = "EXTRA_COMMAND_KEY_STRING";

    /**
     * 現在のユーザーステータス
     */
    public static final String EXTRA_MASTER_PAYLOAD = "COMMANDEXTRA_MASTER_PAYLOAD";

    /**
     * Intentからマスター情報を取り出す
     *
     * @param intent ACEsから起動された際に渡されたIntent
     *
     * @return MasterPayload
     */
    public static AcesProtocol.MasterPayload getMasterPayload(Intent intent) {
        try {
            return AcesProtocol.MasterPayload.parseFrom(intent.getByteArrayExtra(EXTRA_MASTER_PAYLOAD));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * コマンドの起動キーを取得する
     *
     * @param intent ACEsから起動された際に渡されたIntent
     *
     * @return コマンドのキー
     */
    public static CommandKey getKey(Intent intent) {
        String keyString = intent.getStringExtra(EXTRA_COMMAND_KEY_STRING);
        if (!StringUtil.isEmpty(keyString)) {
            return CommandKey.fromString(keyString);
        } else {
            return intent.getParcelableExtra(EXTRA_COMMAND_KEY);
        }
    }

    /**
     * Intentの起動を行う
     *
     * @param context app context
     * @param payload 実行するIntent
     *
     * @throws Exception
     */
    public static void boot(Context context, CommandProtocol.IntentPayload payload) throws Exception {
        Intent intent = makeIntent(payload);
        switch (payload.getType()) {
            case Activity:
                context.startActivity(intent);
                return;
            case Broadcast:
                context.sendBroadcast(intent);
                return;
            case Service:
                context.startService(intent);
                return;
        }
    }

    /**
     * {@link com.eaglesakura.andriders.protocol.CommandProtocol.IntentPayload}からIntentを生成する。
     *
     * @param payload 生成元payload
     *
     * @return 生成されたIntent
     *
     * @see Intent#setAction(String)
     * @see Intent#setData(Uri)
     * @see Intent#setFlags(int)
     * @see Intent#putExtra(String, boolean)
     * @see Intent#putExtra(String, int)
     * @see Intent#putExtra(String, long)
     * @see Intent#putExtra(String, float)
     * @see Intent#putExtra(String, double)
     * @see Intent#putExtra(String, byte[])
     * @see Intent#putExtra(String, String)
     */
    public static Intent makeIntent(CommandProtocol.IntentPayload payload) {
        Intent intent = new Intent();
        if (payload.hasAction()) {
            intent.setAction(payload.getAction());
        }

        intent.setFlags(payload.getFlags());
        if (payload.hasComponentName()) {
            String[] component = payload.getComponentName().split("/");
            intent.setComponent(new ComponentName(component[0], component[1]));
        }

        if (payload.hasDataUri()) {
            try {
                Uri uri = Uri.parse(payload.getDataUri());
                intent.setData(uri);
            } catch (Exception e) {
            }
        }

        List<CommandProtocol.IntentExtra> list = payload.getExtrasList();
        for (CommandProtocol.IntentExtra ex : list) {
            switch (ex.getType()) {
                case Boolean:
                    intent.putExtra(ex.getKey(), Boolean.valueOf(ex.getValue()));
                    break;
                case Integer:
                    intent.putExtra(ex.getKey(), Integer.valueOf(ex.getValue()));
                    break;
                case Long:
                    intent.putExtra(ex.getKey(), Long.valueOf(ex.getValue()));
                    break;
                case Float:
                    intent.putExtra(ex.getKey(), Float.valueOf(ex.getValue()));
                    break;
                case Double:
                    intent.putExtra(ex.getKey(), Double.valueOf(ex.getValue()));
                    break;
                case ByteArray:
                    intent.putExtra(ex.getKey(), StringUtil.toByteArray(ex.getValue()));
                    break;
                default:
                    intent.putExtra(ex.getKey(), ex.getValue());
                    break;
            }
        }
        return intent;
    }

}
