package com.example.lqqq.careui.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.lqqq.careui.R;
import com.example.lqqq.careui.contract.DoctorsInterface;
import com.example.lqqq.careui.presenter.DoctorsPresenter;
import com.example.lqqq.careui.utils.LoadMoreHelper;
import com.example.lqqq.careui.view.adapter.DoctorsAdapter;
import com.example.lqqq.careui.view.adapter.RefreshLoadAdapter;

import java.util.List;

/**
 * 医生列表界面
 */
public class DoctorsFragment extends Fragment implements DoctorsInterface, SwipeRefreshLayout.OnRefreshListener, LoadMoreHelper.LoadMoreListener, AppBarLayout.OnOffsetChangedListener {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private DoctorsAdapter doctorsAdapter;
    private RefreshLoadAdapter refreshLoadAdapter;
    private LoadMoreHelper loadMoreHelper;

    public void setNames(List<String> names) {
        this.names = names;
    }

    private List<String> names;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private Menu menu;
    private DoctorsPresenter presenter;

    public static DoctorsFragment newInstance() {
        DoctorsFragment fragment = new DoctorsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doctors, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
//        //换成下面这句就OK了
//        toolbar.inflateMenu(R.menu.doctors_menu);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        this.menu = menu;
        appBarLayout.addOnOffsetChangedListener(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.doctors_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void initView(View view) {
        presenter = new DoctorsPresenter(this);
        swipeRefreshLayout = view.findViewById(R.id.swipe);
        recyclerView = view.findViewById(R.id.doctors_recycler);
        appBarLayout = view.findViewById(R.id.appbar);
        toolbar = view.findViewById(R.id.toolbar);

        initData();
        doctorsAdapter = new DoctorsAdapter(names);
        refreshLoadAdapter = new RefreshLoadAdapter(doctorsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(refreshLoadAdapter);

        loadMoreHelper = new LoadMoreHelper(recyclerView, refreshLoadAdapter);
        loadMoreHelper.setLoadMoreListener(this);

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setEnabled(false);
    }

    /**
     * 数据装载
     */
    @Override
    public void initData() {
        presenter.setData();
    }

    /**
     * 添加医生
     */
    @Override
    public void addDoctor() {

    }

    /**
     * 查找医生
     */
    @Override
    public void searchDoctor() {

    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        Toast.makeText(getActivity(), "刷新成功", Toast.LENGTH_LONG).show();
        swipeRefreshLayout.setRefreshing(false);
    }

    /**
     * 上拉加载
     */
    @Override
    public void onLoad() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    for (int i = 0; i < 20; i++) {
                        names.add("添加名字" + i);
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            swipeRefreshLayout.setEnabled(true);//启用下拉刷新
                            loadMoreHelper.setLoadMoreFinish();//通知加载完成
                            refreshLoadAdapter.notifyDataSetChanged();//刷新数据源
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * toolbar 偏移量监测
     * @param appBarLayout
     * @param verticalOffset
     */
    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (verticalOffset >= 0) {//当toolbar为展开状态时，设置可以下拉刷新
            swipeRefreshLayout.setEnabled(true);
        } else {
            swipeRefreshLayout.setEnabled(false);
        }
        if (verticalOffset == 0) {//当toolbar完全展开时，设置菜单不可见
            menu.getItem(0).setVisible(false);
            menu.getItem(0).setEnabled(false);
            menu.getItem(1).setVisible(false);
            menu.getItem(1).setEnabled(false);
        }
        //当toolbar完全收缩时，设置菜单可见
        if (Math.abs(verticalOffset) == appBarLayout.getTotalScrollRange()) {
            menu.getItem(0).setVisible(true);
            menu.getItem(0).setEnabled(true);
            menu.getItem(1).setVisible(true);
            menu.getItem(1).setEnabled(true);
        }
    }
}
