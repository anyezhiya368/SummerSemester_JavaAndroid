package com.example.newsapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class FragmentAdapter extends FragmentPagerAdapter {

    List<NewsItemFragment> fragmentList;
    List<String> newstypeList;

    public FragmentAdapter(@NonNull FragmentManager fm, List<NewsItemFragment> fragmentListin, List<String> newstypeListin) {
        super(fm);
        this.fragmentList = fragmentListin;
        this.newstypeList = newstypeListin;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return newstypeList.get(position);
    }
}
