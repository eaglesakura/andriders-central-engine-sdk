package com.eaglesakura.andriders.idl.remote;

import android.content.Context;

public class IdlExtensionInfo extends com.eaglesakura.android.db.BaseProperties {
    
    public static final String ID_TEXT = "IdlExtensionInfo.text";
    public static final String ID_CATEGORY = "IdlExtensionInfo.category";
    public static final String ID_HASSETTING = "IdlExtensionInfo.hasSetting";
    
    public IdlExtensionInfo(Context context){ super(context, "props.db"); _initialize(); }
    public IdlExtensionInfo(Context context, String dbFileName){ super(context, dbFileName); _initialize(); }
    protected void _initialize() {
            
        addProperty("IdlExtensionInfo.text", "");
        addProperty("IdlExtensionInfo.category", "others");
        addProperty("IdlExtensionInfo.hasSetting", "false");
        
        load();
        
    }
    public void setText(String set){ setProperty("IdlExtensionInfo.text", set); }
    public String getText(){ return getStringProperty("IdlExtensionInfo.text"); }
    public void setCategory(String set){ setProperty("IdlExtensionInfo.category", set); }
    public String getCategory(){ return getStringProperty("IdlExtensionInfo.category"); }
    public void setHasSetting(boolean set){ setProperty("IdlExtensionInfo.hasSetting", set); }
    public boolean getHasSetting(){ return getBooleanProperty("IdlExtensionInfo.hasSetting"); }
    
}
