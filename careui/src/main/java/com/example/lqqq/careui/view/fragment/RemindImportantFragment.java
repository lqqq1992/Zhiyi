package com.example.lqqq.careui.view.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lqqq.careui.R;
import com.example.lqqq.careui.contract.RemindImportantInterface;
import com.example.lqqq.careui.utils.SpacesItemDecoration;
import com.example.lqqq.careui.view.adapter.RefreshLoadAdapter;
import com.example.lqqq.careui.view.adapter.RemindAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 重要消息界面
 */
public class RemindImportantFragment extends Fragment implements RemindImportantInterface,SwipeRefreshLayout.OnRefreshListener{

    private SwipeRefreshLayout remindSwipe;
    private RecyclerView remindRecycler;
    private RemindAdapter remindAdapter;
    private List<String> remindNames;
    public static RemindImportantFragment newInstance() {
        RemindImportantFragment fragment = new RemindImportantFragment();
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
        return inflater.inflate(R.layout.fragment_remind_important, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initView(view);
    }

    @Override
    public void initView(View view) {
        remindSwipe = view.findViewById(R.id.remind_swipe);
        remindRecycler = view.findViewById(R.id.remind_recycler);
        remindAdapter = new RemindAdapter(remindNames);
        remindRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        remindRecycler.setAdapter(new RefreshLoadAdapter(remindAdapter));
        remindRecycler.addItemDecoration(new SpacesItemDecoration());
        remindSwipe.setOnRefreshListener(this);
    }

    @Override
    public void initData() {
        remindNames = new ArrayList<>();
        for (int i=0;i<20;i++){
            remindNames.add("提醒"+i);
        }
    }

    @Override
    public void onRefresh() {
        remindSwipe.setRefreshing(false);
    }
}
