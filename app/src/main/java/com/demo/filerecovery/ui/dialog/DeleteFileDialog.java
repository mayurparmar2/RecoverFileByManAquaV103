package com.demo.filerecovery.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.demo.filerecovery.R;

import java.io.File;
import java.util.List;

import kotlin.jvm.internal.Intrinsics;


public final class DeleteFileDialog extends Dialog {
    private DeleteFileDialogCallBack deleteFileDialogCallBack;
    private List<File> files;
    private Integer position;


    public DeleteFileDialog(Context context, List<File> list, Integer num) {
        super(context);
        this.files = list;
        this.position = num;
        setContentView(R.layout.dialog_delete);
    }

    public static final void m81onCreate$lambda0(DeleteFileDialog this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.dismiss();
    }

    public static final void m82onCreate$lambda1(DeleteFileDialog this$0, View view) {
        DeleteFileDialogCallBack deleteFileDialogCallBack;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.dismiss();
        List<File> list = this$0.files;
        if (list != null && !list.isEmpty()) {
            DeleteFileDialogCallBack deleteFileDialogCallBack2 = this$0.deleteFileDialogCallBack;
            if (deleteFileDialogCallBack2 != null) {
                List<File> list2 = this$0.files;
                Intrinsics.checkNotNull(list2);
                deleteFileDialogCallBack2.onDeleteFileCallbackInDialog(list2);
                return;
            }
            return;
        }
        Integer num = this$0.position;
        if (num == null || (deleteFileDialogCallBack = this$0.deleteFileDialogCallBack) == null) {
            return;
        }
        Intrinsics.checkNotNull(num);
        deleteFileDialogCallBack.onDeleteFileCallbackInDialogWithPosition(num.intValue());
    }

    public final DeleteFileDialogCallBack getDeleteFileDialogCallBack() {
        return this.deleteFileDialogCallBack;
    }

    public final void setDeleteFileDialogCallBack(DeleteFileDialogCallBack deleteFileDialogCallBack) {
        this.deleteFileDialogCallBack = deleteFileDialogCallBack;
    }

    public final void setTitleAndContentDialog(String title, String content) {
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(content, "content");
        ((TextView) findViewById(R.id.tvtTitle)).setText(title);
        ((TextView) findViewById(R.id.tvWarning)).setText(content);
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Window window = getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(0));
        }
        ((TextView) findViewById(R.id.tvCancel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                DeleteFileDialog.m81onCreate$lambda0(DeleteFileDialog.this, view);
            }
        });
        ((TextView) findViewById(R.id.tvDelete)).setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                DeleteFileDialog.m82onCreate$lambda1(DeleteFileDialog.this, view);
            }
        });
    }

    public interface DeleteFileDialogCallBack {
        void onDeleteFileCallbackInDialog(List<? extends File> list);

        void onDeleteFileCallbackInDialogWithPosition(int i);


        public static final class DefaultImpls {
            public static void onDeleteFileCallbackInDialog(DeleteFileDialogCallBack deleteFileDialogCallBack, List<? extends File> files) {
                Intrinsics.checkNotNullParameter(deleteFileDialogCallBack, "this");
                Intrinsics.checkNotNullParameter(files, "files");
            }

            public static void onDeleteFileCallbackInDialogWithPosition(DeleteFileDialogCallBack deleteFileDialogCallBack, int i) {
                Intrinsics.checkNotNullParameter(deleteFileDialogCallBack, "this");
            }
        }
    }
}
