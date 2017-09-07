package com.knothing.retrofit.api;

import com.bftv.retrofitlib.BaseApiTransformer;
import com.knothing.retrofit.entitys.MovieEntity;
import com.knothing.retrofit.entitys.UserInfo;

import rx.Observable;

/**
 * Created by MaZhihua on 2017/9/7.
 * 所有接口请求统一写在这里，做统一的转换，以后可以在这里统一对所有请求做进一步的操作
 */
public class ApiTransformer extends BaseApiTransformer {

    private static ApiTransformer mInstance = null;

    private ApiTransformer(){}

    public static ApiTransformer getInstance(){
        if(mInstance == null){
            return new ApiTransformer();
        }
        return mInstance;
    }


    //请求一
    public Observable<MovieEntity> getTopMovie(){
        return ApiRequest.getApiService().getTopMovie(0,10).compose(this.<MovieEntity>applySchedulers());
    }


    //请求二
    public Observable<UserInfo> getMethod2(String username, String possword){
        return ApiRequest.getApiService().postLogin(username,possword).compose(this.<UserInfo>applySchedulers());
    }

}
