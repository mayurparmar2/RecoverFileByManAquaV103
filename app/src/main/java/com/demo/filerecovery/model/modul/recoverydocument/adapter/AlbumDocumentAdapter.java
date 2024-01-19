package com.demo.filerecovery.model.modul.recoverydocument.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.demo.filerecovery.model.modul.recoverydocument.Model.AlbumDocument;
import com.demo.filerecovery.model.modul.recoverydocument.Model.DocumentModel;
import com.demo.filerecovery.R;
import com.demo.filerecovery.databinding.ItemAlbumDocumentBinding;

import org.apache.commons.io.FilenameUtils;

import java.util.ArrayList;
import java.util.List;


public class AlbumDocumentAdapter extends RecyclerView.Adapter<AlbumDocumentAdapter.AlbumDocumentViewHolder> {
    private final int MAX_FILE_SHOW = 3;
    public Context mContext;
    public OnItemClick onItemClick;
    private List<AlbumDocument> albumDocList = new ArrayList();


    public AlbumDocumentAdapter(Context context, OnItemClick onItemClick) {
        this.mContext = context;
        this.onItemClick = onItemClick;
    }

    public void setData(List<AlbumDocument> list) {
        this.albumDocList.clear();
        this.albumDocList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public AlbumDocumentViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new AlbumDocumentViewHolder(ItemAlbumDocumentBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(AlbumDocumentViewHolder albumDocumentViewHolder, int i) {
        albumDocumentViewHolder.onBind(this.albumDocList.get(i).getListDocument(), this.albumDocList.get(i).getStr_folder(), i);
    }

    @Override
    public int getItemCount() {
        return this.albumDocList.size();
    }

    public interface OnItemClick {
        void onCLick(int i, String str);
    }

    public class AlbumDocumentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ItemAlbumDocumentBinding binding;
        private DocumentAdapter documentAdapter;
        private String folderPath;
        private int position;

        public AlbumDocumentViewHolder(ItemAlbumDocumentBinding itemAlbumDocumentBinding) {
            super(itemAlbumDocumentBinding.getRoot());
            this.binding = itemAlbumDocumentBinding;
            this.documentAdapter = new DocumentAdapter(AlbumDocumentAdapter.this.mContext);
        }

        public void onBind(List<DocumentModel> list, final String str, final int i) {
            this.binding.txtParentFolder.setText(FilenameUtils.getName(str));
            this.binding.btnTotalFile.setText(AlbumDocumentAdapter.this.mContext.getResources().getQuantityString(R.plurals.file, list.size(), Integer.valueOf(list.size())));
            if (list.size() > 3) {
                list = list.subList(0, 3);
            }
            this.documentAdapter.setOnItemClick(new DocumentAdapter.OnItemClick() {
                @Override
                public final void onClick(DocumentModel documentModel, Boolean bool) {
                    AlbumDocumentViewHolder.this.lambda$onBind$0$AlbumDocumentAdapter$AlbumDocumentViewHolder(i, str, documentModel, bool);
                }
            });
            this.documentAdapter.setDocumentModelList(list);
            this.documentAdapter.setInAlbumDocumentActivity(true);
            this.documentAdapter.setHideCheckBox(true);
            this.binding.recyclerView.setAdapter(this.documentAdapter);
            this.folderPath = str;
            this.position = i;
            this.binding.getRoot().setOnClickListener(this);
            this.binding.btnTotalFile.setOnClickListener(this);
        }

        public void lambda$onBind$0$AlbumDocumentAdapter$AlbumDocumentViewHolder(int i, String str, DocumentModel documentModel, Boolean bool) {
            AlbumDocumentAdapter.this.onItemClick.onCLick(i, FilenameUtils.getName(str));
        }

        @Override
        public void onClick(View view) {
            AlbumDocumentAdapter.this.onItemClick.onCLick(this.position, FilenameUtils.getName(this.folderPath));
        }
    }
}
