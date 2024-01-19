package com.demo.filerecovery.utilts;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;

import java.io.File;


public class MediaScanner implements MediaScannerConnection.MediaScannerConnectionClient {
    private File destFile;
    private MediaScannerConnection mScannerConnection;

    public MediaScanner(Context context, File file) {
        this.destFile = file;
        MediaScannerConnection mediaScannerConnection = new MediaScannerConnection(context, this);
        this.mScannerConnection = mediaScannerConnection;
        mediaScannerConnection.connect();
    }

    @Override
    public void onMediaScannerConnected() {
        this.mScannerConnection.scanFile(this.destFile.getAbsolutePath(), null);
    }

    @Override
    public void onScanCompleted(String str, Uri uri) {
        this.mScannerConnection.disconnect();
    }
}
