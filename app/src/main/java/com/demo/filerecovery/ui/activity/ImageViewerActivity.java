package com.demo.filerecovery.ui.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.demo.filerecovery.R;
import com.demo.filerecovery.utilts.Utils;


public class ImageViewerActivity extends AppCompatActivity {
    private String pathImage;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Utils.setStatusBarGradiant(this);
        setContentView(R.layout.activity_imageviewer);
        try {
            this.pathImage = getIntent().getStringExtra("data");
            Glide.with((FragmentActivity) this).load(this.pathImage).into((ImageView) findViewById(R.id.iv_preview));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        String str = this.pathImage;
        toolbar.setTitle(str.substring(str.lastIndexOf("/") + 1));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
