package com.eaglesakura.andriders.idl.remote;

import android.content.Context;

public class IdlSpeedAndCadence extends com.eaglesakura.android.db.BaseProperties {

    public static final String ID_CRANKRPM = "IdlSpeedAndCadence.crankRpm";
    public static final String ID_CRANKREVOLUTION = "IdlSpeedAndCadence.crankRevolution";
    public static final String ID_WHEELRPM = "IdlSpeedAndCadence.wheelRpm";
    public static final String ID_WHEELREVOLUTION = "IdlSpeedAndCadence.wheelRevolution";

    public IdlSpeedAndCadence(Context context) {
        super(context, "props.db");
        _initialize();
    }

    public IdlSpeedAndCadence(Context context, String dbFileName) {
        super(context, dbFileName);
        _initialize();
    }

    protected void _initialize() {

        addProperty("IdlSpeedAndCadence.crankRpm", "-1.0");
        addProperty("IdlSpeedAndCadence.crankRevolution", "-1");
        addProperty("IdlSpeedAndCadence.wheelRpm", "-1.0");
        addProperty("IdlSpeedAndCadence.wheelRevolution", "-1");

        load();

    }

    public void setCrankRpm(float set) {
        setProperty("IdlSpeedAndCadence.crankRpm", set);
    }

    public float getCrankRpm() {
        return getFloatProperty("IdlSpeedAndCadence.crankRpm");
    }

    public void setCrankRevolution(int set) {
        setProperty("IdlSpeedAndCadence.crankRevolution", set);
    }

    public int getCrankRevolution() {
        return getIntProperty("IdlSpeedAndCadence.crankRevolution");
    }

    public void setWheelRpm(float set) {
        setProperty("IdlSpeedAndCadence.wheelRpm", set);
    }

    public float getWheelRpm() {
        return getFloatProperty("IdlSpeedAndCadence.wheelRpm");
    }

    public void setWheelRevolution(int set) {
        setProperty("IdlSpeedAndCadence.wheelRevolution", set);
    }

    public int getWheelRevolution() {
        return getIntProperty("IdlSpeedAndCadence.wheelRevolution");
    }

}
