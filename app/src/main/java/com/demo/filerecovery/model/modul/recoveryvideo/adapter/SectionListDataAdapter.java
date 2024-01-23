package com.demo.filerecovery.model.modul.recoveryvideo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.demo.filerecovery.R;
import com.demo.filerecovery.model.SquareImageView;
import com.demo.filerecovery.model.modul.recoveryvideo.Model.VideoModel;

import java.util.ArrayList;


public class SectionListDataAdapter extends RecyclerView.Adapter<SectionListDataAdapter.SingleItemRowHolder> {
    public AlbumsVideoAdapter.OnClickItemListener clickItemListener;
    int postion;
    int size;
    private ArrayList<VideoModel> itemsList;
    private Context mContext;


    public SectionListDataAdapter(Context context, ArrayList<VideoModel> arrayList, int i) {
        this.itemsList = arrayList;
        this.mContext = context;
        this.postion = i;
        if (arrayList.size() >= 5) {
            this.size = 5;
        } else {
            this.size = arrayList.size();
        }
    }

    public void setClickItemListener(AlbumsVideoAdapter.OnClickItemListener onClickItemListener) {
        this.clickItemListener = onClickItemListener;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new SingleItemRowHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_image_video, (ViewGroup) null));
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder singleItemRowHolder, int i) {
        VideoModel videoModel = this.itemsList.get(i);
        try {
            RequestManager with = Glide.with(this.mContext);
            with.load("file://" + videoModel.getPathPhoto()).diskCacheStrategy(DiskCacheStrategy.ALL).priority(Priority.HIGH).centerCrop().error(R.drawable.ic_error).into(singleItemRowHolder.itemImage);
        } catch (Exception e) {
            Context context = this.mContext;
            Toast.makeText(context, "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        singleItemRowHolder.itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SectionListDataAdapter.this.clickItemListener.onClickItem(SectionListDataAdapter.this.postion);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.size;
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {
        protected SquareImageView itemImage;

        public SingleItemRowHolder(View view) {
            super(view);
            this.itemImage = (SquareImageView) view.findViewById(R.id.ivImage);
        }
    }
}
