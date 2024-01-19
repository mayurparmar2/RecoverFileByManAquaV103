package com.demo.filerecovery.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.filerecovery.App;
import com.demo.filerecovery.Constants;
import com.demo.filerecovery.model.modul.recoverydocument.Model.DocumentModel;
import com.demo.filerecovery.ui.adapter.RestoreFileAdapter;
import com.demo.filerecovery.utilts.Utils;
import com.demo.filerecovery.R;
import com.demo.filerecovery.databinding.FragmentRestoredBinding;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;


public final class RestoredDocumentsFrg extends Fragment {
    private final RestoreFileAdapter.OnItemStateChange itemStateChange;
    public FragmentRestoredBinding binding;
    private RestoreFileAdapter adapter;
    private ArrayList<DocumentModel> listDocument = new ArrayList<>();

    public RestoredDocumentsFrg(RestoreFileAdapter.OnItemStateChange itemStateChange) {
        Intrinsics.checkNotNullParameter(itemStateChange, "itemStateChange");
        this.itemStateChange = itemStateChange;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        ViewDataBinding inflate = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_restored, viewGroup, false);
        Intrinsics.checkNotNullExpressionValue(inflate, "inflate(layoutInflater, …stored, container, false)");
        this.binding = (FragmentRestoredBinding) inflate;
        RestoreFileAdapter.OnDataChanged onDataChanged = new RestoreFileAdapter.OnDataChanged() {


            @Override
            public void onDataEmpty() {
                if (binding == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                    binding = null;
                }
                binding.viewEmptyParent.setVisibility(View.VISIBLE);
            }
        };
        RestoreFileAdapter.OnItemStateChange onItemStateChange = this.itemStateChange;
        Context requireContext = requireContext();
        Intrinsics.checkNotNullExpressionValue(requireContext, "requireContext()");
        RestoreFileAdapter restoreFileAdapter = new RestoreFileAdapter(onItemStateChange, onDataChanged, requireContext);
        this.adapter = restoreFileAdapter;
        restoreFileAdapter.setFileType(3);
        ArrayList<DocumentModel> listDocument = App.getInstance().getStorageCommon().getListDocument();
        Intrinsics.checkNotNullExpressionValue(listDocument, "getInstance().storageCommon.listDocument");
        this.listDocument = listDocument;
        FragmentRestoredBinding fragmentRestoredBinding = null;
        if (listDocument.size() == 0) {
            FragmentRestoredBinding fragmentRestoredBinding2 = this.binding;
            if (fragmentRestoredBinding2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                fragmentRestoredBinding2 = null;
            }
            fragmentRestoredBinding2.viewEmptyParent.setVisibility(View.VISIBLE);
        } else {
            FragmentRestoredBinding fragmentRestoredBinding3 = this.binding;
            if (fragmentRestoredBinding3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                fragmentRestoredBinding3 = null;
            }
            fragmentRestoredBinding3.viewEmptyParent.setVisibility(View.GONE);
        }
        RestoreFileAdapter restoreFileAdapter2 = this.adapter;
        if (restoreFileAdapter2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            restoreFileAdapter2 = null;
        }
        restoreFileAdapter2.setDataDocument(this.listDocument);
        FragmentRestoredBinding fragmentRestoredBinding4 = this.binding;
        if (fragmentRestoredBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentRestoredBinding4 = null;
        }
        RecyclerView recyclerView = fragmentRestoredBinding4.gvFolder;
        RestoreFileAdapter restoreFileAdapter3 = this.adapter;
        if (restoreFileAdapter3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            restoreFileAdapter3 = null;
        }
        recyclerView.setAdapter(restoreFileAdapter3);
        FragmentRestoredBinding fragmentRestoredBinding5 = this.binding;
        if (fragmentRestoredBinding5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            fragmentRestoredBinding = fragmentRestoredBinding5;
        }
        View root = fragmentRestoredBinding.getRoot();
        Intrinsics.checkNotNullExpressionValue(root, "binding.root");
        return root;
    }

    public final void resetAdapter() {
        RestoreFileAdapter restoreFileAdapter = this.adapter;
        FragmentRestoredBinding fragmentRestoredBinding = null;
        if (restoreFileAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            restoreFileAdapter = null;
        }
        restoreFileAdapter.setShowCheckBox(false);
        Iterator<DocumentModel> it = this.listDocument.iterator();
        while (it.hasNext()) {
            it.next().setSelected(false);
        }
        RestoreFileAdapter restoreFileAdapter2 = this.adapter;
        if (restoreFileAdapter2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            restoreFileAdapter2 = null;
        }
        restoreFileAdapter2.notifyDataSetChanged();
        if (this.listDocument.size() == 0) {
            FragmentRestoredBinding fragmentRestoredBinding2 = this.binding;
            if (fragmentRestoredBinding2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
            } else {
                fragmentRestoredBinding = fragmentRestoredBinding2;
            }
            fragmentRestoredBinding.viewEmptyParent.setVisibility(View.VISIBLE);
            return;
        }
        FragmentRestoredBinding fragmentRestoredBinding3 = this.binding;
        if (fragmentRestoredBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            fragmentRestoredBinding = fragmentRestoredBinding3;
        }
        fragmentRestoredBinding.viewEmptyParent.setVisibility(View.GONE);
    }

    public final void displayEmptyText() {
        FragmentRestoredBinding fragmentRestoredBinding = this.binding;
        if (fragmentRestoredBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentRestoredBinding = null;
        }
        fragmentRestoredBinding.viewEmptyParent.setVisibility(View.VISIBLE);
    }

    public final void setDataAdapter(ArrayList<DocumentModel> listDocument) {
        Intrinsics.checkNotNullParameter(listDocument, "listDocument");
        RestoreFileAdapter restoreFileAdapter = this.adapter;
        if (restoreFileAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            restoreFileAdapter = null;
        }
        restoreFileAdapter.setDataDocument(listDocument);
    }

    public final void sortList(String styleSort) {
        Intrinsics.checkNotNullParameter(styleSort, "styleSort");
        switch (styleSort.hashCode()) {
            case -1699274633:
                if (styleSort.equals(Constants.SORT_BY_TIME_NEWEST)) {
                    CollectionsKt.sortWith(this.listDocument, new Comparator() {
                        @Override
                        public final int compare(Object obj, Object obj2) {
                            int compare;
                            compare = Intrinsics.compare(((DocumentModel) obj2).getLastModified(), ((DocumentModel) obj).getLastModified());
                            return compare;
                        }
                    });
                    break;
                }
                break;
            case -1664746864:
                if (styleSort.equals(Constants.SORT_BY_TIME_OLDEST)) {
                    CollectionsKt.sortWith(this.listDocument, new Comparator() {
                        @Override
                        public final int compare(Object obj, Object obj2) {
                            int compare;
                            compare = Intrinsics.compare(((DocumentModel) obj).getLastModified(), ((DocumentModel) obj2).getLastModified());
                            return compare;
                        }
                    });
                    break;
                }
                break;
            case -1644940831:
                if (styleSort.equals(Constants.SORT_BY_NAME_A_TO_Z)) {
                    CollectionsKt.sortWith(this.listDocument, new Comparator() {
                        @Override
                        public final int compare(Object obj, Object obj2) {
                            int compareFileName;
                            compareFileName = Utils.compareFileName(((DocumentModel) obj).getPathDocument(), ((DocumentModel) obj2).getPathDocument(), 1);
                            return compareFileName;
                        }
                    });
                    break;
                }
                break;
            case -1322418300:
                if (styleSort.equals(Constants.SORT_BY_SIZE_MIN_TO_MAX)) {
                    CollectionsKt.sortWith(this.listDocument, new Comparator() {
                        @Override
                        public final int compare(Object obj, Object obj2) {
                            int compare;
                            compare = Intrinsics.compare(((DocumentModel) obj).getSizeDocument(), ((DocumentModel) obj2).getSizeDocument());
                            return compare;
                        }
                    });
                    break;
                }
                break;
            case -929212081:
                if (styleSort.equals(Constants.SORT_BY_NAME_Z_TO_A)) {
                    CollectionsKt.sortWith(this.listDocument, new Comparator() {
                        @Override
                        public final int compare(Object obj, Object obj2) {
                            int compareFileName;
                            compareFileName = Utils.compareFileName(((DocumentModel) obj).getPathDocument(), ((DocumentModel) obj2).getPathDocument(), 2);
                            return compareFileName;
                        }
                    });
                    break;
                }
                break;
            case 500549920:
                if (styleSort.equals(Constants.SORT_BY_SIZE_MAX_TO_MIN)) {
                    CollectionsKt.sortWith(this.listDocument, new Comparator() {
                        @Override
                        public final int compare(Object obj, Object obj2) {
                            int compare;
                            compare = Intrinsics.compare(((DocumentModel) obj2).getSizeDocument(), ((DocumentModel) obj).getSizeDocument());
                            return compare;
                        }
                    });
                    break;
                }
                break;
        }
        RestoreFileAdapter restoreFileAdapter = this.adapter;
        if (restoreFileAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            restoreFileAdapter = null;
        }
        restoreFileAdapter.setDataDocument(this.listDocument);
    }
}
