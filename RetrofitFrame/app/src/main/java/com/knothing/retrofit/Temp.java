package com.knothing.retrofit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by MaZhihua on 2017/9/5.
 */

public class Temp {

    public static final String HASH = "KNOTHING";

    public static void main(String[] args){

        System.out.println("HASH Values = " + HASH.hashCode());

        Observable<String> test = Observable.just("ABC","DEF");
        test.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println("s = " + s);
            }
        });

        /*test.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });*/

        test.map(new Func1<String, String>() {
            @Override
            public String call(String s) {
                return s;
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println("s2 = " + s);
            }
        });






    }

    <T> Observable.Transformer<T, T> applySchedulers() {
        return (Observable.Transformer<T, T>) transformer;
    }

    Observable.Transformer transformer = new Observable.Transformer(){

        @Override
        public Object call(Object observable) {
            return ((Observable)observable).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        }
    };

}
