package com.demo.filerecovery.model.modul.recoveryvideo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.demo.filerecovery.model.modul.recoveryvideo.Model.VideoModel;
import com.demo.filerecovery.utilts.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.demo.filerecovery.R;
import com.demo.filerecovery.databinding.ItemFileFolderBinding;

import org.apache.commons.io.FilenameUtils;

import java.text.DateFormat;
import java.util.List;

import kotlin.jvm.internal.Intrinsics;


public final class VideoSuccessAdapter extends RecyclerView.Adapter<VideoSuccessAdapter.VideoSuccessViewHolder> {
    private final Context context;
    private boolean isHideCheckBox;
    private List<? extends VideoModel> videoModelList;


    public VideoSuccessAdapter(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public final Context getContext() {
        return this.context;
    }

    public final void setVideoModelList(List<? extends VideoModel> videoModelList) {
        Intrinsics.checkNotNullParameter(videoModelList, "videoModelList");
        this.videoModelList = videoModelList;
        if (videoModelList.size() == 1) {
            this.isHideCheckBox = true;
        }
    }

    @Override
    public VideoSuccessViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        ItemFileFolderBinding inflate = ItemFileFolderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        Intrinsics.checkNotNullExpressionValue(inflate, "inflate(LayoutInflater.f….context), parent, false)");
        return new VideoSuccessViewHolder(this, inflate);
    }

    @Override
    public void onBindViewHolder(VideoSuccessViewHolder holder, int i) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        List<? extends VideoModel> list = this.videoModelList;
        Intrinsics.checkNotNull(list);
        holder.onBind(list.get(i));
    }

    @Override
    public int getItemCount() {
        List<? extends VideoModel> list = this.videoModelList;
        Intrinsics.checkNotNull(list);
        return list.size();
    }

    public final class VideoSuccessViewHolder extends RecyclerView.ViewHolder {
        final VideoSuccessAdapter this$0;
        private final ItemFileFolderBinding binding;

        public VideoSuccessViewHolder(VideoSuccessAdapter videoSuccessAdapter, ItemFileFolderBinding itemFileFolderBinding) {
            super(itemFileFolderBinding.getRoot());
            this.this$0 = videoSuccessAdapter;
            this.binding = itemFileFolderBinding;
        }

        public final ItemFileFolderBinding getBinding() {
            return this.binding;
        }

        public final void onBind(VideoModel videoModel) {
            Intrinsics.checkNotNullParameter(videoModel, "videoModel");
            try {
                Glide.with(this.this$0.getContext()).load(Intrinsics.stringPlus("file://", videoModel.getPathPhoto())).diskCacheStrategy(DiskCacheStrategy.ALL).priority(Priority.HIGH).centerCrop().error(R.drawable.audio).placeholder(R.drawable.audio).into(this.binding.imgFileType);
            } catch (Exception e) {
                Toast.makeText(this.this$0.getContext(), Intrinsics.stringPlus("Exception: ", e.getMessage()), Toast.LENGTH_SHORT).show();
            }
            this.binding.tvFileName.setText(FilenameUtils.getName(videoModel.getPathPhoto()));
            this.binding.tvFileSize.setText(Utils.formatSize(videoModel.getSizePhoto()));
            this.binding.tvLastModified.setText(DateFormat.getDateInstance().format(Long.valueOf(videoModel.getLastModified())));
        }
    }
}
