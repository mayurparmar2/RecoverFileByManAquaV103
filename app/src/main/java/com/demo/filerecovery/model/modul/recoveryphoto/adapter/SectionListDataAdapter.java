package com.demo.filerecovery.model.modul.recoveryphoto.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.demo.filerecovery.model.SquareImageView;
import com.demo.filerecovery.model.modul.recoveryphoto.Model.PhotoModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.demo.filerecovery.R;

import java.util.ArrayList;


public class SectionListDataAdapter extends RecyclerView.Adapter<SectionListDataAdapter.SingleItemRowHolder> {
    int postion;
    int size;
    private ArrayList<PhotoModel> itemsList;
    private Context mContext;
    private AlbumsPhotoAdapter.OnClickItemListener onClickListener;


    public SectionListDataAdapter(Context context, ArrayList<PhotoModel> arrayList, int i) {
        this.itemsList = arrayList;
        this.mContext = context;
        this.postion = i;
        if (arrayList.size() >= 5) {
            this.size = 5;
        } else {
            this.size = arrayList.size();
        }
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new SingleItemRowHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_image, (ViewGroup) null));
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder singleItemRowHolder, int i) {
        PhotoModel photoModel = this.itemsList.get(i);
        try {
            RequestManager with = Glide.with(this.mContext);
            with.load("file://" + photoModel.getPathPhoto()).diskCacheStrategy(DiskCacheStrategy.ALL).priority(Priority.HIGH).centerCrop().error(R.drawable.ic_error).into(singleItemRowHolder.itemImage);
        } catch (Exception e) {
            Context context = this.mContext;
            Toast.makeText(context, "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        singleItemRowHolder.itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                SectionListDataAdapter.this.lambda$onBindViewHolder$0$SectionListDataAdapter(view);
            }
        });
    }

    public void lambda$onBindViewHolder$0$SectionListDataAdapter(View view) {
        this.onClickListener.onClickItem(this.postion);
    }

    @Override
    public int getItemCount() {
        return this.size;
    }

    public void setOnClickListener(AlbumsPhotoAdapter.OnClickItemListener onClickItemListener) {
        this.onClickListener = onClickItemListener;
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {
        protected SquareImageView itemImage;

        public SingleItemRowHolder(View view) {
            super(view);
            this.itemImage = (SquareImageView) view.findViewById(R.id.ivImage);
        }
    }
}
