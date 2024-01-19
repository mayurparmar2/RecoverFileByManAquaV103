package com.demo.filerecovery.ui.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.demo.filerecovery.R;


public class ScanDoneDialog extends Dialog {
    public ICallback callback;


    @SuppressLint("ResourceType")
    public ScanDoneDialog(Context context) {
        super(context);
        setContentView(R.layout.dialog_scan_done);
        getWindow().getDecorView().setBackgroundResource(17170445);
        findViewById(R.id.ln_restore).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScanDoneDialog.this.findViewById(R.id.ln_restore).setEnabled(false);
                ScanDoneDialog.this.callback.onCancel();
            }
        });
        findViewById(R.id.ln_upgrade).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScanDoneDialog.this.findViewById(R.id.ln_upgrade).setEnabled(false);
                ScanDoneDialog.this.callback.onDone();
            }
        });
    }

    public void setCallback(ICallback iCallback) {
        this.callback = iCallback;
    }

    public void setNumFiles(int i) {
        String string = getContext().getString(R.string.txt_s_files_found);
        ((TextView) findViewById(R.id.tvNumFile)).setText(String.format(string, i + ""));
    }

    public interface ICallback {
        void onCancel();

        void onDone();
    }
}
