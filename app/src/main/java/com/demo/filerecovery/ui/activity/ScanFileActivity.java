package com.demo.filerecovery.ui.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;

import com.demo.filerecovery.R;
import com.demo.filerecovery.asynctask.ScanAsyncTask;
import com.demo.filerecovery.base.BaseActivity;
import com.demo.filerecovery.databinding.ActivityScanFileBinding;
import com.demo.filerecovery.model.modul.recoveryaudio.AlbumAudioActivity;
import com.demo.filerecovery.model.modul.recoverydocument.AlbumDocumentActivity;
import com.demo.filerecovery.model.modul.recoveryphoto.AlbumPhotoActivity;
import com.demo.filerecovery.model.modul.recoveryvideo.AlbumVideoActivity;

import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;


public final class ScanFileActivity extends BaseActivity<ActivityScanFileBinding> implements ScanAsyncTask.ScanAsyncTaskCallback {
    public static final Companion Companion = new Companion(null);
    public static int type;
    private final String TAG;

    ImageView ivSpace;
    RelativeLayout rlBanner;
    RelativeLayout rlNative;
    private boolean isShowNativeSuccess;
    private ScanAsyncTask scanAsyncTask;

    public ScanFileActivity() {
        super(R.layout.activity_scan_file);
        this.TAG = "ScanFileActivity";
    }

    public static final void m68initView$lambda0(ScanFileActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.onBackPressed();
    }

    public static final void m69initView$lambda1(ScanFileActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        CharSequence text = this$0.getBinding().btnScanNow.getText();
        if (Intrinsics.areEqual(text, this$0.getText(R.string.scan_now))) {
            this$0.scanType();
        } else if (Intrinsics.areEqual(text, this$0.getText(R.string.stop_scanning))) {
            ScanAsyncTask scanAsyncTask = this$0.scanAsyncTask;
            if (scanAsyncTask != null) {
                Intrinsics.checkNotNull(scanAsyncTask);
                if (scanAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {
                    ScanAsyncTask scanAsyncTask2 = this$0.scanAsyncTask;
                    Intrinsics.checkNotNull(scanAsyncTask2);
                    scanAsyncTask2.cancel(true);
                    this$0.scanAsyncTask = null;
                    this$0.getBinding().btnScanNow.setText(this$0.getText(R.string.scan_now));
                    this$0.getBinding().btnScanNow.setTextColor(this$0.getResources().getColor(R.color.light_rad));
                    this$0.getBinding().btnScanNow.setBackground(AppCompatResources.getDrawable(this$0, R.drawable.border_gradient));
                    this$0.getBinding().tvtStatusScan.setText(this$0.getText(R.string.tap_scan_now));
                    this$0.setDataScanned(0);
                    this$0.getBinding().tvtFileFound.setText(this$0.getString(R.string.value_file_found, new Object[]{0}));
                    this$0.getBinding().tvtProgress.setText("0%");
                    this$0.getBinding().circularProgress.setCurrentProgress(0.0d);
                }
            }
        } else if (Intrinsics.areEqual(text, this$0.getText(R.string.open_file))) {
            this$0.openAlbumWhenScanDone(type);
        }
    }


    public final String getTAG() {
        return "ScanFileActivity";
    }

    @Override
    public void initView() {


        getBinding().toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                ScanFileActivity.m68initView$lambda0(ScanFileActivity.this, view);
            }
        });
        setDataScanned(0);
        getBinding().tvtProgress.setText("0%");
        int i = type;
        if (i == 0) {
            getBinding().toolbar.setTitle(getString(R.string.photo_recovery));
            getBinding().circularProgress.setGradient(CircularProgressIndicator.LINEAR_GRADIENT, getResources().getColor(R.color.light_rad));
        } else if (i == 1) {
            getBinding().toolbar.setTitle(getString(R.string.video_recovery));
            getBinding().circularProgress.setGradient(CircularProgressIndicator.LINEAR_GRADIENT, getResources().getColor(R.color.light_rad));
        } else if (i == 2) {
            getBinding().toolbar.setTitle(getString(R.string.audio_recovery));
            getBinding().circularProgress.setGradient(CircularProgressIndicator.LINEAR_GRADIENT, getResources().getColor(R.color.light_rad));
        } else if (i == 3) {
            getBinding().toolbar.setTitle(getString(R.string.document_recovery));
            getBinding().circularProgress.setGradient(CircularProgressIndicator.LINEAR_GRADIENT, getResources().getColor(R.color.light_rad));
        }
        getBinding().btnScanNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                ScanFileActivity.m69initView$lambda1(ScanFileActivity.this, view);
            }
        });
        scanType();
    }


    private final void scanType() {
        ScanAsyncTask scanAsyncTask = new ScanAsyncTask(type, this);
        this.scanAsyncTask = scanAsyncTask;
        scanAsyncTask.setCallback(this);
        ScanAsyncTask.mAlbumPhoto.clear();
        ScanAsyncTask.mAlbumVideo.clear();
        ScanAsyncTask.mAlbumAudio.clear();
        ScanAsyncTask.mAlbumDocument.clear();
        getBinding().btnScanNow.setText(getText(R.string.stop_scanning));
        getBinding().tvtStatusScan.setText(getText(R.string.scanning));
        ScanAsyncTask scanAsyncTask2 = this.scanAsyncTask;
        if (scanAsyncTask2 != null) {
            scanAsyncTask2.execute(new Void[0]);
        }
    }

    @Override
    public void onPostExecuteCallback() {
        getBinding().tvtStatusScan.setText(getString(R.string.completed));
        getBinding().btnScanNow.setBackground(AppCompatResources.getDrawable(this, R.drawable.bg_btn_scan_done));
        getBinding().btnScanNow.setText(getText(R.string.open_file));
        getBinding().btnScanNow.setTextColor(getResources().getColor(R.color.white));
        int i = type;
        if (i == 0) {
            if (ScanAsyncTask.mAlbumPhoto.size() == 0) {
                disableBtnOpenFile();
            }
        } else if (i == 1) {
            if (ScanAsyncTask.mAlbumVideo.size() == 0) {
                disableBtnOpenFile();
            }
        } else if (i != 2) {
            if (i == 3 && ScanAsyncTask.mAlbumDocument.size() == 0) {
                disableBtnOpenFile();
            }
        } else if (ScanAsyncTask.mAlbumAudio.size() == 0) {
            disableBtnOpenFile();
        }
    }

    private final void openAlbumWhenScanDone(int i) {
        executeOpenFile(i);
    }

    private final void executeOpenFile(int i) {
        if (i == 0 && ScanAsyncTask.mAlbumPhoto.size() != 0) {
            ScanFileActivity.this.startActivity(new Intent(ScanFileActivity.this.getApplicationContext(), AlbumPhotoActivity.class));
            ScanFileActivity.this.finish();
        }
        if (i == 1 && ScanAsyncTask.mAlbumVideo.size() != 0) {
            ScanFileActivity.this.startActivity(new Intent(ScanFileActivity.this.getApplicationContext(), AlbumVideoActivity.class));
            ScanFileActivity.this.finish();
        }
        if (i == 2 && ScanAsyncTask.mAlbumAudio.size() != 0) {
            ScanFileActivity.this.startActivity(new Intent(ScanFileActivity.this.getApplicationContext(), AlbumAudioActivity.class));
            ScanFileActivity.this.finish();
        }
        if (i != 3 || ScanAsyncTask.mAlbumDocument.size() == 0) {
            return;
        }
        ScanFileActivity.this.startActivity(new Intent(ScanFileActivity.this.getApplicationContext(), AlbumDocumentActivity.class));
        ScanFileActivity.this.finish();
    }

    private final void disableBtnOpenFile() {
        getBinding().btnScanNow.setAlpha(0.1f);
        getBinding().btnScanNow.setEnabled(false);
    }

    @Override
    public void onFileCount(Integer[] numArr) {
        if (numArr != null) {
            setDataScanned(numArr[0].intValue());
        }
    }

    @Override
    public void onProgressCallback(int i) {
        Log.i("ScanFileActivity", Intrinsics.stringPlus("onProgressCallback: ", Integer.valueOf(i)));
        TextView textView = getBinding().tvtProgress;
        StringBuilder sb = new StringBuilder();
        sb.append(i);
        sb.append('%');
        textView.setText(sb.toString());
        getBinding().circularProgress.setCurrentProgress(i);
    }

    @Override
    public void onDestroy() {
        ScanAsyncTask scanAsyncTask;
        try {
            ScanAsyncTask scanAsyncTask2 = this.scanAsyncTask;
            if (scanAsyncTask2 != null) {
                Intrinsics.checkNotNull(scanAsyncTask2);
                if (scanAsyncTask2.getStatus() == AsyncTask.Status.RUNNING && (scanAsyncTask = this.scanAsyncTask) != null) {
                    scanAsyncTask.cancel(true);
                }
            }
        } catch (RuntimeException e) {
            String message = e.getMessage();
            if (message != null) {
                Log.e(getTAG(), message);
            }
        }
        super.onDestroy();
    }

    private final void setDataScanned(int i) {
        int i2 = type;
        if (i2 == 0) {
            getBinding().tvtFileFound.setText(getString(R.string.value_photo_found, new Object[]{Integer.valueOf(i)}));
        } else if (i2 == 1) {
            getBinding().tvtFileFound.setText(getString(R.string.value_video_found, new Object[]{Integer.valueOf(i)}));
        } else if (i2 == 2) {
            getBinding().tvtFileFound.setText(getString(R.string.value_audio_found, new Object[]{Integer.valueOf(i)}));
        } else if (i2 == 3) {
            getBinding().tvtFileFound.setText(getString(R.string.value_document_found, new Object[]{Integer.valueOf(i)}));
        }
    }

    public static final class Companion {
        public Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final int getType() {
            return ScanFileActivity.type;
        }

        public final void setType(int i) {
            ScanFileActivity.type = i;
        }
    }
}
