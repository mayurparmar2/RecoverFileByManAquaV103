package com.demo.filerecovery.ui.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.demo.filerecovery.R;

import java.util.List;


public class ViewPagerAdapter extends FragmentPagerAdapter {
    List<Fragment> fragmentList;
    private Context context;

    public ViewPagerAdapter(FragmentManager fragmentManager, Context context, List<Fragment> list) {
        super(fragmentManager);
        this.context = context;
        this.fragmentList = list;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Fragment getItem(int i) {
        if (i == 0) {
            return this.fragmentList.get(0);
        }
        if (i == 1) {
            return this.fragmentList.get(1);
        }
        if (i != 2) {
            return this.fragmentList.get(3);
        }
        return this.fragmentList.get(2);
    }

    @Override
    public CharSequence getPageTitle(int i) {
        if (i == 0) {
            return this.context.getString(R.string.photos);
        }
        if (i == 1) {
            return this.context.getString(R.string.videos);
        }
        if (i != 2) {
            return this.context.getString(R.string.document);
        }
        return this.context.getString(R.string.audios);
    }
}
