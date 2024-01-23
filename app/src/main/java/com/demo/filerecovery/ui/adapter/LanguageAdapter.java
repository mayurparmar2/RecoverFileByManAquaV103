package com.demo.filerecovery.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.demo.filerecovery.R;
import com.demo.filerecovery.base.BaseViewHolder;
import com.demo.filerecovery.databinding.ItemLanguageBinding;
import com.demo.filerecovery.model.LanguageModel;

import java.util.List;

import kotlin.jvm.internal.Intrinsics;


public final class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.LanguageAdapterVH> {
    public final Context context;
    private final List<LanguageModel> listLanguage;
    private LanguageAdapterCallBack callback;
    private int itemPosition;


    public LanguageAdapter(Context context, List<LanguageModel> listLanguage) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(listLanguage, "listLanguage");
        this.context = context;
        this.listLanguage = listLanguage;
    }

    public final int getItemPosition() {
        return this.itemPosition;
    }

    public final void setItemPosition(int i) {
        this.itemPosition = i;
    }

    public final LanguageAdapterCallBack getCallback() {
        return this.callback;
    }

    public final void setCallback(LanguageAdapterCallBack languageAdapterCallBack) {
        this.callback = languageAdapterCallBack;
    }

    @Override
    public LanguageAdapterVH onCreateViewHolder(ViewGroup parent, int i) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        ItemLanguageBinding inflate = ItemLanguageBinding.inflate(LayoutInflater.from(this.context), parent, false);
        Intrinsics.checkNotNullExpressionValue(inflate, "inflate(\n               …      false\n            )");
        return new LanguageAdapterVH(this, inflate);
    }

    @Override
    public void onBindViewHolder(LanguageAdapterVH holder, int i) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        holder.onBind(this.listLanguage.get(i), i);
    }

    @Override
    public int getItemCount() {
        return this.listLanguage.size();
    }

    public interface LanguageAdapterCallBack {
        void onSelectLanguage(LanguageModel languageModel);
    }

    public final class LanguageAdapterVH extends BaseViewHolder<ItemLanguageBinding> {
        final LanguageAdapter this$0;

        public LanguageAdapterVH(LanguageAdapter languageAdapter, ItemLanguageBinding itemLanguageBinding) {
            super(itemLanguageBinding);
            this.this$0 = languageAdapter;
        }

        public final void onBind(final LanguageModel languageModel, final int i) {
            Intrinsics.checkNotNullParameter(languageModel, "languageModel");
            getBinding().getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public final void onClick(View view) {
                    LanguageAdapterVH languageAdapterVH = LanguageAdapterVH.this;
                    languageAdapterVH.m73onBind$lambda0(languageModel, languageAdapterVH.this$0, i, view);
                }
            });
            if (this.this$0.getItemPosition() == i) {
                getBinding().bgLayout.setBackground(ContextCompat.getDrawable(this.this$0.context, R.drawable.shape_bg_language_select));
                getBinding().tvtNameCountry.setTextColor(ContextCompat.getColor(this.this$0.context, R.color.white));
            } else {
                getBinding().bgLayout.setBackground(ContextCompat.getDrawable(this.this$0.context, R.drawable.shape_bg_language_unselect));
                getBinding().tvtNameCountry.setTextColor(ContextCompat.getColor(this.this$0.context, R.color.color_text_language));
            }
            getBinding().tvtNameCountry.setText(languageModel.getName());
            RequestManager with = Glide.with(this.this$0.context);
            with.load(Uri.parse("file:///android_asset/flags/" + languageModel.getLanguageCode() + ".png")).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(GlideException glideException, Object model, Target<Drawable> target, boolean z) {
                    Intrinsics.checkNotNullParameter(model, "model");
                    Intrinsics.checkNotNullParameter(target, "target");
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable drawable, Object model, Target<Drawable> target, DataSource dataSource, boolean z) {
                    Intrinsics.checkNotNullParameter(model, "model");
                    Intrinsics.checkNotNullParameter(target, "target");
                    Intrinsics.checkNotNullParameter(dataSource, "dataSource");
                    return false;
                }
            }).into(getBinding().countryImage);
        }

        public void m73onBind$lambda0(LanguageModel languageModel, LanguageAdapter this$0, int i, View view) {
            Intrinsics.checkNotNullParameter(languageModel, "$languageModel");
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            languageModel.setSelected(!languageModel.isSelected());
            LanguageAdapterCallBack callback = this$0.getCallback();
            if (callback != null) {
                callback.onSelectLanguage(languageModel);
            }
            if (this$0.getItemPosition() != i) {
                this$0.notifyItemChanged(this$0.getItemPosition());
                Log.d("language", "onBind: " + this$0.getItemPosition() + " , " + i);
                this$0.setItemPosition(i);
                this$0.notifyItemChanged(i);
            }
        }
    }
}
