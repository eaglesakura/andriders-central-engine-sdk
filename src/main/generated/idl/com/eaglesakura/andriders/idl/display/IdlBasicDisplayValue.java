package com.eaglesakura.andriders.idl.display;

import android.content.Context;

public class IdlBasicDisplayValue extends com.eaglesakura.android.db.BaseProperties {
    
    public static final String ID_MAINVALUE = "IdlBasicDisplayValue.mainValue";
    public static final String ID_TITLE = "IdlBasicDisplayValue.title";
    public static final String ID_INFO = "IdlBasicDisplayValue.info";
    public static final String ID_BARA = "IdlBasicDisplayValue.barA";
    public static final String ID_BARR = "IdlBasicDisplayValue.barR";
    public static final String ID_BARG = "IdlBasicDisplayValue.barG";
    public static final String ID_BARB = "IdlBasicDisplayValue.barB";
    
    public IdlBasicDisplayValue(Context context){ super(context, "props.db"); _initialize(); }
    public IdlBasicDisplayValue(Context context, String dbFileName){ super(context, dbFileName); _initialize(); }
    protected void _initialize() {
            
        addProperty("IdlBasicDisplayValue.mainValue", "");
        addProperty("IdlBasicDisplayValue.title", "");
        addProperty("IdlBasicDisplayValue.info", "");
        addProperty("IdlBasicDisplayValue.barA", "0");
        addProperty("IdlBasicDisplayValue.barR", "0");
        addProperty("IdlBasicDisplayValue.barG", "0");
        addProperty("IdlBasicDisplayValue.barB", "0");
        
        load();
        
    }
    public void setMainValue(String set){ setProperty("IdlBasicDisplayValue.mainValue", set); }
    public String getMainValue(){ return getStringProperty("IdlBasicDisplayValue.mainValue"); }
    public void setTitle(String set){ setProperty("IdlBasicDisplayValue.title", set); }
    public String getTitle(){ return getStringProperty("IdlBasicDisplayValue.title"); }
    public void setInfo(String set){ setProperty("IdlBasicDisplayValue.info", set); }
    public String getInfo(){ return getStringProperty("IdlBasicDisplayValue.info"); }
    public void setBarA(int set){ setProperty("IdlBasicDisplayValue.barA", set); }
    public int getBarA(){ return getIntProperty("IdlBasicDisplayValue.barA"); }
    public void setBarR(int set){ setProperty("IdlBasicDisplayValue.barR", set); }
    public int getBarR(){ return getIntProperty("IdlBasicDisplayValue.barR"); }
    public void setBarG(int set){ setProperty("IdlBasicDisplayValue.barG", set); }
    public int getBarG(){ return getIntProperty("IdlBasicDisplayValue.barG"); }
    public void setBarB(int set){ setProperty("IdlBasicDisplayValue.barB", set); }
    public int getBarB(){ return getIntProperty("IdlBasicDisplayValue.barB"); }
    
}
