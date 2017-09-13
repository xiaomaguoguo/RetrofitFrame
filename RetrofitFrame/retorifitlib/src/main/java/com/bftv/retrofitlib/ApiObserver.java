package com.bftv.retrofitlib;

import android.animation.ObjectAnimator;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by MaZhihua on 2017/9/11.
 */
public abstract class ApiObserver<T> implements Observer<T> {



    @Override
    public void onSubscribe(@NonNull Disposable d) {
        onStart(d);
    }

    @Override
    public void onNext(@NonNull T t) {
        onSuccess(t);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        onFailed(e);
    }

    @Override
    public void onComplete() {
        onCompleted();
    }

    public void onStart(Disposable d){

    }


    public void onFailed(Throwable e){

    }

    public void onCompleted(){

    }

    protected abstract void onSuccess(@NonNull T t);
}
