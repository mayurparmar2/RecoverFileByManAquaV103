package com.demo.filerecovery.model.modul.recoveryphoto.task;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.demo.filerecovery.model.modul.recoveryphoto.Model.PhotoModel;
import com.demo.filerecovery.ui.activity.LoadingDialog;
import com.demo.filerecovery.utilts.MediaScanner;
import com.demo.filerecovery.utilts.Utils;
import com.demo.filerecovery.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;


public class RecoverPhotosAsyncTask extends AsyncTask<String, Integer, String> {
    private final String TAG = getClass().getName();
    int count = 0;
    TextView tvNumber;
    private ArrayList<PhotoModel> listPhoto;
    private Context mContext;
    private OnRestoreListener onRestoreListener;
    private LoadingDialog progressDialog;


    public RecoverPhotosAsyncTask(Context context, ArrayList<PhotoModel> arrayList, OnRestoreListener onRestoreListener) {
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
        Log.e("TAG", "doInBackground: " + this.listPhoto.size());
        for (int i = 0; i < this.listPhoto.size(); i++) {
            File file = new File(this.listPhoto.get(i).getPathPhoto());
            File file2 = new File(Utils.getPathSave(this.mContext.getString(R.string.restore_folder_path_photo)));
            File file3 = new File(Utils.getPathSave(this.mContext.getString(R.string.restore_folder_path_photo)) + File.separator + getFileName(this.listPhoto.get(i).getPathPhoto()));
            try {
                if (!file3.exists()) {
                    file2.mkdirs();
                }
                Log.e("TAG", "doInBackground: " + file);
                Log.e("TAG", "doInBackground: " + file3);
                copy(file, file3);
                Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                intent.setData(Uri.fromFile(file3));
                this.mContext.sendBroadcast(intent);
                new MediaScanner(this.mContext, file3);
                int i2 = i + 1;
                this.count = i2;
                publishProgress(Integer.valueOf(i2));
            } catch (IOException e) {
                e.printStackTrace();
                String str = this.TAG;
                Log.e(str, "doInBackground: " + e.getMessage());
            }
        }
        try {
            Thread.sleep(2000L);
            return null;
        } catch (InterruptedException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public void copy(File file, File file2) throws IOException {
        FileChannel channel = new FileInputStream(file).getChannel();
        FileChannel channel2 = new FileOutputStream(file2).getChannel();
        channel.transferTo(0L, channel.size(), channel2);
        channel.close();
        if (channel2 != null) {
            channel2.close();
        }
    }

    public String getFileName(String str) {
        String substring = str.substring(str.lastIndexOf("/") + 1);
        if (substring.endsWith(".jpg") || substring.endsWith(".jpeg") || substring.endsWith(".gif")) {
            return substring;
        }
        return substring + ".jpg";
    }

    @Override
    public void onPostExecute(String str) {
        super.onPostExecute( str);
        try {
            LoadingDialog loadingDialog = this.progressDialog;
            if (loadingDialog != null && loadingDialog.isShowing()) {
                this.progressDialog.dismiss();
                this.progressDialog = null;
            }
        } catch (Exception unused) {
        }
        OnRestoreListener onRestoreListener = this.onRestoreListener;
        if (onRestoreListener != null) {
            onRestoreListener.onComplete();
        }
    }

    @Override
    public void onProgressUpdate(Integer... numArr) {
        super.onProgressUpdate( numArr);
        Log.e("TAG", "onProgressUpdate: " + numArr);
    }

    public interface OnRestoreListener {
        void onComplete();
    }
}
