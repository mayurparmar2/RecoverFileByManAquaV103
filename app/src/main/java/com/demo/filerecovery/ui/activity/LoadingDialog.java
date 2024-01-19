package com.demo.filerecovery.ui.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;

import com.demo.filerecovery.R;


public class LoadingDialog extends Dialog {
    private Context mContext;

    @SuppressLint("ResourceType")
    public LoadingDialog(Context context) {
        super(context, 2131952156);
        this.mContext = context;
        requestWindowFeature(1);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.layout_loading_dialog);
    }
}
