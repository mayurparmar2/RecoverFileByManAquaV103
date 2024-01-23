package com.demo.filerecovery.model.modul.recoverydocument.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.filerecovery.R;
import com.demo.filerecovery.databinding.ItemFileFolderBinding;
import com.demo.filerecovery.model.modul.recoverydocument.Model.DocumentModel;
import com.demo.filerecovery.utilts.Utils;

import org.apache.commons.io.FilenameUtils;

import java.text.DateFormat;
import java.util.List;

public class DocumentAdapter extends RecyclerView.Adapter<DocumentAdapter.DocumentViewHolder> {
    public final Context mContext;
    public List<DocumentModel> documentModelList;
    public Boolean isHideCheckBox = true;
    public Boolean isRestore = false;
    public OnItemClick onItemClick;


    public DocumentAdapter(Context context) {
        this.mContext = context;
    }

    public void setInAlbumDocumentActivity(Boolean bool) {
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public void setHideCheckBox(Boolean bool) {
        this.isHideCheckBox = bool;
    }

    public void setDocumentModelList(List<DocumentModel> list) {
        this.documentModelList = list;
    }

    @Override
    public DocumentViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new DocumentViewHolder(ItemFileFolderBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(DocumentViewHolder documentViewHolder, int i) {
        documentViewHolder.onBind(this.documentModelList.get(i), i, getItemCount());
    }

    @Override
    public int getItemCount() {
        return this.documentModelList.size();
    }

    public interface OnItemClick {
        void onClick(DocumentModel documentModel, Boolean bool);
    }

    public class DocumentViewHolder extends RecyclerView.ViewHolder {
        private final ItemFileFolderBinding binding;

        public DocumentViewHolder(ItemFileFolderBinding itemFileFolderBinding) {
            super(itemFileFolderBinding.getRoot());
            this.binding = itemFileFolderBinding;
        }

        public void onBind(final DocumentModel documentModel, int i, int i2) {
            char c;
            int i3;
            String extension = FilenameUtils.getExtension(documentModel.getPathDocument());
            switch (extension.hashCode()) {
                case 96322:
                    if (extension.equals("aab")) {
                        c = '\t';
                        break;
                    }
                case 96796:
                    if (extension.equals("apk")) {
                        c = '\n';
                        break;
                    }
                case 99640:
                    if (extension.equals("doc")) {
                        c = 1;
                        break;
                    }
                case 110834:
                    if (extension.equals("pdf")) {
                        c = 6;
                        break;
                    }
                case 111220:
                    if (extension.equals("ppt")) {
                        c = 7;
                        break;
                    }
                case 115312:
                    if (extension.equals("txt")) {
                        c = 0;
                        break;
                    }
                case 117155:
                    if (extension.equals("vvc")) {
                        c = '\f';
                        break;
                    }
                case 118783:
                    if (extension.equals("xls")) {
                        c = 3;
                        break;
                    }
                case 120609:
                    if (extension.equals("zip")) {
                        c = 11;
                        break;
                    }
                case 3088960:
                    if (extension.equals("docx")) {
                        c = 2;
                        break;
                    }
                case 3447940:
                    if (extension.equals("pptx")) {
                        c = '\b';
                        break;
                    }
                case 3682382:
                    if (extension.equals("xlsm")) {
                        c = 5;
                        break;
                    }
                case 3682393:
                    if (extension.equals("xlsx")) {
                        c = 4;
                        break;
                    }
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    i3 = R.drawable.ic_action_thum_txt;
                    break;
                case 1:
                case 2:
                    i3 = R.drawable.ic_action_thum_doc;
                    break;
                case 3:
                case 4:
                case 5:
                    i3 = R.drawable.ic_action_thum_xls;
                    break;
                case 6:
                    i3 = R.drawable.ic_action_thum_pdf;
                    break;
                case 7:
                case '\b':
                    i3 = R.drawable.ic_action_thum_ppt;
                    break;
                case '\t':
                    i3 = R.drawable.ic_action_thum_aab;
                    break;
                case '\n':
                    i3 = R.drawable.ic_action_thum_apk;
                    break;
                case 11:
                    i3 = R.drawable.ic_action_thum_zip;
                    break;
                case '\f':
                    i3 = R.drawable.ic_action_thumvvc;
                    break;
                default:
                    i3 = -1;
                    break;
            }
            if (DocumentAdapter.this.isHideCheckBox.booleanValue()) {
                this.binding.checkBox.setVisibility(View.GONE);
                if (i == i2 - 1) {
                    this.binding.line2.setVisibility(View.GONE);
                }
                initView(this.binding.imgFileType, this.binding.tvFileName, this.binding.tvFileSize, this.binding.tvLastModified, documentModel, i3);
                this.binding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public final void onClick(View view) {
                        DocumentViewHolder.this.lambda$onBind$0$DocumentAdapter$DocumentViewHolder(view);
                    }
                });
                return;
            }
            this.binding.line2.setVisibility(View.GONE);
            initView(this.binding.imgFileType, this.binding.tvFileName, this.binding.tvFileSize, this.binding.tvLastModified, documentModel, i3);
            if (DocumentAdapter.this.isRestore.booleanValue() && DocumentAdapter.this.documentModelList.size() == 1) {
                this.binding.checkBox.setVisibility(View.GONE);
            }
            this.binding.checkBox.setChecked(documentModel.isSelected());
            this.binding.checkBox.setClickable(false);
            this.binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public final void onClick(View view) {
                    DocumentViewHolder.this.lambda$onBind$1$DocumentAdapter$DocumentViewHolder(documentModel, view);
                }
            });
        }

        public void lambda$onBind$0$DocumentAdapter$DocumentViewHolder(View view) {
            DocumentAdapter.this.onItemClick.onClick(null, null);
        }

        public void lambda$onBind$1$DocumentAdapter$DocumentViewHolder(DocumentModel documentModel, View view) {
            if (this.binding.checkBox.isChecked()) {
                this.binding.checkBox.setChecked(false);
                documentModel.setSelected(false);
                DocumentAdapter.this.onItemClick.onClick(documentModel, false);
                return;
            }
            this.binding.checkBox.setChecked(true);
            documentModel.setSelected(true);
            DocumentAdapter.this.onItemClick.onClick(documentModel, true);
        }

        private void initView(ImageView imageView, TextView textView, TextView textView2, TextView textView3, DocumentModel documentModel, int i) {
            imageView.setImageDrawable(ContextCompat.getDrawable(DocumentAdapter.this.mContext, i));
            textView.setText(FilenameUtils.getName(documentModel.getPathDocument()));
            textView2.setText(Utils.formatSize(documentModel.getSizeDocument()));
            textView3.setText(DateFormat.getDateInstance().format(Long.valueOf(documentModel.getLastModified())));
        }
    }
}
