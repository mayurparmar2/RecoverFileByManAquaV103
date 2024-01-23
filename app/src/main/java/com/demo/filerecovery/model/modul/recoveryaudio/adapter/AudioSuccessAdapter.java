package com.demo.filerecovery.model.modul.recoveryaudio.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.filerecovery.R;
import com.demo.filerecovery.databinding.ItemFileFolderBinding;
import com.demo.filerecovery.model.modul.recoveryaudio.Model.AudioModel;
import com.demo.filerecovery.model.modul.recoverydocument.Model.DocumentModel;
import com.demo.filerecovery.utilts.Utils;

import org.apache.commons.io.FilenameUtils;

import java.text.DateFormat;
import java.util.List;

import kotlin.jvm.internal.Intrinsics;


public final class AudioSuccessAdapter extends RecyclerView.Adapter<AudioSuccessAdapter.AudioSuccessViewHolder> {
    public final Context mContext;
    public boolean isHideCheckBox;
    private List<AudioModel> audioModelList;


    public AudioSuccessAdapter(Context mContext) {
        Intrinsics.checkNotNullParameter(mContext, "mContext");
        this.mContext = mContext;
    }

    public final List<AudioModel> getAudioModelList() {
        return this.audioModelList;
    }

    public final void setAudioModelList(List<AudioModel> audioModelList) {
        Intrinsics.checkNotNullParameter(audioModelList, "audioModelList");
        this.audioModelList = audioModelList;
        if (audioModelList.size() == 1) {
            this.isHideCheckBox = true;
        }
    }

    @Override
    public AudioSuccessViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        ItemFileFolderBinding inflate = ItemFileFolderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        Intrinsics.checkNotNullExpressionValue(inflate, "inflate(\n               …      false\n            )");
        return new AudioSuccessViewHolder(this, inflate);
    }

    @Override
    public void onBindViewHolder(AudioSuccessViewHolder holder, int i) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        List<AudioModel> list = this.audioModelList;
        Intrinsics.checkNotNull(list);
        holder.onBind(list.get(i));
    }

    @Override
    public int getItemCount() {
        List<AudioModel> list = this.audioModelList;
        Intrinsics.checkNotNull(list);
        return list.size();
    }

    public interface OnItemClick {
        void onClick(DocumentModel documentModel, Boolean bool);
    }

    public final class AudioSuccessViewHolder extends RecyclerView.ViewHolder {
        final AudioSuccessAdapter this$0;
        private final ItemFileFolderBinding binding;

        public AudioSuccessViewHolder(AudioSuccessAdapter audioSuccessAdapter, ItemFileFolderBinding itemFileFolderBinding) {
            super(itemFileFolderBinding.getRoot());
            this.this$0 = audioSuccessAdapter;
            this.binding = itemFileFolderBinding;
        }

        public final void onBind(AudioModel audioModel) {
            Intrinsics.checkNotNullParameter(audioModel, "audioModel");
            this.binding.cvIcon.setRadius(100.0f);
            if (this.this$0.isHideCheckBox) {
                this.binding.checkBox.setVisibility(View.GONE);
            }
            this.binding.imgFileType.setImageDrawable(ContextCompat.getDrawable(this.this$0.mContext, R.drawable.audio));
            this.binding.tvFileName.setText(FilenameUtils.getName(audioModel.getPathPhoto()));
            this.binding.tvFileSize.setText(Utils.formatSize(audioModel.getSizePhoto()));
            this.binding.tvLastModified.setText(DateFormat.getDateInstance().format(Long.valueOf(audioModel.getLastModified())));
        }
    }
}
