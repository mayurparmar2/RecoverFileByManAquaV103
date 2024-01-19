package com.demo.filerecovery.ext;

import android.view.View;

import kotlin.jvm.internal.Intrinsics;


public final class ExtensionsKt {
    public static final void visible(View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        view.setVisibility(View.VISIBLE);
    }

    public static final void invisible(View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        view.setVisibility(View.INVISIBLE);
    }

    public static final void gone(View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        view.setVisibility(View.GONE);
    }
}
