package com.demo.filerecovery.utilts;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


public class SharePreferenceUtils {
    public static final String NAME_PREFERENCES = "FileRecovery";

    public static void setFirstRun(Context context, boolean z) {
        context.getSharedPreferences(NAME_PREFERENCES, 0).edit().putBoolean("first_run_app", z).apply();
    }

    public static boolean getFirstRun(Context context) {
        return context.getSharedPreferences(NAME_PREFERENCES, 0).getBoolean("first_run_app", true);
    }

    public static String getLanguage(Context context) {
        return context.getSharedPreferences(NAME_PREFERENCES, 0).getString("language", "");
    }

    public static void saveLanguage(Context context, String str) {
        SharedPreferences.Editor edit = context.getSharedPreferences(NAME_PREFERENCES, 0).edit();
        edit.putString("language", str);
        edit.apply();
    }

    public static int getLanguageIndex(Context context) {
        return context.getSharedPreferences(NAME_PREFERENCES, 0).getInt("languageindex", 0);
    }

    public static void saveLanguageIndex(Context context, int i) {
        SharedPreferences.Editor edit = context.getSharedPreferences(NAME_PREFERENCES, 0).edit();
        edit.putInt("languageindex", i);
        edit.apply();
    }

    public static boolean isRated(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(NAME_PREFERENCES, 0);
        int i = sharedPreferences.getInt("counts", 1);
        Log.d("ContentValues", "isRated: " + i);
        if (i == 2 || i == 3 || i == 4) {
            return sharedPreferences.getBoolean("rated", false);
        }
        return true;
    }

    public static void increaseCount(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(NAME_PREFERENCES, 0);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putInt("counts", sharedPreferences.getInt("counts", 1) + 1);
        edit.apply();
    }

    public static void forceRated(Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences(NAME_PREFERENCES, 0).edit();
        edit.putBoolean("rated", true);
        edit.apply();
    }

    public static boolean isForceRated(Context context) {
        if (context == null) {
            return true;
        }
        return context.getSharedPreferences(NAME_PREFERENCES, 0).getBoolean("rated", false);
    }

    public static void restoreFeatureUsed(Context context) {
        if (context != null) {
            SharedPreferences.Editor edit = context.getSharedPreferences(NAME_PREFERENCES, 0).edit();
            edit.putBoolean("restore_used", true);
            edit.apply();
        }
    }

    public static boolean isRestoreUsed(Context context) {
        if (context == null) {
            return false;
        }
        return context.getSharedPreferences(NAME_PREFERENCES, 0).getBoolean("restore_used", false);
    }

    public static int getUsingAppCounts(Context context) {
        if (context == null) {
            return 1;
        }
        return context.getSharedPreferences(NAME_PREFERENCES, 0).getInt("using_app_counts", 1);
    }

    public static void increaseUsingApp(Context context) {
        if (context != null) {
            context.getSharedPreferences(NAME_PREFERENCES, 0).edit().putInt("using_app_counts", getUsingAppCounts(context) + 1).apply();
        }
    }

    public static int getRecoveryDoneCounts(Context context) {
        if (context == null) {
            return 1;
        }
        return context.getSharedPreferences(NAME_PREFERENCES, 0).getInt("recovery_done_counts", 0);
    }

    public static void increaseRecoveryDone(Context context) {
        if (context != null) {
            context.getSharedPreferences(NAME_PREFERENCES, 0).edit().putInt("recovery_done_counts", getRecoveryDoneCounts(context) + 1).apply();
        }
    }

    public static void clearRecoveryDone(Context context) {
        if (context != null) {
            context.getSharedPreferences(NAME_PREFERENCES, 0).edit().putInt("recovery_done_counts", 0).apply();
        }
    }
}
