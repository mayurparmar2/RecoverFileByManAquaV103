package com.demo.filerecovery.ui.activity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.demo.filerecovery.R;
import com.demo.filerecovery.utilts.CommonUtils;


public class VideoViewerActivity extends AppCompatActivity {
    public VideoView videoView;
    private ImageView ivPlay;
    private String path;
    private SeekBar seekbar;
    private TextView tvCurrentDuration;
    private TextView tvDuration;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_videoviewer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.restored_video));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.videoView = (VideoView) findViewById(R.id.video);
        this.ivPlay = (ImageView) findViewById(R.id.iv_play);
        this.seekbar = (SeekBar) findViewById(R.id.seekbar);
        this.tvCurrentDuration = (TextView) findViewById(R.id.tv_current_duration);
        this.tvDuration = (TextView) findViewById(R.id.tv_duration);
        this.seekbar = (SeekBar) findViewById(R.id.seekbar);
        this.ivPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                VideoViewerActivity.this.lambda$onCreate$0$VideoViewerActivity(view);
            }
        });
        this.seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                if (z) {
                    VideoViewerActivity.this.videoView.seekTo(i);
                }
            }
        });
        try {
            String stringExtra = getIntent().getStringExtra("data");
            this.path = stringExtra;
            this.videoView.setVideoPath(stringExtra);
            this.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public final void onPrepared(MediaPlayer mediaPlayer) {
                    VideoViewerActivity.this.lambda$onCreate$1$VideoViewerActivity(mediaPlayer);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void lambda$onCreate$0$VideoViewerActivity(View view) {
        if (this.videoView.isPlaying()) {
            this.videoView.pause();
            this.ivPlay.setImageResource(R.drawable.ic_pause);
            return;
        }
        this.videoView.start();
        this.ivPlay.setImageResource(R.drawable.ic_plays);
    }

    public void lambda$onCreate$1$VideoViewerActivity(MediaPlayer mediaPlayer) {
        this.videoView.start();
        this.tvDuration.setText(CommonUtils.getInstance().formatTime(this.videoView.getDuration()));
        this.seekbar.setMax(this.videoView.getDuration());
        updateSeekbar();
        this.videoView.pause();
        this.ivPlay.setImageResource(R.drawable.ic_pause);
    }

    private void updateSeekbar() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public final void run() {
                VideoViewerActivity.this.lambda$updateSeekbar$2$VideoViewerActivity();
            }
        }, 1000L);
    }

    public void lambda$updateSeekbar$2$VideoViewerActivity() {
        int currentPosition = this.videoView.getCurrentPosition();
        this.seekbar.setProgress(currentPosition);
        this.tvCurrentDuration.setText(CommonUtils.getInstance().formatTime(currentPosition));
        updateSeekbar();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.videoView.stopPlayback();
    }
}
