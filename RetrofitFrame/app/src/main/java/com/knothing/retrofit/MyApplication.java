package com.knothing.retrofit;

import android.app.Application;

/**
 * Created by MaZhihua on 2017/9/5.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化网络请求
        BFRequest.getInstance().init(getApplicationContext(),true);
    }
}
