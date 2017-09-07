package com.knothing.retrofit.api;

import android.content.Context;

import com.bftv.retrofitlib.BFRetrofit;


/**
 * Created by MaZhihua on 2017/9/5.
 * 所有请求管理类
 */
public class ApiRequest {

    private static ApiService mApiService;

    private static ApiRequest sApiRequest;

    /**这个是全局对象，不会存在Context内存泄漏**/
    private static BFRetrofit mRetrofit;

    /**是否调用过init()初始化**/
    private static boolean isInit = false;

    private ApiRequest() {}

    public void init(Context context,boolean isDebug){
        init(context,null,isDebug);
    }

    public void init(Context context,String baseUrl, boolean isDebug) {
        if (mRetrofit == null) {
            mRetrofit = new BFRetrofit(context).init(baseUrl, isDebug);
        }
        mApiService = mRetrofit.getRetrofit().create(ApiService.class);
        isInit = true;
    }

    public static ApiRequest getInstance() {

        if(sApiRequest == null){
            sApiRequest = new ApiRequest();
        }
        return sApiRequest;
    }

    public static ApiService getApiService() {
        if(!isInit){
            throw new IllegalStateException("请先在Application里面调用BFRequest.init()初始化网络请求框架! ! ! ");
        }
        return mApiService;
    }

}
