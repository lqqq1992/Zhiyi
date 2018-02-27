package com.example.lqqq.careui.contract;


import android.support.v4.app.Fragment;
import android.widget.TextView;

/**
 * 主界面需要实现的功能
 * Created by LQQQ1 on 2018/2/25.
 */

public interface MainInterface {
    void initView();
    void setFragment(Fragment fragment);
    void changeFragment(Fragment fragment);
    void setUI(TextView textView);
}
