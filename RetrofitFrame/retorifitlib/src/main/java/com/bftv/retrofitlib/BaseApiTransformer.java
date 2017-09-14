package com.bftv.retrofitlib;

import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by MaZhihua on 2017/9/7.
 * 线程转换基础类，以后有需求可以在这里统一对所有接口请求做统一处理
 */
public class BaseApiTransformer {

    /**
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> convertObservable() {
        return (ObservableTransformer<T,T>) transformer;
    };

    protected static ObservableTransformer transformer = new ObservableTransformer(){
        @Override
        public ObservableSource apply(io.reactivex.Observable upstream) {
            return upstream.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    };


}
