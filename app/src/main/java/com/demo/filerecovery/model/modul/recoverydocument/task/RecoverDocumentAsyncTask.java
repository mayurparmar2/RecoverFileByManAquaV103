package com.demo.filerecovery.model.modul.recoverydocument.task;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.widget.TextView;

import com.demo.filerecovery.model.modul.recoverydocument.Model.DocumentModel;
import com.demo.filerecovery.ui.activity.LoadingDialog;
import com.demo.filerecovery.utilts.MediaScanner;
import com.demo.filerecovery.utilts.Utils;
import com.demo.filerecovery.R;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


public class RecoverDocumentAsyncTask extends AsyncTask<String, Integer, String> {
    int count = 0;
    TextView tvNumber;
    private ArrayList<DocumentModel> listDocument;
    private Context mContext;
    private OnRestoreListener onRestoreListener;
    private LoadingDialog progressDialog;


    public RecoverDocumentAsyncTask(Context context, ArrayList<DocumentModel> arrayList, OnRestoreListener onRestoreListener) {
        this.mContext = context;
        this.listDocument = arrayList;
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
        int i = 0;
        while (i < this.listDocument.size()) {
            File file = new File(this.listDocument.get(i).getPathDocument());
            File file2 = new File(Utils.getPathSave(this.mContext.getString(R.string.restore_folder_path_document)));
            File file3 = new File(Utils.getPathSave(this.mContext.getString(R.string.restore_folder_path_document)) + File.separator + FilenameUtils.getName(this.listDocument.get(i).getPathDocument()));
            if (!file3.exists()) {
                file2.mkdirs();
            }
            copyFile(file, file3);
            if (Build.VERSION.SDK_INT >= 19) {
                Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                intent.setData(Uri.fromFile(file3));
                this.mContext.sendBroadcast(intent);
            }
            new MediaScanner(this.mContext, file3);
            i++;
            this.count = i;
            publishProgress(Integer.valueOf(i));
        }
        try {
            Thread.sleep(2000L);
            return null;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void copyFile(File sourceFile, File destinationFile) {
        try (FileInputStream inputStream = new FileInputStream(sourceFile);
             FileOutputStream outputStream = new FileOutputStream(destinationFile)) {
             IOUtils.copy(inputStream, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public void copyFile(File file, File file2) {
//        FileInputStream fileInputStream = null;
//        try {
//            try {
//                FileInputStream fileInputStream2 = new FileInputStream((File) file);
//                try {
//                    try {
//                        FileOutputStream fileOutputStream = new FileOutputStream(file2);
//                        try {
//                            IOUtils.copy(fileInputStream2, fileOutputStream);
//                            IOUtils.closeQuietly((OutputStream) fileOutputStream);
//                        } catch (IOException unused) {
//                            IOUtils.closeQuietly((OutputStream) fileOutputStream);
//                            IOUtils.closeQuietly((InputStream) fileInputStream2);
//                        } catch (Throwable th) {
//                            IOUtils.closeQuietly((OutputStream) fileOutputStream);
//                            IOUtils.closeQuietly((InputStream) fileInputStream2);
//                            throw th;
//                        }
//                    } catch (IOException unused2) {
//                        file = 0;
//                        IOUtils.closeQuietly((OutputStream) file);
//                        IOUtils.closeQuietly((InputStream) fileInputStream2);
//                        file = file;
//                        fileInputStream = fileInputStream2;
//                        IOUtils.closeQuietly((InputStream) fileInputStream);
//                    } catch (Throwable unused3) {
//                        file = 0;
//                        IOUtils.closeQuietly((OutputStream) file);
//                        IOUtils.closeQuietly((InputStream) fileInputStream2);
//                        file = file;
//                        fileInputStream = fileInputStream2;
//                        IOUtils.closeQuietly((InputStream) fileInputStream);
//                    }
//                } catch (IOException unused4) {
//                    IOUtils.closeQuietly((OutputStream) file);
//                    IOUtils.closeQuietly((InputStream) fileInputStream2);
//                    file = file;
//                    fileInputStream = fileInputStream2;
//                    IOUtils.closeQuietly((InputStream) fileInputStream);
//                } catch (Throwable unused5) {
//                    IOUtils.closeQuietly((OutputStream) file);
//                    IOUtils.closeQuietly((InputStream) fileInputStream2);
//                    file = file;
//                    fileInputStream = fileInputStream2;
//                    IOUtils.closeQuietly((InputStream) fileInputStream);
//                }
//                fileInputStream = fileInputStream2;
//            } catch (IOException unused6) {
//                file = 0;
//                IOUtils.closeQuietly((OutputStream) file);
//                IOUtils.closeQuietly((InputStream) null);
//                IOUtils.closeQuietly((InputStream) fileInputStream);
//            } catch (Throwable unused7) {
//                file = 0;
//                IOUtils.closeQuietly((OutputStream) file);
//                IOUtils.closeQuietly((InputStream) null);
//                IOUtils.closeQuietly((InputStream) fileInputStream);
//            }
//        } catch (IOException unused8) {
//            IOUtils.closeQuietly((OutputStream) file);
//            IOUtils.closeQuietly((InputStream) null);
//            IOUtils.closeQuietly((InputStream) fileInputStream);
//        } catch (Throwable unused9) {
//            IOUtils.closeQuietly((OutputStream) file);
//            IOUtils.closeQuietly((InputStream) null);
//            IOUtils.closeQuietly((InputStream) fileInputStream);
//        }
//        IOUtils.closeQuietly((InputStream) fileInputStream);
//    }

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
    }

    public interface OnRestoreListener {
        void onComplete();
    }
}
