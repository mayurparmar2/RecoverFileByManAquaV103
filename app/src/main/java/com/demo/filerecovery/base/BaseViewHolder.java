package com.demo.filerecovery.base;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;


public class BaseViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {
    private final T binding;

    public BaseViewHolder(T t) {
        super(t.getRoot());
        this.binding = t;
    }

    public final T getBinding() {
        return this.binding;
    }
}
