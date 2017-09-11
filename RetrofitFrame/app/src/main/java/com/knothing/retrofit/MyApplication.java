package com.knothing.retrofit;

import android.app.Application;

import com.bftv.retrofitlib.CommonParams;
import com.knothing.retrofit.api.ApiRequest;

/**
 * Created by MaZhihua on 2017/9/5.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化网络请求
        ApiRequest.getInstance().init(getApplicationContext(),true);
//        ApiRequest.getInstance().init(getApplicationContext(),CommonParams.TEST_BASEURL,true);
    }
}
