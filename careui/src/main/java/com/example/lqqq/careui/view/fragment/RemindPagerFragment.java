package com.example.lqqq.careui.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lqqq.careui.R;
import com.example.lqqq.careui.contract.RemindInterface;
import com.example.lqqq.careui.view.adapter.RemindPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 提醒消息主界面
 */
public class RemindPagerFragment extends Fragment implements RemindInterface{

    private List<Fragment> fragments;
    private TabLayout remindTab;
    private ViewPager remindViewPager;
    private RemindPagerAdapter remindPagerAdapter;

    public static RemindPagerFragment newInstance() {
        RemindPagerFragment fragment = new RemindPagerFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_remind, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initView(view);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void initView(View view) {
        remindTab = view.findViewById(R.id.remind_tab);
        remindViewPager = view.findViewById(R.id.remind_viewpager);

        remindPagerAdapter = new RemindPagerAdapter(getChildFragmentManager(),fragments);
        remindViewPager.setAdapter(remindPagerAdapter);
        remindTab.setupWithViewPager(remindViewPager);
        remindTab.setTabMode(TabLayout.MODE_FIXED);
    }

    @Override
    public void initData() {
         fragments = new ArrayList<>();
        for (int i = 0;i<4;i++){
            fragments.add(RemindImportantFragment.newInstance());
        }
    }
}
