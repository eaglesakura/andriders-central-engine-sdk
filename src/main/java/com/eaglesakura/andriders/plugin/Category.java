package com.eaglesakura.andriders.plugin;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Category {

    private static final Map<String, Category> categories = new HashMap<>();

    /**
     * 同一カテゴリ内では１つしか指定できないことを示す
     */
    public static final int ATTRIBUTE_SINGLE_SELECT = 0x01 << 0;

    /**
     * 位置情報
     */
    public static final Category CATEGORY_LOCATION = new Category("sensor.location", ATTRIBUTE_SINGLE_SELECT);

    /**
     * 心拍計
     */
    public static final Category CATEGORY_HEARTRATEMONITOR = new Category("sensor.heartrate", ATTRIBUTE_SINGLE_SELECT);

    /**
     * スピード＆ケイデンス
     */
    public static final Category CATEGORY_SPEED_AND_CADENCE = new Category("sensor.speed_and_cadence", ATTRIBUTE_SINGLE_SELECT);

    /**
     * その他の拡張機能
     */
    public static final Category CATEGORY_OTHERS = new Category("others", 0x00);

    private String name;
    private int attributes;

    private Category(String name, int attributes) {
        this.name = name;
        this.attributes = attributes;

        categories.put(name, this);
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * カテゴリ名を取得する
     */
    public String getName() {
        return name;
    }

    /**
     * 指定した属性情報を持っていればtrue
     */
    public boolean hasAttribute(int flag) {
        return (attributes & flag) != 0;
    }

    /**
     * 名前からカテゴリを取得する
     */
    public static Category fromName(String name) {
        return categories.get(name);
    }

    public static List<Category> list() {
        return new ArrayList<>(categories.values());
    }
}
