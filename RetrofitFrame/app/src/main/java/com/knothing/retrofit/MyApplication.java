package com.knothing.retrofit;

import android.app.Application;

import com.bftv.retrofitlib.CommonParams;

/**
 * Created by MaZhihua on 2017/9/5.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化网络请求
//        BFRequest.getInstance().init(getApplicationContext(),true);
        BFRequest.getInstance().init(getApplicationContext(),CommonParams.TEST_BASEURL,true);
    }
}
