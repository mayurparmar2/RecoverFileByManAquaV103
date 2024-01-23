package com.demo.filerecovery.model.modul.recoveryvideo.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.demo.filerecovery.R;
import com.demo.filerecovery.model.SquareImageView;
import com.demo.filerecovery.model.modul.recoveryvideo.Model.VideoModel;
import com.demo.filerecovery.utilts.Utils;

import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;


public class ViewVideoAdapter extends RecyclerView.Adapter<ViewVideoAdapter.MyViewHolder> {
    Context context;
    ArrayList<VideoModel> listPhoto;
    BitmapDrawable placeholder;


    public ViewVideoAdapter(Context context, ArrayList<VideoModel> arrayList) {
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
        final VideoModel videoModel = this.listPhoto.get(i);
        myViewHolder.tvDate.setText(DateFormat.getDateInstance().format(Long.valueOf(videoModel.getLastModified())));
        myViewHolder.tvSize.setText(Utils.formatSize(videoModel.getSizePhoto()));
        try {
            RequestManager with = Glide.with(this.context);
            with.load("file://" + videoModel.getPathPhoto()).diskCacheStrategy(DiskCacheStrategy.ALL).priority(Priority.HIGH).centerCrop().error(R.drawable.ic_error).placeholder(this.placeholder).into(myViewHolder.ivPhoto);
        } catch (Exception e) {
            Context context = this.context;
            Toast.makeText(context, "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        myViewHolder.album_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewVideoAdapter.this.openFile(videoModel.getPathPhoto());
            }
        });
    }

    public void openFile(String str) {
        Intent createChooser;
        if (Build.VERSION.SDK_INT < 24) {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setDataAndType(Uri.fromFile(new File(str)), "video/*");
            createChooser = Intent.createChooser(intent, "Complete action using");
        } else {
            File file = new File(str);
            Intent intent2 = new Intent("android.intent.action.VIEW");
            Context context = this.context;
            Uri uriForFile = FileProvider.getUriForFile(context, this.context.getPackageName() + ".provider", file);
            Context context2 = this.context;
            context2.grantUriPermission(context2.getPackageName(), uriForFile, Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent2.setType("video/*");
            if (Build.VERSION.SDK_INT < 24) {
                uriForFile = Uri.fromFile(file);
            }
            intent2.setData(uriForFile);
            intent2.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            createChooser = Intent.createChooser(intent2, "Complete action using");
        }
        this.context.startActivity(createChooser);
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
