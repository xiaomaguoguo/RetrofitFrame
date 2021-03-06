package com.knothing.retrofit.api;

import com.knothing.retrofit.entitys.MovieEntity;
import com.knothing.retrofit.entitys.UserInfo;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by MaZhihua on 2017/9/5.
 * 接口服务类，统一配置在这里
 */
public interface ApiService {

    @POST(ApiMethods.LOGIN)
    Observable<UserInfo> login(@Query("username") String username, @Query("password") String password);

    @FormUrlEncoded
    @POST
    Observable<UserInfo> postLogin(@Url String url,@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST(ApiMethods.USER_DETAIL)
    Observable<UserInfo> userDetail(@FieldMap HashMap<String,String> params);

    @GET(ApiMethods.TOP250)
    Observable<MovieEntity> getTopMovie(@Query("start") int start, @Query("count") int count);

}
