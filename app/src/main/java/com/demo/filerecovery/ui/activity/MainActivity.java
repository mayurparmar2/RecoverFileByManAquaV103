package com.demo.filerecovery.ui.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.airbnb.lottie.LottieAnimationView;




import com.demo.filerecovery.asynctask.ScanAsyncTask;
import com.demo.filerecovery.model.modul.fileprotection.FileProtectionActivity;
import com.demo.filerecovery.utilts.CommonUtils;
import com.demo.filerecovery.utilts.Utils;
import com.demo.filerecovery.R;
import com.skyfishjy.library.RippleBackground;

import java.io.File;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_PERMISSIONS = 100;
    private final String TAG = "MainActivity";
    private final String[] readAndWritePermissions = {"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};
    private final ActivityResultLauncher<Intent> requestExternalPermissionLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() {
        @Override
        public final void onActivityResult(Object obj) {
            MainActivity.this.lambda$new$0$MainActivity((ActivityResult) obj);
        }
    });
    public boolean processExecuting = false;
    ImageButton btnScan;
    RelativeLayout cvAudio;
    RelativeLayout cvDocument;
    RelativeLayout cvImage;
    RelativeLayout cvProtectFile;
    RelativeLayout cvRestoredFile;
    RelativeLayout cvVideo;
    Dialog dialog;
     
    ImageView ivDrawer;
    LottieAnimationView ivSearch;
    ImageView ivSpace;
    DrawerLayout mDrawerLayout;
    ScanAsyncTask mScanAsyncTask;
    RippleBackground rippleBackground;
    RelativeLayout rlBanner;
    RelativeLayout rlNative;
    TextView tvNumber;
    private boolean isBack;
    private boolean disableAdResumeByHomeButton = false;
    private boolean onUserEarnedReward = false;

    public void lambda$new$0$MainActivity(ActivityResult activityResult) {
        if (Build.VERSION.SDK_INT < 30 || Environment.isExternalStorageManager()) {
            Toast.makeText(this, (int) R.string.granted_permission, Toast.LENGTH_LONG).show();
            return;
        }
        Toast.makeText(this, (int) R.string.denied_permission, Toast.LENGTH_LONG).show();
        createRestoreDirectory();
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Utils.setLocale(this);
        Utils.setStatusBarGradiant(this);
        setContentView(R.layout.activity_main);
        intDrawer();
        intView();
        intEvent();
        checkPermission();
        BackDialog();
        this.rlNative = (RelativeLayout) findViewById(R.id.rl_native);
        this.ivSpace = (ImageView) findViewById(R.id.iv_space);
        this.rlBanner = (RelativeLayout) findViewById(R.id.rl_banner);
      
        
        
    }

    public void intDrawer() {
        this.mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ImageView imageView = (ImageView) findViewById(R.id.toolbar);
        this.ivDrawer = imageView;
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });
        findViewById(R.id.ll_privacy).setOnClickListener(this);
        findViewById(R.id.ll_share).setOnClickListener(this);
        findViewById(R.id.ll_rate).setOnClickListener(this);
    }

    public void intView() {
        this.btnScan = (ImageButton) findViewById(R.id.btnScan);
        this.tvNumber = (TextView) findViewById(R.id.tvNumber);
        this.ivSearch = (LottieAnimationView) findViewById(R.id.ivSearch);
        this.rippleBackground = (RippleBackground) findViewById(R.id.im_scan_bg);
        this.cvImage = (RelativeLayout) findViewById(R.id.cvImage);
        this.cvAudio = (RelativeLayout) findViewById(R.id.cvAudio);
        this.cvVideo = (RelativeLayout) findViewById(R.id.cvVideo);
        this.cvDocument = (RelativeLayout) findViewById(R.id.cvDocument);
        this.cvRestoredFile = (RelativeLayout) findViewById(R.id.cvRestoredFile);
        this.cvProtectFile = (RelativeLayout) findViewById(R.id.cvProtectFile);
    }

    public void intEvent() {
        this.cvImage.setOnClickListener(this);
        this.cvAudio.setOnClickListener(this);
        this.cvVideo.setOnClickListener(this);
        this.cvDocument.setOnClickListener(this);
        this.cvRestoredFile.setOnClickListener(this);
        this.cvProtectFile.setOnClickListener(this);
    }

    private void openScan(int i) {
        if (isReadAndWritePermissionsGranted()) {
            actionOpenScan(i);
        } else {
            Toast.makeText(this, (int) R.string.error_permission_denided, Toast.LENGTH_LONG).show();
        }
    }

    private void actionOpenScan(final int i) {
        if (i == 4) {
            MainActivity.this.startActivity(new Intent(MainActivity.this, RestoredFileActivity.class));

        } else if (i == 5) {
            MainActivity.this.startActivity(new Intent(MainActivity.this, FileProtectionActivity.class));

        } else {
            ScanFileActivity.Companion.setType(i);
            MainActivity.this.startActivity(new Intent(MainActivity.this, ScanFileActivity.class));
        }
    }

    private boolean isReadAndWritePermissionsGranted() {
        if (Build.VERSION.SDK_INT > 29) {
            return Environment.isExternalStorageManager();
        }
        if (Build.VERSION.SDK_INT > 23) {
            for (String str : this.readAndWritePermissions) {
                if (checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
            return true;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.ll_privacy) {
            this.mDrawerLayout.closeDrawer(GravityCompat.START);
            CommonUtils.getInstance().showPolicy(this);
        }
        if (id == R.id.ll_share) {
            this.mDrawerLayout.closeDrawer(GravityCompat.START);
            CommonUtils.getInstance().shareApp(this);
        }
        if (id == R.id.ll_rate) {
            this.mDrawerLayout.closeDrawer(GravityCompat.START);
            CommonUtils.getInstance().rateApp(this);
        }
        if (id == R.id.btnShare) {
            this.mDrawerLayout.closeDrawer(GravityCompat.START);
            CommonUtils.getInstance().shareApp(this);
        } else {
            switch (id) {
                case R.id.cvAudio:
                    openScan(2);
                    return;
                case R.id.cvDocument:
                    openScan(3);
                    return;
                case R.id.cvImage:
                    openScan(0);
                    return;
                case R.id.cvProtectFile:
                    openScan(5);
                    return;
                case R.id.cvRestoredFile:
                    openScan(4);
                    return;
                case R.id.cvVideo:
                    openScan(1);
                    return;
                default:
                    return;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 100) {
            for (int i2 : iArr) {
                if (iArr.length <= 0 || i2 != 0) {
                    Toast.makeText(this, getString(R.string.this_permission), Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    createRestoreDirectory();
                }
            }
        }
    }

    private void createRestoreDirectory() {
        File file = new File(Utils.getPathSave(getString(R.string.restore_folder_path_audio)));
        if (!file.exists()) {
            file.mkdirs();
        }
        File file2 = new File(Utils.getPathSave(getString(R.string.restore_folder_path_video)));
        if (!file2.exists()) {
            file2.mkdirs();
        }
        File file3 = new File(Utils.getPathSave(getString(R.string.restore_folder_path_photo)));
        if (!file3.exists()) {
            file3.mkdirs();
        }
        File file4 = new File(Utils.getPathSave(getString(R.string.restore_folder_path_document)));
        if (file4.exists()) {
            return;
        }
        file4.mkdirs();
    }

    private void checkPermission() {
        ArrayList arrayList = new ArrayList();
        if (Build.VERSION.SDK_INT >= 30) {
            checkAndRequestExternalPermissionAndroid11();
        } else if (Build.VERSION.SDK_INT >= 23) {
            if (!Utils.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE")) {
                arrayList.add("android.permission.WRITE_EXTERNAL_STORAGE");
            }
            if (!Utils.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE")) {
                arrayList.add("android.permission.READ_EXTERNAL_STORAGE");
            }
            if (arrayList.isEmpty()) {
                return;
            }
            requestPermissions((String[]) arrayList.toArray(new String[0]), 100);
        }
    }

    public void checkAndRequestExternalPermissionAndroid11() {
        if (Build.VERSION.SDK_INT <= 29 || Environment.isExternalStorageManager()) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.permission_title);
        builder.setMessage(R.string.request_permission_cause);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public final void onClick(DialogInterface dialogInterface, int i) {
                MainActivity.this.lambda$checkAndRequestExternalPermissionAndroid11$10$MainActivity(dialogInterface, i);
            }
        });
        builder.setNegativeButton(R.string.cancel_dialog, new DialogInterface.OnClickListener() {
            @Override
            public final void onClick(DialogInterface dialogInterface, int i) {
                MainActivity.this.lambda$checkAndRequestExternalPermissionAndroid11$11$MainActivity(dialogInterface, i);
            }
        });
        builder.create().show();
    }

    public void lambda$checkAndRequestExternalPermissionAndroid11$10$MainActivity(DialogInterface dialogInterface, int i) {
        Intent intent = new Intent();
        intent.setAction("android.settings.MANAGE_APP_ALL_FILES_ACCESS_PERMISSION");
        intent.setData(Uri.fromParts("package", getPackageName(), null));
        this.requestExternalPermissionLauncher.launch(intent);
    }

    public void lambda$checkAndRequestExternalPermissionAndroid11$11$MainActivity(DialogInterface dialogInterface, int i) {
        Toast.makeText(this, (int) R.string.denied_permission, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        ScanAsyncTask scanAsyncTask = this.mScanAsyncTask;
        if (scanAsyncTask == null || scanAsyncTask.getStatus() != AsyncTask.Status.RUNNING) {
            this.dialog.show();
        } else {
            Toast.makeText(this, getString(R.string.scan_wait), Toast.LENGTH_LONG).show();
        }
    }

    private void BackDialog() {
        Dialog dialog = new Dialog(this);
        this.dialog = dialog;
        dialog.requestWindowFeature(1);
        this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        this.dialog.setContentView(R.layout.back_dialog);
        this.dialog.setCancelable(false);
        RelativeLayout relativeLayout = (RelativeLayout) this.dialog.findViewById(R.id.native_exit);
      
        
        ((LinearLayout) this.dialog.findViewById(R.id.rl_yes)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.this.checkClickValidation()) {
                    MainActivity.this.startActivity(new Intent(MainActivity.this, ThankYouActivity.class));
                    MainActivity.this.dialog.dismiss();
                }
            }
        });
        ((LinearLayout) this.dialog.findViewById(R.id.rl_no)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.this.checkClickValidation()) {
                    MainActivity.this.dialog.dismiss();
                }
            }
        });
    }

    public boolean checkClickValidation() {
        if (this.processExecuting) {
            return false;
        }
        this.processExecuting = true;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MainActivity.this.processExecuting = false;
            }
        }, 1500L);
        return true;
    }
}
