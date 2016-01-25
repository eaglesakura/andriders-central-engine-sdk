package com.eaglesakura.andriders.idl.display;

import android.content.Context;

public class IdlLineDisplayValue extends com.eaglesakura.android.db.BaseProperties {
    
    public static final String ID_VALUE = "IdlLineDisplayValue.value";
    public static final String ID_TITLE = "IdlLineDisplayValue.title";
    
    public IdlLineDisplayValue(Context context){ super(context, "props.db"); _initialize(); }
    public IdlLineDisplayValue(Context context, String dbFileName){ super(context, dbFileName); _initialize(); }
    protected void _initialize() {
            
        addProperty("IdlLineDisplayValue.value", "");
        addProperty("IdlLineDisplayValue.title", "");
        
        load();
        
    }
    public void setValue(String set){ setProperty("IdlLineDisplayValue.value", set); }
    public String getValue(){ return getStringProperty("IdlLineDisplayValue.value"); }
    public void setTitle(String set){ setProperty("IdlLineDisplayValue.title", set); }
    public String getTitle(){ return getStringProperty("IdlLineDisplayValue.title"); }
    
}
