package com.demo.filerecovery.utilts;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Environment;
import android.view.Window;

import androidx.core.content.ContextCompat;
import androidx.core.os.EnvironmentCompat;

import com.demo.filerecovery.R;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


public class Utils {
    public static final String SUBSCRIPTION_PER_MONTH = "recovery.monthly.iap";
    public static final String SUBSCRIPTION_PER_WEEK = "recovery.weekly.iap";
    private static String r2;

    /* get format File Size*/
    public static String formatSize(long size) {
        if (size <= 0) {
            return "0 B";
        }
        final String[] units = {"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

//    public static String formatSize(long j) {
//        double d = 0;
//        if (j <= 0) {
//            return "";
//        }
//        int log10 = (int) (Math.log10(j) / Math.log10(1024.0d));
//        return new DecimalFormat("#,##0.#").format(d / Math.pow(1024.0d, log10)) + " " + new String[]{"B", "KB", "MB", "GB", "TB"}[log10];
//    }

    public static String getFileName(String str) {
        return str.substring(str.lastIndexOf("/") + 1);
    }

    public static File[] getFileList(String str) {
        try {
            File file = new File(str);
            return !file.isDirectory() ? new File[0] : file.listFiles();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean checkSelfPermission(Activity activity, String str) {
        return !isAndroid23() || ContextCompat.checkSelfPermission(activity, str) == 0;
    }

    public static boolean isAndroid23() {
        return Build.VERSION.SDK_INT >= 23;
    }

    public static String getFileTitle(String str) {
        return str.substring(str.lastIndexOf("/") + 1);
    }

    public static String getPathSave(String str) {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + str;
    }

    public static String[] getExternalStorageDirectories(Context context) {
        byte[] bArr = new byte[0];
        File[] externalFilesDirs;
        String[] split;
        boolean equals;
        ArrayList arrayList = new ArrayList();
        if (Build.VERSION.SDK_INT >= 19 && (externalFilesDirs = context.getExternalFilesDirs(r2)) != null && externalFilesDirs.length > 0) {
            for (File file : externalFilesDirs) {
                if (file != null && (split = file.getPath().split("/Android")) != null && split.length > 0) {
                    String str = split[0];
                    if (Build.VERSION.SDK_INT >= 21) {
                        equals = Environment.isExternalStorageRemovable(file);
                    } else {
                        equals = "mounted".equals(EnvironmentCompat.getStorageState(file));
                    }
                    if (equals) {
                        arrayList.add(str);
                    }
                }
            }
        }
        if (arrayList.isEmpty()) {
            String str2 = "";
            try {
                Process start = new ProcessBuilder(new String[0]).command("mount | grep /dev/block/vold").redirectErrorStream(true).start();
                start.waitFor();
                InputStream inputStream = start.getInputStream();
                while (inputStream.read(new byte[1024]) != -1) {
                    str2 = str2 + new String(bArr);
                }
                inputStream.close();
            } catch (Exception unused) {
            }
            if (!str2.trim().isEmpty()) {
                String[] split2 = str2.split(IOUtils.LINE_SEPARATOR_UNIX);
                if (split2.length > 0) {
                    for (String str3 : split2) {
                        arrayList.add(str3.split(" ")[2]);
                    }
                }
            }
        }
        String[] strArr = new String[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            strArr[i] = (String) arrayList.get(i);
        }
        return strArr;
    }

    public static String convertDuration(long j) {
        return String.format("%02d:%02d", Long.valueOf(TimeUnit.MILLISECONDS.toMinutes(j)), Long.valueOf(TimeUnit.MILLISECONDS.toSeconds(j) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(j))));
    }

    public static void setLocale(Context context) {
        String language = SharePreferenceUtils.getLanguage(context);
        if (language.equals("")) {
            language = Locale.getDefault().getLanguage();
            SharePreferenceUtils.saveLanguage(context, language);
        }
        Configuration configuration = new Configuration();
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        configuration.locale = locale;
        context.getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());
    }

    @SuppressLint("ResourceType")
    public static void setStatusBarGradiant(Activity activity) {
        Window window = activity.getWindow();
        Drawable drawable = activity.getResources().getDrawable(R.drawable.bg_gradient_start_end);
        window.addFlags(Integer.MIN_VALUE);
        window.setStatusBarColor(activity.getResources().getColor(17170445));
        window.setNavigationBarColor(activity.getResources().getColor(17170445));
        window.setBackgroundDrawable(drawable);
    }

    public static int compareFileName(String str, String str2, int i) {
        String extractFileNameFromPath = extractFileNameFromPath(str);
        String extractFileNameFromPath2 = extractFileNameFromPath(str2);
        if (!(startWithLetterOrDigit(extractFileNameFromPath).booleanValue() && startWithLetterOrDigit(extractFileNameFromPath2).booleanValue()) && (startWithLetterOrDigit(extractFileNameFromPath).booleanValue() || startWithLetterOrDigit(extractFileNameFromPath2).booleanValue())) {
            if (startWithLetterOrDigit(extractFileNameFromPath).booleanValue()) {
                return -1;
            }
            return startWithLetterOrDigit(extractFileNameFromPath2).booleanValue() ? 1 : 0;
        } else if (1 == i) {
            return extractFileNameFromPath.compareTo(extractFileNameFromPath2);
        } else {
            return extractFileNameFromPath2.compareTo(extractFileNameFromPath);
        }
    }

    private static String extractFileNameFromPath(String str) {
        return (str == null || str.trim().isEmpty() || str.endsWith("/")) ? "" : str.substring(str.lastIndexOf("/") + 1);
    }

    private static Boolean startWithLetterOrDigit(String str) {
        return Boolean.valueOf(Character.isLetterOrDigit(str.charAt(0)));
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
