package com.demo.filerecovery.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.demo.filerecovery.App;
import com.demo.filerecovery.R;
import com.demo.filerecovery.databinding.ItemRestoredFileBinding;
import com.demo.filerecovery.model.modul.recoveryaudio.Model.AudioModel;
import com.demo.filerecovery.model.modul.recoverydocument.Model.DocumentModel;
import com.demo.filerecovery.model.modul.recoveryphoto.Model.PhotoModel;
import com.demo.filerecovery.model.modul.recoveryvideo.Model.VideoModel;
import com.demo.filerecovery.ui.dialog.DeleteFileDialog;
import com.demo.filerecovery.utilts.FileUtil;
import com.demo.filerecovery.utilts.Utils;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import kotlin.jvm.internal.Intrinsics;


public final class RestoreFileAdapter extends RecyclerView.Adapter<RestoreFileAdapter.RestoreFileViewHolder> implements DeleteFileDialog.DeleteFileDialogCallBack {
    private final Context context;
    private final OnDataChanged onDataChanged;
    private final OnItemStateChange onItemStateChange;
    private boolean isShowCheckBox;
    private int fileType = -1;
    private ArrayList<AudioModel> listAudio = new ArrayList<>();
    private ArrayList<DocumentModel> listDocument = new ArrayList<>();
    private ArrayList<PhotoModel> listPhoto = new ArrayList<>();
    private ArrayList<VideoModel> listVideo = new ArrayList<>();


    public RestoreFileAdapter(OnItemStateChange onItemStateChange, OnDataChanged onDataChanged, Context context) {
        Intrinsics.checkNotNullParameter(onItemStateChange, "onItemStateChange");
        Intrinsics.checkNotNullParameter(onDataChanged, "onDataChanged");
        Intrinsics.checkNotNullParameter(context, "context");
        this.onItemStateChange = onItemStateChange;
        this.onDataChanged = onDataChanged;
        this.context = context;
    }

    public final Context getContext() {
        return this.context;
    }

    public final OnItemStateChange getOnItemStateChange() {
        return this.onItemStateChange;
    }

    @Override
    public void onDeleteFileCallbackInDialog(List<? extends File> list) {
        DeleteFileDialog.DeleteFileDialogCallBack.DefaultImpls.onDeleteFileCallbackInDialog(this, list);
    }

    public final boolean isShowCheckBox() {
        return this.isShowCheckBox;
    }

    public final void setShowCheckBox(boolean z) {
        this.isShowCheckBox = z;
    }

    public final int getFileType() {
        return this.fileType;
    }

    public final void setFileType(int i) {
        this.fileType = i;
    }

    public final void setDataPhoto(ArrayList<PhotoModel> listPhoto) {
        Intrinsics.checkNotNullParameter(listPhoto, "listPhoto");
        this.listPhoto.clear();
        this.listPhoto.addAll(listPhoto);
        setShowCheckbox(false);
        notifyDataSetChanged();
    }

    public final void setDataVideo(ArrayList<VideoModel> listVideo) {
        Intrinsics.checkNotNullParameter(listVideo, "listVideo");
        this.listVideo.clear();
        this.listVideo.addAll(listVideo);
        setShowCheckbox(false);
        notifyDataSetChanged();
    }

    public final void setDataAudio(ArrayList<AudioModel> listAudio) {
        Intrinsics.checkNotNullParameter(listAudio, "listAudio");
        this.listAudio.clear();
        this.listAudio.addAll(listAudio);
        setShowCheckbox(false);
        notifyDataSetChanged();
    }

    public final void setDataDocument(ArrayList<DocumentModel> listDocument) {
        Intrinsics.checkNotNullParameter(listDocument, "listDocument");
        this.listDocument.clear();
        this.listDocument.addAll(listDocument);
        setShowCheckbox(false);
        notifyDataSetChanged();
    }

    private final void setShowCheckbox(boolean z) {
        this.isShowCheckBox = z;
    }

    @Override
    public RestoreFileViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        ItemRestoredFileBinding inflate = ItemRestoredFileBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        Intrinsics.checkNotNullExpressionValue(inflate, "inflate(\n               …      false\n            )");
        return new RestoreFileViewHolder(this, inflate, this.context);
    }

    @Override
    public void onBindViewHolder(RestoreFileViewHolder restoreFileViewHolder, int i) {
        int i2 = this.fileType;
        if (i2 == 0) {
            restoreFileViewHolder.onBind(this.listPhoto.get(i), i, restoreFileViewHolder);
        } else if (i2 == 1) {
            restoreFileViewHolder.onBind(this.listVideo.get(i), i, restoreFileViewHolder);
        } else if (i2 != 2) {
            restoreFileViewHolder.onBind(this.listDocument.get(i), i, restoreFileViewHolder);
        } else {
            restoreFileViewHolder.onBind(this.listAudio.get(i), i, restoreFileViewHolder);
        }
    }

    @Override
    public int getItemCount() {
        int i = this.fileType;
        if (i == -1) {
            return 0;
        }
        if (i == 0) {
            return this.listPhoto.size();
        }
        if (i == 1) {
            return this.listVideo.size();
        }
        if (i != 2) {
            return this.listDocument.size();
        }
        return this.listAudio.size();
    }

    @Override
    public void onDeleteFileCallbackInDialogWithPosition(int i) {
        int i2 = this.fileType;
        if (i2 == 0) {
            FileUtil.copyFileToInternalStorage(this.context, this.listPhoto.get(i).getPathPhoto());
            this.listPhoto.remove(i);
            App.getInstance().getStorageCommon().getListPhoto().clear();
            App.getInstance().getStorageCommon().getListPhoto().addAll(this.listPhoto);
            if (this.listPhoto.isEmpty()) {
                this.onDataChanged.onDataEmpty();
            }
        } else if (i2 == 1) {
            FileUtil.copyFileToInternalStorage(this.context, this.listVideo.get(i).getPathPhoto());
            this.listVideo.remove(i);
            App.getInstance().getStorageCommon().getListVideo().clear();
            App.getInstance().getStorageCommon().getListVideo().addAll(this.listVideo);
            if (this.listVideo.isEmpty()) {
                this.onDataChanged.onDataEmpty();
            }
        } else if (i2 == 2) {
            FileUtil.copyFileToInternalStorage(this.context, this.listAudio.get(i).getPathPhoto());
            this.listAudio.remove(i);
            App.getInstance().getStorageCommon().getListAudio().clear();
            App.getInstance().getStorageCommon().getListAudio().addAll(this.listAudio);
            if (this.listAudio.isEmpty()) {
                this.onDataChanged.onDataEmpty();
            }
        } else if (i2 == 3) {
            FileUtil.copyFileToInternalStorage(this.context, this.listDocument.get(i).getPathDocument());
            this.listDocument.remove(i);
            App.getInstance().getStorageCommon().getListDocument().clear();
            App.getInstance().getStorageCommon().getListDocument().addAll(this.listDocument);
            if (this.listDocument.isEmpty()) {
                this.onDataChanged.onDataEmpty();
            }
        }
        notifyDataSetChanged();
    }

    public final void setOnAdapterItemClick(int i, boolean z) {
        int i2 = this.fileType;
        if (i2 == 0) {
            this.listPhoto.get(i).setIsCheck(z);
        } else if (i2 == 1) {
            this.listVideo.get(i).setIsCheck(z);
        } else if (i2 != 2) {
            this.listDocument.get(i).setSelected(Boolean.valueOf(z));
        } else {
            this.listAudio.get(i).setIsCheck(z);
        }
    }

    public interface OnDataChanged {
        void onDataEmpty();
    }

    public interface OnItemStateChange {
        void onCheckBoxStateChange(String str, boolean z, int i);

        void onLongClick();
    }

    public final class RestoreFileViewHolder extends RecyclerView.ViewHolder {
        final RestoreFileAdapter this$0;
        private final ItemRestoredFileBinding binding;
        private final Context context;

        public RestoreFileViewHolder(RestoreFileAdapter restoreFileAdapter, ItemRestoredFileBinding itemRestoredFileBinding, Context context) {
            super(itemRestoredFileBinding.getRoot());
            this.this$0 = restoreFileAdapter;
            this.binding = itemRestoredFileBinding;
            this.context = context;
        }

        public final Context getContext() {
            return this.context;
        }

        public final void onBind(PhotoModel photoModel, int i, RestoreFileViewHolder restoreFileViewHolder) {
            Intrinsics.checkNotNullParameter(photoModel, "photoModel");
            this.binding.cvIcon.setRadius(8.0f);
            String pathPhoto = photoModel.getPathPhoto();
            Intrinsics.checkNotNullExpressionValue(pathPhoto, "photoModel.pathPhoto");
            showThumbnail(pathPhoto);
            String pathPhoto2 = photoModel.getPathPhoto();
            Intrinsics.checkNotNullExpressionValue(pathPhoto2, "photoModel.pathPhoto");
            displayFileInformation(restoreFileViewHolder, pathPhoto2, photoModel.getSizePhoto(), photoModel.getLastModified(), photoModel.getIsCheck(), i);
        }

        public final void onBind(AudioModel audioModel, int i, RestoreFileViewHolder restoreFileViewHolder) {
            Intrinsics.checkNotNullParameter(audioModel, "audioModel");
            this.binding.cvIcon.setRadius(100.0f);
            String pathPhoto = audioModel.getPathPhoto();
            Intrinsics.checkNotNullExpressionValue(pathPhoto, "audioModel.pathPhoto");
            showThumbnail(pathPhoto);
            String pathPhoto2 = audioModel.getPathPhoto();
            Intrinsics.checkNotNullExpressionValue(pathPhoto2, "audioModel.pathPhoto");
            displayFileInformation(restoreFileViewHolder, pathPhoto2, audioModel.getSizePhoto(), audioModel.getLastModified(), audioModel.getIsCheck(), i);
        }

        public final void onBind(VideoModel videoModel, int i, RestoreFileViewHolder restoreFileViewHolder) {
            Intrinsics.checkNotNullParameter(videoModel, "videoModel");
            this.binding.cvIcon.setRadius(8.0f);
            String pathPhoto = videoModel.getPathPhoto();
            Intrinsics.checkNotNullExpressionValue(pathPhoto, "videoModel.pathPhoto");
            showThumbnail(pathPhoto);
            String pathPhoto2 = videoModel.getPathPhoto();
            Intrinsics.checkNotNullExpressionValue(pathPhoto2, "videoModel.pathPhoto");
            displayFileInformation(restoreFileViewHolder, pathPhoto2, videoModel.getSizePhoto(), videoModel.getLastModified(), videoModel.getIsCheck(), i);
        }

        public final void onBind(DocumentModel documentModel, int i, RestoreFileViewHolder restoreFileViewHolder) {
            Intrinsics.checkNotNullParameter(documentModel, "documentModel");
            setDocumentIcon(documentModel);
            String pathDocument = documentModel.getPathDocument();
            Intrinsics.checkNotNullExpressionValue(pathDocument, "documentModel.pathDocument");
            long sizeDocument = documentModel.getSizeDocument();
            long lastModified = documentModel.getLastModified();
            Boolean selected = documentModel.getSelected();
            Intrinsics.checkNotNullExpressionValue(selected, "documentModel.selected");
            displayFileInformation(restoreFileViewHolder, pathDocument, sizeDocument, lastModified, selected.booleanValue(), i);
        }

        private final void showThumbnail(String str) {
            try {
                Glide.with(this.context).load(Intrinsics.stringPlus("file://", str)).diskCacheStrategy(DiskCacheStrategy.ALL).priority(Priority.HIGH).centerCrop().error(R.drawable.ic_action_thum_audio).placeholder(R.drawable.ic_action_thum_audio).into(this.binding.imgFile);
            } catch (Exception e) {
                Toast.makeText(this.context, Intrinsics.stringPlus("Exception: ", e.getMessage()), Toast.LENGTH_SHORT).show();
            }
        }

        private final void displayFileInformation(final RestoreFileViewHolder restoreFileViewHolder, final String str, long j, long j2, boolean z, final int i) {
            this.binding.checkBox.setChecked(z);
            this.binding.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public final void onClick(View view) {
                    RestoreFileViewHolder restoreFileViewHolder2 = RestoreFileViewHolder.this;
                    restoreFileViewHolder2.m74displayFileInformation$lambda0(restoreFileViewHolder2, restoreFileViewHolder2.this$0, i, str, view);
                }
            });
            if (this.this$0.isShowCheckBox()) {
                this.binding.checkBox.setVisibility(View.VISIBLE);
                this.binding.imgDelete.setVisibility(View.GONE);
            } else {
                this.binding.checkBox.setVisibility(View.GONE);
                this.binding.imgDelete.setVisibility(View.VISIBLE);
            }
            this.binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public final void onClick(View view) {
                    RestoreFileViewHolder restoreFileViewHolder2 = RestoreFileViewHolder.this;
                    restoreFileViewHolder2.m75displayFileInformation$lambda1(RestoreFileAdapter.this, restoreFileViewHolder, view);
                }
            });
            this.binding.getRoot().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public final boolean onLongClick(View view) {
                    RestoreFileViewHolder restoreFileViewHolder2 = RestoreFileViewHolder.this;
                    return restoreFileViewHolder2.m76displayFileInformation$lambda2(RestoreFileAdapter.this, restoreFileViewHolder, view);
                }
            });
            this.binding.imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public final void onClick(View view) {
                    RestoreFileViewHolder restoreFileViewHolder2 = RestoreFileViewHolder.this;
                    restoreFileViewHolder2.m77displayFileInformation$lambda3(restoreFileViewHolder2, i, restoreFileViewHolder2.this$0, view);
                }
            });
            this.binding.tvFileName.setText(FilenameUtils.getName(str));
            this.binding.tvFileSize.setText(Utils.formatSize(j));
            this.binding.tvLastModified.setText(DateFormat.getDateInstance().format(Long.valueOf(j2)));
        }

        public final void m74displayFileInformation$lambda0(RestoreFileViewHolder this$0, RestoreFileAdapter this$1, int i, String filePath, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(this$1, "this$1");
            Intrinsics.checkNotNullParameter(filePath, "$filePath");
            this$0.binding.checkBox.isChecked();
            this$1.setOnAdapterItemClick(i, this$0.binding.checkBox.isChecked());
            this$1.getOnItemStateChange().onCheckBoxStateChange(filePath, this$0.binding.checkBox.isChecked(), i);
        }

        public final void m75displayFileInformation$lambda1(RestoreFileAdapter this$0, RestoreFileViewHolder this$1, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(this$1, "this$1");
            if (this$0.isShowCheckBox()) {
                this$1.binding.checkBox.performClick();
            }
        }

        public final boolean m76displayFileInformation$lambda2(RestoreFileAdapter this$0, RestoreFileViewHolder this$1, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(this$1, "this$1");
            if (!this$0.isShowCheckBox()) {
                this$1.binding.checkBox.performClick();
            }
            this$0.setShowCheckBox(true);
            this$0.getOnItemStateChange().onLongClick();
            this$0.notifyDataSetChanged();
            return true;
        }

        public final void m77displayFileInformation$lambda3(RestoreFileViewHolder this$0, int i, RestoreFileAdapter this$1, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(this$1, "this$1");
            DeleteFileDialog deleteFileDialog = new DeleteFileDialog(this$0.context, null, Integer.valueOf(i));
            deleteFileDialog.setDeleteFileDialogCallBack(this$1);
            deleteFileDialog.show();
        }

        private final void setDocumentIcon(DocumentModel documentModel) {
            String extension = FilenameUtils.getExtension(documentModel.getPathDocument());
            if (extension != null) {
                switch (extension.hashCode()) {
                    case 96322:
                        if (extension.equals("aab")) {
                            return;
                        }
                        extension.equals("apk");
                        return;
                    case 96796:
                        extension.equals("apk");
                        return;
                    case 99640:
                    case 111220:
                    case 118783:
                    case 3088960:
                    default:
                        return;
                    case 110834:
                        extension.equals("pdf");
                        return;
                    case 115312:
                        if (extension.equals("txt")) {
                            return;
                        }
                        extension.equals("vvc");
                        return;
                    case 117155:
                        extension.equals("vvc");
                        return;
                    case 120609:
                        extension.equals("zip");
                        return;
                }
            }
        }
    }
}
