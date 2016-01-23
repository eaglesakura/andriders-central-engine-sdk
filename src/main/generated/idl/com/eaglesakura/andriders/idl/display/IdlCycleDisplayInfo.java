package com.eaglesakura.andriders.idl.display;

import android.content.Context;

public class IdlCycleDisplayInfo extends com.eaglesakura.android.db.BaseProperties {
    
    public static final String ID_ID = "IdlCycleDisplayInfo.id";
    public static final String ID_TITLE = "IdlCycleDisplayInfo.title";
    public static final String ID_TEXT = "IdlCycleDisplayInfo.text";
    public static final String ID_HASSETTING = "IdlCycleDisplayInfo.hasSetting";
    
    public IdlCycleDisplayInfo(Context context){ super(context, "props.db"); _initialize(); }
    public IdlCycleDisplayInfo(Context context, String dbFileName){ super(context, dbFileName); _initialize(); }
    protected void _initialize() {
            
        addProperty("IdlCycleDisplayInfo.id", "");
        addProperty("IdlCycleDisplayInfo.title", "");
        addProperty("IdlCycleDisplayInfo.text", "");
        addProperty("IdlCycleDisplayInfo.hasSetting", "false");
        
        load();
        
    }
    public void setId(String set){ setProperty("IdlCycleDisplayInfo.id", set); }
    public String getId(){ return getStringProperty("IdlCycleDisplayInfo.id"); }
    public void setTitle(String set){ setProperty("IdlCycleDisplayInfo.title", set); }
    public String getTitle(){ return getStringProperty("IdlCycleDisplayInfo.title"); }
    public void setText(String set){ setProperty("IdlCycleDisplayInfo.text", set); }
    public String getText(){ return getStringProperty("IdlCycleDisplayInfo.text"); }
    public void setHasSetting(boolean set){ setProperty("IdlCycleDisplayInfo.hasSetting", set); }
    public boolean getHasSetting(){ return getBooleanProperty("IdlCycleDisplayInfo.hasSetting"); }
    
}
