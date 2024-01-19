package com.demo.filerecovery.ui.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;






import com.demo.filerecovery.utilts.FileUtil;
import com.demo.filerecovery.utilts.SharePreferenceUtils;
import com.demo.filerecovery.utilts.Utils;
import com.demo.filerecovery.R;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";

    private CompositeDisposable compositeDisposable;
    private List<String> listDeviceTest = new ArrayList();

    static void lambda$checkDateFileProtection$3(Boolean bool) throws Exception {
        Log.e(TAG, "checkDateFileProtection: " + bool);
    }

    @Override
    public void onStart() {
        super.onStart();
        Utils.setLocale(this);
        SharePreferenceUtils.clearRecoveryDone(this);
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_splash);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        this.compositeDisposable = new CompositeDisposable();
        if ((Build.VERSION.SDK_INT >= 30 && Environment.isExternalStorageManager()) || (Build.VERSION.SDK_INT >= 23 && checkPermission(getPermission()))) {
            checkDateFileProtection();
        }
        new Handler().postDelayed(new Runnable() { // from class: com.demo.filerecovery.ui.activity.SplashActivity.3.1
            @Override // java.lang.Runnable
            public final void run() {
                SplashActivity.this.startMain();
            }
        }, 1000L);


        try {
            for (Signature signature : getPackageManager().getPackageInfo(getPackageName(), 64).signatures) {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA");
                messageDigest.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(messageDigest.digest(), 0));
            }
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private List<String> getPermission() {
        return Arrays.asList("android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE");
    }

    private boolean checkPermission(List<String> list) {
        for (String str : list) {
            if (Build.VERSION.SDK_INT >= 23 && checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private void checkDateFileProtection() {
        this.compositeDisposable.add(Observable.fromCallable(new Callable() {
            @Override
            public final Object call() {
                return Boolean.valueOf(SplashActivity.this.loadListAndDeleteFileProtection());
            }
        }).subscribeOn(Schedulers.io()).observeOn(Schedulers.newThread()).subscribe(new Consumer() {
            @Override
            public void accept(Object obj) throws Exception {
                SplashActivity.lambda$checkDateFileProtection$3((Boolean) obj);
            }
        }));
    }

    public boolean loadListAndDeleteFileProtection() {
        ArrayList<File> listFileProtection = FileUtil.getListFileProtection(this);
        if (listFileProtection.size() > 0) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(6, -30);
            Iterator<File> it = listFileProtection.iterator();
            while (it.hasNext()) {
                File next = it.next();
                if (new Date(next.lastModified()).before(calendar.getTime())) {
                    return next.delete();
                }
            }
            return false;
        }
        return false;
    }

    public void startMain() {
        startActivity(new Intent(this, HelpActivity.class));
        finish();
    }

    @Override
    public void onStop() {
        super.onStop();
        this.compositeDisposable.clear();
    }

}
