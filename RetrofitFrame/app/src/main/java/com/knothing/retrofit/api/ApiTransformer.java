package com.knothing.retrofit.api;

import com.bftv.retrofitlib.BaseApiTransformer;
import com.bftv.retrofitlib.CommonParams;
import com.knothing.retrofit.entitys.MovieEntity;
import com.knothing.retrofit.entitys.UserInfo;

import java.util.HashMap;

import io.reactivex.Observable;

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


    //GET请求
    public Observable<MovieEntity> getTopMovie(){
        return ApiRequest.getApiService().getTopMovie(0,10).compose(this.<MovieEntity>applySchedulers2());
    }


    //POST请求,直接传递参数
    public Observable<UserInfo> postLogin(String username, String possword){
        return ApiRequest.getApiService().postLogin("http://www.baidu.com",username,possword).compose(this.<UserInfo>applySchedulers2());
    }

    //POST请求,直接传递参数
    public Observable<UserInfo> userDetail(HashMap<String,String> params){
        return ApiRequest.getApiService().userDetail(params).compose(this.<UserInfo>applySchedulers2());
    }

}
