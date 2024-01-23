package com.demo.filerecovery.model.modul.recoveryvideo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.filerecovery.R;
import com.demo.filerecovery.model.modul.recoveryvideo.Model.AlbumVideo;

import java.util.ArrayList;
import java.util.List;


public class AlbumsVideoAdapter extends RecyclerView.Adapter<AlbumsVideoAdapter.MyViewHolder> {
    private List<AlbumVideo> al_menu = new ArrayList();
    private Context context;
    private OnClickItemListener mOnClickItemListener;


    public AlbumsVideoAdapter(Context context, OnClickItemListener onClickItemListener) {
        this.context = context;
        this.mOnClickItemListener = onClickItemListener;
    }

    public void setData(List<AlbumVideo> list) {
        this.al_menu.clear();
        this.al_menu.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_albums_new, viewGroup, false), this.mOnClickItemListener);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        myViewHolder.tv_foldersize.setText(this.context.getResources().getQuantityString(R.plurals.file, this.al_menu.get(i).getListPhoto().size(), Integer.valueOf(this.al_menu.get(i).getListPhoto().size())));
        String str_folder = this.al_menu.get(i).getStr_folder();
        myViewHolder.tvtTitle.setText(str_folder.substring(str_folder.lastIndexOf("/") + 1));
        SectionListDataAdapter sectionListDataAdapter = new SectionListDataAdapter(this.context, this.al_menu.get(i).getListPhoto(), i);
        sectionListDataAdapter.setClickItemListener(this.mOnClickItemListener);
        myViewHolder.recycler_view_list.setHasFixedSize(true);
        myViewHolder.recycler_view_list.setLayoutManager(new LinearLayoutManager(this.context, 0, false));
        myViewHolder.recycler_view_list.setAdapter(sectionListDataAdapter);
    }

    @Override
    public int getItemCount() {
        return this.al_menu.size();
    }

    public interface OnClickItemListener {
        void onClickItem(int i);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected RecyclerView recycler_view_list;
        OnClickItemListener onClickItemListener;
        TextView tv_foldersize;
        TextView tvtTitle;

        public MyViewHolder(View view, OnClickItemListener onClickItemListener) {
            super(view);
            this.recycler_view_list = (RecyclerView) view.findViewById(R.id.recycler_view_list);
            this.tv_foldersize = (TextView) view.findViewById(R.id.tvtTotalFile);
            this.tvtTitle = (TextView) view.findViewById(R.id.tvtTitle);
            this.onClickItemListener = onClickItemListener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            this.onClickItemListener.onClickItem(getAdapterPosition());
        }
    }
}
