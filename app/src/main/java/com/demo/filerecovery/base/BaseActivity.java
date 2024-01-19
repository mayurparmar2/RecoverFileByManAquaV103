package com.demo.filerecovery.base;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.demo.filerecovery.utilts.Utils;


public abstract class BaseActivity<DB extends ViewDataBinding> extends AppCompatActivity {
    private final int layout;
    private DB binding;
//    private final Lazy binding$delegate = LazyKt.lazy(new Function0(this) {
//
//
//        @Override
//        public Object invoke() {
//            return DataBindingUtil.setContentView(baseActivity, baseActivity.getLayout());
//        }
//    });

    public BaseActivity(int i) {
        this.layout = i;
    }

    public abstract void initView();

    public final int getLayout() {
        return this.layout;
    }

    public final DB getBinding() {

        return binding;
    }

    @Override
    public void onCreate(Bundle bundle) {
        Utils.setLocale(this);
        super.onCreate(bundle);
        Utils.setStatusBarGradiant(this);
        binding = DataBindingUtil.setContentView(this, layout);
//        onBound(binding);
//        setupBindingLifecycleOwner();
        initView();
    }

//    private final void setupBindingLifecycleOwner() {
//        getBinding().setLifecycleOwner(this);
//    }
}
