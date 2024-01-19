package com.demo.filerecovery.model.modul.fileprotection;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.demo.filerecovery.R;
import com.demo.filerecovery.databinding.LayoutBottomsheetFileProtectionBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.File;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;


public final class BottomSheetFileProtection extends BottomSheetDialogFragment {
    public static final Companion Companion = new Companion(null);
    public static final String TAG = "BottomSheetFileProtection";
    private final BottomSheetFileProtectionCallback bottomSheetFileProtectionCallback;
    private final File file;
    private LayoutBottomsheetFileProtectionBinding binding;


    public BottomSheetFileProtection(File file, BottomSheetFileProtectionCallback bottomSheetFileProtectionCallback) {
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(bottomSheetFileProtectionCallback, "bottomSheetFileProtectionCallback");
        this.file = file;
        this.bottomSheetFileProtectionCallback = bottomSheetFileProtectionCallback;
    }

    public static final void m11initView$lambda0(BottomSheetFileProtection this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.dismiss();
        this$0.bottomSheetFileProtectionCallback.onRestoreFile(this$0.file);
    }

    public static final void m12initView$lambda1(BottomSheetFileProtection this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.dismiss();
        this$0.bottomSheetFileProtectionCallback.onDelete(this$0.file);
    }

    @Override
    public int getTheme() {
        return R.style.CustomBottomSheetDialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        this.binding = LayoutBottomsheetFileProtectionBinding.inflate(LayoutInflater.from(getContext()));
        initView();
        LayoutBottomsheetFileProtectionBinding layoutBottomsheetFileProtectionBinding = this.binding;
        Intrinsics.checkNotNull(layoutBottomsheetFileProtectionBinding);
        LinearLayout root = layoutBottomsheetFileProtectionBinding.getRoot();
        Intrinsics.checkNotNullExpressionValue(root, "binding!!.root");
        return root;
    }

    private final void initView() {
        LayoutBottomsheetFileProtectionBinding layoutBottomsheetFileProtectionBinding = this.binding;
        Intrinsics.checkNotNull(layoutBottomsheetFileProtectionBinding);
        layoutBottomsheetFileProtectionBinding.tvtRestoreFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                BottomSheetFileProtection.m11initView$lambda0(BottomSheetFileProtection.this, view);
            }
        });
        LayoutBottomsheetFileProtectionBinding layoutBottomsheetFileProtectionBinding2 = this.binding;
        Intrinsics.checkNotNull(layoutBottomsheetFileProtectionBinding2);
        layoutBottomsheetFileProtectionBinding2.tvtDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                BottomSheetFileProtection.m12initView$lambda1(BottomSheetFileProtection.this, view);
            }
        });
    }

    public interface BottomSheetFileProtectionCallback {
        void onDelete(File file);

        void onRestoreFile(File file);
    }

    public static final class Companion {
        public Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
