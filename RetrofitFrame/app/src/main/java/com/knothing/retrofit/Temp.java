package com.knothing.retrofit;


import android.util.AndroidException;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by MaZhihua on 2017/9/5.
 */
public class Temp {

    public static final String HASH = "KNOTHING";

    public static void main(String[] args){

        System.out.println("HASH Values = " + HASH.hashCode());

        Observable<String> test = Observable.just("ABC","DEF");
        test.doOnNext(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                System.out.println("AAAA = " + s);
//                Log.e("MainActivity", "doOnNext:" + Thread.currentThread().getName());
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                System.out.println("s = " + s);
            }
        }/*, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                System.out.println("throwable = " + throwable.getMessage());
            }
        }*/);

        /*test.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });*/

        test.map(new Function<String, String>() {
            @Override
            public String apply(@NonNull String s) throws Exception {
                return s;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                System.out.println("s2 = " + s);
            }
        });

//        doOnNext();

    }

    private static void doOnNext(){
        Observable.just("A","B","C").map(new Function<String, String>() {
            @Override
            public String apply(@NonNull String s) throws Exception {
                return s;
            }
        }).doOnNext(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                Log.e("MainActivity", "doOnNext:" + s);
                Log.e("MainActivity", "doOnNext:" + Thread.currentThread().getName());
            }
        })
//        .observeOn(Schedulers.io())
//        .subscribeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.e("MainActivity", "onSubscribe");
            }

            @Override
            public void onNext(@NonNull String s) {
                Log.e("MainActivity", "print = " + s);
                Log.e("MainActivity", "subscribe():" + Thread.currentThread().getName());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e("MainActivity", "onError");
            }

            @Override
            public void onComplete() {
                Log.e("MainActivity", "onCompleted");
            }
        })
        ;
    }

}
