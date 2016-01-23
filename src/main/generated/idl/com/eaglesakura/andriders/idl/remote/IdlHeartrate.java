package com.eaglesakura.andriders.idl.remote;

import android.content.Context;

public class IdlHeartrate extends com.eaglesakura.android.db.BaseProperties {
    
    public static final String ID_BPM = "IdlHeartrate.bpm";
    
    public IdlHeartrate(Context context){ super(context, "props.db"); _initialize(); }
    public IdlHeartrate(Context context, String dbFileName){ super(context, dbFileName); _initialize(); }
    protected void _initialize() {
            
        addProperty("IdlHeartrate.bpm", "0");
        
        load();
        
    }
    public void setBpm(int set){ setProperty("IdlHeartrate.bpm", set); }
    public int getBpm(){ return getIntProperty("IdlHeartrate.bpm"); }
    
}
