package com.knothing.retrofit;

import android.app.Activity;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by MaZhihua on 2017/9/5.
 */

public class MainActivity extends Activity {

    public static final String TAG = MainActivity.class.getSimpleName();

    private Button button = null;
    private Button button1 = null;

    private CompositeSubscription subscriptions = new CompositeSubscription();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        button = (Button) findViewById(R.id.button);
        button1 = (Button) findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Observable.OnSubscribe<T> onSubscribe, Subscriber<T> subscriber

                //方式一：直接传递参数
                Observable<UserInfo> userInfoObservable = BFRequest.getApiService().login("BFTV","1234567")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());

                subscriptions.add(userInfoObservable.subscribe());





//                userInfoObservable.subscribe(new Action1<UserInfo>() {
//                    @Override
//                    public void call(UserInfo userInfo) {
//
//                    }
//                });

//                Subscription subscription = userInfoObservable.subscribe(new Subscriber<UserInfo>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(UserInfo userInfo) {
//
//                    }
//                });



                // do you logic

                //方式二：参数放到Map中
                HashMap<String,String> params = new HashMap<>();
                params.put("userId","user123");
                params.put("pwd","123456");
                Observable<UserInfo> userDetail = BFRequest.getApiService().userDetail(params)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
                // do you logic

            }
        });



        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observable<MovieEntity> movieEntitys =  BFRequest.getApiService().getTopMovie(0,10);
                movieEntitys.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<MovieEntity>() {
                    @Override
                    public void call(MovieEntity movieEntity) {
                        Log.d(TAG,"请求结果 = " + movieEntity.toString());
                    }
                });
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(subscriptions != null && subscriptions.isUnsubscribed()){
            subscriptions.clear();
            subscriptions = null;
        }
    }
}
