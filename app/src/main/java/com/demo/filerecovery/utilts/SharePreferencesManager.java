package com.demo.filerecovery.utilts;

import android.content.Context;
import android.content.SharedPreferences;


public class SharePreferencesManager {
    private static final String PREF_NAME = "FileRecovery";
    private static SharePreferencesManager sInstance;
    private final SharedPreferences mPref;

    private SharePreferencesManager(Context context) {
        this.mPref = context.getSharedPreferences("FileRecovery", 0);
    }

    public static synchronized void initializeInstance(Context context) {
        synchronized (SharePreferencesManager.class) {
            synchronized (SharePreferencesManager.class) {
                if (sInstance == null) {
                    sInstance = new SharePreferencesManager(context);
                }
            }
        }
    }

    public static synchronized SharePreferencesManager getInstance() {
        SharePreferencesManager sharePreferencesManager;
        synchronized (SharePreferencesManager.class) {
            synchronized (SharePreferencesManager.class) {
                sharePreferencesManager = sInstance;
                if (sharePreferencesManager == null) {
                    throw new IllegalStateException("SharePreferencesManager is not initialized, call initializeInstance(..) method first.");
                }
            }
            return sharePreferencesManager;
        }
    }

    public void setValue(String str, String str2) {
        this.mPref.edit().putString(str, str2).apply();
    }

    public String getValue(String str, String str2) {
        return this.mPref.getString(str, str2);
    }

    public String getValue(String str) {
        return this.mPref.getString(str, "");
    }

    public void setIntValue(String str, int i) {
        this.mPref.edit().putInt(str, i).apply();
    }

    public int getIntValue(String str) {
        return this.mPref.getInt(str, 0);
    }

    public void setValueBool(String str, boolean z) {
        this.mPref.edit().putBoolean(str, z).apply();
    }

    public boolean getValueBool(String str) {
        return this.mPref.getBoolean(str, false);
    }

    public boolean getValueBool(String str, Boolean bool) {
        return this.mPref.getBoolean(str, bool.booleanValue());
    }



    public void remove(String str) {
        this.mPref.edit().remove(str).apply();
    }

    public boolean clear() {
        return this.mPref.edit().clear().commit();
    }
}
