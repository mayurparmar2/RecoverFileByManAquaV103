package com.demo.filerecovery.model.modul.recoveryaudio.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.filerecovery.R;
import com.demo.filerecovery.model.modul.recoveryaudio.Model.AudioModel;
import com.demo.filerecovery.utilts.Utils;

import java.text.DateFormat;
import java.util.ArrayList;


public class SectionListAudioAdapter extends RecyclerView.Adapter<SectionListAudioAdapter.SingleItemRowHolder> {
    public AlbumsAudioAdapter.OnClickItemListener onClickListener;
    int postion;
    int size;
    private ArrayList<AudioModel> itemsList = new ArrayList<>();
    private Context mContext;


    public SectionListAudioAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(ArrayList<AudioModel> arrayList, int i) {
        this.itemsList.clear();
        this.itemsList.addAll(arrayList);
        this.postion = i;
        if (arrayList.size() >= 5) {
            this.size = 5;
        } else {
            this.size = arrayList.size();
        }
        notifyDataSetChanged();
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new SingleItemRowHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_audio_album, (ViewGroup) null));
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder singleItemRowHolder, int i) {
        AudioModel audioModel = this.itemsList.get(i);
        singleItemRowHolder.tvTitle.setText(Utils.getFileTitle(audioModel.getPathPhoto()));
        singleItemRowHolder.album_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SectionListAudioAdapter.this.onClickListener.onClickItem(SectionListAudioAdapter.this.postion);
            }
        });
        String format = DateFormat.getDateInstance().format(Long.valueOf(audioModel.getLastModified()));
        String formatSize = Utils.formatSize(audioModel.getSizePhoto());
        TextView textView = singleItemRowHolder.txtDateAndSize;
        textView.setText(format + "  " + formatSize);
    }

    @Override
    public int getItemCount() {
        return this.size;
    }

    public void setOnClickListener(AlbumsAudioAdapter.OnClickItemListener onClickItemListener) {
        this.onClickListener = onClickItemListener;
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {
        CardView album_card;
        TextView tvTitle;
        TextView txtDateAndSize;

        public SingleItemRowHolder(View view) {
            super(view);
            this.album_card = (CardView) view.findViewById(R.id.album_card);
            this.tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            this.txtDateAndSize = (TextView) view.findViewById(R.id.txtDateAndSize);
        }
    }
}
