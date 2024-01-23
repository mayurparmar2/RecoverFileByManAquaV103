package com.demo.filerecovery.model.modul.recoverydocument;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;

import com.demo.filerecovery.App;
import com.demo.filerecovery.R;
import com.demo.filerecovery.asynctask.ScanAsyncTask;
import com.demo.filerecovery.base.BaseActivity;
import com.demo.filerecovery.databinding.ActivityAlbumBinding;
import com.demo.filerecovery.model.modul.recoverydocument.Model.AlbumDocument;
import com.demo.filerecovery.model.modul.recoverydocument.adapter.AlbumDocumentAdapter;

import java.util.ArrayList;
import java.util.Iterator;


public class AlbumDocumentActivity extends BaseActivity {
    private static final String TAG = "AlbumDocumentActivity";

    RelativeLayout rlBanner;
    private AlbumDocumentAdapter albumDocAdapter;
    private ArrayList<AlbumDocument> albumDocuments;
    private ActivityAlbumBinding binding;

    public AlbumDocumentActivity() {
        super(R.layout.activity_album);
        this.albumDocuments = new ArrayList<>();
    }

    @Override
    public void initView() {
        ActivityAlbumBinding activityAlbumBinding = (ActivityAlbumBinding) getBinding();
        this.binding = activityAlbumBinding;
        setSupportActionBar(activityAlbumBinding.toolbar);
        this.rlBanner = (RelativeLayout) findViewById(R.id.rl_banner);


        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle(R.string.document_recovery);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowHomeEnabled(true);
        }
        this.albumDocuments = ScanAsyncTask.mAlbumDocument;
        this.albumDocAdapter = new AlbumDocumentAdapter(this, new AlbumDocumentAdapter.OnItemClick() {
            @Override
            public final void onCLick(int i, String str) {
                AlbumDocumentActivity.this.itemClick(i, str);
            }
        });
        this.binding.rvFolder.setAdapter(this.albumDocAdapter);
        this.binding.imgScannedType.setImageDrawable(getDrawable(R.drawable.ic_folder_document));
        this.binding.txtScannedType.setText(R.string.document_recovery);
        this.binding.txtFileType.setText(getString(R.string.document_type) + " " + getString(R.string.were_found));
    }

    @Override
    public void onResume() {
        super.onResume();
        this.albumDocAdapter.setData(this.albumDocuments);
        this.binding.txtTotalFolder.setText(this.albumDocuments.size() + "");
        Iterator<AlbumDocument> it = this.albumDocuments.iterator();
        int i = 0;
        while (it.hasNext()) {
            i += it.next().getListDocument().size();
        }
        TextView textView = this.binding.txtTotalFile;
        textView.setText(i + "");
    }

    public void itemClick(final int i, final String str) {
        Intent intent = new Intent(AlbumDocumentActivity.this, DocumentActivity.class);
        intent.putExtra("FOLDER", str);
        intent.putExtra("FILE_TYPE", 3);
        App.getInstance().getStorageCommon().setListDocumentsScanSelect(((AlbumDocument) AlbumDocumentActivity.this.albumDocuments.get(i)).getListDocument());
        AlbumDocumentActivity.this.startActivityForResult(intent, 101);

    }

    @Override
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 101 && i2 == 102) {
            setResult(102);
            finish();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
    }

    @Override
    public void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
    }
}
