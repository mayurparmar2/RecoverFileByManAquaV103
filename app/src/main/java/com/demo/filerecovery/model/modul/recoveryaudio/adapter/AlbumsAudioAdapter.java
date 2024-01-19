package com.demo.filerecovery.model.modul.recoveryaudio.adapter;

import android.content.Context;
import android.graphics.Rect;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.demo.filerecovery.model.modul.recoveryaudio.Model.AlbumAudio;
import com.demo.filerecovery.model.modul.recoveryaudio.Model.AudioModel;
import com.demo.filerecovery.R;

import java.util.ArrayList;


public class AlbumsAudioAdapter extends RecyclerView.Adapter<AlbumsAudioAdapter.MyViewHolder> {
    private ArrayList<AlbumAudio> al_menu = new ArrayList<>();
    private Context context;
    private SectionListAudioAdapter itemListDataAdapter;
    private OnClickItemListener mOnClickItemListener;


    public AlbumsAudioAdapter(Context context, OnClickItemListener onClickItemListener) {
        this.context = context;
        this.mOnClickItemListener = onClickItemListener;
    }

    public void setData(ArrayList<AlbumAudio> arrayList) {
        this.al_menu.clear();
        this.al_menu.addAll(arrayList);
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_audio, viewGroup, false), this.mOnClickItemListener);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        String str_folder = this.al_menu.get(i).getStr_folder();
        myViewHolder.txtAlbumName.setText(str_folder.substring(str_folder.lastIndexOf("/") + 1));
        TextView textView = myViewHolder.txtTotalFile;
        textView.setText(this.al_menu.get(i).getListPhoto().size() + " " + this.context.getString(R.string.audios));
        ArrayList<AudioModel> listPhoto = this.al_menu.get(i).getListPhoto();
        SectionListAudioAdapter sectionListAudioAdapter = new SectionListAudioAdapter(this.context);
        this.itemListDataAdapter = sectionListAudioAdapter;
        sectionListAudioAdapter.setData(listPhoto, i);
        this.itemListDataAdapter.setOnClickListener(this.mOnClickItemListener);
        myViewHolder.rvListAudio.setAdapter(this.itemListDataAdapter);
    }

    @Override
    public int getItemCount() {
        return this.al_menu.size();
    }

    private int dpToPx(Context context, int i) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, i, context.getResources().getDisplayMetrics()));
    }

    public interface OnClickItemListener {
        void onClickItem(int i);
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
        private boolean includeEdge;
        private int spacing;
        private int spanCount;

        public GridSpacingItemDecoration(int i, int i2, boolean z) {
            this.spanCount = i;
            this.spacing = i2;
            this.includeEdge = z;
        }

        @Override
        public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
            int childAdapterPosition = recyclerView.getChildAdapterPosition(view);
            int i = this.spanCount;
            int i2 = childAdapterPosition % i;
            if (this.includeEdge) {
                int i3 = this.spacing;
                rect.left = i3 - ((i2 * i3) / i);
                rect.right = ((i2 + 1) * this.spacing) / this.spanCount;
                if (childAdapterPosition < this.spanCount) {
                    rect.top = this.spacing;
                }
                rect.bottom = this.spacing;
                return;
            }
            rect.left = (this.spacing * i2) / i;
            int i4 = this.spacing;
            rect.right = i4 - (((i2 + 1) * i4) / this.spanCount);
            if (childAdapterPosition >= this.spanCount) {
                rect.top = this.spacing;
            }
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        OnClickItemListener onClickItemListener;
        RecyclerView rvListAudio;
        TextView txtAlbumName;
        TextView txtTotalFile;

        public MyViewHolder(View view, OnClickItemListener onClickItemListener) {
            super(view);
            this.rvListAudio = (RecyclerView) view.findViewById(R.id.rvListAudio);
            this.txtAlbumName = (TextView) view.findViewById(R.id.txtAlbumName);
            this.txtTotalFile = (TextView) view.findViewById(R.id.txtTotalFile);
            this.onClickItemListener = onClickItemListener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            this.onClickItemListener.onClickItem(getAdapterPosition());
        }
    }
}
