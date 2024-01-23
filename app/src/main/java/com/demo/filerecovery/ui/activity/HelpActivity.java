package com.demo.filerecovery.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.demo.filerecovery.R;
import com.demo.filerecovery.utilts.Utils;


public class HelpActivity extends AppCompatActivity {

    ImageView ivSpace;
    RelativeLayout rlBanner;
    RelativeLayout rlNative;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Utils.setLocale(this);
        setContentView(R.layout.activity_help);
        this.rlNative = (RelativeLayout) findViewById(R.id.rl_native);
        this.ivSpace = (ImageView) findViewById(R.id.iv_space);
        this.rlBanner = (RelativeLayout) findViewById(R.id.rl_banner);


    }

    public void onStartClick(View view) {
        HelpActivity.this.startActivity(new Intent(HelpActivity.this, MainActivity.class));
        HelpActivity.this.finish();
    }
}
