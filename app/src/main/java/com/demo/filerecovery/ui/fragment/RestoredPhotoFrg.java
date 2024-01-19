package com.demo.filerecovery.ui.fragment;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.filerecovery.App;
import com.demo.filerecovery.Constants;
import com.demo.filerecovery.model.modul.recoveryphoto.Model.PhotoModel;
import com.demo.filerecovery.ui.adapter.RestoreFileAdapter;
import com.demo.filerecovery.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;




public class RestoredPhotoFrg extends Fragment {
    RestoreFileAdapter adapter;
    ArrayList<PhotoModel> mList = new ArrayList<>();
    RecyclerView recyclerView;
    LinearLayout viewEmpty;
    private RestoreFileAdapter.OnItemStateChange itemStateChange;

    public RestoredPhotoFrg(RestoreFileAdapter.OnItemStateChange onItemStateChange) {
        this.itemStateChange = onItemStateChange;
    }

    static int lambda$sortListByDate$1(boolean z, PhotoModel photoModel, PhotoModel photoModel2) {
        if (z) {
            return Long.valueOf(photoModel2.getLastModified()).compareTo(Long.valueOf(photoModel.getLastModified()));
        }
        return Long.valueOf(photoModel.getLastModified()).compareTo(Long.valueOf(photoModel2.getLastModified()));
    }

    static int lambda$sortListByName$2(boolean z, PhotoModel photoModel, PhotoModel photoModel2) {
        if (z) {
            return photoModel2.getPathPhoto().substring(photoModel2.getPathPhoto().lastIndexOf("/") + 1).compareTo(photoModel.getPathPhoto().substring(photoModel.getPathPhoto().lastIndexOf("/") + 1));
        }
        return photoModel.getPathPhoto().substring(photoModel.getPathPhoto().lastIndexOf("/") + 1).compareTo(photoModel2.getPathPhoto().substring(photoModel2.getPathPhoto().lastIndexOf("/") + 1));
    }

    static int lambda$sortListBySize$3(boolean z, PhotoModel photoModel, PhotoModel photoModel2) {
        if (z) {
            return Long.valueOf(photoModel2.getSizePhoto()).compareTo(Long.valueOf(photoModel.getSizePhoto()));
        }
        return Long.valueOf(photoModel.getSizePhoto()).compareTo(Long.valueOf(photoModel2.getSizePhoto()));
    }

    public void displayEmptyText() {
        this.viewEmpty.setVisibility(View.VISIBLE);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_restored, viewGroup, false);
        intView(inflate);
        intData();
        return inflate;
    }

    public void intView(View view) {
        this.recyclerView = (RecyclerView) view.findViewById(R.id.gv_folder);
        this.viewEmpty = (LinearLayout) view.findViewById(R.id.viewEmpty);
    }

    public void intData() {
        ArrayList<PhotoModel> listPhoto = App.getInstance().getStorageCommon().getListPhoto();
        this.mList = listPhoto;
        if (listPhoto.size() == 0) {
            this.viewEmpty.setVisibility(View.VISIBLE);
        } else {
            this.viewEmpty.setVisibility(View.GONE);
        }
        RestoreFileAdapter restoreFileAdapter = new RestoreFileAdapter(this.itemStateChange, new RestoreFileAdapter.OnDataChanged() {
            @Override
            public final void onDataEmpty() {
                RestoredPhotoFrg.this.lambda$intData$0$RestoredPhotoFrg();
            }
        }, getContext());
        this.adapter = restoreFileAdapter;
        restoreFileAdapter.setFileType(0);
        this.adapter.setDataPhoto(this.mList);
        this.recyclerView.setAdapter(this.adapter);
    }

    public void lambda$intData$0$RestoredPhotoFrg() {
        this.viewEmpty.setVisibility(View.VISIBLE);
    }

    public void resetAdapter() {
        this.adapter.setShowCheckBox(false);
        Iterator<PhotoModel> it = this.mList.iterator();
        while (it.hasNext()) {
            it.next().setIsCheck(false);
        }
        this.adapter.notifyDataSetChanged();
        if (this.mList.size() == 0) {
            this.viewEmpty.setVisibility(View.VISIBLE);
        } else {
            this.viewEmpty.setVisibility(View.GONE);
        }
    }

    public void setDataAdapter(ArrayList<PhotoModel> arrayList) {
        this.adapter.setDataPhoto(arrayList);
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

    private void sortListByDate(ArrayList<PhotoModel> arrayList, final boolean z) {
        Collections.sort(arrayList, new Comparator() {
            @Override
            public final int compare(Object obj, Object obj2) {
                return RestoredPhotoFrg.lambda$sortListByDate$1(z, (PhotoModel) obj, (PhotoModel) obj2);
            }
        });
        this.adapter.setDataPhoto(arrayList);
    }

    private void sortListByName(ArrayList<PhotoModel> arrayList, final boolean z) {
        Collections.sort(arrayList, new Comparator() {
            @Override
            public final int compare(Object obj, Object obj2) {
                return RestoredPhotoFrg.lambda$sortListByName$2(z, (PhotoModel) obj, (PhotoModel) obj2);
            }
        });
        this.adapter.setDataPhoto(arrayList);
    }

    private void sortListBySize(ArrayList<PhotoModel> arrayList, final boolean z) {
        Collections.sort(arrayList, new Comparator() {
            @Override
            public final int compare(Object obj, Object obj2) {
                return RestoredPhotoFrg.lambda$sortListBySize$3(z, (PhotoModel) obj, (PhotoModel) obj2);
            }
        });
        this.adapter.setDataPhoto(arrayList);
    }
}
