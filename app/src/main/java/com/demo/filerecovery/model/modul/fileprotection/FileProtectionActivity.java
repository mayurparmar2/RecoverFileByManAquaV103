package com.demo.filerecovery.model.modul.fileprotection;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.demo.filerecovery.R;
import com.demo.filerecovery.base.BaseActivity;
import com.demo.filerecovery.databinding.ActivityFileProtectionBinding;
import com.demo.filerecovery.model.modul.recoverydocument.Model.DocumentModel;
import com.demo.filerecovery.model.modul.recoverydocument.task.RecoverDocumentAsyncTask;
import com.demo.filerecovery.ui.dialog.DeleteFileDialog;
import com.demo.filerecovery.utilts.FileUtil;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;


public final class FileProtectionActivity extends BaseActivity<ActivityFileProtectionBinding> implements FileProtectionAdapter.FileProtectionAdapterCallback, BottomSheetFileProtection.BottomSheetFileProtectionCallback, DeleteFileDialog.DeleteFileDialogCallBack, FileProtectionAdapter.OnItemStateChange {
    public static final Companion Companion = new Companion(null);
    private static final String TAG = "FileProtectionActivity";
    private final CompositeDisposable compositeDisposable;
    private final MutableLiveData<ArrayList<File>> mutableLiveData;
    private BottomSheetFileProtection bottomSheetFileProtection;
    private FileProtectionAdapter fileProtectionAdapter;
    private ArrayList<File> listFileDelete;


    public FileProtectionActivity() {
        super(R.layout.activity_file_protection);
        this.compositeDisposable = new CompositeDisposable();
        this.listFileDelete = new ArrayList<>();
        this.mutableLiveData = new MutableLiveData<>();
    }

    public static final void m15initView$lambda1(FileProtectionActivity fileProtectionActivity, ArrayList arrayList) {
        if (arrayList.isEmpty()) {
            fileProtectionActivity.getBinding().recyclerView.setVisibility(View.GONE);
            fileProtectionActivity.getBinding().layoutEmpty.setVisibility(View.VISIBLE);
            return;
        }
        fileProtectionActivity.getBinding().recyclerView.setVisibility(View.VISIBLE);
        fileProtectionActivity.getBinding().layoutEmpty.setVisibility(View.GONE);
        FileProtectionAdapter fileProtectionAdapter = fileProtectionActivity.fileProtectionAdapter;
        if (fileProtectionAdapter != null) {
            fileProtectionAdapter.setData(arrayList);
        }
    }

    public static final void m16initView$lambda2(FileProtectionActivity fileProtectionActivity, View view) {
        fileProtectionActivity.getBinding().editFileToolbar.setVisibility(View.GONE);
        FileProtectionAdapter fileProtectionAdapter = fileProtectionActivity.fileProtectionAdapter;
        if (fileProtectionAdapter != null) {
            fileProtectionAdapter.resetAdapter();
        }
        fileProtectionActivity.listFileDelete.clear();
    }

    public static final void m18initView$lambda4(FileProtectionActivity fileProtectionActivity, View view) {
        if (fileProtectionActivity.listFileDelete.size() > 0) {
            DeleteFileDialog deleteFileDialog = new DeleteFileDialog(fileProtectionActivity, fileProtectionActivity.listFileDelete, null);
            deleteFileDialog.setDeleteFileDialogCallBack(fileProtectionActivity);
            deleteFileDialog.setTitleAndContentDialog(fileProtectionActivity.getString(R.string.delete_protection_file), fileProtectionActivity.getString(R.string.delete_file_protection_not_restore));
            deleteFileDialog.show();
        }
    }

    public static final void m26loadListFileProtection$lambda6(FileProtectionActivity fileProtectionActivity, ArrayList arrayList) {
        fileProtectionActivity.mutableLiveData.postValue(arrayList);
    }

    public static final void m27loadListFileProtection$lambda7(FileProtectionActivity fileProtectionActivity, Throwable th) {
        fileProtectionActivity.mutableLiveData.postValue(new ArrayList<>());
    }

    @Override
    public void onDeleteFileCallbackInDialogWithPosition(int i) {
        DeleteFileDialog.DeleteFileDialogCallBack.DefaultImpls.onDeleteFileCallbackInDialogWithPosition(this, i);
    }

    @Override
    public void initView() {
        getBinding().imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                FileProtectionActivity.this.finish();
            }
        });

        loadListFileProtection();
        FileProtectionAdapter fileProtectionAdapter = new FileProtectionAdapter(this, this);
        this.fileProtectionAdapter = fileProtectionAdapter;
        Intrinsics.checkNotNull(fileProtectionAdapter);
        fileProtectionAdapter.setFileProtectionAdapterCallBack(this);
        getBinding().recyclerView.setAdapter(this.fileProtectionAdapter);
        this.mutableLiveData.observe(this, new Observer() {
            @Override
            public final void onChanged(Object obj) {
                FileProtectionActivity.m15initView$lambda1(FileProtectionActivity.this, (ArrayList) obj);
            }
        });
        getBinding().btnShowTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                FileProtectionActivity.m16initView$lambda2(FileProtectionActivity.this, view);
            }
        });
        getBinding().imgRestoreFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                FileProtectionActivity.this.onRestoreWithEditFileToolbar();
            }
        });
        getBinding().imgDeleteFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                FileProtectionActivity.m18initView$lambda4(FileProtectionActivity.this, view);
            }
        });
    }

    private final void loadListFileProtection() {
        this.compositeDisposable.add(Observable.fromCallable(new Callable() {
            @Override
            public final Object call() {
                return FileUtil.getListFileProtection(FileProtectionActivity.this);
            }
        }).subscribeOn(Schedulers.io()).observeOn(Schedulers.newThread()).subscribe(new Consumer() {
            @Override
            public final void accept(Object obj) {
                FileProtectionActivity.m26loadListFileProtection$lambda6(FileProtectionActivity.this, (ArrayList) obj);
            }
        }, new Consumer() {
            @Override
            public final void accept(Object obj) {
                FileProtectionActivity.m27loadListFileProtection$lambda7(FileProtectionActivity.this, (Throwable) obj);
            }
        }));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.compositeDisposable.clear();
    }

    @Override
    public void onMoreClickListener(final File file) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.layout_bottomsheet_file_protection);
        bottomSheetDialog.findViewById(R.id.tvtRestoreFile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FileProtectionActivity.this.onRestoreFile(file);
            }
        });
        bottomSheetDialog.findViewById(R.id.tvtDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FileProtectionActivity.this.onDelete(file);
            }
        });
        bottomSheetDialog.show();
    }

    @Override
    public void onRestoreFile(final File file) {
        String extension = FilenameUtils.getExtension(file.getPath());
        if (extension != null) {
            extension.hashCode();
        }
        new RecoverDocumentAsyncTask(this, CollectionsKt.arrayListOf(new DocumentModel(file.getPath(), file.lastModified(), file.length())), new RecoverDocumentAsyncTask.OnRestoreListener() {
            @Override
            public final void onComplete() {
                FileProtectionActivity.this.deleteFileProtect(file);
            }
        }).execute(new String[0]);
    }

    @Override
    public void onDelete(File file) {
        DeleteFileDialog deleteFileDialog = new DeleteFileDialog(this, CollectionsKt.mutableListOf(file), null);
        deleteFileDialog.setDeleteFileDialogCallBack(this);
        deleteFileDialog.setTitleAndContentDialog(getString(R.string.delete_protection_file), getString(R.string.delete_file_protection_not_restore));
        deleteFileDialog.show();
    }

    public final void onRestoreWithEditFileToolbar() {
        if (this.listFileDelete.size() > 0) {
            Iterator<File> it = this.listFileDelete.iterator();
            while (it.hasNext()) {
                final File next = it.next();
                String extension = FilenameUtils.getExtension(next.getPath());
                if (extension != null) {
                    extension.hashCode();
                }
                new RecoverDocumentAsyncTask(this, CollectionsKt.arrayListOf(new DocumentModel(next.getPath(), next.lastModified(), next.length())), new RecoverDocumentAsyncTask.OnRestoreListener() {
                    @Override
                    public final void onComplete() {
                        FileProtectionActivity.this.deleteFileProtect(next);
                    }
                }).execute(new String[0]);
            }
        }
    }

    public final void deleteFileProtect(File file) {
        FileUtil.deleteFileProtection(this, file.getPath());
        loadListFileProtection();
    }

    @Override
    public void onDeleteFileCallbackInDialog(List<? extends File> files) {
        Intrinsics.checkNotNullParameter(files, "files");
        for (File file : files) {
            deleteFileProtect(file);
        }
        this.listFileDelete.clear();
        if (this.listFileDelete.isEmpty()) {
            getBinding().editFileToolbar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCheckBoxStateChange(File file, boolean z, int i) {
        if (z) {
            this.listFileDelete.add(file);
        } else {
            this.listFileDelete.remove(file);
        }
        getBinding().tvTotalFileSelected.setText(String.valueOf(this.listFileDelete.size()));
    }

    @Override
    public void onLongClick() {
        getBinding().editFileToolbar.setVisibility(View.VISIBLE);
    }

    public static final class Companion {
        public Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
