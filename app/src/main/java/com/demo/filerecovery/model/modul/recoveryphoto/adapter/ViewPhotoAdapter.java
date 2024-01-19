package com.demo.filerecovery.model.modul.recoveryphoto.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.filerecovery.model.SquareImageView;
import com.demo.filerecovery.model.modul.recoveryphoto.Model.PhotoModel;
import com.demo.filerecovery.utilts.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.demo.filerecovery.R;

import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;


public class ViewPhotoAdapter extends RecyclerView.Adapter<ViewPhotoAdapter.MyViewHolder> {
    Context context;
    ArrayList<PhotoModel> listPhoto;
    BitmapDrawable placeholder;


    public ViewPhotoAdapter(Context context, ArrayList<PhotoModel> arrayList) {
        this.listPhoto = new ArrayList<>();
        this.context = context;
        this.listPhoto = arrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_photo, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        final PhotoModel photoModel = this.listPhoto.get(i);
        myViewHolder.tvDate.setText(DateFormat.getDateInstance().format(Long.valueOf(photoModel.getLastModified())));
        myViewHolder.tvSize.setText(Utils.formatSize(photoModel.getSizePhoto()));
        try {
            RequestManager with = Glide.with(this.context);
            with.load("file://" + photoModel.getPathPhoto()).diskCacheStrategy(DiskCacheStrategy.ALL).priority(Priority.HIGH).centerCrop().error(R.drawable.ic_error).placeholder(this.placeholder).into(myViewHolder.ivPhoto);
        } catch (Exception e) {
            Context context = this.context;
            Toast.makeText(context, "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        myViewHolder.album_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context2 = ViewPhotoAdapter.this.context;
                Uri uriForFile = FileProvider.getUriForFile(context2, ViewPhotoAdapter.this.context.getPackageName() + ".provider", new File(photoModel.getPathPhoto()));
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW").addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION).setDataAndType(uriForFile, ViewPhotoAdapter.this.context.getContentResolver().getType(uriForFile));
                ViewPhotoAdapter.this.context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.listPhoto.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CardView album_card;
        ImageView ivPhoto;
        TextView tvDate;
        TextView tvSize;

        public MyViewHolder(View view) {
            super(view);
            this.tvDate = (TextView) view.findViewById(R.id.tvDate);
            this.tvSize = (TextView) view.findViewById(R.id.tvSize);
            this.ivPhoto = (SquareImageView) view.findViewById(R.id.iv_image);
            this.album_card = (CardView) view.findViewById(R.id.album_card);
        }
    }
}
