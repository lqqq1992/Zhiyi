package com.example.lqqq.careui.contract;

import android.view.View;

/**
 * 医生列表UI 需要实现的功能
 * Created by LQQQ1 on 2018/2/25.
 */

public interface DoctorsInterface {
    void initView(View view);
    void initData();
    void addDoctor();
    void searchDoctor();
}
