package com.demo.filerecovery.model.modul.recoveryaudio.adapter;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.filerecovery.listener.OnItemSelected;
import com.demo.filerecovery.model.modul.recoveryaudio.Model.AudioModel;
import com.demo.filerecovery.utilts.FileUtil;
import com.demo.filerecovery.utilts.Utils;
import com.demo.filerecovery.R;

import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;


public class AudioAdapter extends RecyclerView.Adapter<AudioAdapter.MyViewHolder> {
    public OnItemSelected listener;
    public int totalItemSelected = 0;
    Context context;
    BitmapDrawable placeholder;
    ArrayList<AudioModel> listPhoto = new ArrayList<>();
    private boolean isRestored;


    public AudioAdapter(Context context, OnItemSelected onItemSelected) {
        this.context = context;
        this.listener = onItemSelected;
    }

    public void setData(List<AudioModel> list) {
        this.listPhoto.clear();
        this.listPhoto.addAll(list);
        notifyDataSetChanged();
    }

    public void setRestored(boolean z) {
        this.isRestored = z;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_audio, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, int i) {
        final AudioModel audioModel = this.listPhoto.get(i);
        myViewHolder.tvDate.setText(DateFormat.getDateInstance().format(Long.valueOf(audioModel.getLastModified())));
        myViewHolder.tvSize.setText(Utils.formatSize(audioModel.getSizePhoto()));
        myViewHolder.tvTitle.setText(Utils.getFileTitle(audioModel.getPathPhoto()));
        myViewHolder.ivChecked.setChecked(audioModel.getIsCheck());
        if (this.isRestored) {
            myViewHolder.ivChecked.setVisibility(View.GONE);
        } else {
            myViewHolder.ivChecked.setVisibility(View.VISIBLE);
        }
        myViewHolder.ivChecked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (audioModel.getIsCheck()) {
                    myViewHolder.ivChecked.setChecked(false);
                    audioModel.setIsCheck(false);
                    if (AudioAdapter.this.totalItemSelected > 0) {
                        AudioAdapter.this.totalItemSelected--;
                    }
                } else {
                    myViewHolder.ivChecked.setChecked(true);
                    audioModel.setIsCheck(true);
                    AudioAdapter.this.totalItemSelected++;
                }
                AudioAdapter.this.listener.onItemSelect(AudioAdapter.this.totalItemSelected);
            }
        });
        myViewHolder.album_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                AudioAdapter.this.lambda$onBindViewHolder$0$AudioAdapter(audioModel, view);
            }
        });
    }

    public void lambda$onBindViewHolder$0$AudioAdapter(AudioModel audioModel, View view) {
        try {
            FileUtil.openFileAudio(new File(audioModel.getPathPhoto()), this.context);
        } catch (Exception unused) {
        }
    }

    @Override
    public int getItemCount() {
        return this.listPhoto.size();
    }

    public ArrayList<AudioModel> getSelectedItem() {
        ArrayList<AudioModel> arrayList = new ArrayList<>();
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
        protected AppCompatCheckBox ivChecked;
        protected TextView tvDate;
        protected TextView tvSize;
        protected TextView tvTitle;
        RelativeLayout album_card;

        public MyViewHolder(View view) {
            super(view);
            this.tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            this.tvSize = (TextView) view.findViewById(R.id.tvSize);
            this.tvDate = (TextView) view.findViewById(R.id.tvType);
            this.ivChecked = (AppCompatCheckBox) view.findViewById(R.id.cbSelect);
            this.album_card = (RelativeLayout) view.findViewById(R.id.album_card);
        }
    }
}
