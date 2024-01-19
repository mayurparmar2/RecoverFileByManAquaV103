package com.demo.filerecovery.model.modul.recoveryphoto;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.filerecovery.App;



import com.demo.filerecovery.asynctask.ScanAsyncTask;
import com.demo.filerecovery.model.modul.recoveryphoto.Model.AlbumPhoto;
import com.demo.filerecovery.model.modul.recoveryphoto.adapter.AlbumsPhotoAdapter;
import com.demo.filerecovery.utilts.Utils;
import com.demo.filerecovery.R;

import java.util.ArrayList;


public class AlbumPhotoActivity extends AppCompatActivity implements AlbumsPhotoAdapter.OnClickItemListener {
    AlbumsPhotoAdapter adapter;
     
    RecyclerView recyclerView;
    RelativeLayout rlBanner;
    Toolbar toolbar;
    private ArrayList<AlbumPhoto> mAlbumPhoto = new ArrayList<>();
    private int number;
    private TextView txtFileType;
    private TextView txtTotalFile;
    private TextView txtTotalFolder;

    @Override
    public void onClickItem(final int i) {
        Intent intent = new Intent(AlbumPhotoActivity.this.getApplicationContext(), PhotosActivity.class);
        AlbumPhoto albumPhoto = (AlbumPhoto) AlbumPhotoActivity.this.mAlbumPhoto.get(i);
        String str_folder = albumPhoto.getStr_folder();
        intent.putExtra("FOLDER", str_folder.substring(str_folder.lastIndexOf("/") + 1));
        App.getInstance().getStorageCommon().setListPhotoScanSelect(albumPhoto.getListPhoto());
        AlbumPhotoActivity.this.startActivityForResult(intent, 101);
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Utils.setStatusBarGradiant(this);
        setContentView(R.layout.activity_album);
        intView();
        intData();
        this.rlBanner = (RelativeLayout) findViewById(R.id.rl_banner);
      
        
    }

    public void intView() {
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.txtTotalFile = (TextView) findViewById(R.id.txtTotalFile);
        this.txtFileType = (TextView) findViewById(R.id.txtFileType);
        this.txtTotalFolder = (TextView) findViewById(R.id.txtTotalFolder);
        this.toolbar.setTitle(getString(R.string.photo_recovery));
        setSupportActionBar(this.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvFolder);
        this.recyclerView = recyclerView;
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        this.recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(2), true));
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());
        TextView textView = this.txtFileType;
        textView.setText(getString(R.string.photos) + " " + getString(R.string.were_found));
      
        
    }

    public void intData() {
        this.mAlbumPhoto = ScanAsyncTask.mAlbumPhoto;
        this.number = ScanAsyncTask.number;
        AlbumsPhotoAdapter albumsPhotoAdapter = new AlbumsPhotoAdapter(this, this);
        this.adapter = albumsPhotoAdapter;
        this.recyclerView.setAdapter(albumsPhotoAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        this.adapter.setData(this.mAlbumPhoto);
        this.txtTotalFile.setText(String.valueOf(this.number));
        this.txtTotalFolder.setText(String.valueOf(this.mAlbumPhoto.size()));
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt("NUMBER", this.number);
        this.mAlbumPhoto = ScanAsyncTask.mAlbumPhoto;
    }

    @Override
    public void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        this.number = bundle.getInt("NUMBER");
    }

    private int dpToPx(int i) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, i, getResources().getDisplayMetrics()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 101 && i2 == 102) {
            setResult(102);
            finish();
        }
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
        private boolean includeEdge;
        private int spacing;
        private int spanCount;

        public GridSpacingItemDecoration(int i, int i2, boolean z) {
            this.spanCount = i;
            this.spacing = i2;
            this.includeEdge = z;
        }

        @Override
        public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
            int childAdapterPosition = recyclerView.getChildAdapterPosition(view);
            int i = this.spanCount;
            int i2 = childAdapterPosition % i;
            if (this.includeEdge) {
                int i3 = this.spacing;
                rect.left = i3 - ((i2 * i3) / i);
                rect.right = ((i2 + 1) * this.spacing) / this.spanCount;
                if (childAdapterPosition < this.spanCount) {
                    rect.top = this.spacing;
                }
                rect.bottom = this.spacing;
                return;
            }
            rect.left = (this.spacing * i2) / i;
            int i4 = this.spacing;
            rect.right = i4 - (((i2 + 1) * i4) / this.spanCount);
            if (childAdapterPosition >= this.spanCount) {
                rect.top = this.spacing;
            }
        }
    }
}
