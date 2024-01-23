package com.demo.filerecovery.model.modul.recoveryvideo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.DocumentsContract;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.demo.filerecovery.Constants;
import com.demo.filerecovery.R;
import com.demo.filerecovery.model.modul.recoveryvideo.Model.VideoModel;
import com.demo.filerecovery.model.modul.recoveryvideo.task.RecoverOneVideosAsyncTask;
import com.demo.filerecovery.ui.activity.RestoreResultActivity;
import com.demo.filerecovery.utilts.FileUtil;
import com.demo.filerecovery.utilts.SharePreferenceUtils;
import com.demo.filerecovery.utilts.Utils;

import java.io.File;
import java.security.AccessController;
import java.text.DateFormat;
import java.util.ArrayList;


public class FileInfoActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "FileInfoActivity";
    Button btnOpen;
    Button btnRestore;
    Button btnShare;
    ImageView ivVideo;
    RecoverOneVideosAsyncTask mRecoverOneVideosAsyncTask;
    VideoModel mVideoModel;
    SharedPreferences sharedPreferences;
    Toolbar toolbar;
    TextView tvDate;
    TextView tvSize;
    TextView tvType;
    private boolean isRewarded;

    private static boolean checkIfSDCardRoot(Uri uri) {
        return isExternalStorageDocument(uri) && isRootUri(uri) && !isInternalStorage(uri);
    }

    private static boolean isRootUri(Uri uri) {
        return DocumentsContract.getTreeDocumentId(uri).endsWith(":");
    }

    public static boolean isInternalStorage(Uri uri) {
        return isExternalStorageDocument(uri) && DocumentsContract.getTreeDocumentId(uri).contains("primary");
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Utils.setStatusBarGradiant(this);
        setContentView(R.layout.activity_file_info);
        intView();
        intData();
        intEvent();
    }

    public void intView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.toolbar = toolbar;
        toolbar.setTitle(getString(R.string.video_recovery));
        setSupportActionBar(this.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.btnOpen = (Button) findViewById(R.id.btnOpen);
        this.btnShare = (Button) findViewById(R.id.btnShare);
        this.btnRestore = (Button) findViewById(R.id.btnRestore);
        this.tvDate = (TextView) findViewById(R.id.tvDate);
        this.tvSize = (TextView) findViewById(R.id.tvSize);
        this.tvType = (TextView) findViewById(R.id.tvType);
        this.ivVideo = (ImageView) findViewById(R.id.ivVideo);
    }

    public void intData() {
        File file = new File(getIntent().getStringExtra("pathFile"));
        this.mVideoModel = new VideoModel(file.getAbsolutePath(), file.lastModified(), file.length(), "mp4", "");
        try {
            TextView textView = this.tvDate;
            textView.setText(DateFormat.getDateInstance().format(Long.valueOf(this.mVideoModel.getLastModified())) + "  " + this.mVideoModel.getTimeDuration());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG, "intData: " + this.mVideoModel);
        Log.d(TAG, "intData: " + this.mVideoModel.getSizePhoto());
        this.tvType.setText(this.mVideoModel.getTypeFile());
        try {
            RequestManager with = Glide.with((FragmentActivity) this);
            with.load("file://" + this.mVideoModel.getPathPhoto()).diskCacheStrategy(DiskCacheStrategy.ALL).priority(Priority.HIGH).centerCrop().error(R.drawable.ic_error).into(this.ivVideo);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void intEvent() {
        this.btnOpen.setOnClickListener(this);
        this.btnShare.setOnClickListener(this);
        this.btnRestore.setOnClickListener(this);
        this.ivVideo.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnOpen:
            case R.id.ivVideo:
                FileUtil.openFileVideo(this.mVideoModel.getPathPhoto(), this);
                return;
            case R.id.btnRestore:
                excuteRestore();
                return;
            case R.id.btnShare:
                shareVideo(this.mVideoModel.getPathPhoto());
                return;
            default:
                return;
        }
    }

    private void excuteRestore() {
        startRestoreFile();
    }

    public void openRestoreActivity() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(this.mVideoModel.getPathPhoto());
        Intent intent = new Intent(getApplicationContext(), RestoreResultActivity.class);
        intent.putExtra("value", this.mVideoModel.getSizePhoto());
        intent.putExtra(Constants.FB_LOG_RESTORE_RESULT_TYPE, 1);
        intent.putStringArrayListExtra("listPath", arrayList);
        startActivityForResult(intent, 101);
        SharePreferenceUtils.restoreFeatureUsed(this);
    }

    private void startRestoreFile() {
        RecoverOneVideosAsyncTask recoverOneVideosAsyncTask = new RecoverOneVideosAsyncTask(this, this.mVideoModel, new RecoverOneVideosAsyncTask.OnRestoreListener() {
            @Override
            public final void onComplete() {
                FileInfoActivity.this.openRestoreActivity();
            }
        });
        this.mRecoverOneVideosAsyncTask = recoverOneVideosAsyncTask;
        recoverOneVideosAsyncTask.execute(new String[0]);
    }

    public void cancleUIUPdate() {
        RecoverOneVideosAsyncTask recoverOneVideosAsyncTask = this.mRecoverOneVideosAsyncTask;
        if (recoverOneVideosAsyncTask == null || recoverOneVideosAsyncTask.getStatus() != AsyncTask.Status.RUNNING) {
            return;
        }
        this.mRecoverOneVideosAsyncTask.cancel(true);
        this.mRecoverOneVideosAsyncTask = null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void shareVideo(String str) {
        try {
            startActivity(new Intent().setAction("android.intent.action.SEND").setType("video/*").setFlags(1).putExtra("android.intent.extra.STREAM", FileProvider.getUriForFile(this, getPackageName() + ".provider", new File(str))));
        } catch (Exception unused) {
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        cancleUIUPdate();
    }

    @Override
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 100 && i2 == -1) {
            SharedPreferences.Editor edit = this.sharedPreferences.edit();
            if (intent != null) {
                Uri data = intent.getData();
                if (Build.VERSION.SDK_INT >= 19 && AccessController.getContext() != null) {
                    getContentResolver().takePersistableUriPermission(data, 3);
                }
                if (checkIfSDCardRoot(data)) {
                    edit.putString("sdCardUri", data.toString());
                    edit.putBoolean("storagePermission", true);
                    if (edit.commit()) {
                        edit.apply();
                        RecoverOneVideosAsyncTask recoverOneVideosAsyncTask = new RecoverOneVideosAsyncTask(this, this.mVideoModel, new RecoverOneVideosAsyncTask.OnRestoreListener() {
                            @Override
                            public final void onComplete() {
                                FileInfoActivity.this.lambda$onActivityResult$0$FileInfoActivity();
                            }
                        });
                        this.mRecoverOneVideosAsyncTask = recoverOneVideosAsyncTask;
                        recoverOneVideosAsyncTask.execute(new String[0]);
                    }
                } else {
                    Toast.makeText(this, "Please Select Right SD Card.", Toast.LENGTH_SHORT).show();
                    edit.putBoolean("storagePermission", false);
                    edit.putString("sdCardUri", "");
                }
            } else {
                Toast.makeText(this, "Please Select Right SD Card.", Toast.LENGTH_SHORT).show();
                edit.putString("sdCardUri", "");
            }
            edit.commit();
        }
        if (i == 101 && i2 == 102) {
            setResult(102);
            finish();
        }
        if (i == 200 && i2 == -1) {
            finish();
        }
    }

    public void lambda$onActivityResult$0$FileInfoActivity() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(this.mVideoModel.getPathPhoto());
        Intent intent = new Intent(getApplicationContext(), RestoreResultActivity.class);
        intent.putExtra("value", 1);
        intent.putExtra(Constants.FB_LOG_RESTORE_RESULT_TYPE, 1);
        intent.putStringArrayListExtra("listPath", arrayList);
        startActivityForResult(intent, 101);
    }
}
