package com.demo.filerecovery.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import com.demo.filerecovery.R;


public class RewardDialog extends Dialog {
    public ICallback callback;


    public RewardDialog(Context context) {
        super(context);
        setContentView(R.layout.dialog_reward);
        getWindow().getDecorView().setBackgroundResource(17170445);
        findViewById(R.id.ln_restore).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RewardDialog.this.callback.onRestore();
            }
        });
        findViewById(R.id.ln_upgrade).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RewardDialog.this.callback.onUpgrade();
            }
        });
    }

    public void setCallback(ICallback iCallback) {
        this.callback = iCallback;
    }

    public interface ICallback {
        void onRestore();

        void onUpgrade();
    }
}
