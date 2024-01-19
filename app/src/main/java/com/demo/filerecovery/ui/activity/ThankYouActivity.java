package com.demo.filerecovery.ui.activity;

import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.demo.filerecovery.R;


public class ThankYouActivity extends AppCompatActivity {
    Handler handler = new Handler();


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_thank_you);
        this.handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ThankYouActivity.this.finishAffinity();
            }
        }, 2000L);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
