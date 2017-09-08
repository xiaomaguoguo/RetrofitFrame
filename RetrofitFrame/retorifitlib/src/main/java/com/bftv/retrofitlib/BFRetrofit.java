package com.bftv.retrofitlib;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by MaZhihua on 2017/9/5.
 * 接口请求底层配置
 */
public class BFRetrofit {

    public static final String TAG = BFRetrofit.class.getSimpleName();

    private Retrofit mRetrofit;

    private OkHttpClient mOkHttpClient;

    private Context mContext;

    private Gson gson = new GsonBuilder()
            .enableComplexMapKeySerialization()//支持Map的key为复杂对象的形式
            .serializeNulls() //智能null
            .setPrettyPrinting()// 调教格式
            .disableHtmlEscaping() //默认是GSON把HTML 转义的
            .create();

    public BFRetrofit(Context context){
        this.mContext = context;
    }

    public BFRetrofit init(String baseUrl, boolean isDebug) {
        if (getRetrofit() != null) {
            throw new IllegalStateException("不要重复调用init（）函数! ! ! ");
        }

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(isDebug ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        interceptor.setLevel(isDebug ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        HttpLoggingInterceptor headerInterceptor = new HttpLoggingInterceptor();
        headerInterceptor.setLevel(isDebug ? HttpLoggingInterceptor.Level.HEADERS : HttpLoggingInterceptor.Level.NONE);
        mOkHttpClient = new OkHttpClient.Builder()
//                .addNetworkInterceptor(headerInterceptor)
                .addInterceptor(interceptor)
                .addInterceptor(new ParamsInterceptor(mContext,isDebug)) // 通用参数在这里注入
                .retryOnConnectionFailure(true)
                .connectTimeout(15, TimeUnit.SECONDS)
                .addNetworkInterceptor(interceptor)
                .build();

        mRetrofit = new Retrofit.Builder()
                .client(mOkHttpClient)
                .baseUrl(!TextUtils.isEmpty(baseUrl) ? baseUrl : (isDebug ? CommonParams.API_REQUEST_BASE_URL_TEST : CommonParams.API_REQUEST_BASE_URL_FORMAL))
                .addConverterFactory(GsonConverterFactory.create(gson)) //gson convert
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) //support rxjava
                .build();
        return this;

    }


    public Retrofit getRetrofit() {
        return mRetrofit;
    }

}
