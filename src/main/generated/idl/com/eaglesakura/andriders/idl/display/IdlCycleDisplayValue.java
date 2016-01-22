package com.eaglesakura.andriders.idl.display;

import android.content.Context;

public class IdlCycleDisplayValue extends com.eaglesakura.android.db.BaseProperties {

    public static final String ID_ID = "IdlCycleDisplayValue.id";
    public static final String ID_TYPE = "IdlCycleDisplayValue.type";
    public static final String ID_VALUES = "IdlCycleDisplayValue.values";

    public IdlCycleDisplayValue(Context context) {
        super(context, "props.db");
        _initialize();
    }

    public IdlCycleDisplayValue(Context context, String dbFileName) {
        super(context, dbFileName);
        _initialize();
    }

    protected void _initialize() {

        addProperty("IdlCycleDisplayValue.id", "");
        addProperty("IdlCycleDisplayValue.type", "");
        addProperty("IdlCycleDisplayValue.values", "");

        load();

    }

    public void setId(String set) {
        setProperty("IdlCycleDisplayValue.id", set);
    }

    public String getId() {
        return getStringProperty("IdlCycleDisplayValue.id");
    }

    public void setType(String set) {
        setProperty("IdlCycleDisplayValue.type", set);
    }

    public String getType() {
        return getStringProperty("IdlCycleDisplayValue.type");
    }

    public void setValues(String set) {
        setProperty("IdlCycleDisplayValue.values", set);
    }

    public String getValues() {
        return getStringProperty("IdlCycleDisplayValue.values");
    }

}
