package com.demo.filerecovery.model;

import kotlin.jvm.internal.Intrinsics;


public final class LanguageModel {
    private int id;
    private boolean isSelected;
    private String languageCode;
    private String name;

    public LanguageModel(int i, String str, String str2, boolean z) {
        this.id = i;
        this.name = str;
        this.languageCode = str2;
        this.isSelected = z;
    }

    public final int getId() {
        return this.id;
    }

    public final void setId(int i) {
        this.id = i;
    }

    public final String getName() {
        return this.name;
    }

    public final void setName(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.name = str;
    }

    public final String getLanguageCode() {
        return this.languageCode;
    }

    public final void setLanguageCode(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.languageCode = str;
    }

    public final boolean isSelected() {
        return this.isSelected;
    }

    public final void setSelected(boolean z) {
        this.isSelected = z;
    }

    public String toString() {
        return "LanguageModel(id=" + this.id + ", name='" + this.name + "', languageCode='" + this.languageCode + "', isSelected=" + this.isSelected + ')';
    }
}
