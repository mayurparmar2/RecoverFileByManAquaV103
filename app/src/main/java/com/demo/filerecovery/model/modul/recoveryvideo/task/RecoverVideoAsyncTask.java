package com.demo.filerecovery.model.modul.recoveryvideo.task;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.widget.TextView;

import com.demo.filerecovery.R;
import com.demo.filerecovery.model.modul.recoveryvideo.Model.VideoModel;
import com.demo.filerecovery.ui.activity.LoadingDialog;
import com.demo.filerecovery.utilts.MediaScanner;
import com.demo.filerecovery.utilts.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;


public class RecoverVideoAsyncTask extends AsyncTask<String, Integer, String> {
    private final String TAG = getClass().getName();
    int count = 0;
    TextView tvNumber;
    private ArrayList<VideoModel> listPhoto;
    private Context mContext;
    private OnRestoreListener onRestoreListener;
    private LoadingDialog progressDialog;


    public RecoverVideoAsyncTask(Context context, ArrayList<VideoModel> arrayList, OnRestoreListener onRestoreListener) {
        this.mContext = context;
        this.listPhoto = arrayList;
        this.onRestoreListener = onRestoreListener;
    }

    @Override
    public void onPreExecute() {
        super.onPreExecute();
        LoadingDialog loadingDialog = new LoadingDialog(this.mContext);
        this.progressDialog = loadingDialog;
        loadingDialog.setCancelable(false);
        this.progressDialog.show();
    }

    @Override
    public String doInBackground(String... strArr) {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < this.listPhoto.size(); i++) {
            File file = new File(this.listPhoto.get(i).getPathPhoto());
            File file2 = new File(Utils.getPathSave(this.mContext.getString(R.string.restore_folder_path_video)));
            File file3 = new File(Utils.getPathSave(this.mContext.getString(R.string.restore_folder_path_video)) + File.separator + getFileName(this.listPhoto.get(i).getPathPhoto()));
            try {
                if (!file3.exists()) {
                    file2.mkdirs();
                }
                copy(file, file3);
                if (Build.VERSION.SDK_INT >= 19) {
                    Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                    intent.setData(Uri.fromFile(file3));
                    this.mContext.sendBroadcast(intent);
                }
                new MediaScanner(this.mContext, file3);
                int i2 = i + 1;
                this.count = i2;
                publishProgress(Integer.valueOf(i2));
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e3) {
                e3.printStackTrace();
            }
        }
        return null;
    }

    public void copy(File file, File file2) throws IOException {
        try {
            FileChannel channel = new FileInputStream(file).getChannel();
            FileChannel channel2 = new FileOutputStream(file2).getChannel();
            channel.transferTo(0L, channel.size(), channel2);
            if (channel != null) {
                channel.close();
            }
            if (channel2 != null) {
                channel2.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getFileName(String str) {
        String substring = str.substring(str.lastIndexOf("/") + 1);
        if (substring.endsWith(".3gp") || substring.endsWith(".mp4") || substring.endsWith(".mkv") || substring.endsWith(".flv")) {
            return substring;
        }
        return substring + ".mp4";
    }

    @Override
    public void onPostExecute(String str) {
        super.onPostExecute(str);
        try {
            LoadingDialog loadingDialog = this.progressDialog;
            if (loadingDialog != null && loadingDialog.isShowing()) {
                this.progressDialog.dismiss();
                this.progressDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        OnRestoreListener onRestoreListener = this.onRestoreListener;
        if (onRestoreListener != null) {
            onRestoreListener.onComplete();
        }
    }

    @Override
    public void onProgressUpdate(Integer... numArr) {
        super.onProgressUpdate(numArr);
    }

    public interface OnRestoreListener {
        void onComplete();
    }
}
