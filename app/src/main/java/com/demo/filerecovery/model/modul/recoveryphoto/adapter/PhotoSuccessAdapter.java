package com.demo.filerecovery.model.modul.recoveryphoto.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.demo.filerecovery.model.modul.recoveryphoto.Model.PhotoModel;
import com.demo.filerecovery.utilts.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.demo.filerecovery.R;
import com.demo.filerecovery.databinding.ItemFileFolderBinding;

import org.apache.commons.io.FilenameUtils;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;


public final class PhotoSuccessAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final Companion Companion = new Companion(null);
    private static final int MENU_ITEM_VIEW_TYPE = 0;
    private static final int UNIFIED_NATIVE_AD_VIEW_TYPE = 1;
    private final Context mContext;
    public boolean isHideCheckBox;
    private List<? extends PhotoModel> photoModelList;
    private ArrayList<Object> listFileAndAds = new ArrayList<>();
    private ArrayList<Integer> nativePositionList = new ArrayList<>();


    public PhotoSuccessAdapter(Context mContext) {
        Intrinsics.checkNotNullParameter(mContext, "mContext");
        this.mContext = mContext;
    }

    public final Context getMContext() {
        return this.mContext;
    }

    public final void setPhotoModelList(List<? extends PhotoModel> photoModelList) {
        Intrinsics.checkNotNullParameter(photoModelList, "photoModelList");
        this.photoModelList = photoModelList;
        if (photoModelList.size() == 1) {
            this.isHideCheckBox = true;
        }
    }

    public final void setNativePositionList(ArrayList<Integer> nativePositionList) {
        Intrinsics.checkNotNullParameter(nativePositionList, "nativePositionList");
        this.nativePositionList = nativePositionList;
        notifyDataSetChanged();
    }

    public final ArrayList<Object> getListFileAndAds() {
        return this.listFileAndAds;
    }

    public final void setListFileAndAds(ArrayList<Object> listFileAndAds) {
        Intrinsics.checkNotNullParameter(listFileAndAds, "listFileAndAds");
        this.listFileAndAds.clear();
        this.listFileAndAds = listFileAndAds;
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        ItemFileFolderBinding inflate = ItemFileFolderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        Intrinsics.checkNotNullExpressionValue(inflate, "inflate(\n               …      false\n            )");
        return new PhotoSuccessViewHolder(this, inflate);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int i) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        Object obj = this.listFileAndAds.get(i);
        Objects.requireNonNull(obj, "null cannot be cast to non-null type com.demo.filerecovery.model.modul.recoveryphoto.Model.PhotoModel");
        ((PhotoSuccessViewHolder) holder).onBind((PhotoModel) obj);
    }

//    @Override
//    public int getItemViewType(int i) {
//        return ((this.listFileAndAds.get(i) instanceof NativeAd) || !(this.listFileAndAds.get(i) instanceof PhotoModel)) ? 1 : 0;
//    }

    @Override
    public int getItemCount() {
        return this.listFileAndAds.size();
    }

    public static final class Companion {
        public Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final class PhotoSuccessViewHolder extends RecyclerView.ViewHolder {
        final PhotoSuccessAdapter this$0;
        private final ItemFileFolderBinding binding;

        public PhotoSuccessViewHolder(PhotoSuccessAdapter photoSuccessAdapter, ItemFileFolderBinding itemFileFolderBinding) {
            super(itemFileFolderBinding.getRoot());
            this.this$0 = photoSuccessAdapter;
            this.binding = itemFileFolderBinding;
        }

        public final ItemFileFolderBinding getBinding() {
            return this.binding;
        }

        public final void onBind(PhotoModel photoModel) {
            Intrinsics.checkNotNullParameter(photoModel, "photoModel");
            this.binding.cvIcon.setRadius(8.0f);
            if (this.this$0.isHideCheckBox) {
                this.binding.checkBox.setVisibility(View.GONE);
            }
            try {
                Glide.with(this.this$0.getMContext()).load(Intrinsics.stringPlus("file://", photoModel.getPathPhoto())).diskCacheStrategy(DiskCacheStrategy.ALL).priority(Priority.HIGH).centerCrop().error(R.drawable.ic_error).placeholder(R.drawable.ic_error).into(this.binding.imgFileType);
            } catch (Exception e) {
                Toast.makeText(this.this$0.getMContext(), Intrinsics.stringPlus("Exception: ", e.getMessage()), Toast.LENGTH_SHORT).show();
            }
            this.binding.tvFileName.setText(FilenameUtils.getName(photoModel.getPathPhoto()));
            this.binding.tvFileSize.setText(Utils.formatSize(photoModel.getSizePhoto()));
            this.binding.tvLastModified.setText(DateFormat.getDateInstance().format(Long.valueOf(photoModel.getLastModified())));
        }
    }
}
