package com.demo.filerecovery.model.modul.fileprotection;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.filerecovery.R;
import com.demo.filerecovery.base.BaseViewHolder;
import com.demo.filerecovery.databinding.ItemFileProtectionBinding;
import com.demo.filerecovery.utilts.Utils;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;


public final class FileProtectionAdapter extends RecyclerView.Adapter<FileProtectionAdapter.FileProtectionViewHolder> {
    public static final Companion Companion = new Companion(null);
    public static final String TAG = "FileProtectionAdapter";
    private final Context context;
    private final OnItemStateChange onItemStateChange;
    private FileProtectionAdapterCallback fileProtectionAdapterCallBack;
    private boolean isShowCheckBox;
    private ArrayList<FileWithSelection> listFile = new ArrayList<>();


    public FileProtectionAdapter(Context context, OnItemStateChange onItemStateChange) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(onItemStateChange, "onItemStateChange");
        this.context = context;
        this.onItemStateChange = onItemStateChange;
    }

    public static final void m37onBindViewHolder$lambda1(FileProtectionAdapter this$0, FileWithSelection item, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(item, "$item");
        FileProtectionAdapterCallback fileProtectionAdapterCallback = this$0.fileProtectionAdapterCallBack;
        if (fileProtectionAdapterCallback != null) {
            fileProtectionAdapterCallback.onMoreClickListener(item.getFile());
        }
    }

    public static final boolean m38onBindViewHolder$lambda2(FileProtectionViewHolder holder, View view) {
        Intrinsics.checkNotNullParameter(holder, "$holder");
        holder.getBinding().checkBox.setVisibility(View.VISIBLE);
        holder.getBinding().imgMore.setVisibility(View.GONE);
        return true;
    }

    public static final void m39onBindViewHolder$lambda3(FileWithSelection item, FileProtectionViewHolder holder, FileProtectionAdapter this$0, int i, View view) {
        Intrinsics.checkNotNullParameter(item, "$item");
        Intrinsics.checkNotNullParameter(holder, "$holder");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        item.setSelected(holder.getBinding().checkBox.isChecked());
        this$0.onItemStateChange.onCheckBoxStateChange(item.getFile(), holder.getBinding().checkBox.isChecked(), i);
    }

    public static final void m40onBindViewHolder$lambda4(FileProtectionAdapter this$0, FileProtectionViewHolder holder, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(holder, "$holder");
        if (this$0.isShowCheckBox) {
            holder.getBinding().checkBox.performClick();
        }
    }

    public static final boolean m41onBindViewHolder$lambda5(FileProtectionAdapter this$0, FileProtectionViewHolder holder, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(holder, "$holder");
        if (!this$0.isShowCheckBox) {
            holder.getBinding().checkBox.performClick();
        }
        this$0.isShowCheckBox = true;
        this$0.onItemStateChange.onLongClick();
        this$0.notifyDataSetChanged();
        return true;
    }

    public final FileProtectionAdapterCallback getFileProtectionAdapterCallBack() {
        return this.fileProtectionAdapterCallBack;
    }

    public final void setFileProtectionAdapterCallBack(FileProtectionAdapterCallback fileProtectionAdapterCallback) {
        this.fileProtectionAdapterCallBack = fileProtectionAdapterCallback;
    }

    public final void resetAdapter() {
        this.isShowCheckBox = false;
        Iterator<FileWithSelection> it = this.listFile.iterator();
        while (it.hasNext()) {
            it.next().setSelected(false);
        }
        notifyDataSetChanged();
    }

    public final void setData(ArrayList<File> listFile) {
        Intrinsics.checkNotNullParameter(listFile, "listFile");
        sortListFile(listFile);
        this.listFile.clear();
        Iterator<File> it = listFile.iterator();
        while (it.hasNext()) {
            File file = it.next();
            ArrayList<FileWithSelection> arrayList = this.listFile;
            Intrinsics.checkNotNullExpressionValue(file, "file");
            arrayList.add(new FileWithSelection(this, file, false));
        }
        this.isShowCheckBox = false;
        notifyDataSetChanged();
    }

    private final void sortListFile(ArrayList<File> arrayList) {
        if (arrayList.size() > 1) {
            CollectionsKt.sortWith(arrayList, new Comparator() {
                @Override
                public int compare(Object obj, Object obj2) {
                    return ComparisonsKt.compareValues(Long.valueOf(((File) obj).lastModified()), Long.valueOf(((File) obj2).lastModified()));
                }
            });
        }
    }

    @Override
    public FileProtectionViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        ItemFileProtectionBinding inflate = ItemFileProtectionBinding.inflate(LayoutInflater.from(this.context), parent, false);
        Intrinsics.checkNotNullExpressionValue(inflate, "inflate(\n               …      false\n            )");
        return new FileProtectionViewHolder(this, inflate);
    }

    @Override
    public void onBindViewHolder(final FileProtectionViewHolder holder, @SuppressLint("RecyclerView") final int i) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        FileWithSelection fileWithSelection = this.listFile.get(i);
        Intrinsics.checkNotNullExpressionValue(fileWithSelection, "listFile[position]");
        final FileWithSelection fileWithSelection2 = fileWithSelection;
        holder.getBinding().tvFileName.setText(FilenameUtils.getName(fileWithSelection2.getFile().getPath()));
        holder.getBinding().tvLastModified.setText(DateFormat.getDateInstance().format(Long.valueOf(fileWithSelection2.getFile().lastModified())));
        holder.getBinding().tvFileSize.setText(Utils.formatSize(fileWithSelection2.getFile().length()));
        holder.getBinding().imgMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                FileProtectionAdapter.m37onBindViewHolder$lambda1(FileProtectionAdapter.this, fileWithSelection2, view);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public final boolean onLongClick(View view) {
                return FileProtectionAdapter.m38onBindViewHolder$lambda2(holder, view);
            }
        });
        if (this.isShowCheckBox) {
            holder.getBinding().checkBox.setVisibility(View.VISIBLE);
            holder.getBinding().imgMore.setVisibility(View.GONE);
        } else {
            holder.getBinding().checkBox.setVisibility(View.GONE);
            holder.getBinding().imgMore.setVisibility(View.VISIBLE);
        }
        holder.getBinding().checkBox.setChecked(fileWithSelection2.isSelected());
        holder.getBinding().checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                FileWithSelection fileWithSelection3 = fileWithSelection2;
                FileProtectionViewHolder fileProtectionViewHolder = holder;
                FileProtectionAdapter.m39onBindViewHolder$lambda3(fileWithSelection3, fileProtectionViewHolder, fileProtectionViewHolder.this$0, i, view);
            }
        });
        holder.getBinding().getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                FileProtectionAdapter.m40onBindViewHolder$lambda4(FileProtectionAdapter.this, holder, view);
            }
        });
        holder.getBinding().getRoot().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public final boolean onLongClick(View view) {
                return FileProtectionAdapter.m41onBindViewHolder$lambda5(FileProtectionAdapter.this, holder, view);
            }
        });
        Log.e(TAG, Intrinsics.stringPlus("path: ", fileWithSelection2.getFile().getPath()));
        Log.e(TAG, Intrinsics.stringPlus("length: ", Long.valueOf(fileWithSelection2.getFile().length())));
        String extension = FilenameUtils.getExtension(fileWithSelection2.getFile().getPath());
        if (extension != null) {
            extension.hashCode();
        }
        String extension2 = FilenameUtils.getExtension(fileWithSelection2.getFile().getPath());
        Log.e("image", "" + extension2.hashCode());
        int i2 = -1;
        switch (extension2.hashCode()) {
            case 96322:
                if (extension2.equals("aab")) {
                    i2 = R.drawable.ic_action_thum_aab;
                    break;
                }
                break;
            case 96796:
                if (extension2.equals("apk")) {
                    i2 = R.drawable.ic_action_thum_apk;
                    break;
                }
                break;
            case 105441:
                if (extension2.equals("jpg")) {
                    i2 = R.drawable.ic_action_thum_image;
                    break;
                }
                break;
            case 108272:
                if (extension2.equals("mp3")) {
                    i2 = R.drawable.ic_action_thum_audiio;
                    break;
                }
                break;
            case 108273:
                if (extension2.equals("mp4")) {
                    i2 = R.drawable.ic_action_thum_video;
                    break;
                }
                break;
            case 110834:
                if (extension2.equals("pdf")) {
                    i2 = R.drawable.ic_action_thum_pdf;
                    break;
                }
                break;
            case 115312:
                if (extension2.equals("txt")) {
                    i2 = R.drawable.ic_action_thum_txt;
                    break;
                }
                break;
            case 117155:
                if (extension2.equals("vvc")) {
                    i2 = R.drawable.ic_action_thumvvc;
                    break;
                }
                break;
            case 120609:
                if (extension2.equals("zip")) {
                    i2 = R.drawable.ic_action_thum_zip;
                    break;
                }
                break;
        }
        holder.getBinding().imgFile.setImageDrawable(ContextCompat.getDrawable(this.context, i2));
        holder.getBinding().imgPlay.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return this.listFile.size();
    }

    public interface FileProtectionAdapterCallback {
        void onMoreClickListener(File file);
    }

    public interface OnItemStateChange {
        void onCheckBoxStateChange(File file, boolean z, int i);

        void onLongClick();
    }

    public static final class Companion {
        public Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final class FileProtectionViewHolder extends BaseViewHolder<ItemFileProtectionBinding> {
        final FileProtectionAdapter this$0;

        public FileProtectionViewHolder(FileProtectionAdapter fileProtectionAdapter, ItemFileProtectionBinding itemFileProtectionBinding) {
            super(itemFileProtectionBinding);
            this.this$0 = fileProtectionAdapter;
        }
    }

    public final class FileWithSelection {
        final FileProtectionAdapter this$0;
        private File file;
        private boolean isSelected;

        public FileWithSelection(FileProtectionAdapter this$0, File file, boolean z) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(file, "file");
            this.this$0 = this$0;
            this.file = file;
            this.isSelected = z;
        }

        public final File getFile() {
            return this.file;
        }

        public final void setFile(File file) {
            Intrinsics.checkNotNullParameter(file, "file");
            this.file = file;
        }

        public final boolean isSelected() {
            return this.isSelected;
        }

        public final void setSelected(boolean z) {
            this.isSelected = z;
        }
    }
}
