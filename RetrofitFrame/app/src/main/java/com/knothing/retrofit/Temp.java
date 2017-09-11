package com.knothing.retrofit;


import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by MaZhihua on 2017/9/5.
 */
public class Temp {

    public static final String HASH = "KNOTHING";

    public static void main(String[] args){

        System.out.println("HASH Values = " + HASH.hashCode());

        Observable<String> test = Observable.just("ABC","DEF");
        test.subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
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

    }

}
