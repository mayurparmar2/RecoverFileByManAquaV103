package com.demo.filerecovery.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.demo.filerecovery.R;
import com.demo.filerecovery.model.SquareImageView;
import com.demo.filerecovery.utilts.Utils;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.ArrayList;


public class ItemRestoredAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int MENU_ITEM_VIEW_TYPE = 0;
    private static final int UNIFIED_NATIVE_AD_VIEW_TYPE = 1;
    private Context context;
    private OnListFileRestoredListener listener;
    private int typeMedia;
    private ArrayList<Object> listFileAndAds = new ArrayList<>();
    private ArrayList<String> listPathSelect = new ArrayList<>();
    private ArrayList<Integer> nativePositionList = new ArrayList<>();
    private int posCurrentAdNative = 0;


    public ItemRestoredAdapter(Context context, OnListFileRestoredListener onListFileRestoredListener) {
        this.context = context;
        this.listener = onListFileRestoredListener;
    }

    public void setTypeMedia(int i) {
        this.typeMedia = i;
    }

    public void setNativePositionList(ArrayList<Integer> arrayList) {
        this.nativePositionList = arrayList;
        notifyDataSetChanged();
    }

    public ArrayList<Object> getListFileAndAds() {
        return this.listFileAndAds;
    }

    public void setListFileAndAds(ArrayList<Object> arrayList) {
        this.listFileAndAds.clear();
        this.listFileAndAds = arrayList;
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_restored_media, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        int itemViewType = getItemViewType(i);
        char c = '\b';
        final MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        if (this.listFileAndAds.size() == 1) {
            myViewHolder.checkBox.setVisibility(View.GONE);
        }
        final String str = (String) this.listFileAndAds.get(i);
        int i2 = this.typeMedia;
        if (i2 == 2) {
            Glide.with(this.context).load(Integer.valueOf((int) R.drawable.ic_action_thum_audio)).diskCacheStrategy(DiskCacheStrategy.ALL).priority(Priority.HIGH).centerCrop().error(R.drawable.ic_error).into(myViewHolder.imgThumbnail);
        } else if (i2 == 0 || i2 == 1) {
            try {
                RequestManager with = Glide.with(this.context);
                with.load("file://" + str).diskCacheStrategy(DiskCacheStrategy.ALL).priority(Priority.HIGH).centerCrop().error(R.drawable.ic_error).into(myViewHolder.imgThumbnail);
            } catch (Exception e) {
                Context context = this.context;
                Toast.makeText(context, "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            if (this.typeMedia == 1) {
                myViewHolder.imgPlay.setVisibility(View.VISIBLE);
            }
        } else {
            String extension = FilenameUtils.getExtension(str);
            int i3 = -1;
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
                    break;
                case 3447940:
                    break;
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
            }
            myViewHolder.imgThumbnail.setImageDrawable(ContextCompat.getDrawable(this.context, i3));
        }
        myViewHolder.txtSizeFile.setText(Utils.formatSize(new File(str).length()));
        myViewHolder.txtNameFile.setText(FilenameUtils.getName((String) this.listFileAndAds.get(i)));
        myViewHolder.checkBox.setChecked(this.listPathSelect.contains(str));
        myViewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                ItemRestoredAdapter.this.lambda$onBindViewHolder$0$ItemRestoredAdapter(myViewHolder, str, view);
            }
        });
    }

    public void lambda$onBindViewHolder$0$ItemRestoredAdapter(MyViewHolder myViewHolder, String str, View view) {
        if (myViewHolder.checkBox.isChecked()) {
            this.listPathSelect.add(str);
        } else {
            this.listPathSelect.remove(str);
        }
        this.listener.onListFileChange(this.listPathSelect);
    }


    @Override
    public int getItemCount() {
        return this.listFileAndAds.size();
    }

    public interface OnListFileRestoredListener {
        void onListFileChange(ArrayList<String> arrayList);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        ImageView imgPlay;
        SquareImageView imgThumbnail;
        TextView txtNameFile;
        TextView txtSizeFile;

        public MyViewHolder(View view) {
            super(view);
            this.imgThumbnail = (SquareImageView) view.findViewById(R.id.imgThumbnail);
            this.checkBox = (CheckBox) view.findViewById(R.id.checkbox);
            this.txtNameFile = (TextView) view.findViewById(R.id.txtNameFile);
            this.txtSizeFile = (TextView) view.findViewById(R.id.txtSizeFile);
            this.imgPlay = (ImageView) view.findViewById(R.id.imgPlay);
        }
    }
}
