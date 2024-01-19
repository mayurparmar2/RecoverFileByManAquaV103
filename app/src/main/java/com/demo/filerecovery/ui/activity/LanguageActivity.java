package com.demo.filerecovery.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.View;
import android.widget.ImageView;

import com.demo.filerecovery.Constants;
import com.demo.filerecovery.base.BaseActivity;
import com.demo.filerecovery.ext.ExtensionsKt;
import com.demo.filerecovery.model.LanguageModel;
import com.demo.filerecovery.ui.adapter.LanguageAdapter;
import com.demo.filerecovery.utilts.SharePreferenceUtils;
import com.demo.filerecovery.utilts.Utils;
import com.demo.filerecovery.R;
import com.demo.filerecovery.databinding.ActivityLanguageBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;


public final class LanguageActivity extends BaseActivity<ActivityLanguageBinding> implements LanguageAdapter.LanguageAdapterCallBack {
    public static final Companion Companion = new Companion(null);
    public static final List<String> countryName = CollectionsKt.mutableListOf("English", "Indonesia", "Portuguese", "Spanish", "India", "Turkey", "France", "Vietnamese", "Russian");
    public static final List<String> languageCode = CollectionsKt.mutableListOf("en", "in", "pt", "es", "hi", "tr", "fr", "vi", "ru");
    private LanguageModel languageModel;
    private int type;


    public LanguageActivity() {
        super(R.layout.activity_language);
    }

    public static final void m65initView$lambda0(LanguageActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
    }

    public static final void m66initView$lambda1(LanguageActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        LanguageModel languageModel = this$0.languageModel;
        if (languageModel != null) {
            if (this$0.type == 0) {
                SharePreferenceUtils.saveLanguage(this$0, languageModel != null ? languageModel.getLanguageCode() : null);
                Utils.setLocale(this$0);
                this$0.startActivity(new Intent(this$0, HelpActivity.class));
                this$0.finish();
                return;
            }
            SharePreferenceUtils.saveLanguage(this$0, languageModel == null ? null : languageModel.getLanguageCode());
            LanguageModel languageModel2 = this$0.languageModel;
            String languageCode2 = languageModel2 != null ? languageModel2.getLanguageCode() : null;
            Intrinsics.checkNotNull(languageCode2);
            this$0.setLocale(languageCode2);
        } else if (this$0.type == 0) {
            Utils.setLocale(this$0);
            this$0.startActivity(new Intent(this$0, HelpActivity.class));
            this$0.finish();
        } else {
            this$0.startActivity(new Intent(this$0, MainActivity.class));
            this$0.finish();
        }
    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        Integer valueOf = intent == null ? null : Integer.valueOf(intent.getIntExtra(Constants.FB_LOG_RESTORE_RESULT_TYPE, 0));
        Intrinsics.checkNotNull(valueOf);
        int intValue = valueOf.intValue();
        this.type = intValue;
        if (intValue == 0) {
            ImageView imageView = getBinding().imgBack;
            Intrinsics.checkNotNullExpressionValue(imageView, "binding.imgBack");
            ExtensionsKt.invisible(imageView);
        } else {
            ImageView imageView2 = getBinding().imgBack;
            Intrinsics.checkNotNullExpressionValue(imageView2, "binding.imgBack");
            ExtensionsKt.visible(imageView2);
        }
        getBinding().imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                LanguageActivity.m65initView$lambda0(LanguageActivity.this, view);
            }
        });
        getBinding().txtDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                LanguageActivity.m66initView$lambda1(LanguageActivity.this, view);
            }
        });
        initLanguageData();
    }

    private final void initLanguageData() {
        ArrayList arrayList = new ArrayList();
        String preferredLanguageCode = getPreferredLanguageCode();
        int size = languageCode.size();
        int i = 0;
        while (i < size) {
            int i2 = i + 1;
            List<String> list = languageCode;
            LanguageModel languageModel = new LanguageModel(i, countryName.get(i), list.get(i), false);
            if (Intrinsics.areEqual(list.get(i), preferredLanguageCode)) {
                arrayList.add(0, languageModel);
            } else {
                arrayList.add(languageModel);
            }
            i = i2;
        }
        ((LanguageModel) arrayList.get(0)).setSelected(true);
        LanguageAdapter languageAdapter = new LanguageAdapter(this, arrayList);
        languageAdapter.setCallback(this);
        getBinding().recyclerView.setAdapter(languageAdapter);
    }

    private final String getPreferredLanguageCode() {
        String userPreferred = SharePreferenceUtils.getLanguage(this);
        Intrinsics.checkNotNullExpressionValue(userPreferred, "userPreferred");
        if (userPreferred.length() != 0) {
            return userPreferred;
        }
        String language = Locale.getDefault().getLanguage();
        Intrinsics.checkNotNullExpressionValue(language, "getDefault().language");
        return language;
    }

    @SuppressLint("WrongConstant")
    private final void setLocale(String str) {
        Configuration configuration = new Configuration();
        Locale locale = new Locale(str);
        Locale.setDefault(locale);
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, null);
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(335577088);
        startActivity(intent);
        finish();
    }

    @Override
    public void onSelectLanguage(LanguageModel languageModel) {
        Intrinsics.checkNotNullParameter(languageModel, "languageModel");
        this.languageModel = languageModel;
    }

    public static final class Companion {
        public Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final List<String> getCountryName() {
            return LanguageActivity.countryName;
        }

        public final List<String> getLanguageCode() {
            return LanguageActivity.languageCode;
        }
    }
}
