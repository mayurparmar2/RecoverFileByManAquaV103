package com.demo.filerecovery.model.modul.recoveryphoto.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.demo.filerecovery.R;
import com.demo.filerecovery.listener.OnItemSelected;
import com.demo.filerecovery.model.modul.recoveryphoto.Model.PhotoModel;
import com.demo.filerecovery.ui.activity.ImageViewerActivity;
import com.demo.filerecovery.utilts.Utils;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;


public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.MyViewHolder> {
    Context context;
    BitmapDrawable placeholder;
    List<PhotoModel> listPhoto = new ArrayList();
    private boolean isRestored;
    private OnItemSelected listener;
    private int totalItemSelected = 0;


    public PhotoAdapter(Context context, OnItemSelected onItemSelected) {
        this.context = context;
        this.listener = onItemSelected;
    }

    public void setData(List<PhotoModel> list) {
        this.listPhoto.clear();
        this.listPhoto.addAll(list);
        notifyDataSetChanged();
    }

    public void setRestored(boolean z) {
        this.isRestored = z;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_photo, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, int i) {
        final PhotoModel photoModel = this.listPhoto.get(i);
        myViewHolder.tvDate.setText(DateFormat.getDateInstance().format(Long.valueOf(photoModel.getLastModified())));
        myViewHolder.tvSize.setText(Utils.formatSize(photoModel.getSizePhoto()));
        myViewHolder.txtFileName.setText(photoModel.getPathPhoto().substring(photoModel.getPathPhoto().lastIndexOf("/") + 1));
        if (this.isRestored) {
            myViewHolder.ivChecked.setVisibility(View.GONE);
        } else if (photoModel.getIsCheck()) {
            myViewHolder.ivChecked.setVisibility(View.VISIBLE);
        } else {
            myViewHolder.ivChecked.setVisibility(View.GONE);
        }
        try {
            RequestManager with = Glide.with(this.context);
            with.load("file://" + photoModel.getPathPhoto()).diskCacheStrategy(DiskCacheStrategy.ALL).priority(Priority.HIGH).centerCrop().error(R.drawable.ic_error).placeholder(this.placeholder).into(myViewHolder.ivPhoto);
        } catch (Exception e) {
            Context context = this.context;
            Toast.makeText(context, "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        myViewHolder.album_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                PhotoAdapter.this.lambda$onBindViewHolder$0$PhotoAdapter(photoModel, myViewHolder, view);
            }
        });
    }

    public void lambda$onBindViewHolder$0$PhotoAdapter(PhotoModel photoModel, MyViewHolder myViewHolder, View view) {
        if (this.isRestored) {
            Intent intent = new Intent(this.context, ImageViewerActivity.class);
            intent.putExtra("data", "file://" + photoModel.getPathPhoto());
            this.context.startActivity(intent);
            return;
        }
        if (photoModel.getIsCheck()) {
            myViewHolder.ivChecked.setVisibility(View.GONE);
            photoModel.setIsCheck(false);
            int i = this.totalItemSelected;
            if (i > 0) {
                this.totalItemSelected = i - 1;
            }
        } else {
            myViewHolder.ivChecked.setVisibility(View.VISIBLE);
            photoModel.setIsCheck(true);
            this.totalItemSelected++;
        }
        this.listener.onItemSelect(this.totalItemSelected);
    }

    @Override
    public int getItemCount() {
        return this.listPhoto.size();
    }

    public ArrayList<PhotoModel> getSelectedItem() {
        ArrayList<PhotoModel> arrayList = new ArrayList<>();
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
        LinearLayout album_card;
        ImageView ivChecked;
        ImageView ivPhoto;
        TextView tvDate;
        TextView tvSize;
        TextView txtFileName;

        public MyViewHolder(View view) {
            super(view);
            this.txtFileName = (TextView) view.findViewById(R.id.txtFileName);
            this.tvDate = (TextView) view.findViewById(R.id.tvDate);
            this.tvSize = (TextView) view.findViewById(R.id.tvSize);
            this.ivPhoto = (ImageView) view.findViewById(R.id.iv_image);
            this.ivChecked = (ImageView) view.findViewById(R.id.isChecked);
            this.album_card = (LinearLayout) view.findViewById(R.id.album_card);
        }
    }
}
