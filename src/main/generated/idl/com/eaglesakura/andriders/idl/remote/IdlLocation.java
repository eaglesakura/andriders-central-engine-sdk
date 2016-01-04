package com.eaglesakura.andriders.idl.remote;

import android.content.Context;

public class IdlLocation extends com.eaglesakura.android.db.BaseProperties {
    
    public static final String ID_LATITUDE = "IdlLocation.latitude";
    public static final String ID_LONGITUDE = "IdlLocation.longitude";
    public static final String ID_ALTITUDE = "IdlLocation.altitude";
    public static final String ID_ACCURACYMETER = "IdlLocation.accuracyMeter";
    
    public IdlLocation(Context context){ super(context, "props.db"); _initialize(); }
    public IdlLocation(Context context, String dbFileName){ super(context, dbFileName); _initialize(); }
    protected void _initialize() {
            
        addProperty("IdlLocation.latitude", "0.0");
        addProperty("IdlLocation.longitude", "0.0");
        addProperty("IdlLocation.altitude", "0.0");
        addProperty("IdlLocation.accuracyMeter", "50.0");
        
        load();
        
    }
    public void setLatitude(double set){ setProperty("IdlLocation.latitude", set); }
    public double getLatitude(){ return getDoubleProperty("IdlLocation.latitude"); }
    public void setLongitude(double set){ setProperty("IdlLocation.longitude", set); }
    public double getLongitude(){ return getDoubleProperty("IdlLocation.longitude"); }
    public void setAltitude(double set){ setProperty("IdlLocation.altitude", set); }
    public double getAltitude(){ return getDoubleProperty("IdlLocation.altitude"); }
    public void setAccuracyMeter(double set){ setProperty("IdlLocation.accuracyMeter", set); }
    public double getAccuracyMeter(){ return getDoubleProperty("IdlLocation.accuracyMeter"); }
    
}
