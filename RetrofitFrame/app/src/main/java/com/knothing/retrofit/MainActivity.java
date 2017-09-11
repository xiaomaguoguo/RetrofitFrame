package com.knothing.retrofit;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.bftv.retrofitlib.ApiObserver;
import com.knothing.retrofit.api.ApiRequest;
import com.knothing.retrofit.api.ApiTransformer;
import com.knothing.retrofit.entitys.MovieEntity;
import com.knothing.retrofit.entitys.UserInfo;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by MaZhihua on 2017/9/5.
 */

public class MainActivity extends Activity implements View.OnClickListener {

    public static final String TAG = MainActivity.class.getSimpleName();

    private Button button = null;
    private Button button1 = null;
    private Button button2 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        button = (Button) findViewById(R.id.button);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.button: // get请求
                ctrlButton();
                break;


            case R.id.button1: // post请求，依次传递参数
                ctrlButton1();
                break;

            case R.id.button2: // post请求，直接传递map作为参数
                ctrlButton2();
                break;

        }
    }

    /**
     * GET请求
     */
    private void ctrlButton1() {
        Observable<MovieEntity> movieEntitys =  ApiTransformer.getInstance().getTopMovie();
        movieEntitys.subscribe(new ApiObserver<MovieEntity>() {
            @Override
            protected void onSuccess(@NonNull MovieEntity movieEntity) {
                Log.d(TAG, "请求结果 = " + movieEntity.toString());
            }

            @Override
            public void onFailed(Throwable e) {
                Log.e(TAG, "请求结果 = " + e.getMessage());
            }
        });
//        movieEntitys.subscribe(new Consumer<MovieEntity>() {
//            @Override
//            public void accept(MovieEntity movieEntity) throws Exception {
//                Log.d(TAG, "请求结果 = " + movieEntity.toString());
//            }
//        }, new Consumer<Throwable>() {
//            @Override
//            public void accept(@NonNull Throwable throwable) throws Exception {
//                Log.e(TAG, "请求结果 = " + throwable.getMessage());
//            }
//        });
    }

    /**
     * POST请求
     */
    private void ctrlButton() {
        Observable<UserInfo> userInfoObservable = ApiTransformer.getInstance().postLogin("BFTV","1234567");
        userInfoObservable.subscribe(new ApiObserver<UserInfo>() {
            @Override
            protected void onSuccess(@NonNull UserInfo userInfo) {
                Log.i(TAG,"请求结果 userInfo = " + userInfo);
            }

            @Override
            public void onFailed(Throwable e) {
                Log.e(TAG,"请求结果 Throwable = " + e.getMessage());
            }
        });




    }

    //方式二：参数放到Map中
    private void ctrlButton2(){
        HashMap<String,String> params = new HashMap<>();
        params.put("userId","user123");
        params.put("pwd","123456");
        Observable<UserInfo> userDetail = ApiTransformer.getInstance().userDetail(params);
        userDetail.subscribe(new Consumer<UserInfo>() {
            @Override
            public void accept(@NonNull UserInfo userInfo) throws Exception {
                // do you logic
            }
        });
    }

}
