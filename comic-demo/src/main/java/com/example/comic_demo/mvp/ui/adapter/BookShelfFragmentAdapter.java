package com.example.comic_demo.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * author: 小川
 * Date: 2019/9/12
 * Description:
 */
public class BookShelfFragmentAdapter extends FragmentStatePagerAdapter {

    private final List<Fragment> fragmentList;
    private final List<String> title;

    public BookShelfFragmentAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> title) {
        super(fm);
        this.fragmentList = fragmentList;
        this.title = title;
    }

    @Override
    public Fragment getItem(int i) {
        return fragmentList.get(i);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return title.get(position);
    }
}
