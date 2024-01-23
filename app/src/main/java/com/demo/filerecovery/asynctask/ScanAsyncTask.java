package com.demo.filerecovery.asynctask;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.demo.filerecovery.R;
import com.demo.filerecovery.model.modul.recoveryaudio.Model.AlbumAudio;
import com.demo.filerecovery.model.modul.recoveryaudio.Model.AudioModel;
import com.demo.filerecovery.model.modul.recoverydocument.Model.AlbumDocument;
import com.demo.filerecovery.model.modul.recoverydocument.Model.DocumentModel;
import com.demo.filerecovery.model.modul.recoveryphoto.Model.AlbumPhoto;
import com.demo.filerecovery.model.modul.recoveryphoto.Model.PhotoModel;
import com.demo.filerecovery.model.modul.recoveryvideo.Model.AlbumVideo;
import com.demo.filerecovery.model.modul.recoveryvideo.Model.VideoModel;
import com.demo.filerecovery.utilts.Utils;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;


public class ScanAsyncTask extends AsyncTask<Void, Integer, Void> {
    public static ArrayList<AlbumAudio> mAlbumAudio = new ArrayList<>();
    public static ArrayList<AlbumDocument> mAlbumDocument = new ArrayList<>();
    public static ArrayList<AlbumPhoto> mAlbumPhoto = new ArrayList<>();
    public static ArrayList<AlbumVideo> mAlbumVideo = new ArrayList<>();
    public static int number;
    final List<String> AUDIO_FILE_EXTENSIONS = Arrays.asList("mp3", "aac", "amr", "m4a", "ogg", "wav", "flac");
    final List<String> DOCUMENT_FILE_EXTENSIONS = Arrays.asList("txt", "ppt", "pptx", "xls", "xlsx", "xlsm", "pdf", "doc", "docx", "aab", "apk", "zip", "vvc");
    final List<String> PHOTO_FILE_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png", "gif");
    final List<String> VIDEO_FILE_EXTENSIONS = Arrays.asList("3gp", "mp4", "mkv", "flv");
    int scanType;
    ArrayList<AudioModel> listAudio = new ArrayList<>();
    ArrayList<DocumentModel> listDocument = new ArrayList<>();
    ArrayList<PhotoModel> listPhoto = new ArrayList<>();
    ArrayList<VideoModel> listVideo = new ArrayList<>();
    private ScanAsyncTaskCallback callback;
    private Context myContext;
    private int count = 0;
    private int progress = 0;
    private long totalFile = 0;


    public ScanAsyncTask(int i, Context context) {
        this.scanType = 0;
        this.scanType = i;
        this.myContext = context;
    }

    public void setCallback(ScanAsyncTaskCallback scanAsyncTaskCallback) {
        this.callback = scanAsyncTaskCallback;
    }

    @Override
    public void onPreExecute() {
        super.onPreExecute();
        number = 0;
        this.count = 0;
    }

    @Override
    public void onPostExecute(Void r2) {
        super.onPostExecute(r2);
        this.callback.onPostExecuteCallback();
        this.callback.onProgressCallback(this.progress);
    }

    @Override
    public void onProgressUpdate(Integer... numArr) {
        super.onProgressUpdate(numArr);
        this.callback.onFileCount(numArr);
        this.callback.onProgressCallback(this.progress);
    }

    @Override
    public Void doInBackground(Void... voidArr) {
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mAlbumPhoto.clear();
        mAlbumVideo.clear();
        mAlbumAudio.clear();
        String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        this.totalFile = Utils.getFileList(absolutePath).length;
        int i = this.scanType;
        if (i == 0) {
            try {
                getSdCardImage();
                File[] fileList = Utils.getFileList(absolutePath);
                Objects.requireNonNull(fileList);
                countProgressAfterReadFile(0, absolutePath, fileList);
                Collections.sort(mAlbumPhoto, new Comparator() {
                    @Override
                    public final int compare(Object obj, Object obj2) {
                        return Long.compare(((AlbumPhoto) obj2).getLastModified(), ((AlbumPhoto) obj).getLastModified());
                    }
                });
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            return null;
        } else if (i == 1) {
            try {
                getSdCardVideo();
                countProgressAfterReadFile(1, absolutePath, Utils.getFileList(absolutePath));
                Collections.sort(mAlbumVideo, new Comparator() {
                    @Override
                    public final int compare(Object obj, Object obj2) {
                        return Long.compare(((AlbumVideo) obj2).getLastModified(), ((AlbumVideo) obj).getLastModified());
                    }
                });
            } catch (Exception e3) {
                e3.printStackTrace();
            }
            return null;
        } else if (i == 2) {
            try {
                getSdCardAudio();
                File[] fileList2 = Utils.getFileList(absolutePath);
                Objects.requireNonNull(fileList2);
                countProgressAfterReadFile(2, absolutePath, fileList2);
            } catch (Exception e4) {
                e4.printStackTrace();
            }
            Collections.sort(mAlbumAudio, new Comparator() {
                @Override
                public final int compare(Object obj, Object obj2) {
                    return Long.compare(((AlbumAudio) obj2).getLastModified(), ((AlbumAudio) obj).getLastModified());
                }
            });
            return null;
        } else if (i != 3) {
            try {
                Thread.sleep(3000L);
            } catch (InterruptedException e5) {
                e5.printStackTrace();
            }
            return null;
        } else {
            try {
                getSdCardDocument();
                countProgressAfterReadFile(3, absolutePath, Utils.getFileList(absolutePath));
                Collections.sort(mAlbumDocument, new Comparator() {
                    @Override
                    public final int compare(Object obj, Object obj2) {
                        return Long.compare(((AlbumDocument) obj2).getLastModified(), ((AlbumDocument) obj).getLastModified());
                    }
                });
            } catch (Exception e6) {
                e6.printStackTrace();
            }
            return null;
        }
    }

    @Override
    public void onCancelled() {
        super.onCancelled();
        number = 0;
    }

    @Override
    public void onCancelled(Void r1) {
        super.onCancelled(r1);
    }

    public void countProgressAfterReadFile(int i, String str, File[] fileArr) {
        int i2;
        int length = fileArr.length;
        char c = 0;
        int i3 = 0;
        while (i3 < length) {
            File file = fileArr[i3];
            if (isCancelled()) {
                return;
            }
            this.count++;
            Log.e("TAG", "fileArr.length: " + fileArr.length);
            this.progress = (int) ((((double) this.count) / ((double) this.totalFile)) * 100.0d);
            if (file.isDirectory()) {
                String path = file.getPath();
                File[] fileList = Utils.getFileList(file.getPath());
                if (i == 0) {
                    if (this.listPhoto.size() != 0 && !path.contains(Utils.getPathSave(this.myContext.getString(R.string.restore_folder_path_photo)))) {
                        AlbumPhoto albumPhoto = new AlbumPhoto();
                        albumPhoto.setStr_folder(path);
                        albumPhoto.setLastModified(new File(path).lastModified());
                        Collections.sort(this.listPhoto, new Comparator() {
                            @Override
                            public final int compare(Object obj, Object obj2) {
                                return Long.valueOf(((PhotoModel) obj2).getLastModified()).compareTo(Long.valueOf(((PhotoModel) obj).getLastModified()));
                            }
                        });
                        albumPhoto.setListPhoto((ArrayList) this.listPhoto.clone());
                        mAlbumPhoto.add(albumPhoto);
                    }
                    this.listPhoto.clear();
                } else if (i == 1) {
                    if (this.listVideo.size() != 0 && !path.contains(Utils.getPathSave(this.myContext.getString(R.string.restore_folder_path_video)))) {
                        AlbumVideo albumVideo = new AlbumVideo();
                        albumVideo.setStr_folder(path);
                        albumVideo.setLastModified(new File(path).lastModified());
                        Collections.sort(this.listVideo, new Comparator() {
                            @Override
                            public final int compare(Object obj, Object obj2) {
                                return Long.valueOf(((VideoModel) obj2).getLastModified()).compareTo(Long.valueOf(((VideoModel) obj).getLastModified()));
                            }
                        });
                        albumVideo.setListPhoto((ArrayList) this.listVideo.clone());
                        mAlbumVideo.add(albumVideo);
                    }
                    this.listVideo.clear();
                } else if (i == 2) {
                    if (this.listAudio.size() != 0 && !path.contains(Utils.getPathSave(this.myContext.getString(R.string.restore_folder_path_audio)))) {
                        AlbumAudio albumAudio = new AlbumAudio();
                        albumAudio.setStr_folder(path);
                        albumAudio.setLastModified(new File(path).lastModified());
                        Collections.sort(this.listAudio, new Comparator() {
                            @Override
                            public final int compare(Object obj, Object obj2) {
                                return Long.valueOf(((AudioModel) obj2).getLastModified()).compareTo(Long.valueOf(((AudioModel) obj).getLastModified()));
                            }
                        });
                        albumAudio.setListPhoto((ArrayList) this.listAudio.clone());
                        mAlbumAudio.add(albumAudio);
                    }
                    this.listAudio.clear();
                } else if (i == 3) {
                    if (this.listDocument.size() > 0 && !path.contains(Utils.getPathSave(this.myContext.getString(R.string.restore_folder_path_document)))) {
                        AlbumDocument albumDocument = new AlbumDocument();
                        albumDocument.setStr_folder(path);
                        albumDocument.setLastModified(new File(path).lastModified());
                        Collections.sort(this.listAudio, new Comparator() {
                            @Override
                            public final int compare(Object obj, Object obj2) {
                                return Long.valueOf(((AudioModel) obj2).getLastModified()).compareTo(Long.valueOf(((AudioModel) obj).getLastModified()));
                            }
                        });
                        albumDocument.setListDocument((ArrayList) this.listDocument.clone());
                        mAlbumDocument.add(albumDocument);
                    }
                    this.listDocument.clear();
                }
                if (fileList != null && fileList.length > 0) {
                    if (i == 0) {
                        checkFileOfDirectoryImage(path, fileList);
                    } else if (i == 1) {
                        checkFileOfDirectoryVideo(path, fileList);
                    } else if (i == 2) {
                        checkFileOfDirectoryAudio(path, fileList);
                    } else if (i == 3) {
                        try {
                            checkFileOfDirectoryDocument(path, fileList);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(file.getPath(), options);
                if (options.outWidth != -1 && options.outHeight != -1) {
                    if (i != 0) {
                        if (i != 1) {
                            if (i != 2) {
                                if (i == 3 && this.DOCUMENT_FILE_EXTENSIONS.contains(FilenameUtils.getExtension(file.getName())) && !checkRestored(this.myContext.getString(R.string.restore_folder_path_audio), file.getName())) {
                                    this.listDocument.add(new DocumentModel(file.getPath(), file.lastModified(), file.length()));
                                    int i4 = number + 1;
                                    number = i4;
                                    Integer[] numArr = new Integer[1];
                                    numArr[c] = Integer.valueOf(i4);
                                    publishProgress(numArr);
                                }
                            } else if (this.AUDIO_FILE_EXTENSIONS.contains(FilenameUtils.getExtension(file.getName()))) {
                                File file2 = new File(file.getPath());
                                int parseInt = Integer.parseInt(String.valueOf(file2.length()));
                                if (parseInt > 10000 && !checkRestored(this.myContext.getString(R.string.restore_folder_path_audio), file.getName())) {
                                    this.listAudio.add(new AudioModel(file.getPath(), file2.lastModified(), parseInt));
                                    int i5 = number + 1;
                                    number = i5;
                                    Integer[] numArr2 = new Integer[1];
                                    numArr2[c] = Integer.valueOf(i5);
                                    publishProgress(numArr2);
                                }
                            }
                        } else if (this.VIDEO_FILE_EXTENSIONS.contains(FilenameUtils.getExtension(file.getName()))) {
                            File file3 = new File(file.getPath());
                            String substring = file.getPath().substring(file.getPath().lastIndexOf(".") + 1);
                            long j = 0;
                            MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
                            try {
                                mediaMetadataRetriever.setDataSource(file3.getPath());
                                j = Long.parseLong(mediaMetadataRetriever.extractMetadata(9));
                                mediaMetadataRetriever.release();
                            } catch (Exception unused) {
                            }
                            if (!checkRestored(this.myContext.getString(R.string.restore_folder_path_video), file.getName())) {
                                this.listVideo.add(new VideoModel(file.getPath(), file3.lastModified(), file3.length(), substring, Utils.convertDuration(j)));
                                int i6 = number + 1;
                                number = i6;
                                Integer[] numArr3 = new Integer[1];
                                numArr3[c] = Integer.valueOf(i6);
                                publishProgress(numArr3);
                            }
                        }
                    } else if (this.PHOTO_FILE_EXTENSIONS.contains(FilenameUtils.getExtension(file.getName()))) {
                        File file4 = new File(file.getPath());
                        int parseInt2 = Integer.parseInt(String.valueOf(file4.length()));
                        if (parseInt2 > 10000 && !checkRestored(this.myContext.getString(R.string.restore_folder_path_photo), file.getName())) {
                            this.listPhoto.add(new PhotoModel(file.getPath(), file4.lastModified(), parseInt2));
                            int i7 = number + 1;
                            number = i7;
                            Integer[] numArr4 = new Integer[1];
                            numArr4[c] = Integer.valueOf(i7);
                            publishProgress(numArr4);
                        }
                    } else {
                        File file5 = new File(file.getPath());
                        int parseInt3 = Integer.parseInt(String.valueOf(file5.length()));
                        if (parseInt3 > 50000 && !checkRestored(this.myContext.getString(R.string.restore_folder_path_photo), file.getName())) {
                            this.listPhoto.add(new PhotoModel(file.getPath(), file5.lastModified(), parseInt3));
                            int i8 = number + 1;
                            number = i8;
                            publishProgress(Integer.valueOf(i8));
                            i2 = i3 + 1;
                            i3 = i2 + 1;
                            c = 0;
                        }
                    }
                }
            }
            i2 = i3;
            i3 = i2 + 1;
            c = 0;
        }
    }

    public void checkFileOfDirectoryImage(String str, File[] fileArr) {
        for (File file : fileArr) {
            if (file.isDirectory()) {
                String path = file.getPath();
                File[] fileList = Utils.getFileList(file.getPath());
                if (fileList != null && fileList.length > 0) {
                    try {
                        checkFileOfDirectoryImage(path, fileList);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(file.getPath(), options);
                if (options.outWidth != -1 && options.outHeight != -1) {
                    if (this.PHOTO_FILE_EXTENSIONS.contains(FilenameUtils.getExtension(file.getName()))) {
                        File file2 = new File(file.getPath());
                        int parseInt = Integer.parseInt(String.valueOf(file2.length()));
                        if (parseInt > 10000 && !checkRestored(this.myContext.getString(R.string.restore_folder_path_photo), file.getName())) {
                            this.listPhoto.add(new PhotoModel(file.getPath(), file2.lastModified(), parseInt));
                            int i = number + 1;
                            number = i;
                            publishProgress(Integer.valueOf(i));
                        }
                    } else {
                        File file3 = new File(file.getPath());
                        int parseInt2 = Integer.parseInt(String.valueOf(file3.length()));
                        if (parseInt2 > 50000 && !checkRestored(this.myContext.getString(R.string.restore_folder_path_photo), file.getName())) {
                            this.listPhoto.add(new PhotoModel(file.getPath(), file3.lastModified(), parseInt2));
                            int i2 = number + 1;
                            number = i2;
                            publishProgress(Integer.valueOf(i2));
                        }
                    }
                }
            }
        }
    }

    public boolean checkRestored(String str, String str2) {
        File[] listFiles = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + str).listFiles();
        if (listFiles != null) {
            for (File file : listFiles) {
                if (file.getName().contains(str2)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void getSdCardImage() {
        String[] externalStorageDirectories = Utils.getExternalStorageDirectories(this.myContext);
        Log.e("TAG", "getSdCardImage: " + externalStorageDirectories.length);
        if (externalStorageDirectories.length > 0) {
            for (String str : externalStorageDirectories) {
                Log.e("TAG", "getSdCardImage: " + str);
                File file = new File(str);
                if (file.exists()) {
                    try {
                        checkFileOfDirectoryImage(str, file.listFiles());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void checkFileOfDirectoryVideo(String str, File[] fileArr) {
        long j;
        for (int i = 0; i < fileArr.length; i++) {
            if (fileArr[i].isDirectory()) {
                String path = fileArr[i].getPath();
                File[] fileList = Utils.getFileList(fileArr[i].getPath());
                if (fileList != null && fileList.length > 0) {
                    checkFileOfDirectoryVideo(path, fileList);
                }
            } else if (this.VIDEO_FILE_EXTENSIONS.contains(FilenameUtils.getExtension(fileArr[i].getName()))) {
                File file = new File(fileArr[i].getPath());
                String substring = fileArr[i].getPath().substring(fileArr[i].getPath().lastIndexOf(".") + 1);
                MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
                try {
                    mediaMetadataRetriever.setDataSource(file.getPath());
                    j = Long.parseLong(mediaMetadataRetriever.extractMetadata(9));
                    try {
                        mediaMetadataRetriever.release();
                    } catch (Exception unused) {
                    }
                } catch (Exception unused2) {
                    j = 0;
                }
                if (!checkRestored(this.myContext.getString(R.string.restore_folder_path_video), fileArr[i].getName()) && file.length() > 0) {
                    this.listVideo.add(new VideoModel(fileArr[i].getPath(), file.lastModified(), file.length(), substring, Utils.convertDuration(j)));
                    int i2 = number + 1;
                    number = i2;
                    publishProgress(Integer.valueOf(i2));
                }
            }
        }
    }

    public void getSdCardVideo() {
        String[] externalStorageDirectories = Utils.getExternalStorageDirectories(this.myContext);
        if (externalStorageDirectories.length > 0) {
            for (String str : externalStorageDirectories) {
                File file = new File(str);
                if (file.exists()) {
                    checkFileOfDirectoryVideo(str, file.listFiles());
                }
            }
        }
    }

    public void getSdCardAudio() {
        String[] externalStorageDirectories = Utils.getExternalStorageDirectories(this.myContext);
        if (externalStorageDirectories.length > 0) {
            for (String str : externalStorageDirectories) {
                File file = new File(str);
                if (file.exists()) {
                    checkFileOfDirectoryAudio(str, file.listFiles());
                }
            }
        }
    }

    public void getSdCardDocument() {
        String[] externalStorageDirectories = Utils.getExternalStorageDirectories(this.myContext);
        if (externalStorageDirectories.length > 0) {
            for (String str : externalStorageDirectories) {
                File file = new File(str);
                if (file.exists()) {
                    checkFileOfDirectoryDocument(str, file.listFiles());
                }
            }
        }
    }

    public void checkFileOfDirectoryAudio(String str, File[] fileArr) {
        for (int i = 0; i < fileArr.length; i++) {
            if (fileArr[i].isDirectory()) {
                String path = fileArr[i].getPath();
                File[] fileList = Utils.getFileList(fileArr[i].getPath());
                if (path != null && fileList != null && fileList.length > 0) {
                    checkFileOfDirectoryAudio(path, fileList);
                }
            } else if (this.AUDIO_FILE_EXTENSIONS.contains(FilenameUtils.getExtension(fileArr[i].getName()))) {
                File file = new File(fileArr[i].getPath());
                int parseInt = Integer.parseInt(String.valueOf(file.length()));
                if (parseInt > 10000 && !checkRestored(this.myContext.getString(R.string.restore_folder_path_audio), fileArr[i].getName())) {
                    this.listAudio.add(new AudioModel(fileArr[i].getPath(), file.lastModified(), parseInt));
                    int i2 = number + 1;
                    number = i2;
                    publishProgress(Integer.valueOf(i2));
                }
            }
        }
    }

    public void checkFileOfDirectoryDocument(String str, File[] fileArr) {
        for (File file : fileArr) {
            if (file.isDirectory()) {
                String path = file.getPath();
                File[] listFiles = file.listFiles();
                if (path != null && listFiles != null && listFiles.length > 0) {
                    checkFileOfDirectoryDocument(path, listFiles);
                }
            } else if (this.DOCUMENT_FILE_EXTENSIONS.contains(FilenameUtils.getExtension(file.getName())) && Integer.parseInt(String.valueOf(file.length())) > 1000 && !checkRestored(this.myContext.getString(R.string.restore_folder_path_document), file.getName())) {
                this.listDocument.add(new DocumentModel(file.getPath(), file.lastModified(), file.length()));
                int i = number + 1;
                number = i;
                publishProgress(Integer.valueOf(i));
            }
        }
    }

    public interface ScanAsyncTaskCallback {
        void onFileCount(Integer[] numArr);

        void onPostExecuteCallback();

        void onProgressCallback(int i);
    }
}
