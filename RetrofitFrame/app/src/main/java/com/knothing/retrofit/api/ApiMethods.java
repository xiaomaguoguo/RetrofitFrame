package com.knothing.retrofit.api;

/**
 * Created by MaZhihua on 2017/9/5.
 * 接口请求的具体Method,统一写在此处
 */
public interface ApiMethods {


    /**登陆**/
    String LOGIN = "user/login";

    /**用户详情**/
    String USER_DETAIL = "user/detail";

    /**用户所在的公司**/
    String COMPANY = "com.bftv.userCompany";

    String TOP250 = "https://api.douban.com/v2/movie/top250";

    String TOP2502 = "top250";

}
