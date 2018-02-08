package com.example.lqqq.push;

import android.app.Application;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by LQQQ1 on 2018/2/5.
 */

public class PushApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);//开启Log调试
        JPushInterface.init(this);//注册APP
        JPushInterface.requestPermission(this);//权限
    }
}
