package com.demo.filerecovery.ui.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.filerecovery.App;
import com.demo.filerecovery.Constants;
import com.demo.filerecovery.R;
import com.demo.filerecovery.model.modul.recoveryvideo.Model.VideoModel;
import com.demo.filerecovery.ui.adapter.RestoreFileAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;


public class RestoredVideoFrg extends Fragment {
    RestoreFileAdapter adapter;
    RestoreFileAdapter.OnItemStateChange itemStateChange;
    ArrayList<VideoModel> mList = new ArrayList<>();
    RecyclerView recyclerView;
    private LinearLayout layoutEmpty;


    public RestoredVideoFrg(RestoreFileAdapter.OnItemStateChange onItemStateChange) {
        this.itemStateChange = onItemStateChange;
    }

    static int lambda$sortListByDate$1(boolean z, VideoModel videoModel, VideoModel videoModel2) {
        if (z) {
            return Long.valueOf(videoModel2.getLastModified()).compareTo(Long.valueOf(videoModel.getLastModified()));
        }
        return Long.valueOf(videoModel.getLastModified()).compareTo(Long.valueOf(videoModel2.getLastModified()));
    }

    static int lambda$sortListByName$2(boolean z, VideoModel videoModel, VideoModel videoModel2) {
        if (z) {
            return videoModel2.getPathPhoto().substring(videoModel2.getPathPhoto().lastIndexOf("/") + 1).compareTo(videoModel.getPathPhoto().substring(videoModel.getPathPhoto().lastIndexOf("/") + 1));
        }
        return videoModel.getPathPhoto().substring(videoModel.getPathPhoto().lastIndexOf("/") + 1).compareTo(videoModel2.getPathPhoto().substring(videoModel2.getPathPhoto().lastIndexOf("/") + 1));
    }

    static int lambda$sortListBySize$3(boolean z, VideoModel videoModel, VideoModel videoModel2) {
        if (z) {
            return Long.valueOf(videoModel2.getSizePhoto()).compareTo(Long.valueOf(videoModel.getSizePhoto()));
        }
        return Long.valueOf(videoModel.getSizePhoto()).compareTo(Long.valueOf(videoModel2.getSizePhoto()));
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_restored, viewGroup, false);
        intView(inflate);
        intData();
        this.layoutEmpty = (LinearLayout) inflate.findViewById(R.id.viewEmpty);
        if (this.mList.size() > 0) {
            this.layoutEmpty.setVisibility(View.GONE);
        }
        return inflate;
    }

    public void intData() {
        this.mList = App.getInstance().getStorageCommon().getListVideo();
        RestoreFileAdapter restoreFileAdapter = new RestoreFileAdapter(this.itemStateChange, new RestoreFileAdapter.OnDataChanged() {
            @Override
            public final void onDataEmpty() {
                RestoredVideoFrg.this.lambda$intData$0$RestoredVideoFrg();
            }
        }, requireContext());
        this.adapter = restoreFileAdapter;
        restoreFileAdapter.setFileType(1);
        this.adapter.setDataVideo(this.mList);
        this.adapter.getFileType();
        this.recyclerView.setAdapter(this.adapter);
        if (this.mList.size() == 0) {
            this.layoutEmpty.setVisibility(View.VISIBLE);
        } else {
            this.layoutEmpty.setVisibility(View.GONE);
        }
    }

    public void lambda$intData$0$RestoredVideoFrg() {
        this.layoutEmpty.setVisibility(View.VISIBLE);
    }

    public void resetAdapter() {
        this.adapter.setShowCheckBox(false);
        Iterator<VideoModel> it = this.mList.iterator();
        while (it.hasNext()) {
            it.next().setIsCheck(false);
        }
        this.adapter.notifyDataSetChanged();
        if (this.mList.size() == 0) {
            this.layoutEmpty.setVisibility(View.VISIBLE);
        } else {
            this.layoutEmpty.setVisibility(View.GONE);
        }
    }

    public void setDataAdapter(ArrayList<VideoModel> arrayList) {
        this.adapter.setDataVideo(arrayList);
    }

    public void intView(View view) {
        this.recyclerView = (RecyclerView) view.findViewById(R.id.gv_folder);
        this.layoutEmpty = (LinearLayout) view.findViewById(R.id.viewEmpty);
        this.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        this.recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(5), true));
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    public void displayEmptyText() {
        this.layoutEmpty.setVisibility(View.VISIBLE);
    }

    private int dpToPx(int i) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, i, getResources().getDisplayMetrics()));
    }

    public void sortList(String str) {
        char c;
        switch (str.hashCode()) {
            case -1699274633:
                if (str.equals(Constants.SORT_BY_TIME_NEWEST)) {
                    c = 5;
                    break;
                }
            case -1664746864:
                if (str.equals(Constants.SORT_BY_TIME_OLDEST)) {
                    c = 4;
                    break;
                }
            case -1644940831:
                if (str.equals(Constants.SORT_BY_NAME_A_TO_Z)) {
                    c = 0;
                    break;
                }
            case -1322418300:
                if (str.equals(Constants.SORT_BY_SIZE_MIN_TO_MAX)) {
                    c = 3;
                    break;
                }
            case -929212081:
                if (str.equals(Constants.SORT_BY_NAME_Z_TO_A)) {
                    c = 1;
                    break;
                }
            case 500549920:
                if (str.equals(Constants.SORT_BY_SIZE_MAX_TO_MIN)) {
                    c = 2;
                    break;
                }
            default:
                c = 65535;
                break;
        }
        if (c == 0) {
            sortListByName(this.mList, false);
        } else if (c == 1) {
            sortListByName(this.mList, true);
        } else if (c == 2) {
            sortListBySize(this.mList, true);
        } else if (c == 3) {
            sortListBySize(this.mList, false);
        } else if (c == 4) {
            sortListByDate(this.mList, false);
        } else if (c == 5) {
            sortListByDate(this.mList, true);
        }
    }

    private void sortListByDate(ArrayList<VideoModel> arrayList, final boolean z) {
        Collections.sort(arrayList, new Comparator() {
            @Override
            public final int compare(Object obj, Object obj2) {
                return RestoredVideoFrg.lambda$sortListByDate$1(z, (VideoModel) obj, (VideoModel) obj2);
            }
        });
        this.adapter.setDataVideo(arrayList);
    }

    private void sortListByName(ArrayList<VideoModel> arrayList, final boolean z) {
        Collections.sort(arrayList, new Comparator() {
            @Override
            public final int compare(Object obj, Object obj2) {
                return RestoredVideoFrg.lambda$sortListByName$2(z, (VideoModel) obj, (VideoModel) obj2);
            }
        });
        this.adapter.setDataVideo(arrayList);
    }

    private void sortListBySize(ArrayList<VideoModel> arrayList, final boolean z) {
        Collections.sort(arrayList, new Comparator() {
            @Override
            public final int compare(Object obj, Object obj2) {
                return RestoredVideoFrg.lambda$sortListBySize$3(z, (VideoModel) obj, (VideoModel) obj2);
            }
        });
        this.adapter.setDataVideo(arrayList);
    }

    public static class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
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
}
