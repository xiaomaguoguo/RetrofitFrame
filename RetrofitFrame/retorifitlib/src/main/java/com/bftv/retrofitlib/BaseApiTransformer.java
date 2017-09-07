package com.bftv.retrofitlib;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by MaZhihua on 2017/9/7.
 * 转换基础类，以后有需求可以在这里统一对所有接口请求做统一处理
 */
public class BaseApiTransformer {

    /***这两个函数不要修改****/
    protected static <T> Observable.Transformer<T, T> applySchedulers() {
        return (Observable.Transformer<T, T>) transformer;
    }

    /***这两个函数不要修改****/
    protected static Observable.Transformer transformer = new Observable.Transformer(){
        @Override
        public Object call(Object observable) {
            return ((Observable)observable).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        }
    };


}
