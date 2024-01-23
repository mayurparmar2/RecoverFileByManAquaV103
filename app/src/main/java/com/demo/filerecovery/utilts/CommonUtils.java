package com.demo.filerecovery.utilts;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.demo.filerecovery.App;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


public class CommonUtils {
    public static final String KEY_LOAD_ANIM_END = "KEY_LOAD_ANIM";
    private static final String EMAIL = "trustedapp.help@gmail.com";
    private static final String FILE_SETTING = "setting.pref";
    private static final String POLICY_URL = "https://www.google.com";
    private static final String PUBLISH_NAME = "TrustedApp";
    private static final String SUBJECT = "FeedBack";

    private static CommonUtils instance;

    private CommonUtils() {
    }

    public static CommonUtils getInstance() {
        if (instance == null) {
            instance = new CommonUtils();
        }
        return instance;
    }

    public void loadAnim(View view, int i) {
        view.startAnimation(AnimationUtils.loadAnimation(App.getInstance(), i));
    }

    public void toast(String str) {
        Toast.makeText(App.getInstance(), str, Toast.LENGTH_LONG).show();
    }

    public void shareApp(Context context) {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("text/plain");
        intent.putExtra("android.intent.extra.SUBJECT", SUBJECT);
        intent.putExtra("android.intent.extra.TEXT", "https://play.google.com/store/apps/details?id=" + context.getPackageName());
        context.startActivity(Intent.createChooser(intent, "Share to"));
    }


    public void rateApp(Context context) {
        try {
            context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + context.getPackageName())));
        } catch (ActivityNotFoundException unused) {
            context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + context.getPackageName())));
        }
    }

    public void showPolicy(Context context) {
        try {
            context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(POLICY_URL)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void log(String str) {
        Log.d("TAG", str);
    }

    public String readFileFromAssets(String str) {
        int read = 0;
        String str2 = "";
        try {
            InputStream open = App.getInstance().getAssets().open(str);
            byte[] bArr = new byte[1024];
            while (true) {
                if (open.read(bArr) <= 0) {
                    break;
                }
                str2 = str2 + new String(bArr, 0, read);
            }
            open.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str2;
    }

    public InputStream getFileFromAssets(String str) {
        try {
            return App.getInstance().getAssets().open(str);
        } catch (IOException unused) {
            return null;
        }
    }

    public void saveFileToSystemStorage(File file, String str) {
        FileInputStream fileInputStream;
        String str2 = Environment.getDataDirectory().toString() + "/data/" + App.getInstance().getPackageName();
        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException unused) {
            fileInputStream = null;
        }
        saveFile(fileInputStream, str2, str);
    }

    public void saveFile(InputStream inputStream, String str, String str2) {
        File file = new File(str);
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(str + "/" + str2));
            byte[] bArr = new byte[1024];
            int read = inputStream.read(bArr);
            while (read > 0) {
                fileOutputStream.write(bArr, 0, read);
                read = inputStream.read(bArr);
            }
            inputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveFileToStorage(File file, String str, String str2) {
        FileInputStream fileInputStream = null;
        String path = App.getInstance().getExternalFilesDir(null).getPath();
        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        saveFile(fileInputStream, path, str2);
    }

    public void savePref(String str, String str2) {
        App.getInstance().getSharedPreferences(FILE_SETTING, 0).edit().putString(str, str2).apply();
    }

    public void savePref(String str, String str2, boolean z) {
        SharedPreferences sharedPreferences;
        if (!z) {
            sharedPreferences = App.getInstance().getSharedPreferences(FILE_SETTING, 0);
        } else {
            sharedPreferences = App.getInstance().getSharedPreferences(FILE_SETTING, Context.MODE_PRIVATE);
        }
        sharedPreferences.edit().putString(str, str2).apply();
    }

    public String getValuePref(String str) {
        return App.getInstance().getSharedPreferences(FILE_SETTING, 0).getString(str, "");
    }

    public String getValuePref(String str, String str2) {
        return App.getInstance().getSharedPreferences(FILE_SETTING, 0).getString(str, str2);
    }

    public void clearPref() {
        App.getInstance().getSharedPreferences(FILE_SETTING, 0).edit().clear().apply();
    }

    public String formatTime(long j) {
        return formatTime(j, true);
    }

    public String formatTime(long j, boolean z) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        if (!z) {
            simpleDateFormat = new SimpleDateFormat("mm:ss");
        }
        Date date = new Date(j);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return simpleDateFormat.format(date);
    }
}
