package com.knothing.retrofit;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.knothing.retrofit.api.ApiRequest;
import com.knothing.retrofit.api.ApiTransformer;
import com.knothing.retrofit.entitys.MovieEntity;
import com.knothing.retrofit.entitys.UserInfo;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by MaZhihua on 2017/9/5.
 */

public class MainActivity extends Activity implements View.OnClickListener {

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
        button.setOnClickListener(this);
        button1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.button:
                ctrlButton();
                break;


            case R.id.button1:
                ctrlButton1();
                break;

        }
    }

    private void ctrlButton1() {
        Observable<MovieEntity> movieEntitys =  ApiTransformer.getInstance().getTopMovie();
        movieEntitys/*.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())*/.subscribe(new Action1<MovieEntity>() {
            @Override
            public void call(MovieEntity movieEntity) {
                Log.d(TAG,"请求结果 = " + movieEntity.toString());
            }
        });

        /*movieEntitys.subscribe(new Observer<MovieEntity>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(MovieEntity movieEntity) {

            }
        });*/

    }

    private void ctrlButton() {



//                Observable.OnSubscribe<T> onSubscribe, Subscriber<T> subscriber

        //方式一：直接传递参数
//        Observable<UserInfo> userInfoObservable1 = ApiRequest.getApiService().postLogin("BFTV","1234567").compose(this.<UserInfo>applySchedulers());
        Observable<UserInfo> userInfoObservable = ApiRequest.getApiService().postLogin("BFTV","1234567")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());


        Subscription subscription = userInfoObservable.subscribe();
        if(!subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }


        subscriptions.add(userInfoObservable.subscribe(new Observer<UserInfo>() {
            @Override
            public void onCompleted() {
                Log.i(TAG,"onCompleted()");
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG,"onError = " + e.getMessage());
            }

            @Override
            public void onNext(UserInfo userInfo) {
                Log.d(TAG,"onNext()");
            }
        }));





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
//                HashMap<String,String> params = new HashMap<>();
//                params.put("userId","user123");
//                params.put("pwd","123456");
//                Observable<UserInfo> userDetail = ApiRequest.getApiService().userDetail(params)
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread());
        // do you logic

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
