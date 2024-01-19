package com.demo.filerecovery.model.modul.recoveryvideo.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.filerecovery.listener.OnItemSelected;
import com.demo.filerecovery.model.SquareImageView;
import com.demo.filerecovery.model.modul.recoveryvideo.Model.VideoModel;
import com.demo.filerecovery.ui.activity.VideoViewerActivity;
import com.demo.filerecovery.utilts.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.demo.filerecovery.R;

import java.text.DateFormat;
import java.util.ArrayList;


public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyViewHolder> {
    Activity activity;
    Context context;
    ArrayList<VideoModel> listPhoto = new ArrayList<>();
    private boolean isRestored;
    private OnItemSelected listener;
    private int totalItemSelected = 0;


    public VideoAdapter(Context context, Activity activity, OnItemSelected onItemSelected) {
        this.context = context;
        this.activity = activity;
        this.listener = onItemSelected;
    }

    public void setData(ArrayList<VideoModel> arrayList) {
        this.listPhoto.clear();
        this.listPhoto.addAll(arrayList);
        notifyDataSetChanged();
    }

    public void setRestored(boolean z) {
        this.isRestored = z;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(this.context).inflate(R.layout.card_video, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, int i) {
        final VideoModel videoModel = this.listPhoto.get(i);
        TextView textView = myViewHolder.tvDate;
        textView.setText(DateFormat.getDateInstance().format(Long.valueOf(videoModel.getLastModified())) + "  " + videoModel.getTimeDuration());
        myViewHolder.cbSelected.setChecked(videoModel.getIsCheck());
        if (this.isRestored) {
            myViewHolder.cbSelected.setVisibility(View.GONE);
        } else {
            myViewHolder.cbSelected.setVisibility(View.VISIBLE);
        }
        myViewHolder.tvSize.setText(Utils.formatSize(videoModel.getSizePhoto()));
        myViewHolder.tvName.setText(videoModel.getPathPhoto().substring(videoModel.getPathPhoto().lastIndexOf("/") + 1));
        try {
            RequestManager with = Glide.with(this.context);
            with.load("file://" + videoModel.getPathPhoto()).diskCacheStrategy(DiskCacheStrategy.ALL).priority(Priority.HIGH).centerCrop().error(R.drawable.ic_error).into(myViewHolder.ivPhoto);
        } catch (Exception e) {
            Context context = this.context;
            Toast.makeText(context, "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        myViewHolder.album_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                VideoAdapter.this.lambda$onBindViewHolder$0$VideoAdapter(videoModel, view);
            }
        });
        myViewHolder.cbSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                VideoAdapter.this.lambda$onBindViewHolder$1$VideoAdapter(myViewHolder, videoModel, view);
            }
        });
    }

    public void lambda$onBindViewHolder$0$VideoAdapter(VideoModel videoModel, View view) {
        if (this.isRestored) {
            openFile(videoModel.getPathPhoto());
        }
    }

    public void lambda$onBindViewHolder$1$VideoAdapter(MyViewHolder myViewHolder, VideoModel videoModel, View view) {
        if (myViewHolder.cbSelected.isChecked()) {
            videoModel.setIsCheck(true);
            this.totalItemSelected++;
        } else {
            videoModel.setIsCheck(false);
            int i = this.totalItemSelected;
            if (i > 0) {
                this.totalItemSelected = i - 1;
            }
        }
        this.listener.onItemSelect(this.totalItemSelected);
    }

    public void openFile(String str) {
        Intent intent = new Intent(this.context, VideoViewerActivity.class);
        intent.putExtra("data", str);
        this.context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return this.listPhoto.size();
    }

    public ArrayList<VideoModel> getSelectedItem() {
        ArrayList<VideoModel> arrayList = new ArrayList<>();
        if (this.listPhoto != null) {
            for (int i = 0; i < this.listPhoto.size(); i++) {
                if (this.listPhoto.get(i).getIsCheck()) {
                    arrayList.add(this.listPhoto.get(i));
                }
            }
        }
        return arrayList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout album_card;
        AppCompatCheckBox cbSelected;
        SquareImageView ivPhoto;
        TextView tvDate;
        TextView tvName;
        TextView tvSize;

        public MyViewHolder(View view) {
            super(view);
            this.tvDate = (TextView) view.findViewById(R.id.tvDate);
            this.tvSize = (TextView) view.findViewById(R.id.tvSize);
            this.tvName = (TextView) view.findViewById(R.id.tvName);
            this.ivPhoto = (SquareImageView) view.findViewById(R.id.iv_image);
            this.cbSelected = (AppCompatCheckBox) view.findViewById(R.id.cbSelected);
            this.album_card = (RelativeLayout) view.findViewById(R.id.album_card);
        }
    }
}
