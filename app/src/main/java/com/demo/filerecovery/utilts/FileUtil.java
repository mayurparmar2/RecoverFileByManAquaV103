package com.demo.filerecovery.utilts;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.demo.filerecovery.Constants;
import com.demo.filerecovery.R;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;


public final class FileUtil {
    public static final String MIME_TYPE_JSON = "application/json";
    public static final String TAG = "FileUtil";

    public static String getMimeType(Context context, Uri uri) {
        if (uri.getScheme().equals("content")) {
            return context.getContentResolver().getType(uri);
        }
        String extension = getExtension(uri.toString());
        if (extension == null) {
            return null;
        }
        String mimeTypeFromExtension = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension.toLowerCase());
        return mimeTypeFromExtension == null ? handleMiscFileExtensions(extension) : mimeTypeFromExtension;
    }

    private static String getExtension(String str) {
        if (str == null || TextUtils.isEmpty(str)) {
            return null;
        }
        char[] charArray = str.toCharArray();
        for (int length = charArray.length - 1; length > 0; length--) {
            if (charArray[length] == '.') {
                return str.substring(length + 1);
            }
        }
        return null;
    }

    private static String handleMiscFileExtensions(String str) {
        if (str.equals("json")) {
            return MIME_TYPE_JSON;
        }
        return null;
    }

    public static void openFileVideo(String str, Context context) {
        Intent intent;
        if (Build.VERSION.SDK_INT < 24) {
            Intent intent2 = new Intent("android.intent.action.VIEW");
            intent2.setDataAndType(Uri.fromFile(new File(str)), "video/*");
            intent = Intent.createChooser(intent2, "Complete action using");
        } else {
            try {
                File file = new File(str);
                Intent intent3 = new Intent("android.intent.action.VIEW");
                Uri uriForFile = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file);
                context.grantUriPermission(context.getPackageName(), uriForFile, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent3.setType("*/*");
                if (Build.VERSION.SDK_INT < 24) {
                    uriForFile = Uri.fromFile(file);
                }
                intent3.setData(uriForFile);
                intent3.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent = Intent.createChooser(intent3, "Complete action using");
            } catch (Exception e) {
                e.printStackTrace();
                intent = null;
            }
        }
        if (intent != null) {
            context.startActivity(intent);
        } else {
            Toast.makeText(context, context.getString(R.string.can_not_open_file), Toast.LENGTH_SHORT).show();
        }
    }

    public static void openFileAudio(File file, Context context) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        if (file.exists()) {
            if (Build.VERSION.SDK_INT < 24) {
                intent.setDataAndType(Uri.fromFile(file), "audio/*");
            } else {
                Uri uriForFile = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file);
                context.grantUriPermission(context.getPackageName(), uriForFile, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setDataAndType(uriForFile, "audio/*");
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
            context.startActivity(Intent.createChooser(intent, "Complete action using"));
        }
    }

    public static void copyFileToInternalStorage(Context context, String str) {
        String name = FilenameUtils.getName(str);
        File file = new File(context.getFilesDir() + "/" + Constants.FILE_PROTECTION);
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(str);
            FileOutputStream fileOutputStream = new FileOutputStream(file + "/" + name);
            byte[] bArr = new byte[1024];
            while (true) {
                int read = fileInputStream.read(bArr);
                if (read != -1) {
                    fileOutputStream.write(bArr, 0, read);
                } else {
                    fileInputStream.close();
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    Log.e("TAG", "copyFileToInternalStorage: File saved successfully!");
                    boolean delete = new File(str).delete();
                    Log.e(TAG, "resultDelete: " + delete);
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    public static ArrayList<File> getListFileProtection(Context context) {
        ArrayList<File> arrayList = new ArrayList<>();
        File[] listFiles = new File(context.getFilesDir() + "/" + Constants.FILE_PROTECTION).listFiles();
        if (listFiles != null && listFiles.length > 0) {
            for (File file : listFiles) {
                if (file.isFile()) {
                    arrayList.add(file);
                }
            }
        }
        return arrayList;
    }

    public static void deleteFileProtection(Context context, String str) {
        File[] listFiles = new File(context.getFilesDir() + "/" + Constants.FILE_PROTECTION).listFiles();
        if (listFiles == null || listFiles.length <= 0) {
            return;
        }
        for (File file : listFiles) {
            if (str.equals(file.getPath())) {
                file.delete();
            }
        }
    }
}
