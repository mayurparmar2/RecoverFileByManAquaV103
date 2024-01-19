package com.demo.filerecovery.model.modul.recoverydocument;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.ActionBar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.filerecovery.App;
import com.demo.filerecovery.Constants;



import com.demo.filerecovery.base.BaseActivity;
import com.demo.filerecovery.model.modul.recoverydocument.Model.DocumentModel;
import com.demo.filerecovery.model.modul.recoverydocument.adapter.DocumentAdapter;
import com.demo.filerecovery.model.modul.recoverydocument.task.RecoverDocumentAsyncTask;
import com.demo.filerecovery.ui.activity.RestoreResultActivity;
import com.demo.filerecovery.utilts.Utils;
import com.demo.filerecovery.R;
import com.demo.filerecovery.databinding.ActivityFileFolderBinding;
import com.demo.filerecovery.databinding.LayoutBottomSheetSortFileBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;


public final class DocumentActivity extends BaseActivity<ActivityFileFolderBinding> {
    public static final Companion Companion = new Companion(null);
    public static final int SORT_ASC = 1;
    public static final int SORT_DESC = 2;
    private final ArrayList<DocumentModel> listDocument;
     
    private BottomSheetDialog bottomSheetDialogSortBy;
    private DocumentAdapter docAdapter;
    private RecoverDocumentAsyncTask mDocAsyncTask;
    private int position;
    private ArrayList<DocumentModel> restoreFileList;


    public DocumentActivity() {
        super(R.layout.activity_file_folder);
        this.listDocument = new ArrayList<>();
        this.restoreFileList = new ArrayList<>();
    }

    public static final void m43initView$lambda0(DocumentActivity this$0, DocumentModel documentModel, Boolean isSelected) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullExpressionValue(documentModel, "documentModel");
        Intrinsics.checkNotNullExpressionValue(isSelected, "isSelected");
        this$0.restoreFileList(documentModel, isSelected.booleanValue());
    }

    public static final void m44initView$lambda1(DocumentActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getBinding().llSortBy.setVisibility(View.GONE);
    }

    public static final void m57showMenuSortFile$lambda5(DocumentActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getBinding().llSortBy.setVisibility(View.VISIBLE);
        this$0.getBinding().txtTypeSort.setText(this$0.getString(R.string.sort_by_latest));
        CollectionsKt.sortWith(this$0.listDocument, new Comparator() {
            @Override
            public final int compare(Object obj, Object obj2) {
                int compare;
                compare = Intrinsics.compare(((DocumentModel) obj).getLastModified(), ((DocumentModel) obj2).getLastModified());
                return compare;
            }
        });
        this$0.resetAdapter(this$0.listDocument);
    }

    public static final void m59showMenuSortFile$lambda7(DocumentActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getBinding().llSortBy.setVisibility(View.VISIBLE);
        this$0.getBinding().txtTypeSort.setText(this$0.getString(R.string.sort_by_newest));
        CollectionsKt.sortWith(this$0.listDocument, new Comparator() {
            @Override
            public final int compare(Object obj, Object obj2) {
                int compare;
                compare = Intrinsics.compare(((DocumentModel) obj2).getLastModified(), ((DocumentModel) obj).getLastModified());
                return compare;
            }
        });
        this$0.resetAdapter(this$0.listDocument);
    }

    public static final void m61showMenuSortFile$lambda9(DocumentActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getBinding().llSortBy.setVisibility(View.VISIBLE);
        this$0.getBinding().txtTypeSort.setText(this$0.getString(R.string.sort_by_name_a_to_z));
        CollectionsKt.sortWith(this$0.listDocument, new Comparator() {
            @Override
            public final int compare(Object obj, Object obj2) {
                int compareFileName;
                compareFileName = Utils.compareFileName(((DocumentModel) obj).getPathDocument(), ((DocumentModel) obj2).getPathDocument(), 1);
                return compareFileName;
            }
        });
        this$0.resetAdapter(this$0.listDocument);
    }

    public static final void m51showMenuSortFile$lambda11(DocumentActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getBinding().llSortBy.setVisibility(View.VISIBLE);
        this$0.getBinding().txtTypeSort.setText(this$0.getString(R.string.sort_by_name_z_to_a));
        CollectionsKt.sortWith(this$0.listDocument, new Comparator() {
            @Override
            public final int compare(Object obj, Object obj2) {
                int compareFileName;
                compareFileName = Utils.compareFileName(((DocumentModel) obj).getPathDocument(), ((DocumentModel) obj2).getPathDocument(), 2);
                return compareFileName;
            }
        });
        this$0.resetAdapter(this$0.listDocument);
    }

    public static final void m53showMenuSortFile$lambda13(DocumentActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getBinding().llSortBy.setVisibility(View.VISIBLE);
        this$0.getBinding().txtTypeSort.setText(this$0.getString(R.string.sort_by_storage_min_to_max));
        CollectionsKt.sortWith(this$0.listDocument, new Comparator() {
            @Override
            public final int compare(Object obj, Object obj2) {
                int compare;
                compare = Intrinsics.compare(((DocumentModel) obj).getSizeDocument(), ((DocumentModel) obj2).getSizeDocument());
                return compare;
            }
        });
        this$0.resetAdapter(this$0.listDocument);
    }

    public static final void m55showMenuSortFile$lambda15(DocumentActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getBinding().llSortBy.setVisibility(View.VISIBLE);
        this$0.getBinding().txtTypeSort.setText(this$0.getString(R.string.sort_by_storage_max_to_min));
        CollectionsKt.sortWith(this$0.listDocument, new Comparator() {
            @Override
            public final int compare(Object obj, Object obj2) {
                int compare;
                compare = Intrinsics.compare(((DocumentModel) obj2).getSizeDocument(), ((DocumentModel) obj).getSizeDocument());
                return compare;
            }
        });
        this$0.resetAdapter(this$0.listDocument);
    }

    public static final void m63startAsyncTask$lambda16(DocumentActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.openRestoreSuccessActivity();
    }

    @Override
    public void initView() {
        setSupportActionBar(getBinding().toolbar);
        ActionBar supportActionBar = getSupportActionBar();
      
        
        Intrinsics.checkNotNull(supportActionBar);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        ActionBar supportActionBar2 = getSupportActionBar();
        Intrinsics.checkNotNull(supportActionBar2);
        supportActionBar2.setDisplayShowHomeEnabled(true);
        Bundle extras = getIntent().getExtras();
        DocumentAdapter documentAdapter = null;
        String valueOf = String.valueOf(extras == null ? null : extras.get("FOLDER"));
        this.listDocument.addAll(App.getInstance().getStorageCommon().getListDocumentsScanSelect());
        DocumentAdapter documentAdapter2 = new DocumentAdapter(this);
        this.docAdapter = documentAdapter2;
        documentAdapter2.setHideCheckBox(false);
        DocumentAdapter documentAdapter3 = this.docAdapter;
        if (documentAdapter3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("docAdapter");
            documentAdapter3 = null;
        }
        documentAdapter3.setDocumentModelList(this.listDocument);
        DocumentAdapter documentAdapter4 = this.docAdapter;
        if (documentAdapter4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("docAdapter");
            documentAdapter4 = null;
        }
        documentAdapter4.setOnItemClick(new DocumentAdapter.OnItemClick() {


            @Override
            public final void onClick(DocumentModel documentModel, Boolean bool) {
                DocumentActivity.m43initView$lambda0(DocumentActivity.this, documentModel, bool);
            }
        });
        RecyclerView recyclerView = getBinding().rvListFile;
        DocumentAdapter documentAdapter5 = this.docAdapter;
        if (documentAdapter5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("docAdapter");
        } else {
            documentAdapter = documentAdapter5;
        }
        recyclerView.setAdapter(documentAdapter);
        ActionBar supportActionBar3 = getSupportActionBar();
        Intrinsics.checkNotNull(supportActionBar3);
        supportActionBar3.setTitle(valueOf);
        getBinding().llSortBy.setOnClickListener(new View.OnClickListener() {


            @Override
            public final void onClick(View view) {
                DocumentActivity.m44initView$lambda1(DocumentActivity.this, view);
            }
        });
        getBinding().btnRestoreFile.setOnClickListener(new View.OnClickListener() {


            @Override
            public final void onClick(View view) {
                DocumentActivity documentActivity = DocumentActivity.this;
                documentActivity.m45initView$lambda3(documentActivity, view);
            }
        });
    }

    public final void m45initView$lambda3(DocumentActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        DocumentActivity.this.startAsyncTask();

    }

    private final void showMenuSortFile() {
        LayoutBottomSheetSortFileBinding inflate = LayoutBottomSheetSortFileBinding.inflate(LayoutInflater.from(this));
        Intrinsics.checkNotNullExpressionValue(inflate, "inflate(LayoutInflater.from(this))");
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this, R.style.CustomBottomSheetDialogTheme);
        this.bottomSheetDialogSortBy = bottomSheetDialog;
        Intrinsics.checkNotNull(bottomSheetDialog);
        bottomSheetDialog.setContentView(inflate.getRoot());
        inflate.txtLatest.setOnClickListener(new View.OnClickListener() {


            @Override
            public final void onClick(View view) {
                DocumentActivity.m57showMenuSortFile$lambda5(DocumentActivity.this, view);
            }
        });
        inflate.txtNewest.setOnClickListener(new View.OnClickListener() {


            @Override
            public final void onClick(View view) {
                DocumentActivity.m59showMenuSortFile$lambda7(DocumentActivity.this, view);
            }
        });
        inflate.txtAtoZ.setOnClickListener(new View.OnClickListener() {


            @Override
            public final void onClick(View view) {
                DocumentActivity.m61showMenuSortFile$lambda9(DocumentActivity.this, view);
            }
        });
        inflate.txtZtoA.setOnClickListener(new View.OnClickListener() {


            @Override
            public final void onClick(View view) {
                DocumentActivity.m51showMenuSortFile$lambda11(DocumentActivity.this, view);
            }
        });
        inflate.txtMinToMax.setOnClickListener(new View.OnClickListener() {


            @Override
            public final void onClick(View view) {
                DocumentActivity.m53showMenuSortFile$lambda13(DocumentActivity.this, view);
            }
        });
        inflate.txtMaxToMin.setOnClickListener(new View.OnClickListener() {


            @Override
            public final void onClick(View view) {
                DocumentActivity.m55showMenuSortFile$lambda15(DocumentActivity.this, view);
            }
        });
        DocumentAdapter documentAdapter = this.docAdapter;
        DocumentAdapter documentAdapter2 = null;
        if (documentAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("docAdapter");
            documentAdapter = null;
        }
        documentAdapter.setDocumentModelList(this.listDocument);
        RecyclerView recyclerView = getBinding().rvListFile;
        DocumentAdapter documentAdapter3 = this.docAdapter;
        if (documentAdapter3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("docAdapter");
        } else {
            documentAdapter2 = documentAdapter3;
        }
        recyclerView.setAdapter(documentAdapter2);
        BottomSheetDialog bottomSheetDialog2 = this.bottomSheetDialogSortBy;
        Intrinsics.checkNotNull(bottomSheetDialog2);
        bottomSheetDialog2.setDismissWithAnimation(true);
        BottomSheetDialog bottomSheetDialog3 = this.bottomSheetDialogSortBy;
        Intrinsics.checkNotNull(bottomSheetDialog3);
        Window window = bottomSheetDialog3.getWindow();
        Intrinsics.checkNotNull(window);
        window.setBackgroundDrawable(new ColorDrawable(0));
        BottomSheetDialog bottomSheetDialog4 = this.bottomSheetDialogSortBy;
        Intrinsics.checkNotNull(bottomSheetDialog4);
        bottomSheetDialog4.show();
    }

    private final void resetAdapter(List<? extends DocumentModel> list) {
        DocumentAdapter documentAdapter = this.docAdapter;
        DocumentAdapter documentAdapter2 = null;
        if (documentAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("docAdapter");
            documentAdapter = null;
        }
        documentAdapter.setDocumentModelList((List<DocumentModel>) list);
        DocumentAdapter documentAdapter3 = this.docAdapter;
        if (documentAdapter3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("docAdapter");
        } else {
            documentAdapter2 = documentAdapter3;
        }
        documentAdapter2.notifyDataSetChanged();
        BottomSheetDialog bottomSheetDialog = this.bottomSheetDialogSortBy;
        if (bottomSheetDialog != null) {
            bottomSheetDialog.dismiss();
        }
    }

    public final void startAsyncTask() {
        RecoverDocumentAsyncTask recoverDocumentAsyncTask = new RecoverDocumentAsyncTask(this, this.restoreFileList, new RecoverDocumentAsyncTask.OnRestoreListener() {


            @Override
            public final void onComplete() {
                DocumentActivity.m63startAsyncTask$lambda16(DocumentActivity.this);
            }
        });
        this.mDocAsyncTask = recoverDocumentAsyncTask;
        recoverDocumentAsyncTask.execute(new String[0]);
    }

    private final void openRestoreSuccessActivity() {
        Intent intent = new Intent(this, RestoreResultActivity.class);
        ArrayList<String> arrayList = new ArrayList<>();
        Iterator<DocumentModel> it = this.restoreFileList.iterator();
        long j = 0;
        while (it.hasNext()) {
            DocumentModel next = it.next();
            j += next.getSizeDocument();
            arrayList.add(next.getPathDocument());
        }
        intent.putExtra("value" , j);
        intent.putExtra(Constants.FB_LOG_RESTORE_RESULT_TYPE, 3);
        intent.putStringArrayListExtra("listPath", arrayList);
        startActivityForResult(intent, 101);
    }

    private final void changeRestoreButtonUI(boolean z) {
        if (z) {
            getBinding().txtAdsReward.setVisibility(View.GONE);
            getBinding().btnRestoreFile.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.grey6));
            getBinding().btnRestoreFile.setEnabled(false);
            return;
        }
        getBinding().btnRestoreFile.setBackgroundTintList(null);
        getBinding().btnRestoreFile.setEnabled(true);
    }

    private final void restoreFileList(DocumentModel documentModel, boolean z) {
        if (z) {
            this.restoreFileList.add(documentModel);
        } else if (this.restoreFileList.contains(documentModel)) {
            this.restoreFileList.remove(documentModel);
        }
        getBinding().btnRestoreFile.setText(getString(R.string.restore_file) + '(' + this.restoreFileList.size() + ')');
        if (this.restoreFileList.size() == 0) {
            changeRestoreButtonUI(true);
        } else {
            changeRestoreButtonUI(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intrinsics.checkNotNullParameter(item, "item");
        if (item.getItemId() == R.id.btnSortList) {
            showMenuSortFile();
            RecyclerView.Adapter adapter = getBinding().rvListFile.getAdapter();
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 101 && i2 == 102) {
            setResult(102);
            finish();
        }
    }

    public static final class Companion {
        public Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
