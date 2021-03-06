package com.eaglesakura.andriders.display;

import com.eaglesakura.andriders.sdk.R;
import com.eaglesakura.andriders.sensor.CadenceZone;
import com.eaglesakura.andriders.sensor.HeartrateZone;
import com.eaglesakura.andriders.sensor.InclinationType;
import com.eaglesakura.andriders.sensor.SpeedZone;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;

/**
 * アプリ内で統一するゾーン色
 */
public class ZoneColor {

    /**
     * 表示色
     */
    private final int[] colors = new int[7];

    public ZoneColor(Context context) {
        int index = 0;
        Resources resources = context.getResources();
        try {
            colors[index++] = resources.getColor(R.color.AceUI_Zonebar_Lv0);
            colors[index++] = resources.getColor(R.color.AceUI_Zonebar_Lv1);
            colors[index++] = resources.getColor(R.color.AceUI_Zonebar_Lv2);
            colors[index++] = resources.getColor(R.color.AceUI_Zonebar_Lv3);
            colors[index++] = resources.getColor(R.color.AceUI_Zonebar_Lv4);
            colors[index++] = resources.getColor(R.color.AceUI_Zonebar_Lv5);
            colors[index++] = resources.getColor(R.color.AceUI_Zonebar_Lv6);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @ColorInt
    public int getColor(InclinationType type) {
        switch (type) {
            case Hill:
                return colors[2];
            case IntenseHill:
                return colors[4];
            default:
                return colors[0];
        }
    }

    @ColorInt
    public int getColor(SpeedZone zone) {
        switch (zone) {
            case Slow:
                return colors[1];
            case Cruise:
                return colors[4];
            case Sprint:
                return colors[6];
            default:
                return 0x00;
        }
    }

    @ColorInt
    public int getColor(CadenceZone zone) {
        switch (zone) {
            case Slow:
                return colors[0];
            case Ideal:
                return colors[2];
            case High:
                return colors[4];
            default:
                return 0x00;
        }
    }

    @ColorInt
    public int getColor(HeartrateZone zone) {
        if (zone == null) {
            return 0x00;
        } else {
            return colors[zone.ordinal()];
        }
    }

    /**
     * 0.0 : 軽度
     * 1.0 : 高負荷
     *
     * @return 表示すべき色
     */
    @ColorInt
    public int getColor(@FloatRange(from = 0.0, to = 1.0) float level) {
        level += (1.0 / colors.length / 2.0);
        if (level <= 0) {
            return colors[0];
        } else if (level >= 1.0) {
            return colors[colors.length - 1];
        } else {
            int index = (int) (level * (colors.length));
            return colors[index];
        }
    }
}
