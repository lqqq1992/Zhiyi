package com.example.lqqq.careui.view.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 多种提醒page切换适配
 * Created by LQQQ1 on 2018/2/27.
 */

public class RemindPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    private String[] tabs = new String[]{"重要","今天","未来7天","未来15天"};
    public RemindPagerAdapter(FragmentManager manager, List<Fragment> fragments){
        super(manager);
        this.fragments = fragments;
    }
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }
}
