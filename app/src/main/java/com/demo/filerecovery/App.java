package com.demo.filerecovery;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Process;

import com.demo.filerecovery.utilts.SharePreferencesManager;


public class App extends Application {
    private static App instance = null;
    private static App myApp = null;
    private Activity currentActivity;
    private StorageCommon storageCommon;

    public static App getInstance() {
        return instance;
    }

    public StorageCommon getStorageCommon() {
        return this.storageCommon;
    }

    @Override
    public void onCreate() {
        SharePreferencesManager.initializeInstance(this);
        super.onCreate();
        this.storageCommon = new StorageCommon();
        instance = this;
        if (isMainProcess()) {
            myApp = this;
        }
    }

    private boolean isMainProcess() {
        return getPackageName().equals(getProcessNames());
    }

    public String getProcessNames() {
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE)).getRunningAppProcesses()) {
            if (runningAppProcessInfo.pid == myPid) {
                return runningAppProcessInfo.processName;
            }
        }
        return null;
    }
}
