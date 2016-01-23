package com.eaglesakura.andriders.idl.remote;

import android.content.Context;

public class IdlStringProperty extends com.eaglesakura.android.db.BaseProperties {
    
    public static final String ID_VALUE = "IdlStringProperty.value";
    
    public IdlStringProperty(Context context){ super(context, "props.db"); _initialize(); }
    public IdlStringProperty(Context context, String dbFileName){ super(context, dbFileName); _initialize(); }
    protected void _initialize() {
            
        addProperty("IdlStringProperty.value", "");
        
        load();
        
    }
    public void setValue(String set){ setProperty("IdlStringProperty.value", set); }
    public String getValue(){ return getStringProperty("IdlStringProperty.value"); }
    
}
